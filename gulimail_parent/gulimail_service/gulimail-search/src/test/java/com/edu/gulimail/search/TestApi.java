package com.edu.gulimail.search;

import com.alibaba.fastjson.JSON;
import com.edu.gulimail.search.config.ElasticSearchConfig;
import lombok.Data;
import lombok.ToString;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @Author: DDG
 * @Date: 2020/5/16 11:24
 * @Description:
 */
@SpringBootTest()
class TestApi {

    @Autowired
    private RestHighLevelClient client;

    @Test
    void indexApi() {
        System.out.println(client);
    }

    /**
     * index方法 就像是 post 和 put一样 能进行数据的保存 和 更新：
     * @throws IOException
     */
    @Test
    void index() throws IOException {
        IndexRequest users = new IndexRequest("users");
        users.id("1");// 只能是设置一个string类型的id
        User user = new User();
        user.setAge(18);
        user.setGender("F");
        user.setUserName("张三");
        String jsonString = JSON.toJSONString(user);
        // 进行保存的方式有非常多的中 但是 一般都是使用这一种方式 传入的是json字符串
        users.source(jsonString, XContentType.JSON);
        // 进行保存 两种方式 一种就是 异步的方式 另一种就是同步的方式：

        IndexResponse index = client.index(users, ElasticSearchConfig.COMMON_OPTIONS);

        System.out.println(index);
    }
    @Test
    void searchData()  throws IOException {
        // 创建一个search请求
        SearchRequest searchRequest = new SearchRequest();
        // 指定索引：
        searchRequest.indices("bank");
        // 进行检索的条件的构建： 指定的是 DSL 传入的是一个 sourceBuilder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 进行查询中的 根操作 都是在searchSourceBuilder
        searchSourceBuilder.query(QueryBuilders.matchQuery("address", "mill"));
        searchSourceBuilder.aggregation(AggregationBuilders.terms("aggAge").field("age").size(10));
        searchSourceBuilder.aggregation(AggregationBuilders.avg("balanceAvg").field("balance"));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        searchRequest.source(searchSourceBuilder);
        // 指定检索： 使用的是client进行执行 和 进行数据的保存的操作是一致的 同样的是需要传入 请求 和 options
        SearchResponse search = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
        // 进行结果的分析： 结果封装到 SearchResult 对象中了 可以获取对象中相应的信息 如 took 花费的时间
        // 响应的状态 等 hits 命中的结果集 等等 获取的所有的 hits
        SearchHits hits = search.getHits();
        //  从hits中进行 真正的hits获取
        TotalHits totalHits = hits.getTotalHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
        // 获取聚合
        Aggregations aggregations = search.getAggregations();
        System.out.println(search);

    }

    @Data
    @ToString
    class Account{
        /**
         * account_number : 970
         * balance : 19648
         * firstname : Forbes
         * lastname : Wallace
         * age : 28
         * gender : M
         * address : 990 Mill Road
         * employer : Pheast
         * email : forbeswallace@pheast.com
         * city : Lopezo
         * state : AK
         */

        private int account_number;
        private int balance;
        private String firstname;
        private String lastname;
        private int age;
        private String gender;
        private String address;
        private String employer;
        private String email;
        private String city;
        private String state;
    }

    @Data
    class User {
        private String userName;
        private String gender;
        private Integer age;
    }
    class Account2{

        /**
         * account_number : 970
         * balance : 19648
         * firstname : Forbes
         * lastname : Wallace
         * age : 28
         * gender : M
         * address : 990 Mill Road
         * employer : Pheast
         * email : forbeswallace@pheast.com
         * city : Lopezo
         * state : AK
         */

        private int account_number;
        private int balance;
        private String firstname;
        private String lastname;
        private int age;
        private String gender;
        private String address;
        private String employer;
        private String email;
        private String city;
        private String state;

        public int getAccount_number() {
            return account_number;
        }

        public void setAccount_number(int account_number) {
            this.account_number = account_number;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmployer() {
            return employer;
        }

        public void setEmployer(String employer) {
            this.employer = employer;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
