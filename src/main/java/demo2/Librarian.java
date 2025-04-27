package com.example.demo2;

public class Librarian extends Person{
    public Librarian() {
    }

    public Librarian(String name, String username, String password, String id) {
        super(name, username, password, id);
    }

    @Override
    public void showInfo() {
        System.out.println("Name: " + getName());
        System.out.println("ID: " + getId());
        System.out.println("Username: " + getUsername());
    }
}
