package me.yanggang.junit5test.study;

import me.yanggang.junit5test.domain.Member;
import me.yanggang.junit5test.domain.Study;
import me.yanggang.junit5test.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;


    @Test
    void createStudyService(@Mock MemberService memberService,
                            @Mock StudyRepository studyRepository) {

        /*MemberService memberService = mock(MemberService.class);
        StudyRepository studyRepository = mock(StudyRepository.class);*/

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("yanggang@email.com");

        when(memberService.findById(any())).thenReturn(Optional.of(member));

        Study study = new Study(10, "JAVA Study");

        Optional<Member> findByIdMember = memberService.findById(1L);
        assertEquals("yanggang@email.com", memberService.findById(1L).get().getEmail());
        assertEquals("yanggang@email.com", memberService.findById(2L).get().getEmail());

        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        assertThrows(IllegalArgumentException.class, () -> {
            memberService.validate(1L);
        });

        memberService.validate(2L);
    }

}