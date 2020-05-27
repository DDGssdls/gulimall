package com.edu.gulimail.product.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: DDG
 * @Date: 2020/5/26 09:20
 * @Description:
 */
@Data
public class SpuItemBaseAttrVo {

    private String groupName;

    private List<SpuItemBaseAttrVo> attrVos;

}
