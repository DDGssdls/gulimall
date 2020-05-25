package com.edu.gulimail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.common.utils.PageUtils;
import com.edu.gulimail.product.entity.AttrEntity;
import com.edu.gulimail.product.vo.AttrRespVo;
import com.edu.gulimail.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author DDG
 * @email 1306082199@qq.com
 * @date 2020-04-14 19:34:06
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByCategoryId(Map<String, Object> params, Long catelogId, String attrType);

    void saveAttrVo(AttrVo attr);

    AttrRespVo getAttrInfo(Long attrId);

    List<Long> getCanSearchAttrIds(List<Long> attrIds);
}

