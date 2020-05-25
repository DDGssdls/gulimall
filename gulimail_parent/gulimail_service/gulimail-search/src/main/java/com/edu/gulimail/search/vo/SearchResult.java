package com.edu.gulimail.search.vo;

import com.edu.common.to.es.SkuEsModelTo;
import lombok.Data;

import java.util.List;

/**
 * @Author: DDG
 * @Date: 2020/5/21 14:41
 * @Description:
 */
@Data
public class SearchResult {
    /**
     * 查询的所有的商品信息
     * */
    private List<SkuEsModelTo> products;

    /**
     * 分页信息：
     * */

    private Integer pageNumber;

    private Long total;

    private Long totalPages;

    /**
     * 分类 和 品牌数据信息
     * */

    private List<BrandInfo> brandInfos;

    private List<attrInfo> attrInfos;

    private List<catalogInfo> catalogInfos;


    @Data
    public static class BrandInfo{
        private Long brandId;
        private String brandName;
        private String brandImg;
    }

    @Data
    public static class attrInfo{
        private Long attrId;
        private String attrName;
        private List<String> attrValue;
    }

    @Data
    public static class catalogInfo{
        private Integer catalogId;
        private String catalogName;
    }
}
