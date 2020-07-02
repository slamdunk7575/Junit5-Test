package me.yanggang.junit5test.study;

import me.yanggang.junit5test.domain.Member;
import me.yanggang.junit5test.domain.Study;
import me.yanggang.junit5test.member.MemberService;

public class StudyService {

    private final MemberService memberService;

    private final StudyService studyService;

    public StudyService(MemberService memberService, StudyService studyService) {
        this.memberService = memberService;
        this.studyService = studyService;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Member member = memberService.findById(memberId);
        if(member == null) {
            throw new IllegalArgumentException("Member doesn't exist for id: '" + member + "'");
        }
        study.setOwner(member);

        return study;
    }
}
