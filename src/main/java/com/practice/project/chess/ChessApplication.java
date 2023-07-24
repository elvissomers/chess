package com.practice.project.chess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class ChessApplication {
//    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        SpringApplication.run(ChessApplication.class, args);
//        displayAllBeans();

    }

//    public static void displayAllBeans() {
//        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
//        for(String beanName : allBeanNames) {
//            System.out.println(beanName);
//        }
//    }

}