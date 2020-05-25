package com.edu.gulimail.coupon.dao;

import com.edu.gulimail.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author DDG
 * @email 1306082199@qq.com
 * @date 2020-04-14 19:30:53
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
