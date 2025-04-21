package com.school.repository;


import com.school.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Classroom findByName(String name);
}
