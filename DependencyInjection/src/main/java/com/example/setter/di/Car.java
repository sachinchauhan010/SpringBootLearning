package com.example.setter.di;

import com.example.setter.di.Specification;

public class Car {
    private Specification specification;

    //Setter method for dependency Injection
    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public void DisplayDetails(){
        System.out.println("Car Details: "+ specification.getMake()+ " "+ specification.getModel());
    }
}
