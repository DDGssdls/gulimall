package com.edu.gulimail.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.common.to.SKuReduceTo;
import com.edu.common.utils.PageUtils;
import com.edu.gulimail.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author DDG
 * @email 1306082199@qq.com
 * @date 2020-04-14 19:30:53
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveReductionInfo(SKuReduceTo sKuReduceTo);
}

