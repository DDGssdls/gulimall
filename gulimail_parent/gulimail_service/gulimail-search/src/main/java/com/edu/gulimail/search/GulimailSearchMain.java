package com.edu.gulimail.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: DDG
 * @Date: 2020/5/16 11:08
 * @Description:
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GulimailSearchMain {
    public static void main(String[] args) {
        SpringApplication.run(GulimailSearchMain.class, args);
    }
}
