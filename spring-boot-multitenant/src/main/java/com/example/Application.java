package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@SpringBootApplication
@EnableJdbcHttpSession
@ComponentScan("com.example.*")
public class Application extends SpringBootServletInitializer {
//public class Application  {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
