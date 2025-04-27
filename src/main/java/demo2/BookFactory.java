package com.example.demo2;

public class BookFactory {

    public static Book createBook(String title, double price, int numCopies) {
        return new Book(title, null, price, null, numCopies);
    }
}
