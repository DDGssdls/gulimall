package com.edu.gulimail.cart.vo;

import lombok.Data;

/**
 * @Author: DDG
 * @Date: 2020/5/31 14:47
 * @Description: UserInfoVo 类
 */
@Data
public class UserInfoTo {

    private Long userId;
    private String userKey;
    // 用于判断是否是临时用户 要是临时用户 需要设置cookie的过期时间 只有设置成true才进行cookie maxAge 的设置
    private boolean tempUser;
}
