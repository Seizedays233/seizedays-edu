package com.seizedays.edu.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OSSMain {
    public static void main(String[] args) {

        SpringApplication.run(OSSMain.class, args);

    }
}
