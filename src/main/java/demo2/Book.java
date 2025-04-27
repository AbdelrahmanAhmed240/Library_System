package com.example.demo2;

public class Book {
    private String title;
    private String authorName;
    private String ISBN;
    private int numCopies;
    private double price;

    public Book(String title, String authorName, String ISBN, int numCopies, double price) {
        this.title = title;
        this.authorName = authorName;
        this.ISBN = ISBN;
        this.numCopies = numCopies;
        this.price = price;
    }

    public Book(String title, double price) {
        this.title = title;
        this.price = price;
    }

    public Book(String bookName, String author, double price, String isbn, int numberOfCopies) {
        title = bookName;
        this.authorName = author;
        this.price = price;
        this.numCopies = numberOfCopies;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getNumCopies() {
        return numCopies;
    }

    public double getPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setNumCopies(int numCopies) {
        this.numCopies = numCopies;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", numCopies=" + numCopies +
                ", price=" + price +
                '}';
    }
}
