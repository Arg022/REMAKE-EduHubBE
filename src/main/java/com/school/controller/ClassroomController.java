package com.school.controller;

import com.school.model.Classroom;
import com.school.service.ClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Classrooms", description = "Endpoints for managing classrooms")
@RestController
@RequestMapping("/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;

    @Autowired
    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @Operation(summary = "Get all classrooms", description = "Retrieve a list of all classrooms")
    @GetMapping
    public ResponseEntity<List<Classroom>> getAllClassrooms() {
        return ResponseEntity.ok(classroomService.getAllClassrooms());
    }

    @Operation(summary = "Get classroom by ID", description = "Retrieve a classroom by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<Classroom> getClassroomById(@PathVariable Long id) {
        return ResponseEntity.ok(classroomService.getClassroomById(id));
    }

    @Operation(summary = "Create a new classroom", description = "Add a new classroom to the system")
    @PostMapping
    public ResponseEntity<Classroom> createClassroom(@RequestBody Classroom classroom) {
        return ResponseEntity.ok(classroomService.saveClassroom(classroom));
    }

    @Operation(summary = "Update a classroom", description = "Update the details of an existing classroom")
    @PutMapping("/{id}")
    public ResponseEntity<Classroom> updateClassroom(@PathVariable Long id, @RequestBody Classroom classroom) {
        return ResponseEntity.ok(classroomService.updateClassroom(id, classroom));
    }

    @Operation(summary = "Delete a classroom", description = "Remove a classroom from the system")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) {
        classroomService.deleteClassroomById(id);
        return ResponseEntity.noContent().build();
    }
}