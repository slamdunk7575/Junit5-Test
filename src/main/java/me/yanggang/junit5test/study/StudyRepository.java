package me.yanggang.junit5test.study;

import me.yanggang.junit5test.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
