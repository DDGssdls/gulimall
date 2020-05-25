package com.edu.gulimail.product.service.impl;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.common.utils.PageUtils;
import com.edu.common.utils.Query;

import com.edu.gulimail.product.dao.SkuInfoDao;
import com.edu.gulimail.product.entity.SkuInfoEntity;
import com.edu.gulimail.product.service.SkuInfoService;
import org.springframework.util.StringUtils;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();
        /**  进行wrapper的构建
         key:
         catelogId: 0
         brandId: 0
         min: 0
         max: 0
         */
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            wrapper.and((w) -> {
                w.eq("sku_id", key).or().likeRight("sku_name", key);
            });
        }
        String catelogId = (String) params.get("catelogId");
        if (!StringUtils.isEmpty(catelogId)) {
            long l = Long.parseLong(catelogId);
            // 这里提交String类型 和 Long类型的都行 但是我是转成了 Long类型进行提交 要是不转
            // "0".equalsIgnoreCase(catelogId)
            // 使用
            if (l > 0) {
                wrapper.eq("catalog_id", l);
            }
        }
        String brandId = (String) params.get("brandId");
        if (!StringUtils.isEmpty(brandId)) {
            long l = Long.parseLong(brandId);
            if (l > 0) {
                wrapper.eq("brand_id", l);
            }
        }
        String max = (String) params.get("max");
        if (!StringUtils.isEmpty(max)) {
            try {
                BigDecimal maxLong = new BigDecimal(max);
                if (1 == maxLong.compareTo(new BigDecimal("0"))) {
                    wrapper.le("price", maxLong);
                }
            } catch (Exception e) {
                // 什么都不做：
            }
        }
        String min = (String) params.get("min");
        if (!StringUtils.isEmpty(min)) {
            try {
                BigDecimal minLong = new BigDecimal(min);
                wrapper.ge("price", minLong);
            } catch (Exception e) {
                // 什么都不做：
            }
        }

        IPage<SkuInfoEntity> page = this.page(new Query<SkuInfoEntity>().getPage(params),
                wrapper);

        return new PageUtils(page);
    }

    @Override
    public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {
        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("spu_id", spuId);
        List<SkuInfoEntity> skuInfoEntities = baseMapper.selectList(wrapper);
        return skuInfoEntities;

    }

}