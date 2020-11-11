package com.example.human.dto;
import lombok.Data;




@Data
public class Human {

    private Long id;
    private String name;
    private int age;

    public Human() {
    }

    public Human(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setAll(Long id, String name, int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }



}

