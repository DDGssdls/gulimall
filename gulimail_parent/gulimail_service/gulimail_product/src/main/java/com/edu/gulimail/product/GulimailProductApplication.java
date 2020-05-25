package com.edu.gulimail.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.edu")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.edu.gulimail.product.feign"})
public class GulimailProductApplication {

    /*
    *  使用 jsr 303进行校验 两种自定义提示的方式 一种就是在注解中自定义message 另一种方式就是使用配置文件进行配置
    *  但是一般都是使用全局的配置获取 在需要的字段上加上valid注解
    *  使用的是一种高级的分组校验： 就是 在校验注解上使用 groups 属性
    * 1.
    * @NotNull(message = "添加的时候必须指定品牌的id", groups = {UpdateGroup.class})
	  @Null(message = "新增的时候不能指定品牌的id", groups = {AddGroup.class}) 需要在给定的注解上加上需要校验的分组：
	  * 2. 就是不能使用 valid 而是使用Validated(AddGroup.class)注解 注解中有一个属性就是 指定校验的分组 但是没有添加分组的就不进行校验
	  * 要是没有指定分组的校验注解 在分组的情况下是不能生效的 需要特别的注意 只能是在 没有指定分组的时候生效 Validated
	  * 3 就是自定义校验注解 ： 没使用一个校验的注解 需要关联一个校验器：
	  *  使用方式：
	  * 1 自定义校验注解 2 自定义校验器 3 进行两者的关联
	  *  自定义校验注解： 需要有三种属性 1 message 就是校验失败的提示信息： 默认的是到配置文件中取 2 就是   groups分组
	  * 3 就是payload 负载信息 在message配置文件中 不需要添加上""
	  * mybatis plus 使用分页查询就是三步 创建一个page 对象 构造器中传入 page 和 size 使用service中的page方法 传入page对象
	  * 就是将数据封装到page对象 中 使用 get 方法获取 列数据 和 getPage等
    *
    *  feign:远程服务调用功能： 三步
                * 1调用方和 被调用方都需要进行服务的注册 和 发现
                * 2 调用方使用接口进行调用 接口上使用注解： feignClient 指定上服务的名称
                * 3 调用方需要开启远程调用的功能： enableFeignClients 使用属性basePackage 指定 调用的接口的位置reference
                *
                * 使用 远程的功能进行 文件上传 使用的是 put -r 进行上传
                * 本地缓存 出现的两种问题一种就是 使用本地缓存 会出现 容量问题 另一种就是会出现数据的一致性问题
                * 而是用缓存中间件 就能避免 本地缓存的出现的容量问题 同时也能避免 一定程度上的数据一致性的问题
    * */

    public static void main(String[] args) {
        SpringApplication.run(GulimailProductApplication.class, args);
    }

}
