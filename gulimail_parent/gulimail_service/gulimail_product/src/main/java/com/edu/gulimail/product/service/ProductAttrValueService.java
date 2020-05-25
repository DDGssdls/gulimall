package com.edu.gulimail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.common.utils.PageUtils;
import com.edu.gulimail.product.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author DDG
 * @email 1306082199@qq.com
 * @date 2020-04-14 19:34:06
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
    List<ProductAttrValueEntity> baseAttrListForSpu(Long spuId);
}

