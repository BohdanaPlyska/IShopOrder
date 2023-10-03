package com.example.ishoporder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IShopOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(IShopOrderApplication.class, args);
    }

}
