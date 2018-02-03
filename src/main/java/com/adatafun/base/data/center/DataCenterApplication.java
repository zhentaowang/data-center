package com.adatafun.base.data.center;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.adatafun.base.data.center.mapper")
@SpringBootApplication
@EnableScheduling
public class DataCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataCenterApplication.class, args);
	}

}
