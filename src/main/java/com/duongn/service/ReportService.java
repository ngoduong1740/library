package com.duongn.service;

import com.duongn.model.Book;
import com.duongn.model.BookGenre;
import com.duongn.model.BookRanking;
import com.duongn.model.BorrowRecord;
import com.duongn.model.BorrowStatus;
import com.duongn.model.Member;
import com.duongn.model.MemberRanking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {

    private BookService bookService;
    private MemberService memberService;
    private BorrowService borrowService;

    public ReportService(BookService bookService, MemberService memberService,
            BorrowService borrowService) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.borrowService = borrowService;
    }

    public Map<BookGenre, Integer> getTotalBookByGenre() {
        Map<BookGenre, Integer> result = new HashMap<>();
        for (Book b : bookService.getAll()) {
            result.merge(b.getGenre(), b.getQuantity(), Integer::sum);
        }
        return result;
    }

    public List<BookRanking> getTop5BorrowedBook() {
        Map<String, Integer> countByBookId = new HashMap<>();
        for (BorrowRecord r : borrowService.getAll()) {
            countByBookId.merge(r.getBookId(), 1, Integer::sum);
        }

        List<BookRanking> rankings = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : countByBookId.entrySet()) {
            Book book = bookService.getById(entry.getKey());
            if (book != null) {
                rankings.add(new BookRanking(book, entry.getValue()));
            }
        }

        rankings.sort((a, b) -> b.getBorrowCount() - a.getBorrowCount());

        return rankings.size() > 5 ? rankings.subList(0, 5) : rankings;
    }

    public MemberRanking getTopBorrowingMember() {
        Map<String, Integer> countByMemberId = new HashMap<>();
        for (BorrowRecord r : borrowService.getAll()) {
            countByMemberId.merge(r.getMemberId(), 1, Integer::sum);
        }

        String topMemberId = null;
        int maxCount = -1;
        for (Map.Entry<String, Integer> entry : countByMemberId.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                topMemberId = entry.getKey();
            }
        }

        if (topMemberId == null) {
            return null;
        }

        Member member = memberService.getById(topMemberId);
        return new MemberRanking(member, maxCount);
    }

    public int getTotalBorrowBook() {
        int count = 0;
        for (BorrowRecord r : borrowService.getAll()) {
            if (r.getStatus() == BorrowStatus.BORROWED) {
                count++;
            }
        }
        return count;
    }
}
