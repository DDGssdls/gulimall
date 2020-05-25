package com.edu.gulimail.ware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan("com.edu")
@EnableDiscoveryClient
public class GulimailWareApplication {
    /**
     *  配置中心 就是在resource中创建一个bootstrap properties 或者是yml文件进行配置 配置 application name 和
     *  配置中心的 nacos config addr 和 nacos namespace配置
     *
     * */

    public static void main(String[] args) {
        SpringApplication.run(GulimailWareApplication.class, args);
    }

}
