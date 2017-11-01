package com.huangwenbin.demoapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.huangwenbin.demoapi.mapper")
@SpringBootApplication
@EnableTransactionManagement
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60 * 24)
public class DemoApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApiApplication.class, args);
    }
}
