package com.tlongx.sorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableSwagger2
@EnableTransactionManagement
@MapperScan("com.tlongx.sorder.*.dao")
@ComponentScan(basePackages= {"com.tlongx"})
@EnableScheduling
public class SorderApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SorderApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SorderApplication.class);
	}

}

