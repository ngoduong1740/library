package com.duongn.controller;

import com.duongn.model.BorrowRecord;
import com.duongn.service.BorrowService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class BorrowController {

    private BorrowService borrowService;
    private Scanner scanner;

    public BorrowController(BorrowService borrowService, Scanner scanner) {
        this.borrowService = borrowService;
        this.scanner = scanner;
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- QUẢN LÝ MƯỢN/TRẢ SÁCH ---");
            System.out.println("1. Mượn sách");
            System.out.println("2. Trả sách");
            System.out.println("3. Hiển thị danh sách phiếu mượn");
            System.out.println("4. Xem lịch sử mượn của 1 thành viên");
            System.out.println("5. Cập nhật hạn trả");
            System.out.println("6. Xóa phiếu mượn");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        borrowBook();
                        break;
                    case "2":
                        returnBook();
                        break;
                    case "3":
                        borrowService.show();
                        break;
                    case "4":
                        showHistory();
                        break;
                    case "5":
                        updateDueDate();
                        break;
                    case "6":
                        deleteRecord();
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Lỗi: Ngày không đúng định dạng (yyyy-MM-dd).");
            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }
    }

    private void borrowBook() throws Exception {
        System.out.print("ID sách: ");
        String bookId = scanner.nextLine();
        System.out.print("ID thành viên: ");
        String memberId = scanner.nextLine();
        System.out.print("Hạn trả (yyyy-MM-dd): ");
        LocalDate dueDate = LocalDate.parse(scanner.nextLine());

        borrowService.create(bookId, memberId, dueDate);
        System.out.println("Tạo phiếu mượn thành công.");
    }

    private void returnBook() throws Exception {
        System.out.print("ID phiếu mượn: ");
        String id = scanner.nextLine();
        System.out.print("Ngày trả (yyyy-MM-dd, Enter = hôm nay): ");
        String input = scanner.nextLine();
        LocalDate returnDate = input.isEmpty() ? LocalDate.now() : LocalDate.parse(input);

        BorrowRecord record = borrowService.returnBook(id, returnDate);
        System.out.println("Trả sách thành công. Phí trễ: " + record.getLateFee());
    }

    private void showHistory() {
        System.out.print("Nhập ID thành viên: ");
        String memberId = scanner.nextLine();
        List<BorrowRecord> history = borrowService.getHistoryByMember(memberId);

        if (history.isEmpty()) {
            System.out.println("Thành viên này chưa có lịch sử mượn sách.");
        } else {
            for (BorrowRecord r : history) {
                System.out.println(r.getId() + " - Sách: " + r.getBookId()
                        + " - Trạng thái: " + r.getStatus());
            }
        }
    }

    private void updateDueDate() throws Exception {
        System.out.print("ID phiếu mượn: ");
        String id = scanner.nextLine();
        System.out.print("Hạn trả mới (yyyy-MM-dd): ");
        LocalDate dueDate = LocalDate.parse(scanner.nextLine());

        borrowService.update(id, dueDate);
        System.out.println("Cập nhật hạn trả thành công.");
    }

    private void deleteRecord() throws Exception {
        System.out.print("ID phiếu mượn cần xóa: ");
        String id = scanner.nextLine();
        borrowService.delete(id);
        System.out.println("Xóa phiếu mượn thành công.");
    }
}
