package com.edu.gulimail.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


/**
 * @Author: DDG
 * @Date: 2020/5/31 11:22
 * @Description: GulimallCartMain 类
 */
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("com.edu")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GulimallCartMain {
    public static void main(String[] args) {
        SpringApplication.run(GulimallCartMain.class, args);
    }
}
