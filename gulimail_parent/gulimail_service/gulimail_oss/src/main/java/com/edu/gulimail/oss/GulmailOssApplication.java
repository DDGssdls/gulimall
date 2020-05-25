package com.edu.gulimail.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: DDG
 * @Date: 2020/5/10 16:18
 * @Description:
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.edu")
@EnableDiscoveryClient
public class GulmailOssApplication {
    public static void main(String[] args) {
        SpringApplication.run(GulmailOssApplication.class, args);
    }
}
