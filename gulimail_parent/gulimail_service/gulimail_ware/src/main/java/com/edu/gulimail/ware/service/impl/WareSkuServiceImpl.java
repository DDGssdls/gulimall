package com.edu.gulimail.ware.service.impl;

import com.edu.gulimail.ware.vo.SkuVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.common.utils.PageUtils;
import com.edu.common.utils.Query;

import com.edu.gulimail.ware.dao.WareSkuDao;
import com.edu.gulimail.ware.entity.WareSkuEntity;
import com.edu.gulimail.ware.service.WareSkuService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> wrapper = new QueryWrapper<>();
        String skuId = (String) params.get("skuId");
        if (!StringUtils.isEmpty(skuId)){
            wrapper.eq("sku_id", Long.parseLong(skuId));
        }
        String wareId = (String) params.get("wareId");
        if (!StringUtils.isEmpty(wareId)){
            wrapper.eq("ware_id", Long.parseLong(skuId));
        }
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuVo> getSkuHasStock(List<Long> skuIds) {
        List<SkuVo> collect = skuIds.stream().map(skuId -> {
            SkuVo skuVo = new SkuVo();
            // 获取的是当前sku 总库存量 就是 所有的库存 - 锁定的库存
            Long count = baseMapper.selectAllStock(skuId);
            skuVo.setSkuId(skuId);
            skuVo.setHasStock(count != null && count > 0);
            return skuVo;
        }).collect(Collectors.toList());

        return collect;
    }

}