package com.example.componentscan;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainMethod {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("componentscan.xml");
        Employee employee= context.getBean("employee", Employee.class);
        System.out.println(employee.toString());
    }
}
