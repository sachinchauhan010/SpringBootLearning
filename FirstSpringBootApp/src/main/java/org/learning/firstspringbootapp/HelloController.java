package org.learning.firstspringbootapp;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String Hello(){
        return "hello Programmer";
    }

    @PostMapping("/hello")
    public  String hello(@RequestBody String name){
        return "Hello "+ name;
    }

    @GetMapping("/hello/{name}")
    public String HelloParam(@PathVariable String name){
        return "hello"+name;
    }
}
