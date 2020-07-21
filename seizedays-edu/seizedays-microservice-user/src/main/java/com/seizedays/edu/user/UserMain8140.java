package com.seizedays.edu.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.seizedays.edu.user.mapper")
public class UserMain8140 {
    public static void main(String[] args) {
        SpringApplication.run(UserMain8140.class, args);
    }
}
