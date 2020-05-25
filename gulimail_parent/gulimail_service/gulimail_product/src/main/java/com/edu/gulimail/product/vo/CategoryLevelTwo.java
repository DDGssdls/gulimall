package com.edu.gulimail.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @Author: DDG
 * @Date: 2020/5/19 09:30
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class CategoryLevelTwo {

    private String catalog1Id;

    private List catalog3List;

    private String id;

    private String name;

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Data
    public static class categoryLevelThree{

        private String catalog2Id;

        private String id;

        private String name;
    }
}
