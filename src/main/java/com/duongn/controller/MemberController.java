package com.duongn.controller;

import com.duongn.model.Member;
import com.duongn.service.MemberService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MemberController {

    private MemberService memberService;
    private Scanner scanner;

    public MemberController(MemberService memberService, Scanner scanner) {
        this.memberService = memberService;
        this.scanner = scanner;
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- QUẢN LÝ THÀNH VIÊN ---");
            System.out.println("1. Đăng ký thành viên mới");
            System.out.println("2. Hiển thị danh sách thành viên");
            System.out.println("3. Tìm kiếm thành viên");
            System.out.println("4. Cập nhật thành viên");
            System.out.println("5. Xóa thành viên");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        addMember();
                        break;
                    case "2":
                        memberService.show();
                        break;
                    case "3":
                        searchMember();
                        break;
                    case "4":
                        updateMember();
                        break;
                    case "5":
                        deleteMember();
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

    private void addMember() throws Exception{
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Tên: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Số điện thoại: ");
        String phone = scanner.nextLine();

        Member member = new Member(id, name, email, phone, LocalDate.now());
        memberService.create(member);
        System.out.println("Đăng ký thành viên thành công.");
    }

    private void searchMember() {
        System.out.println("Tìm theo: 1. Tên   2. Email   3. Số điện thoại");
        System.out.print("Chọn: ");
        String option = scanner.nextLine();
        List<Member> results;

        switch (option) {
            case "1":
                System.out.print("Nhập tên cần tìm: ");
                results = memberService.searchByName(scanner.nextLine());
                break;
            case "2":
                System.out.print("Nhập email cần tìm: ");
                results = memberService.searchByEmail(scanner.nextLine());
                break;
            case "3":
                System.out.print("Nhập số điện thoại cần tìm: ");
                results = memberService.searchByPhoneNumber(scanner.nextLine());
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ.");
                return;
        }

        if (results.isEmpty()) {
            System.out.println("Không tìm thấy thành viên nào.");
        } else {
            for (Member m : results) {
                System.out.println(m.getId() + " - " + m.getName() + " - " + m.getEmail());
            }
        }
    }

    private void updateMember() throws Exception {
        System.out.print("Nhập ID thành viên cần cập nhật: ");
        String id = scanner.nextLine();
        Member existing = memberService.getById(id);
        if (existing == null) {
            System.out.println("Không tìm thấy thành viên.");
            return;
        }

        System.out.print("Tên mới (Enter để giữ nguyên \"" + existing.getName() + "\"): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            existing.setName(name);
        }

        System.out.print("Email mới (Enter để giữ nguyên \"" + existing.getEmail() + "\"): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            existing.setEmail(email);
        }

        System.out.print("SĐT mới (Enter để giữ nguyên \"" + existing.getPhoneNumber() + "\"): ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) {
            existing.setPhoneNumber(phone);
        }

        memberService.update(existing);
        System.out.println("Cập nhật thành công.");
    }

    private void deleteMember() throws Exception {
        System.out.print("Nhập ID thành viên cần xóa: ");
        String id = scanner.nextLine();
        memberService.delete(id);
        System.out.println("Xóa thành viên thành công.");
    }
}
