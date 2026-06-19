# Library Management System

Ứng dụng quản lý thư viện viết bằng Java thuần (console), áp dụng mô hình OOP với kiến trúc phân tầng (Model – Repository – Service – Controller). Dự kiến nâng cấp lên Spring Boot để cung cấp REST API kết nối với Front-end trong tương lai.

## Chức năng chính

### 1. Quản lý sách
- Thêm sách mới (ID, tên, tác giả, năm xuất bản, số lượng, thể loại)
- Hiển thị danh sách sách
- Tìm kiếm sách theo tên / tác giả / thể loại
- Cập nhật thông tin sách
- Xóa sách theo ID

### 2. Quản lý thành viên
- Đăng ký thành viên mới (ID, tên, email, số điện thoại, ngày tham gia)
- Hiển thị danh sách thành viên
- Tìm kiếm thành viên theo tên / email / số điện thoại
- Cập nhật / xóa thành viên

### 3. Quản lý mượn/trả sách
- Mượn sách (kiểm tra số lượng tồn, tạo phiếu mượn)
- Trả sách (tự động tính phí trễ nếu quá hạn)
- Hiển thị danh sách phiếu mượn
- Xem lịch sử mượn sách theo từng thành viên

### 4. Thống kê & báo cáo
- Số lượng sách theo từng thể loại
- Top 5 sách được mượn nhiều nhất
- Thành viên mượn sách nhiều nhất
- Tổng số sách đang được mượn

## Kiến trúc

```
com.library/
├── model/        # Entity: Book, Member, BorrowRecord, BookRanking, MemberRanking, enum BookGenre/BorrowStatus
├── exception/     # Custom checked exception: BookNotFoundException, BookOutOfStockException, ...
├── repository/    # Lưu trữ dữ liệu (hiện tại dùng List trong bộ nhớ)
├── service/       # Toàn bộ logic nghiệp vụ (CRUD, mượn/trả, tính phí trễ, thống kê)
├── controller/    # Giao diện console (menu), gọi xuống Service
└── Main.java      # Khởi tạo và "lắp ráp" các tầng (Dependency Injection thủ công)
```

Kiến trúc được tách sẵn theo tinh thần Spring Boot để thuận tiện nâng cấp sau này:
- `repository/` → sẽ chuyển thành `JpaRepository` khi có database thật
- `service/` → giữ gần như nguyên vẹn khi lên Spring Boot
- `controller/` → console hiện tại sẽ được thay bằng `@RestController`

## Công nghệ sử dụng

- Java (console application)
- *(Định hướng tương lai)* Spring Boot, Spring Data JPA, REST API kết nối Front-end

## Trạng thái dự án

🚧 Đang phát triển — phần Model, Repository, Service, Controller cho module **Sách** đã hoàn thành mẫu; các module **Thành viên**, **Mượn/trả**, **Thống kê** đang được xây dựng theo cùng cấu trúc.
