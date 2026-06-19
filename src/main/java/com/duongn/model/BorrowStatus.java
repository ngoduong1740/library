package com.duongn.model;

public enum BorrowStatus {
    BORROWED("Đang mượn"),
    RETURNED("Đã trả");

    private final String vietnameseName;

    BorrowStatus(String vietnameseName) {
        this.vietnameseName = vietnameseName;
    }

    public String getVietnameseName() {
        return vietnameseName;
    }
}
