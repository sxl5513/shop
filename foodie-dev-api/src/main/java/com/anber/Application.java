package com.anber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Anber
 */
@SpringBootApplication
//mybatis 扫描mapper xml文件
@MapperScan("com.anber.mapper")
//扫描项目包以及相关工具包
@ComponentScan(basePackages = {"com.anber","org.n3r.idworker"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
