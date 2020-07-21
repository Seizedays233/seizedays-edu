package com.seizedays.edu.user.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author helen
 * @since 2019/6/24
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

	@Bean
	public Docket adminApiConfig(){
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("adminApi")
				.apiInfo(adminApiInfo())
				.select()
				.paths(Predicates.and(PathSelectors.regex("/admin/.*")))
				.build();
	}

	@Bean
	public Docket webApiConfig(){
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("webApi")
				.apiInfo(webApiInfo())
				.select()
				.paths(Predicates.not(PathSelectors.regex("/admin/.*")))
				.paths(Predicates.not(PathSelectors.regex("/error.*")))
				.build();
	}

	private ApiInfo adminApiInfo(){

		return new ApiInfoBuilder()
				.title("后台管理系统-用户中心API文档")
				.description("本文档描述了后台管理系统用户中心微服务接口定义")
				.version("1.0")
				.contact(new Contact("Seizedays", "http://github.com/seizedays233", "seizedays@163.com"))
				.build();
	}

	private ApiInfo webApiInfo(){

		return new ApiInfoBuilder()
				.title("网站-用户中心API文档")
				.description("本文档描述了用户中心微服务接口定义")
				.version("1.0")
				.contact(new Contact("Seizedays", "http://github.com/seizedays233", "seizedays@163.com"))
				.build();
	}
}
