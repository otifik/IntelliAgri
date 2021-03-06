package com.jit.aquaculture;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@Slf4j
//@Log4j2
@MapperScan("com.jit.aquaculture.mapper")
@EnableScheduling
@EnableEurekaClient
@EnableFeignClients
@EnableDiscoveryClient
public class AquacultureApplication {

    public static void main(String[] args) {
        SpringApplication.run(AquacultureApplication.class, args);
        log.info("aquaculture application is running.........");

    }

}
