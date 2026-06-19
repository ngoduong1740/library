package com.duongn.repository;

import com.duongn.model.Member;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository {

    List<Member> members = new ArrayList<>();

    public void save(Member member) {
        members.add(member);
    }

    public void deleteById(String id) {
        members.removeIf(b -> b.getId().equals(id));
    }

    public Member findById(String id) {
        for (Member m : members) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }

    public List<Member> findAll() {
        return members;
    }
}
