package com.edu.gulimail.product.feign;

import com.edu.common.to.es.SkuEsModelTo;
import com.edu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author: DDG
 * @Date: 2020/5/17 10:22
 * @Description: SearchFeignService 接口
 */
@FeignClient("gulimail-search")
public interface SearchFeignService {
    /**
     * 商品上架 远程接口
     * @param skuEsModelTos
     * @return
     */
    @PostMapping("/search/save/product")
    R saveDataToEs(@RequestBody List<SkuEsModelTo> skuEsModelTos);
}
