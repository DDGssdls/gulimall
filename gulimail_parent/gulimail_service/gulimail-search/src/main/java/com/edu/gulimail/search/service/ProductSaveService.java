package com.edu.gulimail.search.service;

import com.edu.common.to.es.SkuEsModelTo;

import java.io.IOException;
import java.util.List;

/**
 * @Author: DDG
 * @Date: 2020/5/17 09:46
 * @Description: ProductSaveService 接口
 */
public interface ProductSaveService {


    boolean productStatusUp(List<SkuEsModelTo> skuEsModelTos) throws IOException;
}
