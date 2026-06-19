package com.duongn.controller;

import com.duongn.model.Book;
import com.duongn.model.BookGenre;
import com.duongn.service.BookService;

import java.util.List;
import java.util.Scanner;

public class BookController {

    private BookService bookService;
    private Scanner scanner;

    public BookController(BookService bookService, Scanner scanner) {
        this.bookService = bookService;
        this.scanner = scanner;
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- QUẢN LÝ SÁCH ---");
            System.out.println("1. Thêm sách");
            System.out.println("2. Hiển thị danh sách sách");
            System.out.println("3. Tìm kiếm theo tên");
            System.out.println("4. Cập nhật sách");
            System.out.println("5. Xóa sách");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        addBook();
                        break;
                    case "2":
                        bookService.show();
                        break;
                    case "3":
                        searchBook();
                        break;
                    case "4":
                        updateBook();
                        break;
                    case "5":
                        deleteBook();
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ.");
                }
            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }
    }

    private void addBook() throws Exception {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Tên sách: ");
        String title = scanner.nextLine();
        System.out.print("Tác giả: ");
        String author = scanner.nextLine();
        System.out.print("Năm xuất bản: ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Số lượng: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.print("Thể loại (vd: FICTION, SCIENCE...): ");
        BookGenre genre = BookGenre.valueOf(scanner.nextLine().toUpperCase());

        Book book = new Book(id, title, author, year, quantity, genre);
        bookService.create(book);
        System.out.println("Thêm sách thành công.");
    }

    private void searchBook() {
        System.out.println("Tìm theo: 1. Tên   2. Tác giả   3. Thể loại");
        System.out.print("Chọn: ");
        String option = scanner.nextLine();
        List<Book> results;

        switch (option) {
            case "1":
                System.out.print("Nhập tên sách cần tìm: ");
                results = bookService.searchByTitle(scanner.nextLine());
                break;
            case "2":
                System.out.print("Nhập tên tác giả cần tìm: ");
                results = bookService.searchByAuthor(scanner.nextLine());
                break;
            case "3":
                // Enum: người dùng không đoán được giá trị hợp lệ -> phải in ra trước
                System.out.print("Nhập thể loại (" + genreOptionsAsString() + "): ");
                String genreInput = scanner.nextLine().trim().toUpperCase();
                BookGenre genre;
                try {
                    genre = BookGenre.valueOf(genreInput);
                } catch (IllegalArgumentException e) {
                    System.out.println("Thể loại không hợp lệ.");
                    return;
                }
                results = bookService.searchByGenre(genre);
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ.");
                return;
        }

        if (results.isEmpty()) {
            System.out.println("Không tìm thấy sách nào.");
        } else {
            for (Book b : results) {
                System.out.println(b.getId() + " - " + b.getTitle() + " - " + b.getAuthor()
                        + " - " + b.getGenre());
            }
        }
    }

    private String genreOptionsAsString() {
        BookGenre[] values = BookGenre.values();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            sb.append(values[i]);
            if (i < values.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private void updateBook() throws Exception {
        System.out.print("Nhập ID sách cần cập nhật: ");
        String id = scanner.nextLine();
        Book existing = bookService.getById(id);
        if (existing == null) {
            System.out.println("Không tìm thấy sách.");
            return;
        }
        System.out.print("Tên sách mới (Enter để giữ nguyên \"" + existing.getTitle() + "\"): ");
        String title = scanner.nextLine();
        if (!title.isEmpty()) {
            existing.setTitle(title);
        }

        bookService.update(existing);
        System.out.println("Cập nhật thành công.");
    }

    private void deleteBook() throws Exception {
        System.out.print("Nhập ID sách cần xóa: ");
        String id = scanner.nextLine();
        bookService.delete(id);
        System.out.println("Xóa sách thành công.");
    }
}
