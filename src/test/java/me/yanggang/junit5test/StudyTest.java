package me.yanggang.junit5test;

import me.yanggang.junit5test.domain.Study;
import me.yanggang.junit5test.study.StudyStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;


// @ExtendWith(FindSlowTestExtension.class)
// @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudyTest {

    int value = 1;

    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);

    @Order(11)
    @FastTest
    @DisplayName("스터디 만들기 (~˘▾˘)~ ")
    void create_new_study_1() {
        Study study = new Study(2);
        System.out.println("FAST");
        assertAll(
            () -> assertNotNull(study),
            () -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
                () -> "Study를 처음 만들면 상태값은 " +  StudyStatus.DRAFT + " 여야 한다."),
            () -> assertTrue(study.getLimitCount() > 0, "스터디의 최대 참석 인원은 0보다 커야  한다.")
        );
    }


    @Order(10)
    @SlowTest
    @DisplayName("스터디 만들기 \uD83E\uDD29")
    // @DisabledOnOs(OS.MAC)
    // @DisabledOnJre(JRE.JAVA_8)
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "yanggang")
    void create_new_study_2() {
        System.out.println("SLOW");
    }

    @Order(9)
    @Test
    @DisplayName("예외 메시지 확인")
    void create_new_study_3() {
        IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        assertEquals("limit은 0보다 커야 한다.", exception.getMessage());
    }

    @Order(8)
    @Test
    @DisplayName("타임 아웃 테스트")
    void create_new_study_4() {
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            new Study(10);
            Thread.sleep(300);
        });

        // ThreadLocal을 사용하여 별개의 Thread로 테스트 코드를 실행하면 롤백이 안되고 DB에 반영되는 문제가 있음. 스프링 트랜잭션도 ThreadLocal을 기본전략으로 사용함.
    }

    @Order(7)
    @Test
    @DisplayName("Assertj 사용해보기")
    void create_new_study_5() {
        Study study = new Study(10);
        assertThat(study.getLimitCount()).isGreaterThan(0);
    }

    @Order(6)
    @Test
    @DisplayName("조건에 따라 테스트 실행하기")
    void create_new_study_6() {

        String test_env = System.getenv("TEST_ENV");
        System.out.println(test_env);

        assumeTrue("LOCAL".equalsIgnoreCase(test_env));

        Study study = new Study(10);
        assertThat(study.getLimitCount()).isGreaterThan(0);
    }


    @Order(5)
    @Test
    @DisplayName("조건에 따라 테스트 실행하기")
    // @EnabledOnOs({OS.MAC, OS.LINUX})
    // @EnabledOnJre(JRE.JAVA_8)
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    void create_new_study_7() {

        String test_env = System.getenv("TEST_ENV");

        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
            System.out.println("LOCAL");
            Study study = new Study(100);
            assertThat(study.getLimitCount()).isGreaterThan(0);
        });

        assumingThat("yanggang".equalsIgnoreCase(test_env), () -> {
            System.out.println("yanggang");
            Study study = new Study(10);
            assertThat(study.getLimitCount()).isGreaterThan(0);
        });
    }


    @Order(4)
    @DisplayName("스터디 만들기")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println("TEST " + repetitionInfo.getCurrentRepetition() + "/"
            + repetitionInfo.getTotalRepetitions());
    }


    @Order(3)
    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    // @ValueSource(strings = {"날씨가", "많이", "더워지고", "있네요."})
    // @ValueSource(ints = {10, 20, 30, 40})
    // @NullAndEmptySource
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterizedTest(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study);
    }

    // 여러 인자를 변환
    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            return new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        }
    }


    // 하나의 인자를 변환
    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can Only Convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }


    @Order(2)
    @FastTest
    @Disabled
    @DisplayName("TEST 인스턴스 1")
    void create_new_study_8() {
        System.out.println(this);
        System.out.println(value++);
    }


    @Order(1)
    @Test
    // @SlowTest
    // @DisplayName("TEST 인스턴스 2")
    void create_new_study_9() throws InterruptedException {
        Thread.sleep(1005L);
        System.out.println(this);
        System.out.println(value++);
    }



    @BeforeAll
    void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    void afterAll() {
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