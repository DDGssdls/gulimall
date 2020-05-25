package com.edu.gulimail.member.feign;

import com.edu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("gulimail-coupon")
public interface CouponFeignService {

    @GetMapping("/coupon/coupon/member/list")
    R memberCoupons();
}
