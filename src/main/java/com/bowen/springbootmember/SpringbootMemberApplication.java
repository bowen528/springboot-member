package com.bowen.springbootmember;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"com.bowen.springbootmember.controllers","com.bowen.springbootmember.domain"})
public class SpringbootMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMemberApplication.class, args);
    }

}
