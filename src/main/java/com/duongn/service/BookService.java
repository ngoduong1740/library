package com.duongn.service;

import com.duongn.exception.BookNotFoundException;
import com.duongn.exception.BookOutOfStockException;
import com.duongn.model.Book;
import com.duongn.model.BookGenre;
import com.duongn.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void create(Book book) throws Exception {
        if (bookRepository.findById(book.getId()) != null) {
            throw new Exception("Sách với ID " + book.getId() + " đã tồn tại");
        }
        bookRepository.save(book);
    }

    public void update(Book book) throws BookNotFoundException {
        Book existing = bookRepository.findById(book.getId());
        if (existing == null) {
            throw new BookNotFoundException("Không tìm thấy sách với ID " + book.getId());
        }
        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setPublishYear(book.getPublishYear());
        existing.setQuantity(book.getQuantity());
        existing.setGenre(book.getGenre());
    }

    public void delete(String id) throws BookNotFoundException {
        Book existing = bookRepository.findById(id);
        if (existing == null) {
            throw new BookNotFoundException("Không tìm thấy sách với ID " + id);
        }
        bookRepository.deleteById(id);
    }

    public void show() {
        List<Book> all = bookRepository.findAll();
        if (all.isEmpty()) {
            System.out.println("Chưa có sách nào trong thư viện.");
            return;
        }
        for (Book b : all) {
            System.out.printf("[%s] %s - %s (%d) - SL: %d - Thể loại: %s%n",
                    b.getId(), b.getTitle(), b.getAuthor(), b.getPublishYear(),
                    b.getQuantity(), b.getGenre());
        }
    }

    public Book getById(String id) {
        return bookRepository.findById(id);
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public List<Book> searchByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book b : bookRepository.findAll()) {
            if (b.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(b);
            }
        }
        return result;
    }

    public List<Book> searchByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book b : bookRepository.findAll()) {
            if (b.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(b);
            }
        }
        return result;
    }

    public List<Book> searchByGenre(BookGenre genre) {
        List<Book> result = new ArrayList<>();
        for (Book b : bookRepository.findAll()) {
            if (b.getGenre() == genre) {
                result.add(b);
            }
        }
        return result;
    }

    public void increaseQuantity(String id, int amount) throws BookNotFoundException {
        Book book = bookRepository.findById(id);
        if (book == null) {
            throw new BookNotFoundException("Không tìm thấy sách với ID " + id);
        }
        book.setQuantity(book.getQuantity() + amount);
    }

    public void decreaseQuantity(String id, int amount) throws BookNotFoundException, BookOutOfStockException {
        Book book = bookRepository.findById(id);
        if (book == null) {
            throw new BookNotFoundException("Không tìm thấy sách với ID " + id);
        }
        if (book.getQuantity() < amount) {
            throw new BookOutOfStockException("Sách \"" + book.getTitle() + "\" không đủ số lượng để mượn");
        }
        book.setQuantity(book.getQuantity() - amount);
    }
}
