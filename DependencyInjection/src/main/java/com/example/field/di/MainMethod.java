package com.example.field.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainMethod {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationFieldInjection.xml");
        Car car= (Car) context.getBean(Car.class);
        car.DisplayDetails();
    }
}
