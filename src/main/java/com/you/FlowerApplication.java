package com.you;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.you.mapper")
public class FlowerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowerApplication.class, args);
	}
}
