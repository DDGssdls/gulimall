package com.eud.gulimall.auth.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: DDG
 * @Date: 2020/5/27 12:05
 * @Description:
 */
@Data
public class UserLoginVo implements Serializable {
    // 用户可以使用 用户名称 手机号 邮箱进行登录
    private String loginAccount;

    private String password;
}
