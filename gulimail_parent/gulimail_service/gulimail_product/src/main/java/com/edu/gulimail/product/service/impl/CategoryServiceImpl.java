package com.edu.gulimail.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.edu.gulimail.product.vo.CategoryLevelTwo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.common.utils.PageUtils;
import com.edu.common.utils.Query;

import com.edu.gulimail.product.dao.CategoryDao;
import com.edu.gulimail.product.entity.CategoryEntity;
import com.edu.gulimail.product.service.CategoryService;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.annotation.Resources;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List listWithTree() {
        // 查出所有的分类 并且形成三级树形结构：
        List<CategoryEntity> categoryEntityList = baseMapper.selectList(null);
        List<CategoryEntity> level1 = categoryEntityList.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == 0).map(categoryEntity -> {
            categoryEntity.setChildren(getChildren(categoryEntity, categoryEntityList));
            return categoryEntity;
        }).sorted((categoryEntity1, categoryEntity2) ->
                (categoryEntity1.getSort() == null ? 0 : categoryEntity1.getSort()) - (categoryEntity1.getSort() == null ? 0 : categoryEntity1.getSort())
        ).collect(Collectors.toList());
        return level1;
    }

    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> list = all.stream().filter(categoryEntity -> Objects.equals(root.getCatId(), categoryEntity.getParentCid()))
                .map(categoryEntity -> {
                    categoryEntity.setChildren(getChildren(categoryEntity, all));
                    return categoryEntity;
                }).sorted((categoryEntity1, categoryEntity2) ->
                        (categoryEntity1.getSort() == null ? 0 : categoryEntity1.getSort()) - (categoryEntity1.getSort() == null ? 0 : categoryEntity1.getSort())
                ).collect(Collectors.toList());
        return list;
    }

    // 使用遍历的方式进行获取
    public List<CategoryEntity> getListTree() {
        // 先将所有的查出来：
        List<CategoryEntity> categoryEntityList = baseMapper.selectList(null);
        List<CategoryEntity> treeList = new ArrayList<>();
        // 进行一级分类的查找
        for (CategoryEntity categoryEntity : categoryEntityList) {
            if (categoryEntity.getCatLevel() == 1) {
                // 进行二级分类组装
                List<CategoryEntity> childrenList = getChildrenList(categoryEntity, categoryEntityList);
                if (!CollectionUtils.isEmpty(childrenList)) {
                    categoryEntity.setChildren(childrenList);
                    for (CategoryEntity entity : childrenList) {
                        List<CategoryEntity> childrenList1 = getChildrenList(entity, categoryEntityList);
                        entity.setChildren(childrenList1);
                    }
                }

                treeList.add(categoryEntity);

            }
        }

        return treeList;

    }

    @Override
    public void removeMenuByIds(List<Long> asList) {

        // TODO 批量的删除菜单 是否是被其他的地方进行引用
        int i = baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCategoryPath(Long catelogId) {
        // 使用递归进行父节点的查找：
        List<Long> path = new ArrayList<>();
        findPath(catelogId, path);
        Collections.reverse(path);
        return path.toArray(new Long[path.size()]);
    }
    @Cacheable(value = {"catalog"}, key = "#root.methodName")
    @Override
    public List<CategoryEntity> getLevelOne(int level) {
        QueryWrapper<CategoryEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("cat_level", level);
        List<CategoryEntity> categoryEntityList = baseMapper.selectList(wrapper);
        return categoryEntityList;

    }

    @Override
    public Map<String, List<CategoryLevelTwo>> getCataLogJson() {
        // 需要使用 锁进行保证 防止 缓存击穿  1就是使用syn锁this 由于是单例的 可以 但是分布式的情况下不行
        // 使用分布式锁  而本地锁 无论是 synchronized 或者又是 reentrantLock 都是 不能保证只有一个线程进入
        String catalogJson = stringRedisTemplate.opsForValue().get("catalogJson");
        if (catalogJson == null) {
            Map<String, List<CategoryLevelTwo>> cataLogJsonFromDb = getCataLogJsonFromDb();
            return cataLogJsonFromDb;
        }
        // 要是复杂类型的json字符串 需要在parseObject 方法 中传入一个 typeReference对象
        // 这个是一个Protected类 使用的是内部类的形式进行初始化
        // TODO 会出现对外内存溢出 springboot 使用的是letture 使用的是netty进行的是网络通信
        // netty 要是没有指定对外内存就是使用 和 -Xmx一样的大小的内存作为对外内存 outOfDirectMemoryError 就会出现
        Map<String, List<CategoryLevelTwo>> stringListMap = JSON.parseObject(catalogJson,
                new TypeReference<Map<String, List<CategoryLevelTwo>>>() {
                });
        return stringListMap;
    }


    public synchronized Map<String, List<CategoryLevelTwo>> getCataLogJsonFromDb() {
        // 使用的是另一种方式 不是和 老师一样的方式进行的获取数据
        // 正向代理 就是进行客户端的隐藏  反向代理 隐藏的是 服务端 nginx 就是进行反向代理的时候就像是网关
        // 这样的形式就是 判断是否是 有缓存 有缓存 走缓存
        // 具体的流程就是 三步：
        // 第一步 判断是否是有缓存 有缓存 直接拿出缓存
        // 第二步：没有缓存 进行查询 封装数据
        // 第三步： 将查出来的数据 放到redis汇总
        return getStringListMapFromDb();
    }

    public Map<String, List<CategoryLevelTwo>> getCatalogWithRedisson() {
        // 需要注意的就是锁名称就是代表的  锁的粒度 越小越好
        RLock lock = redissonClient.getLock("catalog-lock");

        lock.lock();

        // 要是加锁成功 进行的是数据的保存 和 获取
        Map<String, List<CategoryLevelTwo>> stringListMapFromDb = null;
        try {
            stringListMapFromDb = getStringListMapFromDb();
        } finally {
            lock.unlock();
        }
        return stringListMapFromDb;


    }

    public Map<String, List<CategoryLevelTwo>> getCatalogWithRedis() {
        // 使用分布式锁 Redis实现
        String s = UUID.randomUUID().toString();
        Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent("lock", s, 300, TimeUnit.SECONDS);

        if (aBoolean) {
           /* // 设置过期时间
            stringRedisTemplate.expire("lock", 100, TimeUnit.MILLISECONDS);*/
            // 要是加锁成功 进行的是数据的保存 和 获取
            Map<String, List<CategoryLevelTwo>> stringListMapFromDb = null;
            try {
                stringListMapFromDb = getStringListMapFromDb();
            } finally {
                // 获取到数据之后 将锁释放：直接删除锁可能删除的是别人的锁：
                //stringRedisTemplate.delete("lock");
                // 判断是否是自己的锁：
                String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                        "    return redis.call(\"del\",KEYS[1])\n" +
                        "else\n" +
                        "    return 0\n" +
                        "end";
                stringRedisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class),
                        Arrays.asList("lock"),
                        s);
            }

            /*String lock = stringRedisTemplate.opsForValue().get("lock");
            if (s.equals(lock)){
                stringRedisTemplate.delete("lock");
            }*/
            return stringListMapFromDb;
        }
        // 要是没有获取到锁：使用的自选的方式 一段时间之后重试 就是重新的 进行方法的调用 但是需要一段时间之后 在进行重试
        try {
            // 防止多次的重试 导致的 栈溢出
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getCatalogWithRedis();
    }

    private Map<String, List<CategoryLevelTwo>> getStringListMapFromDb() {
        String catalogJson = stringRedisTemplate.opsForValue().get("catalogJson");
        if (catalogJson != null) {
            Map<String, List<CategoryLevelTwo>> stringListMap = JSON.parseObject(catalogJson,
                    new TypeReference<Map<String, List<CategoryLevelTwo>>>() {
                    });
            return stringListMap;
        }

        // 没有缓存 封装数据
        List<CategoryEntity> listTree = getListTree();
        Map<String, List<CategoryLevelTwo>> listMap = listTree.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            List<CategoryEntity> children = v.getChildren();
            List<CategoryLevelTwo> collect1 = null;
            if (!CollectionUtils.isEmpty(children)) {
                collect1 = children.stream().map(categoryEntity -> {
                    CategoryLevelTwo categoryLevelTwo = new CategoryLevelTwo();
                    categoryLevelTwo.setCatalog1Id(categoryEntity.getParentCid().toString());
                    categoryLevelTwo.setId(categoryEntity.getCatId().toString());
                    categoryLevelTwo.setName(categoryEntity.getName());
                    List<CategoryEntity> children1 = categoryEntity.getChildren();
                    if (!CollectionUtils.isEmpty(children1)) {
                        List<CategoryLevelTwo.categoryLevelThree> collect = children1.stream().map(categoryEntity1 -> {
                            CategoryLevelTwo.categoryLevelThree categoryLevelThree = new CategoryLevelTwo.categoryLevelThree();
                            categoryLevelThree.setCatalog2Id(categoryEntity1.getParentCid().toString());
                            categoryLevelThree.setId(categoryEntity1.getCatId().toString());
                            categoryLevelThree.setName(categoryEntity1.getName());
                            return categoryLevelThree;
                        }).collect(Collectors.toList());
                        categoryLevelTwo.setCatalog3List(collect);
                    }

                    return categoryLevelTwo;
                }).collect(Collectors.toList());
            }
            return collect1;
        }));

        // 将查到的数据放入到redis中 需要将对象转成json放到数据库中
        String jsonString = JSON.toJSONString(listMap);
        stringRedisTemplate.opsForValue().set("catlogJson", jsonString);

        return listMap;
    }

    private List<Long> findPath(Long catelogId, List<Long> paths) {
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        // 要是父节点不是第一层
        Long parentCid = byId.getParentCid();
        while (parentCid != 0) {
            paths.add(parentCid);
            CategoryEntity byId1 = this.getById(parentCid);
            parentCid = byId1.getParentCid();
        }

       /* if (parentCid != 0){
            findPath(parentCid, paths);
        }*/
        return paths;
    }

    private List<CategoryEntity> getChildrenList(CategoryEntity categoryEntity, List<CategoryEntity> treeList) {
        List<CategoryEntity> list = new ArrayList<>();
        for (CategoryEntity entity : treeList) {
            if (Objects.equals(categoryEntity.getCatId(), entity.getParentCid())) {
                list.add(entity);
            }
        }
        return list;
    }

    private List<CategoryEntity> getList() {
        // 先将所有的查出来：
        List<CategoryEntity> categoryEntityList = baseMapper.selectList(null);
        List<CategoryEntity> entityList = categoryEntityList.stream()
                .filter(categoryEntity -> categoryEntity.getCatLevel() == 1)
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))
                ).map(categoryEntity -> {
                    categoryEntity.setChildren(getChildren(categoryEntity, categoryEntityList));
                    return categoryEntity;
                }).collect(Collectors.toList());
        return entityList;
    }

}