package com.duongn.model;

public enum BookGenre {
    FICTION("Tiểu thuyết"),
    SCIENCE_FICTION("Khoa học viễn tưởng"),
    FANTASY("Giả tưởng / Kỳ ảo"),
    MYSTERY("Trinh thám"),
    HORROR("Kinh dị"),
    ROMANCE("Lãng mạn"),
    ADVENTURE("Phiêu lưu"),
    HISTORY("Lịch sử"),
    BIOGRAPHY_MEMOIR("Tiểu sử & Hồi ký"),
    SELF_HELP("Phát triển bản thân"),
    BUSINESS_FINANCE("Kinh doanh & Tài chính"),
    PSYCHOLOGY("Tâm lý học"),
    SCIENCE("Khoa học"),
    CHILDREN_YOUNG_ADULT("Thiếu nhi & Thanh thiếu niên"),
    COMICS_MANGA("Truyện tranh");

    private final String vietnameseName;

    BookGenre(String vietnameseName) {
        this.vietnameseName = vietnameseName;
    }

    public String getVietnameseName() {
        return vietnameseName;
    }
}
