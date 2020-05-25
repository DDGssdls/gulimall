package com.edu.gulimail.product.feign;

import com.edu.common.to.SKuReduceTo;
import com.edu.common.to.SpuBoundsTo;
import com.edu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: DDG
 * @Date: 2020/5/12 15:48
 * @Description: 远程服务调用接口
 *
 */
@FeignClient("gulimail-coupon")
public interface CouponFeignService {
    /**
     * 在进行远程服务的调用的时候 首先是将对象转成json数据  从接口上的注解上找到对应的服务进行远程的调用
     * 将上面的转成json放到请求体中进行发送 请求头 请求行 请求空行 请求体
     * 需要注意的就是只要是能进行转换就可以转换 类型不一定需要一致的 只要是上面的属性相同
     * 只要是双方的数据模式是兼容的就能进行转换
     * @param spuBoundsTo
     * @return
     */
    @PostMapping("coupon/spubounds/save")
    R saveBounds(@RequestBody SpuBoundsTo spuBoundsTo);

    @PostMapping("coupon/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SKuReduceTo sKuReduceTo);
}
