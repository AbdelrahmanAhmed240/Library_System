package com.example.demo2;

import java.util.Date;

public class BorrowedBook {

    private String title;
    private String studentName;
    private double price;
    private Date borrowedDate;
    private Date dueDate;
    private Date returnDate;

    public BorrowedBook(String title, String studentName, double price, Date borrowedDate, Date returnDate, Date dueDate) {
        this.title = title;
        this.studentName = studentName;
        this.price = price;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public String getTitle() {
        return title;
    }

    public String getStudentName() {
        return studentName;
    }

    public double getPrice() {
        return price;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }
}
