package com.edu.gulimail.cart.controller;

import com.edu.gulimail.cart.interceptor.CartInterceptor;
import com.edu.gulimail.cart.vo.UserInfoTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: DDG
 * @Date: 2020/5/31 13:06
 * @Description: CartController 类
 */
@Controller
public class CartController {
    /**
     *  使用的是userkey进行用身份的验证
     * @return
     */
    @GetMapping("/index.html")
    public String cartListPage(){
        // 获取数据
        ThreadLocal<UserInfoTo> threadLocal = CartInterceptor.getThreadLocal();
        UserInfoTo userInfoTo = threadLocal.get();
        System.out.println(userInfoTo);
        return "index";
    }
}
