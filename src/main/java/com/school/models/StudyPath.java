package com.school.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyPath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "study_path_course",
            joinColumns = @JoinColumn(name = "study_path_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @EqualsAndHashCode.Exclude
    private Set<Course> courses = new HashSet<>();
}
