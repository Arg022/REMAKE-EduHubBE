package com.school.controller;

import com.school.model.Classroom;
import com.school.dto.ClassroomDTO;
import com.school.service.ClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Classrooms", description = "Endpoints for managing classrooms")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;

    @Autowired
    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    private ClassroomDTO convertToDTO(Classroom classroom) {
        return new ClassroomDTO(
            classroom.getId(),
            classroom.getName(),
            classroom.getCapacity(),
            classroom.getLocation()
        );
    }

    @Operation(summary = "Get all classrooms", description = "Retrieve a list of all classrooms")
    @GetMapping
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<List<ClassroomDTO>> getAllClassrooms() {
        List<ClassroomDTO> classroomDTOs = classroomService.getAllClassrooms()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(classroomDTOs);
    }

    @Operation(summary = "Get classroom by ID", description = "Retrieve a classroom by its ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<ClassroomDTO> getClassroomById(@PathVariable Long id) {
        Classroom classroom = classroomService.getClassroomById(id);
        return ResponseEntity.ok(convertToDTO(classroom));
    }

    @Operation(summary = "Create a new classroom", description = "Add a new classroom to the system")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassroomDTO> createClassroom(@RequestBody Classroom classroom) {
        Classroom savedClassroom = classroomService.saveClassroom(classroom);
        return ResponseEntity.ok(convertToDTO(savedClassroom));
    }

    @Operation(summary = "Update a classroom", description = "Update the details of an existing classroom")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClassroomDTO> updateClassroom(@PathVariable Long id, @RequestBody Classroom classroom) {
        Classroom updatedClassroom = classroomService.updateClassroom(id, classroom);
        return ResponseEntity.ok(convertToDTO(updatedClassroom));
    }

    @Operation(summary = "Delete a classroom", description = "Remove a classroom from the system")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) {
        classroomService.deleteClassroomById(id);
        return ResponseEntity.noContent().build();
    }
}