package com.eud.gulimall.auth.fegin;

import com.edu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: DDG
 * @Date: 2020/5/26 14:34
 * @Description: 短信第三方服务远程调用接口
 */
@FeignClient("service-oss")
public interface OssFeignService {

    @GetMapping("/edumsm/msm/send/{phoneNum}")
    R getSendPhoneNum(@PathVariable("phoneNum") String phoneNum);
}
