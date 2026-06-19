package com.duongn.model;

public class BookRanking {
    // Fields
    private Book book;
    private int borrowCount;

    // Constructor
    public BookRanking() { }

    public BookRanking(Book book, int borrowCount) {
        this.book = book;
        this.borrowCount = borrowCount;
    }

    // Getter/Setter
    public Book getBook() {
        return book;
    }

    public int getBorrowCount() {
        return borrowCount;
    }
}
