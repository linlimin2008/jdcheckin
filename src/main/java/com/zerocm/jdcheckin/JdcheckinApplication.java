package com.zerocm.jdcheckin;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //开启定时
@MapperScan("com.zerocm.jdcheckin")
@EnableAsync
public class JdcheckinApplication {
    private static Logger logger = LoggerFactory.getLogger(JdcheckinApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JdcheckinApplication.class, args);
    }
}
