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
            userRepository.save(new Users(null,"admin", passwordEncoder.encode("admin123"), Role.ADMIN));
            userRepository.save(new Users(null,"teacher1", passwordEncoder.encode("teacher123"), Role.TEACHER));
            userRepository.save(new Users(null,"student1", passwordEncoder.encode("student123"), Role.STUDENT));

            Teacher teacher = teacherRepository.save(new Teacher(null,"John", "Doe", "john.doe@example.com", "1234567890", "Mathematics", null));

            Subject subject = subjectRepository.save(new Subject(null,"Mathematics", "Advanced Mathematics", null));

            StudyPath studyPath = studyPathRepository.save(new StudyPath(null,"Science Path", "Focus on science subjects", null));

            Student student = studentRepository.save(new Student(null,"Jane", "Smith", LocalDate.of(2000, 1, 1), "jane.smith@example.com", "0987654321", "123 Main St", "TAX123456", LocalDate.now(), null));

            Course course = courseRepository.save(new Course(null,"Algebra 101", "Basic Algebra", 40, BigDecimal.valueOf(200), null, null, Set.of(subject), Set.of(studyPath)));

            Classroom classroom = classroomRepository.save(new Classroom(null,"Room A", 30, "Building 1", null));

            Lesson lesson = lessonRepository.save(new Lesson(null,course, teacher, classroom, LocalDateTime.now(), LocalDateTime.now().plusHours(2)));

            Enrollment enrollment = enrollmentRepository.save(new Enrollment(null,student, course, LocalDate.now(), "Active", null));

            evaluationRepository.save(new Evaluation(null,enrollment, 85, LocalDate.now(), "Good performance"));
        };
    }
}
