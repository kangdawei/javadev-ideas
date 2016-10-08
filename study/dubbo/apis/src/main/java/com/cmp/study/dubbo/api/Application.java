package com.cmp.study.dubbo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by breez on 2016/03/30.
 */

@SpringBootApplication
@ImportResource(locations = "classpath:accountcenter-consumer.xml")
@ComponentScan(basePackages = {"com.cmp.study.dubbo.api.accountcenter"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}


