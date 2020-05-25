package com.edu.gulimail.product.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: DDG
 * @Date: 2020/5/22 15:46
 * @Description:
 */
@Controller
public class ItemController {

    @GetMapping("/{item}.html")
    public String skuItemInfo(@PathVariable("item") Long item){
        return "item";
    }
}
