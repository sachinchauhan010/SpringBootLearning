package com.example.autowiring;

public class Car {
    private Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public void start(){
        engine.run();
        System.out.println("Car started...");
    }
}
