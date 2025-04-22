package com.school.service;

import com.school.model.Student;
import com.school.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student.get();
        } else {
            throw new EntityNotFoundException("Student not found with id: " + id);
        }
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudentById(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Student not found with id: " + id);
        }
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Optional<Student> existingStudentOptional = studentRepository.findById(id);

        if (existingStudentOptional.isEmpty()) {
            throw new EntityNotFoundException("Student not found with id: " + id);
        }

        Student existingStudent = existingStudentOptional.get();
        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setLastName(updatedStudent.getLastName());
        existingStudent.setDateOfBirth(updatedStudent.getDateOfBirth());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setPhone(updatedStudent.getPhone());
        existingStudent.setAddress(updatedStudent.getAddress());
        existingStudent.setTaxCode(updatedStudent.getTaxCode());
        existingStudent.setRegistrationDate(updatedStudent.getRegistrationDate());

        return studentRepository.save(existingStudent);
    }

    public ByteArrayInputStream exportToCSV() {
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader("ID", "First Name", "Last Name", "Email")
                .build();
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             PrintWriter writer = new PrintWriter(out);
             CSVPrinter printer = new CSVPrinter(writer, format)) {

            for (Student student : getAllStudents()) {
                printer.printRecord(student.getId(), student.getFirstName(), student.getLastName(), student.getEmail());
            }

            printer.flush();
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new UncheckedIOException("Error while exporting to CSV", e);
        }
    }

    public ByteArrayInputStream generatePDF(Long id) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Student student = getStudentById(id);
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph("Student Profile"));
            document.add(new Paragraph("ID: " + student.getId()));
            document.add(new Paragraph("Name: " + student.getFirstName() + " " + student.getLastName()));
            document.add(new Paragraph("Email: " + student.getEmail()));
            document.close();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (DocumentException | IOException e) {
            throw new RuntimeException("Error while generating PDF", e);
        }
    }

    public ByteArrayInputStream exportToExcel() {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Students");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("First Name");
            headerRow.createCell(2).setCellValue("Last Name");
            headerRow.createCell(3).setCellValue("Email");

            int rowIdx = 1;
            for (Student student : getAllStudents()) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(student.getId());
                row.createCell(1).setCellValue(student.getFirstName());
                row.createCell(2).setCellValue(student.getLastName());
                row.createCell(3).setCellValue(student.getEmail());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new UncheckedIOException("Error while exporting to Excel", e);
        }
    }
}