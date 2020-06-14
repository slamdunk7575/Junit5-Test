package me.yanggang.junit5test;

import org.junit.jupiter.api.*;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("스터디 만들기 (~˘▾˘)~ ")
    void create_new_study_1() {
        Study study = new Study();
        assertNotNull(study);
        assertEquals(StudyStatus.DRAFT, study.getStatus(),
            () -> "Study를 처음 만들면 상태값이" +  StudyStatus.DRAFT + "여야 한다.");
    }

    @Test
    @DisplayName("스터디 만들기 \uD83E\uDD29")
    void create_new_study_2() {
        System.out.println("Create2");
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