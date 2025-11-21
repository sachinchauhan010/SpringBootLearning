package com.example.constructor.di;

public class Car {

    private Specification specification;

    public void DisplayDetails(){
        System.out.println("Car Details: " + specification.getMake() +" " +specification.getModel());
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }
}
