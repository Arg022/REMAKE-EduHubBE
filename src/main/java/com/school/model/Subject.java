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
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Subject name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @ManyToMany(mappedBy = "subjects")
    @JsonIgnoreProperties("subjects")
    @EqualsAndHashCode.Exclude
    private Set<Course> courses = new HashSet<>();
}
