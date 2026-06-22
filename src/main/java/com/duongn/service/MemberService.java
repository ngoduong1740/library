package com.duongn.service;

import com.duongn.exception.MemberNotFoundException;
import com.duongn.model.Member;
import com.duongn.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void create(Member member) throws Exception {
        if (memberRepository.findById(member.getId()) != null) {
            throw new Exception("Thành viên với ID " + member.getId() + " đã tồn tại");
        }
        memberRepository.save(member);
    }

    public void update(Member member) throws MemberNotFoundException {
        Member existing = memberRepository.findById(member.getId());
        if (existing == null) {
            throw new MemberNotFoundException("Không tìm thấy thành viên với ID " + member.getId());
        }
        existing.setName(member.getName());
        existing.setEmail(member.getEmail());
        existing.setPhoneNumber(member.getPhoneNumber());
    }

    public void delete(String id) throws MemberNotFoundException {
        Member existing = memberRepository.findById(id);
        if (existing == null) {
            throw new MemberNotFoundException("Không tìm thấy thành viên với ID " + id);
        }
        memberRepository.deleteById(id);
    }

    public void show() {
        List<Member> all = memberRepository.findAll();
        if (all.isEmpty()) {
            System.out.println("Chưa có thành viên nào trong hệ thống");
            return;
        }
        for (Member m : all) {
            System.out.printf("[%s] %s - %s - %s - Ngày tham gia: %s%n",
                    m.getId(), m.getName(), m.getEmail(), m.getPhoneNumber(),
                    m.getJoinDate());
        }
    }

    public Member getById(String id) {
        return memberRepository.findById(id);
    }

    public List<Member> getAll() {
        return memberRepository.findAll();
    }

    public List<Member> searchByName(String name) {
        List<Member> result = new ArrayList<>();
        for (Member m : memberRepository.findAll()) {
            if (m.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(m);
            }
        }
        return result;
    }

    public List<Member> searchByEmail(String email) {
        List<Member> result = new ArrayList<>();
        for (Member m : memberRepository.findAll()) {
            if (m.getEmail().toLowerCase().contains(email.toLowerCase())) {
                result.add(m);
            }
        }
        return result;
    }

    public List<Member> searchByPhoneNumber(String phoneNumber) {
        List<Member> result = new ArrayList<>();
        for (Member m : memberRepository.findAll()) {
            if (m.getPhoneNumber().contains(phoneNumber)) {
                result.add(m);
            }
        }
        return result;
    }
}
