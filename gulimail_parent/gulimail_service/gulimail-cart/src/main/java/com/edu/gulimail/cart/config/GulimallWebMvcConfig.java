package com.edu.gulimail.cart.config;

import com.edu.gulimail.cart.interceptor.CartInterceptor;
import com.edu.gulimail.cart.vo.Cart;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: DDG
 * @Date: 2020/5/31 15:10
 * @Description: GulimallWebMvcConfig 类
 */
@Configuration
public class GulimallWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CartInterceptor()).addPathPatterns("/**");
    }
}
