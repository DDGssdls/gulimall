package com.edu.gulimail.order.dao;

import com.edu.gulimail.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author DDG
 * @email 1306082199@qq.com
 * @date 2020-04-14 19:32:47
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}