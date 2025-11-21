package com.example.field.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {
    @Autowired
    private Specification specification;

    public void DisplayDetails(){
        System.out.println("Car Details: " + specification.getMake()+ " "+  specification.getModel());
    }
}
