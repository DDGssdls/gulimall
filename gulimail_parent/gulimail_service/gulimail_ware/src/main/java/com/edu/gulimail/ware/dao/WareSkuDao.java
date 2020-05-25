package com.edu.gulimail.ware.dao;

import com.edu.gulimail.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author DDG
 * @email 1306082199@qq.com
 * @date 2020-04-14 19:35:20
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    Long selectAllStock(Long skuId);
}
