package com.edu.gulimail.search.controller;

import com.edu.common.exception.GuliMailCodeMnum;
import com.edu.common.to.es.SkuEsModelTo;
import com.edu.common.utils.R;
import com.edu.gulimail.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @Author: DDG
 * @Date: 2020/5/17 09:39
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/search/save")
public class ElasticSaveController {

    @Resource
    private ProductSaveService productSaveService;

    /**
     *  进行商品的上架 就是将mysql中的数据保存到 es中
     */
    @PostMapping("/product")
    public R saveDataToEs(@RequestBody List<SkuEsModelTo> skuEsModelTos){
        boolean b = false;
        try {
           b = productSaveService.productStatusUp(skuEsModelTos);
        } catch (IOException e) {
            log.error("ElasticSaveController商品上架异常{}", e.getMessage());
            return R.error(GuliMailCodeMnum.ELASTICSEARCH_EXCEPTION.getCode(), GuliMailCodeMnum.ELASTICSEARCH_EXCEPTION.getMessage());
        }


        if (b){
            return R.error("商品上架部分出现错误");
        }else{
            return R.ok();
        }
    }
}
