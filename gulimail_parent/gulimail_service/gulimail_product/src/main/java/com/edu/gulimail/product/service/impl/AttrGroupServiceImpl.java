package com.edu.gulimail.product.service.impl;

import com.edu.gulimail.product.dao.AttrAttrgroupRelationDao;
import com.edu.gulimail.product.dao.AttrDao;
import com.edu.gulimail.product.entity.AttrAttrgroupRelationEntity;
import com.edu.gulimail.product.entity.AttrEntity;
import com.edu.gulimail.product.vo.AttrGroupVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.common.utils.PageUtils;
import com.edu.common.utils.Query;

import com.edu.gulimail.product.dao.AttrGroupDao;
import com.edu.gulimail.product.entity.AttrGroupEntity;
import com.edu.gulimail.product.service.AttrGroupService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Resource
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Resource
    private AttrDao attrDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByID(Map<String, Object> params, Long catelogId) {
        String key = (String) params.get("key");
        QueryWrapper<AttrGroupEntity> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and((wrapper) -> {
                wrapper.eq("attr_group_id", key).or().likeRight("attr_group_name", key);
            });
        }
        if (catelogId == 0) {
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    queryWrapper
            );
            return new PageUtils(page);

        } else {
            // 要是传入的 cateId不是零就是使用 具体的处查询 使用的是queryWrapper
            queryWrapper.eq("catelog_id", catelogId);
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    queryWrapper
            );
            return new PageUtils(page);
        }

    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<AttrGroupVo> getWithAttrByCatId(Long catId) {
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("catelog_id", catId);
        List<AttrGroupEntity> attrGroupEntities = baseMapper.selectList(wrapper);
        List<AttrGroupVo> attrGroupVos = attrGroupEntities.stream().map(item -> {
                    AttrGroupVo attrGroupVo = new AttrGroupVo();
                    BeanUtils.copyProperties(item, attrGroupVo);
                    Long attrGroupId = item.getAttrGroupId();
                    if (attrGroupId != null) {
                        List<AttrAttrgroupRelationEntity> attrgroupRelationEntityList = attrAttrgroupRelationDao.
                                selectList(new QueryWrapper<AttrAttrgroupRelationEntity>()
                                        .eq("attr_group_id", attrGroupId));
                        List<Long> collect = attrgroupRelationEntityList.stream()
                                .map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
                        // 需要注意的就是这里的collect 不能传入一个null 会出现sql的 异常
                        if (!CollectionUtils.isEmpty(collect)){
                            List<AttrEntity> attrEntities = attrDao.selectBatchIds(collect);
                            attrGroupVo.setAttrs(attrEntities);
                        }else{
                            // 前端中使用的是arr进行遍历 所以 这里必须传入一个List 要是不传入List 就是不能便来进行显示
                            attrGroupVo.setAttrs(new ArrayList<AttrEntity>());
                        }
                    }
                    return attrGroupVo;
                }
        ).collect(Collectors.toList());
        return attrGroupVos;
    }

}