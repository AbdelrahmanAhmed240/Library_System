package com.example.demo2;

public class Person {
    private String name;
    private String username;
    private String password;
    private String id;

    public Person() {
    }

    public Person(String name, String username, String password, String id) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.id = id;
    }
    public Person(String name, String ID) {
        this.name = name;
        this.id = ID;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void showInfo() {
        System.out.println("Name: " + getName());
        System.out.println("ID: " + getId());
    }

}
