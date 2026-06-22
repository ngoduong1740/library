package com.duongn.controller;

import com.duongn.model.BookGenre;
import com.duongn.model.BookRanking;
import com.duongn.model.MemberRanking;
import com.duongn.service.ReportService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ReportController {

    private ReportService reportService;
    private Scanner scanner;

    public ReportController(ReportService reportService, Scanner scanner) {
        this.reportService = reportService;
        this.scanner = scanner;
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- THỐNG KÊ & BÁO CÁO ---");
            System.out.println("1. Số lượng sách theo thể loại");
            System.out.println("2. Top 5 sách được mượn nhiều nhất");
            System.out.println("3. Thành viên mượn nhiều nhất");
            System.out.println("4. Tổng số sách đang được mượn");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showTotalByGenre();
                    break;
                case "2":
                    showTop5Borrowed();
                    break;
                case "3":
                    showTopMember();
                    break;
                case "4":
                    System.out.println("Tổng số sách đang được mượn: " + reportService.getTotalBorrowBook());
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    private void showTotalByGenre() {
        Map<BookGenre, Integer> stats = reportService.getTotalBookByGenre();
        if (stats.isEmpty()) {
            System.out.println("Chưa có dữ liệu sách.");
            return;
        }
        for (Map.Entry<BookGenre, Integer> entry : stats.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private void showTop5Borrowed() {
        List<BookRanking> top5 = reportService.getTop5BorrowedBook();
        if (top5.isEmpty()) {
            System.out.println("Chưa có phiếu mượn nào.");
            return;
        }
        int rank = 1;
        for (BookRanking r : top5) {
            System.out.println(rank + ". " + r.getBook().getTitle() + " - " + r.getBorrowCount() + " lượt mượn");
            rank++;
        }
    }

    private void showTopMember() {
        MemberRanking top = reportService.getTopBorrowingMember();
        if (top == null) {
            System.out.println("Chưa có phiếu mượn nào.");
            return;
        }
        System.out.println(top.getMember().getName() + " - " + top.getBorrowCount() + " lượt mượn");
    }
}
