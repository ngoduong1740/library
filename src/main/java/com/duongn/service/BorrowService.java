package com.duongn.service;

import com.duongn.exception.BookNotFoundException;
import com.duongn.exception.BookOutOfStockException;
import com.duongn.exception.BorrowRecordNotFoundException;
import com.duongn.exception.InvalidBorrowStatusException;
import com.duongn.exception.MemberNotFoundException;
import com.duongn.model.Book;
import com.duongn.model.BorrowRecord;
import com.duongn.model.BorrowStatus;
import com.duongn.model.Member;
import com.duongn.repository.BorrowRecordRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BorrowService {

    private static final double LATE_FEE_PER_DAY = 5000;

    private BorrowRecordRepository borrowRepository;
    private BookService bookService;
    private MemberService memberService;

    private int counter = 1;

    public BorrowService(BorrowRecordRepository borrowRepository,
            BookService bookService,
            MemberService memberService) {
        this.borrowRepository = borrowRepository;
        this.bookService = bookService;
        this.memberService = memberService;
    }

    public void create(String bookId, String memberId, LocalDate dueDate) throws Exception {
        Member member = memberService.getById(memberId);
        if (member == null) {
            throw new MemberNotFoundException("Không tìm thấy thành viên với ID " + memberId);
        }

        Book book = bookService.getById(bookId);
        if (book == null) {
            throw new BookNotFoundException("Không tìm thấy sách với ID " + bookId);
        }

        String id = "BR" + counter;
        counter++;

        BorrowRecord record = new BorrowRecord(
            id,
            bookId,
            memberId,
            LocalDate.now(),
            dueDate,
            null,
            BorrowStatus.BORROWED,
            0
        );
        borrowRepository.save(record);

        bookService.decreaseQuantity(bookId, 1);
    }

    public void update(String id, LocalDate dueDate) throws BorrowRecordNotFoundException {
        BorrowRecord record = borrowRepository.findById(id);
        if (record == null) {
            throw new BorrowRecordNotFoundException("Không tìm thấy phiếu mượn với ID " + id);
        }
        record.setDueDate(dueDate);
    }

    public BorrowRecord returnBook(String id, LocalDate returnDate) throws Exception {
        BorrowRecord record = borrowRepository.findById(id);
        if (record == null) {
            throw new BorrowRecordNotFoundException("Không tìm thấy phiếu mượn với ID " + id);
        }
        if (record.getStatus() == BorrowStatus.RETURNED) {
            throw new InvalidBorrowStatusException("Phiếu mượn " + id + " đã được trả trước đó");
        }
        record.setReturnDate(returnDate);

        long daysLate = ChronoUnit.DAYS.between(record.getDueDate(), returnDate);
        double lateFee = daysLate > 0 ? daysLate * LATE_FEE_PER_DAY : 0;
        record.setLateFee(lateFee);

        record.setStatus(BorrowStatus.RETURNED);

        bookService.increaseQuantity(record.getBookId(), 1);

        return record;
    }

    public void delete(String id) throws BorrowRecordNotFoundException {
        BorrowRecord record = borrowRepository.findById(id);
        if (record == null) {
            throw new BorrowRecordNotFoundException("Không tìm thấy phiếu mượn với ID " + id);
        }
        borrowRepository.deleteById(id);
    }

    public void show() {
        List<BorrowRecord> records = borrowRepository.findAll();
        if (records.isEmpty()) {
            System.out.println("Chưa có phiếu mượn nào.");
            return;
        }
        for(BorrowRecord r : records) {
            printRecord(r);
        }
    }

    public List<BorrowRecord> getHistoryByMember(String memberId) {
        List<BorrowRecord> result = new ArrayList<>();
        for (BorrowRecord r : borrowRepository.findAll()) {
            if (r.getMemberId().equals(memberId)) {
                result.add(r);
            }
        }
        return result;
    }

    public boolean isBookCurrentlyBorrowed(String bookId) {
        for (BorrowRecord r : borrowRepository.findAll()) {
            if (r.getBookId().equals(bookId) && r.getStatus() == BorrowStatus.BORROWED) {
                return true;
            }
        }
        return false;
    }

    public List<BorrowRecord> getAll() {
        return borrowRepository.findAll();
    }

    private void printRecord(BorrowRecord r) {
        Book book = bookService.getById(r.getBookId());
        Member member = memberService.getById(r.getMemberId());
        String returnDateStr = r.getReturnDate() == null ? "Chưa trả" : r.getReturnDate().toString();

        System.out.printf("[%s] Sách: %s - Người mượn: %s - Mượn: %s - Hạn: %s - Trả: %s - Trạng thái: %s - Phí trễ: %.0f%n",
                r.getId(),
                book != null ? book.getTitle() : "(sách đã xóa)",
                member != null ? member.getName() : "(thành viên đã xóa)",
                r.getBorrowDate(), r.getDueDate(), returnDateStr,
                r.getStatus(), r.getLateFee());
    }
}
