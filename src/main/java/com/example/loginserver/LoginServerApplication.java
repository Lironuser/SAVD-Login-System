package com.example.loginserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginServerApplication {

    public static void main(String[] args) {
        System.out.println("(Log)-Server ON");
        SpringApplication.run(LoginServerApplication.class, args);
        System.out.println("(Log)-Server OFF");
    }
}
