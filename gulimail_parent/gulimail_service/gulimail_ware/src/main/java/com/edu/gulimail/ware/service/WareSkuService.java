package com.edu.gulimail.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.common.utils.PageUtils;
import com.edu.gulimail.ware.entity.WareSkuEntity;
import com.edu.gulimail.ware.vo.SkuVo;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author DDG
 * @email 1306082199@qq.com
 * @date 2020-04-14 19:35:20
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SkuVo> getSkuHasStock(List<Long> skuIds);
}

