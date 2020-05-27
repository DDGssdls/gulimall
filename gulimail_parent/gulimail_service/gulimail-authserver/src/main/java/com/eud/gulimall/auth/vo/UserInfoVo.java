package com.eud.gulimall.auth.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @Author: DDG
 * @Date: 2020/5/27 08:33
 * @Description:
 */
@Data
public class UserInfoVo {
    @NotEmpty(message = "用户的注册名称必须提交")
    @Length(min = 6, max = 18 ,message = "用户名称长度必须是6~18位")
    private String userName;
    @NotEmpty(message = "用户的注册密码必须提交")
    @Length(min = 6, max = 18 ,message = "用户密码长度必须是6~18位")
    private String password;
    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$", message = "手机号格式不正确")
    private String phoneNum;
    @NotEmpty(message = "验证码不能为空")
    private String code;
}
