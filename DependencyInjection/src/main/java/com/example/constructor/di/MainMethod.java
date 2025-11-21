package com.example.constructor.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainMethod {
    public static void main(String[] args) {
        ApplicationContext context= new ClassPathXmlApplicationContext("applicationConstructorInjection.xml");
        Car car = (Car) context.getBean("myCar");
        car.DisplayDetails();
    }
}
