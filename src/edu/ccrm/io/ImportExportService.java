package edu.ccrm.io;

import edu.ccrm.config.AppConfig;
import edu.ccrm.config.DataStore;
import edu.ccrm.domain.*;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.CourseServiceImpl;
import edu.ccrm.service.StudentService;
import edu.ccrm.service.StudentServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImportExportService {

    private final Path dataPath = AppConfig.getInstance().getDataPath();

    public void importStudents(Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.filter(l -> !l.trim().isEmpty())
                 .skip(1)
                 .forEach(line -> {
                     String[] parts = line.split(",");
                     if (parts.length < 6) return;
                     int id = Integer.parseInt(parts[0].trim());
                     String regNo = parts[1].trim();
                     Name name = new Name(parts[2].trim());
                     String email = parts[3].trim();
                     String status = parts[4].trim();
                     LocalDate date = LocalDate.parse(parts[5].trim());
                     Student student = new Student(id, regNo, name, email, status, date);
                     DataStore.getInstance().getStudents().add(student);
                 });
        }
    }

    public void exportStudents(Path filePath) throws IOException {
        List<String> lines = DataStore.getInstance().getStudents().stream()
                .map(s -> s.getId() + "," + s.getRegNo() + "," + s.getName().getFullName() + "," + s.getEmail() + "," + s.getStatus() + "," + s.getRegistrationDate())
                .collect(Collectors.toList());
        lines.add(0, "id,regNo,fullName,email,status,registrationDate");
        Files.write(filePath, lines);
    }

    public void importCourses(Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.filter(l -> !l.trim().isEmpty())
                 .skip(1)
                 .forEach(line -> {
                     String[] parts = line.split(",");
                     if (parts.length < 5) return;
                     Course course = new Course.Builder()
                             .code(parts[0].trim())
                             .title(parts[1].trim())
                             .credits(Integer.parseInt(parts[2].trim()))
                             .semester(Semester.valueOf(parts[3].trim()))
                             .department(parts[4].trim())
                             .build();
                     DataStore.getInstance().getCourses().add(course);
                 });
        }
    }

    public void exportCourses(Path filePath) throws IOException {
        List<String> lines = DataStore.getInstance().getCourses().stream()
                .map(c -> c.getCode().getCode() + "," + c.getTitle() + "," + c.getCredits() + "," + c.getSemester() + "," + c.getDepartment())
                .collect(Collectors.toList());
        lines.add(0, "code,title,credits,semester,department");
        Files.write(filePath, lines);
    }

    public void importEnrollments(Path filePath) throws IOException {
        StudentService studentService = new StudentServiceImpl();
        CourseService courseService = new CourseServiceImpl();
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.filter(l -> !l.trim().isEmpty())
                 .skip(1)
                 .forEach(line -> {
                     String[] parts = line.split(",");
                     if (parts.length < 4) return;
                     int studentId = Integer.parseInt(parts[0].trim());
                     String courseCode = parts[1].trim();
                     double marks = Double.parseDouble(parts[2].trim());
                     LocalDate date = LocalDate.parse(parts[3].trim());
                     Student student = studentService.findById(studentId);
                     Course course = courseService.findByCode(new CourseCode(courseCode));
                     if (student != null && course != null) {
                         Enrollment enrollment = new Enrollment(student, course, date);
                         enrollment.setMarks(marks);
                         student.getEnrolledCourses().add(enrollment);
                         DataStore.getInstance().getEnrollments().add(enrollment);
                     }
                 });
        }
    }

    public void exportEnrollments(Path filePath) throws IOException {
        List<String> lines = DataStore.getInstance().getEnrollments().stream()
                .map(e -> e.getStudent().getId() + "," + e.getCourse().getCode().getCode() + "," + e.getMarks() + "," + e.getEnrollmentDate())
                .collect(Collectors.toList());
        lines.add(0, "studentId,courseCode,marks,enrollmentDate");
        Files.write(filePath, lines);
    }
}