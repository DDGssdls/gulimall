package com.edu.gulimail.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.edu.common.constant.ProductStatusEnum;
import com.edu.common.to.SKuReduceTo;
import com.edu.common.to.SkuTo;
import com.edu.common.to.SpuBoundsTo;
import com.edu.common.to.es.SkuEsModelTo;
import com.edu.common.utils.MapUtils;
import com.edu.common.utils.R;
import com.edu.gulimail.product.entity.*;
import com.edu.gulimail.product.feign.CouponFeignService;
import com.edu.gulimail.product.feign.SearchFeignService;
import com.edu.gulimail.product.feign.WareFeignService;
import com.edu.gulimail.product.service.*;
import com.edu.gulimail.product.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.common.utils.PageUtils;
import com.edu.common.utils.Query;

import com.edu.gulimail.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;



@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Resource
    private SpuInfoDescService spuInfoDescService;
    @Resource
    private SpuImagesService spuImagesService;
    @Resource
    private AttrService attrService;
    @Resource
    private ProductAttrValueService productAttrValueService;
    @Resource
    private SkuInfoService skuInfoService;
    @Resource
    private SkuImagesService skuImagesService;
    @Resource
    private SkuSaleAttrValueService saleAttrValueService;
    @Resource
    private CouponFeignService couponFeignService;
    @Resource
    private BrandService brandService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private WareFeignService wareFeignService;
    @Resource
    private SearchFeignService searchFeignService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveSpuInfo(SpuSaveVo spuSaveVo) {
        // 进行spu基本的信息的保存 spu info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        // 将vo中的信息赋值到spuInfo中
        BeanUtils.copyProperties(spuSaveVo, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.saveBaseSaleInfo(spuInfoEntity);
        // 进行spu描述的保存 spu info desc
        List<String> descList = spuSaveVo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        Long spuInfoEntityId = spuInfoEntity.getId();
        // 这里的需要手动添加id
        spuInfoDescEntity.setSpuId(spuInfoEntityId);
        spuInfoDescEntity.setDecript(String.join("," , descList));
        // 通过上次保存的实体进行保存
        // TODO 在进行分布式的时候需要完善
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescService.saveSpuInfoDesc(spuInfoDescEntity);
        // 进行spu的图片集 spu images
        //SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
        List<String> images = spuSaveVo.getImages();
        if (!CollectionUtils.isEmpty(images)){
            List<SpuImagesEntity> collect = images.stream().map(item -> {
                SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
                spuImagesEntity.setImgUrl(item);
                spuImagesEntity.setSpuId(spuInfoEntityId);
                return spuImagesEntity;
            }).collect(Collectors.toList());
            spuImagesService.saveSpuImage(collect);
        }
        // 进行spu的规格参数的保存 product attr value
        List<BaseAttrs> baseAttrs = spuSaveVo.getBaseAttrs();
        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(item -> {
            ProductAttrValueEntity attrEntity = new ProductAttrValueEntity();
            //BeanUtils.copyProperties(item, attrEntity); 要是数据比较的少就是使用set get进行保存
            attrEntity.setAttrId(item.getAttrId());
            // 进行的是attrName的保存：
            AttrEntity byId = attrService.getById(item.getAttrId());
            if (byId != null) {
                attrEntity.setAttrName(byId.getAttrName());
            }
            attrEntity.setAttrValue(item.getAttrValues());
            attrEntity.setQuickShow(item.getShowDesc());
            attrEntity.setSpuId(spuInfoEntityId);

            return attrEntity;
        }).collect(Collectors.toList());
        productAttrValueService.saveBatch(collect);

        // 远程服务调用： 进行积分的保存
        Bounds bounds = spuSaveVo.getBounds();
        SpuBoundsTo spuBoundsTo = new SpuBoundsTo();
        BeanUtils.copyProperties(bounds, spuBoundsTo);
        spuBoundsTo.setSpuId(spuInfoEntityId);
        couponFeignService.saveBounds(spuBoundsTo);

        // 进行spu锁对应的sku信息 sku info  同时需要记性sku 的图片信息 sku images
        List<Skus> skus = spuSaveVo.getSkus();
        if (!CollectionUtils.isEmpty(skus)){
            skus.forEach((sku -> {
                String defaultImage = null;
                for (Images image : sku.getImages()) {
                    if (image.getDefaultImg() == 1){
                        defaultImage = image.getImgUrl();
                    }
                }
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(sku, skuInfoEntity);
                // 进行属性的对拷 剩余的属性进行设置：
                skuInfoEntity.setBrandId(spuInfoEntityId);
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSpuId(spuInfoEntityId);
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSkuDefaultImg(defaultImage);
                // 将sku 的信息进行保存
                skuInfoService.save(skuInfoEntity);
                Long skuInfoId = skuInfoEntity.getSkuId();
                List<Images> imagesList = sku.getImages();
                List<SkuImagesEntity> skuImagesEntities = imagesList.stream()
                        .filter(images1 -> !StringUtils.isEmpty(images1.getImgUrl())).map(image -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setId(skuInfoId);
                    skuImagesEntity.setImgUrl(image.getImgUrl());
                    skuImagesEntity.setDefaultImg(image.getDefaultImg());
                    return skuImagesEntity;
                }).collect(Collectors.toList());
                // 将图片信息进行批量的保存
                skuImagesService.saveBatch(skuImagesEntities);
                List<Attr> attr = sku.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attr.stream().map(attr1 -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(attr1, skuSaleAttrValueEntity);
                    // 进行关联属性的设置
                    skuSaleAttrValueEntity.setSkuId(skuInfoId);
                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());
                saleAttrValueService.saveBatch(skuSaleAttrValueEntities);
                //couponFeignService 进行远程服务调用：进行优惠满减的信息添加
                SKuReduceTo sKuReduceTo = new SKuReduceTo();
                BeanUtils.copyProperties(sku, sKuReduceTo);
                sKuReduceTo.setSkuId(skuInfoId);
                if(sKuReduceTo.getFullCount() > 0 || (sKuReduceTo.getFullPrice().compareTo(new BigDecimal("0")) == 1)){
                    couponFeignService.saveSkuReduction(sKuReduceTo);
                }


            }));
        }
        // 同时保存销售信息 sku sale attr value
        // 最后的进行优惠满减信息
    }

    @Override
    public void saveBaseSaleInfo(SpuInfoEntity spuInfoEntity) {
        // 进行spu的基本信息的保存：
        baseMapper.insert(spuInfoEntity);
    }

    /**
     * status:
     * key: 9
     * brandId: 0
     * catelogId: 0*/
    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)){
            // 要是内嵌的条件使用and 箭头函数 进行显示
           wrapper.and((w) ->{
               w.eq("id", key).or().likeRight("spu_name", key);
           });
        }
        String status = (String) params.get("status");
        if (!StringUtils.isEmpty(status)){
            wrapper.eq("publish_status", Integer.parseInt(status));
        }
        String brandId = (String) params.get("brandId");
        if (!StringUtils.isEmpty(brandId)){
            long l = Long.parseLong(brandId);
            if (l > 0){
                wrapper.eq("brand_id", l);
            }

        }
        String catelogId = (String) params.get("catelogId");
        if (!StringUtils.isEmpty(catelogId)){
            long l = Long.parseLong(catelogId);
            if (l > 0){
                wrapper.eq("catalog_id", l);
            }

        }
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);

    }

    @Override
    public void upProduct(Long spuId) {
        // 商品上架  就是将mysql中的数据存储到es中
        // 通过当前传入的spuId 获取到 所有的sku信息：

        // 查询sku 可以被查询的属性
        List<ProductAttrValueEntity> entities = productAttrValueService.baseAttrListForSpu(spuId);
        // 获取到 所有的属性的id 在attr表中进行查询 + 属性的类型是可检索的
        List<Long> attrIds = entities.stream().map(ProductAttrValueEntity::getAttrId).collect(Collectors.toList());
        List<Long> canSearchAttrIds = attrService.getCanSearchAttrIds(attrIds);
        // 进行过滤 获取到需要保存的真正的 attr信息
        List<SkuEsModelTo.Attrs> attrsList = entities.stream().
                filter(entity -> canSearchAttrIds.contains(entity.getAttrId()))
                .map(entity -> {
                    SkuEsModelTo.Attrs attrs = new SkuEsModelTo.Attrs();
                    BeanUtils.copyProperties(entity, attrs);
                    return attrs;
                })
                .collect(Collectors.toList());

        List<SkuInfoEntity> skuInfoEntities = skuInfoService.getSkusBySpuId(spuId);
        //TODO 进行库存的判断 进行的是远程的请求 使用的是库存系统
        List<Long> skuIdList = skuInfoEntities.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());
         Map<Long, Boolean> hasStockMap = null;
        try {
            R listR = wareFeignService.selectHasStore(skuIdList);
            Object dataObject = listR.get("data");
            // 使用JSON进行 类型的转换
            String s = JSON.toJSONString(dataObject);
            //  要是 protected 首保护的构造器 使用的是内部类的形式进行创建
            // 要是想要使用泛型方法 需要实现声明 要是不进行传入的话
            TypeReference<List<SkuTo>> typeReference = new TypeReference<List<SkuTo>>(){};
            List<SkuTo> data = JSON.parseObject(s, typeReference);
            if (!CollectionUtils.isEmpty(data)){
                hasStockMap = data.stream().collect(Collectors.toMap(SkuTo::getSkuId, SkuTo::getHasStock));
            }
        }catch (Exception e){
            String message = e.getMessage();
            log.error("库存服务远程调用 出现异常{}", e);
        }

        // 进行数据的组装：
        Map<Long, Boolean> finalHasStockMap = hasStockMap;
        List<SkuEsModelTo> collect = skuInfoEntities.stream().map(skuInfoEntity -> {
            SkuEsModelTo skuEsModelTo = new SkuEsModelTo();
            BeanUtils.copyProperties(skuInfoEntity, skuEsModelTo);
            skuEsModelTo.setSkuPrice(skuInfoEntity.getPrice());
            skuEsModelTo.setSkuImg(skuInfoEntity.getSkuDefaultImg());
            // 进行库存的设置：要是没有属性 也是设置成有货
            if (finalHasStockMap != null && finalHasStockMap.size() > 0){
                skuEsModelTo.setHasStock(finalHasStockMap.get(skuInfoEntity.getSkuId()));
            }else{
                // 要是出现了问题就是默认的有货
                skuEsModelTo.setHasStock(true);
            }
            // TODO 进行的是热度评分的操作 ： 现在默认的就是 零 但是可以有更多的操作：
            // 进行的是品牌 和 分类的查询
            BrandEntity brandServiceById = brandService.getById(skuInfoEntity.getBrandId());
            // 进行品牌相关的属性的设置：
            skuEsModelTo.setBrandName(brandServiceById.getName());
            skuEsModelTo.setBrandImg(brandServiceById.getLogo());
            CategoryEntity categoryServiceById = categoryService.getById(skuInfoEntity.getCatalogId());
            skuEsModelTo.setCatalogName(categoryServiceById.getName());
            // 进行的是检索属性的设置： 将检索属性的获取放到 map操作的外面 防止的 是在 map映射中 重复的操作：
            skuEsModelTo.setHotScore(0L);
            skuEsModelTo.setAttrs(attrsList);
            return skuEsModelTo;
        }).collect(Collectors.toList());

        // 将上面封装到的信息发送到es中进行保存  这里远程调用的是search服务
        R r = searchFeignService.saveDataToEs(collect);
        if (r.getCode() == 0){
            // 远程的调用成功：修改商品的上架状态 已上架
            baseMapper.updateSpuStatus(spuId, ProductStatusEnum.UP_SPU.getStatus());
        }else{
            // 出错了 怎么办 TODO 接口 幂等性 重复调用 重试机制
        }
    }


}