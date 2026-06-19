package com.duongn.repository;

import com.duongn.model.BorrowRecord;
import java.util.ArrayList;
import java.util.List;

public class BorrowRecordRepository {

    List<BorrowRecord> records = new ArrayList<>();

    public void save(BorrowRecord record) {
        records.add(record);
    }

    public void deleteById(String id) {
        records.removeIf(r -> r.getId().equals(id));
    }

    public BorrowRecord findById(String id) {
        for (BorrowRecord r : records) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    public List<BorrowRecord> findAll() {
        return records;
    }
}
