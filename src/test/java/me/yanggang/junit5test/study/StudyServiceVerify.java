package me.yanggang.junit5test.study;

import me.yanggang.junit5test.domain.Member;
import me.yanggang.junit5test.domain.Study;
import me.yanggang.junit5test.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudyServiceVerify {

    @Mock MemberService memberService;
    @Mock StudyRepository studyRepository;

    @Test
    void createNewStudy() {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("yanggang@email.com");

        Study study = new Study(10, "테스트");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);
        assertEquals(member, study.getOwner());

        verify(memberService, times(1)).notify(study);
        verify(memberService, never()).validate(any());
    }

}
