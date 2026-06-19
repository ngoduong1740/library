package com.duongn.model;

public class MemberRanking {
    // Fields
    private Member member;
    private int borrowCount;

    // Constructor
    public MemberRanking() { }

    public MemberRanking(Member member, int borrowCount) {
        this.member = member;
        this.borrowCount = borrowCount;
    }

    // Getter/Setter
    public Member getMember() {
        return member;
    }

    public int getBorrowCount() {
        return borrowCount;
    }
}
