package com.adatafun.base.data.center;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 数据中心 入口
 *
 * @date: 2018/2/26 下午2:51
 * @author: ironc
 * @version: 1.0
 */
@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.adatafun.base.data.center.mapper")
@SpringBootApplication
//@EnableScheduling
// 定时器暂时未使用
public class DataCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataCenterApplication.class, args);
    }

}
