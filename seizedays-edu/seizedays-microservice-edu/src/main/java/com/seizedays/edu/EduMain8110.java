package com.seizedays.edu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.seizedays.edu.mapper")
//@ComponentScan(basePackages = {"com.seizedays.edu.common", "com.seizedays.edu"})
public class EduMain8110 {
    public static void main(String[] args) {
        SpringApplication.run(EduMain8110.class, args);
    }
}
