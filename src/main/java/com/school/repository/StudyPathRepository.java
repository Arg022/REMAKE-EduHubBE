package com.school.repository;

import com.school.model.StudyPath;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StudyPathRepository extends JpaRepository<StudyPath, Long> {
    StudyPath findByName(String name);
}
