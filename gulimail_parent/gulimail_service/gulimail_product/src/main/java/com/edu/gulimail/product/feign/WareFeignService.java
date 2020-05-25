package com.edu.gulimail.product.feign;

import com.edu.common.to.SkuTo;
import com.edu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author: DDG
 * @Date: 2020/5/16 18:10
 * @Description: 远程调用 ： 注册发现  调用开启 创建接口  方法签名 完整路径
 */
@FeignClient("gulimail-ware")
public interface WareFeignService {
    @PostMapping("ware/waresku/hasstock")
    // 解决这里的 类型 问题两种方式 1中就是 在 R设置的时候使用泛型 2 就是 直接 返回List类型的数据
    // 一般的解决方式 就是第一种 但是  现在没有什么办法就是使用第二种方式 第三种方式就是自己设置
    R selectHasStore(@RequestBody List<Long> skuIds);
}
