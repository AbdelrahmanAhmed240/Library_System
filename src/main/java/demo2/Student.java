package com.example.demo2;

public class Student {
    private String name;
    private String id;
    private String username;
    private String password;
    private int borrowedBooks;
    private final int maxBooksBorrowed = 5;
    private double fines = 0;

    public Student() {
    }

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
    }

    // Constructor with all details
    public Student(String id, String name, String username, String password, int borrowedBooks, double fines) {
        this.name = name;
        this.id = id;
        this.username = username;
        this.password = password;
        this.borrowedBooks = borrowedBooks;
        this.fines = fines;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getBorrowedBooks() {
        return borrowedBooks;
    }

    public int getMaxBooksBorrowed() {
        return maxBooksBorrowed;
    }

    public double getFines() {
        return fines;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBorrowedBooks(int borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public void setFines(double fines) {
        this.fines = fines;
    }

    public void showInfo() {
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Username: " + username);
        System.out.println("Borrowed Books: " + borrowedBooks);
        System.out.println("Fines: " + fines);
    }

    public boolean canBorrowMoreBooks() {
        return borrowedBooks < maxBooksBorrowed;
    }

    public void borrowBook() {
        if (canBorrowMoreBooks()) {
            borrowedBooks++;
        } else {
            System.out.println("Maximum borrowed books reached.");
        }
    }

    public void returnBook() {
        if (borrowedBooks > 0) {
            borrowedBooks--;
        } else {
            System.out.println("No borrowed books to return.");
        }
    }

    public void addFines(double amount) {
        fines += amount;
    }

    public void clearFines() {
        fines = 0;
    }
}
