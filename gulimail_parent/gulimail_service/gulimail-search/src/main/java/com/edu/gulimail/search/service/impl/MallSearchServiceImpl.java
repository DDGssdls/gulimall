package com.edu.gulimail.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.edu.common.to.es.SkuEsModelTo;
import com.edu.gulimail.search.config.ElasticSearchConfig;
import com.edu.gulimail.search.constant.ProductConstant;
import com.edu.gulimail.search.service.MallSearchService;
import com.edu.gulimail.search.vo.SearchParamVo;
import com.edu.gulimail.search.vo.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.TotalHits;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: DDG
 * @Date: 2020/5/21 13:05
 * @Description:
 */
@Service
public class MallSearchServiceImpl implements MallSearchService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Override
    public SearchResult search(SearchParamVo paramVo) {
        // 准备检索的请求
        SearchRequest searchRequest = buildSearchRequest(paramVo);
        SearchResult searchResult = null;

        try {
            // 执行检索请求
            SearchResponse search = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
            //进行的结果的封装
            searchResult = buildSearchResult(search, paramVo);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 进行结果数据的构建
     * @param search
     * @param paramVo
     * @return
     */
    private SearchResult buildSearchResult(SearchResponse search, SearchParamVo paramVo) {
        //  将 查询的结果封装成 需要的结果
        SearchResult searchResult = new SearchResult();
        SearchHits hits = search.getHits();
        //进行命中的封装
        List<SkuEsModelTo> list = new ArrayList<>();
       if (hits != null && hits.getHits().length > 0){
           for (SearchHit hit : hits) {
               String sourceAsString = hit.getSourceAsString();
               SkuEsModelTo skuEsModelTo = JSON.parseObject(sourceAsString, SkuEsModelTo.class);
               list.add(skuEsModelTo);
           }
       }
       // 进行命中记录的设置：
        searchResult.setProducts(list);
       // 设置商品涉及到的属性：都是聚合的信息：
        Aggregations searchAggregations = search.getAggregations();
        ParsedLongTerms catalog_agg = searchAggregations.get("catalog_agg");
        List<? extends Terms.Bucket> buckets = catalog_agg.getBuckets();
        List<SearchResult.catalogInfo> catalog_name_agg1 = buckets.stream().map(bucket -> {
            SearchResult.catalogInfo catalogInfo = new SearchResult.catalogInfo();
            catalogInfo.setCatalogId(Integer.parseInt(bucket.getKeyAsString()));
            ParsedStringTerms catalog_name_agg = bucket.getAggregations().get("catalog_name_agg");
            String keyAsString = catalog_name_agg.getBuckets().get(0).getKeyAsString();
            catalogInfo.setCatalogName(keyAsString);
            return catalogInfo;
        }).collect(Collectors.toList());

        // 设置商品涉及到的品牌：都是聚合的信息：
        ParsedLongTerms brand_agg = search.getAggregations().get("brand_agg");
        List<SearchResult.BrandInfo> collect = brand_agg.getBuckets().stream().map((bucket) -> {
            SearchResult.BrandInfo brandInfo = new SearchResult.BrandInfo();
            String keyAsString = bucket.getKeyAsString();
            brandInfo.setBrandId(Long.parseLong(keyAsString));
            // 获取品牌的名称
            ParsedStringTerms brand_img_agg = bucket.getAggregations().get("brand_img_agg");
            Terms.Bucket bucket1 = brand_img_agg.getBuckets().get(0);
            String keyAsString1 = null;
            if (bucket1 != null){
                keyAsString1 = bucket1.getKeyAsString();
            }
            brandInfo.setBrandImg(keyAsString1);
            ParsedStringTerms bucket_name_agg = bucket.getAggregations().get("bucket_name_agg");
            Terms.Bucket bucket2 = bucket_name_agg.getBuckets().get(0);
            String keyAsString2 = null;
            if (bucket1 != null){
                keyAsString2 = bucket2.getKeyAsString();
            }
            brandInfo.setBrandName(keyAsString2);
            return brandInfo;
        }).collect(Collectors.toList());
        // 进行的是商品的属性的聚合：
        searchResult.setBrandInfos(collect);
        searchResult.setCatalogInfos(catalog_name_agg1);
        TotalHits totalHits = hits.getTotalHits();
        long value = totalHits.value;
        // 将总记录数据进行保存进行保存
        searchResult.setTotal(value);
        // 进行的是总页数的保存：
        long pageSize = (value + ProductConstant.PAGE_SIZE - 1) / ProductConstant.PAGE_SIZE;
        searchResult.setTotalPages(pageSize);
        // 设置 页码：
        searchResult.setPageNumber(paramVo.getPageNum());


        return null;
    }

    /**
     * 进行结果请求条件的构建
     * @return
     */
    private SearchRequest buildSearchRequest(SearchParamVo paramVo) {
        // searchSourceBuilder 进行构建 需要使用searchSourceBuilder进行构建
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 进行查询：
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        if(!StringUtils.isEmpty(paramVo.getKeyword())){
            queryBuilder.must(QueryBuilders.matchQuery("skyTitle", paramVo.getKeyword()));
        }
        if (paramVo.getCatalog3Id() != null){
            queryBuilder.filter(QueryBuilders.termQuery("catalogId", paramVo.getCatalog3Id()));
        }
        if (!CollectionUtils.isEmpty(paramVo.getBrandId())){
            queryBuilder.filter(QueryBuilders.termsQuery("brandId", paramVo.getBrandId()));
        }
        if (paramVo.getHasStock() != null){
            queryBuilder.filter(QueryBuilders.termQuery("hasStock", paramVo.getHasStock()));
        }
        if (!StringUtils.isEmpty(paramVo.getSkuPrice())){
            String[] s = paramVo.getSkuPrice().split("_");
            RangeQueryBuilder skuPrice = QueryBuilders.rangeQuery("skuPrice");
            if (s.length == 2){
                skuPrice.gte(s[0]).lte(s[1]);
            }else {
                if (paramVo.getSkuPrice().startsWith("_")){
                    skuPrice.lte(s[0]);
                }else{
                    skuPrice.gte(s[0]);
                }

            }

            queryBuilder.filter();

        }
        // 这里的attrs 是数组的形式 使用的是nested 的形式进行查询的条件的构建
        // 需要使用的参数就是三种 path
        if (!CollectionUtils.isEmpty(paramVo.getAttrs())){
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            // 进行条件的构建：
           paramVo.getAttrs().forEach(attr ->{
               String[] s = attr.split("_");
               String attrId = s[0];
               String[] attrValues = s[1].split(":");
               boolQueryBuilder.must(QueryBuilders.termQuery("attrs.attrId", attrId));
               boolQueryBuilder.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues));
               NestedQueryBuilder attrs = QueryBuilders.nestedQuery("attrs", boolQueryBuilder, ScoreMode.None);
               queryBuilder.filter(attrs);
           });
        }
        // 将所有的条件进行封装
        sourceBuilder.query(queryBuilder);
        /**
         *  进行的是排序的使用：
         * */
        // 排序
        if (!StringUtils.isEmpty(paramVo.getSort())){
            String sort = paramVo.getSort();
            String[] s = sort.split("_");
            SortOrder sortOrder = s[1].equalsIgnoreCase("asc")? SortOrder.ASC : SortOrder.DESC;
            sourceBuilder.sort(s[0], sortOrder);
        }
        // 分页：
        sourceBuilder.from((paramVo.getPageNum() - 1) * ProductConstant.PAGE_SIZE);
        sourceBuilder.size(ProductConstant.PAGE_SIZE);

        // 进行高亮显示：只有传入了keyword 才进行高亮显示：
        if (!StringUtils.isEmpty(paramVo.getKeyword())){
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("</b>");
            sourceBuilder.highlighter(highlightBuilder);
        }
        // 进行的是聚合显示：
        // 进行的是品牌的id进行聚合
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms("brand_agg");
        brandAgg.field("brandId").size(50);
        // 进行子聚合
        brandAgg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(1));
        brandAgg.subAggregation(AggregationBuilders.terms("brand_image_agg").field("brandImg").size(1));
        sourceBuilder.aggregation(brandAgg);

        // 进行的是品牌分类的聚合：
        TermsAggregationBuilder catalogAgg = AggregationBuilders.terms("catalog_agg").field("catalogId").size(20);
        sourceBuilder.aggregation(catalogAgg);
        // 进行的是属性的聚合： 需要注意的就是属性的聚合 使用的是nested
        NestedAggregationBuilder nested = AggregationBuilders.nested("attr_agg", "attrs");
        NestedAggregationBuilder attrIdAgg = AggregationBuilders.nested("attr_id_agg", "attrs.attrId");
        attrIdAgg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
        attrIdAgg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(50));
        nested.subAggregation(attrIdAgg);
        sourceBuilder.aggregation(nested);


        return new SearchRequest(new String[]{ProductConstant.PRODUCT_INDEX}, sourceBuilder);
    }
}
