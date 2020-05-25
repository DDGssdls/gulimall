package com.edu.gulimail.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.edu.common.to.es.SkuEsModelTo;
import com.edu.gulimail.search.config.ElasticSearchConfig;
import com.edu.gulimail.search.constant.ProductConstant;
import com.edu.gulimail.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: DDG
 * @Date: 2020/5/17 09:48
 * @Description:
 */
@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Override
    public boolean productStatusUp(List<SkuEsModelTo> skuEsModelTos) throws IOException {

        // 将数据保存到es中 首先就是进行索引的建立： 同时需要将映射关系进行建立
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModelTo skuEsModelTo : skuEsModelTos) {
            IndexRequest indexRequest = new IndexRequest(ProductConstant.PRODUCT_INDEX);
            // 进行id的设置：
            indexRequest.id(skuEsModelTo.getSkuId().toString());
            // 进行source的设置：
            String s = JSON.toJSONString(skuEsModelTo);
            // 使用source方法进行内容的设置 需要指定类型
            indexRequest.source(s, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulkItemResponses = restHighLevelClient.bulk(bulkRequest, ElasticSearchConfig.COMMON_OPTIONS);
        // 判断是否是含有错误 也就是上架失败的
        boolean b = bulkItemResponses.hasFailures();
        List<String> collect = Arrays.stream(bulkItemResponses.getItems()).map(BulkItemResponse::getId).collect(Collectors.toList());
        if (b){
            // TODO 出现错误 怎么进行重试
            log.error("出现了异常 上架没有成功{}", collect );
        }

        return b;

    }
}
