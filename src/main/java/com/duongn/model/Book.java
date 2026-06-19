package com.duongn.model;

public class Book {
    // Fields
    private String id;
    private String title;
    private String author;
    private int publishYear;
    private int quantity;
    private BookGenre genre;

    // Constructor
    public Book() { }

    public Book(String id, String title, String author, int publishYear, int quantity, BookGenre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.quantity = quantity;
        this.genre = genre;
    }

    // Getter/Setter
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        }
    }

    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre;
    }
}
