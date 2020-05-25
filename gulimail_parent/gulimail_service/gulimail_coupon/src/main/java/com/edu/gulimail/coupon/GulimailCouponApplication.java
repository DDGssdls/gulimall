package com.edu.gulimail.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

//使用nacos 最为配置中心 就是统一进行配置管理 1 就是引入依赖nacos-config 2 创建bootstrap文件
// 进行springapplication name配置 和配置config中心的地址 和在配置中心中添加一个数据集
// 就是项目名称。properties文件3动态获取配置 就是使用一个注解进行刷新@RefreshScope @value("${}")
// 配置中心的优先使用 配置中心的配置
@SpringBootApplication
@ComponentScan("com.edu")
@EnableDiscoveryClient
public class GulimailCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimailCouponApplication.class, args);
    }

}
