package com.edu.gulimail.product.dao;

import com.edu.gulimail.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author DDG
 * @email 1306082199@qq.com
 * @date 2020-04-14 19:34:06
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
