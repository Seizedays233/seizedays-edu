package com.seizedays.edu.statistic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.seizedays.edu.statistic.mapper")
public class StatisticMain8150 {
    public static void main(String[] args) {
        SpringApplication.run(StatisticMain8150.class, args);
    }
}
