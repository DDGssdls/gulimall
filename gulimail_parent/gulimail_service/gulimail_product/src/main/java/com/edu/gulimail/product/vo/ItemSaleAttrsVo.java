package com.edu.gulimail.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;


/**
 * @Author: DDG
 * @Date: 2020/5/26 09:16
 * @Description: item中的销售属性 组合 vo
 */
@Data
public class ItemSaleAttrsVo {

    /**
     * 属性id
     */
    private Long attrId;
    /**
     * 属性名
     */
    private String attrName;

    private List<String> attrValues;
}
