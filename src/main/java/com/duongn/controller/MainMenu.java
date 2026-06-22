package com.duongn.controller;

import java.util.Scanner;

public class MainMenu {

    private BookController bookController;
    private MemberController memberController;
    private BorrowController borrowController;
    private ReportController reportController;
    private Scanner scanner;

    public MainMenu(BookController bookController, MemberController memberController,
                    BorrowController borrowController, ReportController reportController,
                    Scanner scanner) {
        this.bookController = bookController;
        this.memberController = memberController;
        this.borrowController = borrowController;
        this.reportController = reportController;
        this.scanner = scanner;
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n========= HỆ THỐNG QUẢN LÝ THƯ VIỆN =========");
            System.out.println("1. Quản lý sách");
            System.out.println("2. Quản lý thành viên");
            System.out.println("3. Mượn/trả sách");
            System.out.println("4. Thống kê & báo cáo");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    bookController.showMenu();
                    break;
                case "2":
                    memberController.showMenu();
                    break;
                case "3":
                    borrowController.showMenu();
                    break;
                case "4":
                    reportController.showMenu();
                    break;
                case "0":
                    running = false;
                    System.out.println("Tạm biệt!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }
}
