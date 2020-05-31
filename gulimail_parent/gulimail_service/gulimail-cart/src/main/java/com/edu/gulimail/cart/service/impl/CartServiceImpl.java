package com.edu.gulimail.cart.service.impl;

import com.edu.gulimail.cart.service.CartService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: DDG
 * @Date: 2020/5/31 12:55
 * @Description: CartServiceImpl 类
 */
@Service
public class CartServiceImpl implements CartService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
}
