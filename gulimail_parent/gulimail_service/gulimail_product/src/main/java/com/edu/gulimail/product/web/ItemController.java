package com.edu.gulimail.product.web;

import com.edu.gulimail.product.service.SkuInfoService;
import com.edu.gulimail.product.vo.SkuItemVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author: DDG
 * @Date: 2020/5/22 15:46
 * @Description:
 */
@Controller
public class ItemController {
    @Resource
    private SkuInfoService skuInfoService;

    @GetMapping("/{item}.html")
    public String skuItemInfo(@PathVariable("item") Long item){
        System.out.println(item);
        SkuItemVo skuItemVo =  skuInfoService.item(item);
        return "item";
    }
}
