package com.edu.gulimail.order;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 *  首先引入 rabbitmq的 起步依赖
 *  进行配置 需要配置的就是 host port 虚拟主机
 *  开启功能 rabbit
 *  首先就是创建一个交换机：
 *  new DirectExchange 指定名称 指定是否是持久化
 *  指定是否是自动删除 等参数
 *   要是进行 消息的监听 使用的注解就是 @RabbitListener(属性就是 绑定的队列) 讲这个注解标注在 方法上 或者是 类型
 *   参数中就能接收到 消息 类型就是 Message 类型 这样的话 Message 中会有 原生的 消息头 和 消息体
 *   另一个能接收到的就是 信道channel
 *   或者是直接接受消息真正类型 比如是一个对象的类型 这样的话 通过spring能自动的进行消息的类型的转换
 *   queue： 可以有多个队列进行监听 只要是收到了消息队列中就是删除消息 而且只有一个能收到此消息
 *   消息接收 是一次只能接收一个 等到消息的处理完成之后在进行消息的下一次接收
 *   RabbitHandler 标注在方法上 是用来和 RabbitListener 一起使用的
 *   RabbitListener 标注在类上的时候 需要使用RabbitHandler 指定那个方法 进行消息的接受
 *   使用 RabbitHandler 能进行不同类型的消息的处理 用来重载 区分不同的消息
 *   RabbitListener 标注在类上表示监听那些队列
 *
 *
 *
 */

@SpringBootApplication
@ComponentScan("com.edu")
@EnableDiscoveryClient
@EnableRabbit
public class GulimailOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimailOrderApplication.class, args);
    }

}
