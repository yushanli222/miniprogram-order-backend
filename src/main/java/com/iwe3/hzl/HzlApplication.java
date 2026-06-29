package com.iwe3.hzl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = "com.iwe3.hzl.dao")
@EnableCaching //开启缓存
public class HzlApplication {

    public static void main(String[] args) {
        SpringApplication.run(HzlApplication.class,args);
    }
}
