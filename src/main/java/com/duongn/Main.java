package com.duongn;

import com.duongn.controller.BookController;
import com.duongn.controller.BorrowController;
import com.duongn.controller.MainMenu;
import com.duongn.controller.MemberController;
import com.duongn.controller.ReportController;
import com.duongn.repository.BookRepository;
import com.duongn.repository.BorrowRecordRepository;
import com.duongn.repository.MemberRepository;
import com.duongn.service.BookService;
import com.duongn.service.BorrowService;
import com.duongn.service.MemberService;
import com.duongn.service.ReportService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Repository
        BookRepository bookRepository = new BookRepository();
        MemberRepository memberRepository = new MemberRepository();
        BorrowRecordRepository borrowRecordRepository = new BorrowRecordRepository();

        // Service
        BookService bookService = new BookService(bookRepository);
        MemberService memberService = new MemberService(memberRepository);
        BorrowService borrowService = new BorrowService(borrowRecordRepository, bookService, memberService);
        ReportService reportService = new ReportService(bookService, memberService, borrowService);

        // Controller
        BookController bookController = new BookController(bookService, scanner);
        MemberController memberController = new MemberController(memberService, scanner);
        BorrowController borrowController = new BorrowController(borrowService, scanner);
        ReportController reportController = new ReportController(reportService, scanner);

        // MainMenu
        MainMenu mainMenu = new MainMenu(bookController, memberController, borrowController, reportController, scanner);
        mainMenu.run();

        scanner.close();
    }
}
