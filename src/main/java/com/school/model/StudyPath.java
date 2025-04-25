package com.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Study path name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "study_path_course",
            joinColumns = @JoinColumn(name = "study_path_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonIgnoreProperties("studyPaths")
    @EqualsAndHashCode.Exclude
    private Set<Course> courses = new HashSet<>();
}
