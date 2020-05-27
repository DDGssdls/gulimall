package com.edu.gulimail.product.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: DDG
 * @Date: 2020/5/26 09:21
 * @Description:
 */
@Data
public class SpuBaseAttrVo {
    private String name;
    private List<String> attrValues;
}
