package me.yanggang.junit5test;

import org.junit.jupiter.api.*;

import javax.swing.text.Style;
import java.time.Duration;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("스터디 만들기 (~˘▾˘)~ ")
    void create_new_study_1() {
        Study study = new Study(2);

        assertAll(
            () -> assertNotNull(study),
            () -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
                () -> "Study를 처음 만들면 상태값은 " +  StudyStatus.DRAFT + " 여야 한다."),
            () -> assertTrue(study.getLimit() > 0, "스터디의 최대 참석 인원은 0보다 커야  한다.")
        );
    }


    @Test
    @DisplayName("스터디 만들기 \uD83E\uDD29")
    void create_new_study_2() {
        System.out.println("Create2");
    }

    @Test
    @DisplayName("예외 메시지 확인")
    void create_new_study_3() {
        IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        assertEquals("limit은 0보다 커야 한다.", exception.getMessage());
    }

    @Test
    @DisplayName("타임 아웃 테스트")
    void create_new_study_4() {
        assertTimeout(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(300);
        });
    }


    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("Before Each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("After Each");
    }

}