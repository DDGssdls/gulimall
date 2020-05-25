package com.edu.gulimail.search.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @Author: DDG
 * @Date: 2020/5/21 13:01
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchParamVo {

    /**
     *  传入的检索关键字
     * */
    private String keyword;
    /**
     *  传入的三级分类的id
     * */
    private Long catalog3Id;
    /**
     * 是否是只显示有货
    * */
    private Integer hasStock;
    /**
     * 排序字段
     * */
    private String sort;
    /**
     *  传入的价格区间
     * */
    private String skuPrice;
    /**
     * 品牌的id 可以传入多个 品牌的id
     * */
    private List<Long> brandId;
    /**
     * 按照属性进行筛选：
     * */
    private List<String> attrs;

    private Integer pageNum = 1;


}
