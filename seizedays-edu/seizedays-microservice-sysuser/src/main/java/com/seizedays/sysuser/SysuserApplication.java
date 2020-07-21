package com.seizedays.sysuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author helen
 * @since 2019/3/1
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SysuserApplication {

	public static void main(String[] args) {
		SpringApplication.run(SysuserApplication.class, args);
	}
}
