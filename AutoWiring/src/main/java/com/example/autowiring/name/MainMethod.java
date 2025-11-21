package com.example.autowiring.name;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainMethod {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationAutowiringName.xml");

        Car car = (Car) context.getBean("car");
        car.DisplayDetails();
    }
}
