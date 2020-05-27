package com.edu.gulimail.product.vo;

import com.edu.gulimail.product.entity.SkuImagesEntity;
import com.edu.gulimail.product.entity.SkuInfoEntity;
import com.edu.gulimail.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;

/**
 * @Author: DDG
 * @Date: 2020/5/26 09:03
 * @Description:
 */
@Data
public class SkuItemVo {

    private SkuInfoEntity skuInfoEntity;

    private List<SkuImagesEntity> skuImagesEntityList;

    private SpuInfoDescEntity spuInfoDescEntity;

    private List<SpuItemBaseAttrVo> attrVos;

}
