package com.eud.gulimall.auth.fegin;

import com.edu.common.utils.R;
import com.eud.gulimall.auth.vo.UserInfoVo;
import com.eud.gulimall.auth.vo.UserLoginVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: DDG
 * @Date: 2020/5/27 11:29
 * @Description: MemberRegisterService 接口
 */
@FeignClient("gulimail-member")
public interface MemberRegisterService {

    @PostMapping("member/member/regist")
     R userRegister(@RequestBody UserInfoVo userInfoVo);

    @PostMapping("member/member/login")
    R login(@RequestBody UserLoginVo vo);
}
