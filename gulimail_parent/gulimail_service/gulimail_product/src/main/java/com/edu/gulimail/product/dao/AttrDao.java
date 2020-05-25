package com.edu.gulimail.product.dao;

import com.edu.gulimail.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性
 * 
 * @author DDG
 * @email 1306082199@qq.com
 * @date 2020-04-14 19:34:06
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {

    List<Long> getCanSearchAttrIds(@Param("attrIds") List<Long> attrIds);
}
