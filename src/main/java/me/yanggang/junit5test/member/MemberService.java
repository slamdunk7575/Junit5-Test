package me.yanggang.junit5test.member;

import me.yanggang.junit5test.domain.Member;

import java.util.Optional;

public interface MemberService {
    // Member findById(Long memberId) throws MemberNotFoundException;
    Optional<Member> findById(Long memberId);

    void validate(Long memberId);
}
