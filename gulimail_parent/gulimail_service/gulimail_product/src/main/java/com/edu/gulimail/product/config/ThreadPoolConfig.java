package com.edu.gulimail.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: DDG
 * @Date: 2020/5/26 09:46
 * @Description: 业务线程池配置 使用线程池进行业务的异步编排
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor executor(ThreadPoolProperties threadPoolProperties){
      return  new ThreadPoolExecutor(threadPoolProperties.getCorePoolSize(),
                threadPoolProperties.getMaxPoolSize(),
                threadPoolProperties.getMaxPoolSize(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
    }
}
