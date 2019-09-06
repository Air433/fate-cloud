package com.fate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fate.modules.sys.dao")
public class FateCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(FateCloudApplication.class, args);
	}
}
