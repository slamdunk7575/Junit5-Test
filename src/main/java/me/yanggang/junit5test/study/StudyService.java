package me.yanggang.junit5test.study;

import me.yanggang.junit5test.domain.Member;
import me.yanggang.junit5test.domain.Study;
import me.yanggang.junit5test.member.MemberService;

import java.util.Optional;

public class StudyService {

    private final MemberService memberService;

    private final StudyRepository studyRepository;

    public StudyService(MemberService memberService, StudyRepository studyRepository) {
        assert memberService != null;
        assert studyRepository != null;
        this.memberService = memberService;
        this.studyRepository = studyRepository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Optional<Member> member = memberService.findById(memberId);

        /*if(member == null) {
            throw new IllegalArgumentException("Member doesn't exist for id: '" + member + "'");
        }
        study.setOwner(member.get());*/

        study.setOwner(member.orElseThrow(() -> new IllegalArgumentException("Member doesn't exist for id: '" + member + "'")));

        Study newStudy = studyRepository.save(study);
        memberService.notify(newStudy);

        return newStudy;
    }
}
