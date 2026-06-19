package com.duongn.repository;

import com.duongn.model.Book;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private List<Book> books = new ArrayList<>();

    public void save(Book book) {
        books.add(book);
    }

    public void deleteById(String id) {
        books.removeIf(b -> b.getId().equals(id));
    }

    public Book findById(String id) {
        for (Book b : books) {
            if (b.getId().equals(id)) {
                return b;
            }
        }
        return null;
    }

    public List<Book> findAll() {
        return books;
    }
}
