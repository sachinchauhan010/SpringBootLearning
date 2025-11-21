package com.example.autowiring.name;

public class Car {
    private Specification specification;

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public void DisplayDetails(){
        System.out.println("Car Details: "+ specification.getMake()+" "+specification.getModel());
    }
}
