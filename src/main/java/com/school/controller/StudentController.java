package com.school.controller;

import com.school.dto.StudentDTO;
import com.school.model.Student;
import com.school.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Students", description = "Endpoints for managing students")
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(summary = "Get all students", description = "Retrieve a list of all students")
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudents()
                .stream()
                .map(student -> new StudentDTO(student.getId(), student.getFirstName(), student.getLastName(), student.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(students);
    }

    @Operation(summary = "Get student by ID", description = "Retrieve a student by their ID")
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        StudentDTO studentDTO = new StudentDTO(student.getId(), student.getFirstName(), student.getLastName(), student.getEmail());
        return ResponseEntity.ok(studentDTO);
    }

    @Operation(summary = "Create a new student", description = "Add a new student to the system")
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.saveStudent(student));
    }

    @Operation(summary = "Update a student", description = "Update the details of an existing student")
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @Operation(summary = "Delete a student", description = "Remove a student from the system")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export/csv")
    public ResponseEntity<InputStreamResource> exportStudentsToCSV() {
        ByteArrayInputStream csvData = studentService.exportToCSV();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=students.csv");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(new InputStreamResource(csvData));
    }

    @GetMapping("/export/pdf/{id}")
    public ResponseEntity<InputStreamResource> generateStudentPDF(@PathVariable Long id) {
        ByteArrayInputStream pdfData = studentService.generatePDF(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=student_" + id + ".pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfData));
    }

    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportStudentsToExcel() {
        ByteArrayInputStream excelData = studentService.exportToExcel();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=students.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(excelData));
    }
}