package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@ServletComponentScan
public class AppApiLoginDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApiLoginDemoApplication.class, args);
    }
}
