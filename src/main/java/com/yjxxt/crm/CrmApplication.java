package com.yjxxt.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@SpringBootApplication
@MapperScan("com.yjxxt.crm.mapper")
public class CrmApplication implements WebMvcConfigurer {
    private static Logger logger = LoggerFactory.getLogger(CrmApplication.class);

    public static void main(String[] args) {        //启动日志
        logger.info("crm启动");
        SpringApplication.run(CrmApplication.class, args);
    }
}
