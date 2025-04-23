package com.school;

import com.school.enums.Role;
import com.school.model.*;
import com.school.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ScuolaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScuolaApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(UserRepository userRepository, TeacherRepository teacherRepository,
                                        SubjectRepository subjectRepository, StudyPathRepository studyPathRepository,
                                        StudentRepository studentRepository, LessonRepository lessonRepository,
                                        EvaluationRepository evaluationRepository, EnrollmentRepository enrollmentRepository,
                                        CourseRepository courseRepository, ClassroomRepository classroomRepository) {

        return args -> {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Users adminUser = userRepository.save(new Users(null, "admin", passwordEncoder.encode("admin123"), Role.ADMIN, null, null));
            Users teacherUser = userRepository.save(new Users(null, "teacher1", passwordEncoder.encode("teacher123"), Role.TEACHER, null, null));
            Users studentUser = userRepository.save(new Users(null, "student1", passwordEncoder.encode("student123"), Role.STUDENT, null, null));

            Teacher teacher = new Teacher(null, teacherUser, "John", "Doe", "john.doe@example.com", "1234567890", "Mathematics", new HashSet<>());
            teacher = teacherRepository.save(teacher);

            Subject subject = new Subject(null, "Mathematics", "Advanced Mathematics", new HashSet<>());
            subject = subjectRepository.save(subject);

            StudyPath studyPath = new StudyPath(null, "Science Path", "Focus on science subjects", new HashSet<>());
            studyPath = studyPathRepository.save(studyPath);

            Student student = new Student(null, studentUser, "Jane", "Smith", LocalDate.of(2000, 1, 1),
                    "jane.smith@example.com", "0987654321", "123 Main St", "TAX123456",
                    LocalDate.now(), new HashSet<>());
            student = studentRepository.save(student);

            Course course = new Course(null, "Algebra 101", "Basic Algebra", 40,
                    BigDecimal.valueOf(200), new HashSet<>(), new HashSet<>(),
                    new HashSet<>(Set.of(subject)), new HashSet<>(Set.of(studyPath)));
            course = courseRepository.save(course);

            subject.getCourses().add(course);
            subjectRepository.save(subject);
            
            studyPath.getCourses().add(course);
            studyPathRepository.save(studyPath);

            Classroom classroom = new Classroom(null, "Room A", 30, "Building 1", new HashSet<>());
            classroom = classroomRepository.save(classroom);

            Lesson lesson = new Lesson(null, course, teacher, classroom,
                    LocalDateTime.now(), LocalDateTime.now().plusHours(2));
            lesson = lessonRepository.save(lesson);

            course.getLessons().add(lesson);
            courseRepository.save(course);
            
            teacher.getLessons().add(lesson);
            teacherRepository.save(teacher);
            
            classroom.getLessons().add(lesson);
            classroomRepository.save(classroom);


            Enrollment enrollment = new Enrollment(null, student, course,
                    LocalDate.now(), "Active", null);
            enrollment = enrollmentRepository.save(enrollment);

            student.getEnrollments().add(enrollment);
            studentRepository.save(student);
            
            course.getEnrollments().add(enrollment);
            courseRepository.save(course);

            Evaluation evaluation = new Evaluation(null, enrollment, 85,
                    LocalDate.now(), "Good performance");
            evaluation = evaluationRepository.save(evaluation);

            enrollment.setEvaluation(evaluation);
            enrollmentRepository.save(enrollment);
        };
    }
}
