package me.yanggang.junit5test.study;

import me.yanggang.junit5test.member.MemberService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class StudyServiceTest {

    @Test
    void createStudyService() {

        MemberService memberService = mock(MemberService.class);

        StudyRepository studyRepository = mock(StudyRepository.class);

        StudyService studyService = new StudyService(memberService, studyRepository);

    }
}