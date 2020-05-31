package com.eud.gulimall.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Author: DDG
 * @Date: 2020/5/26 10:27
 * @Description:
 */
@EnableRedisHttpSession // 整合 redis 进行分布式session的存储 使用的是spring session 本质就是装饰者模式
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients
public class GulimallAuthMain {
    public static void main(String[] args) {
        SpringApplication.run(GulimallAuthMain.class, args);
    }
}
