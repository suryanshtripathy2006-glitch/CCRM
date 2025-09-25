package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.io.BackupService;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.service.*;
import edu.ccrm.util.RecursionUtil;
import edu.ccrm.util.Validator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CCRMMain {
    private static final StudentService studentService = new StudentServiceImpl();
    private static final CourseService courseService = new CourseServiceImpl();
    private static final EnrollmentService enrollmentService = new EnrollmentServiceImpl();
    private static final TranscriptService transcriptService = new TranscriptServiceImpl();
    private static final ImportExportService ioService = new ImportExportService();
    private static final BackupService backupService = new BackupService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final Path dataDir = Paths.get("data");

    public static void main(String[] args) {
        AppConfig config = AppConfig.getInstance();

        String[] courseCodes = {"CS101", "MATH201", "PHYS301"};
        Arrays.sort(courseCodes);
        int index = Arrays.binarySearch(courseCodes, "MATH201");

        String example = "Hello, World!";
        example.substring(0, 5);
        String[] parts = example.split(", ");
        String joined = String.join("-", parts);

        try {
            Files.createDirectories(dataDir);
            loadData();
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }

        boolean running = true;
        outer: while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Courses");
            System.out.println("3. Manage Enrollments/Grades");
            System.out.println("4. Import/Export Data");
            System.out.println("5. Backup & Show Size");
            System.out.println("6. Reports");
            System.out.println("7. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1 -> manageStudents();
                case 2 -> manageCourses();
                case 3 -> manageEnrollments();
                case 4 -> manageIO();
                case 5 -> manageBackup();
                case 6 -> generateReports();
                case 7 -> {
                    running = false;
                    continue outer; 
                }
                default -> System.out.println("Invalid choice.");
            }

            int i = 0;
            do {
                i++;
                if (i == 2) continue; 
                if (i == 3) break; 
            } while (i < 5);

            for (int j = 0; j < 3; j++) {
            }

            List<Student> students = studentService.listStudents();
            for (Student s : students) {
            }
        }
    }

    private static void loadData() throws IOException {
        Path studentsPath = dataDir.resolve("students.csv");
        Path coursesPath = dataDir.resolve("courses.csv");
        Path enrollmentsPath = dataDir.resolve("enrollments.csv");

        if (Files.exists(studentsPath)) {
            ioService.importStudents(studentsPath);
        } else {
            ioService.importStudents(Paths.get("test-data/students.csv"));
        }

        if (Files.exists(coursesPath)) {
            ioService.importCourses(coursesPath);
        } else {
            ioService.importCourses(Paths.get("test-data/courses.csv"));
        }

        if (Files.exists(enrollmentsPath)) {
            ioService.importEnrollments(enrollmentsPath);
        } else {
            ioService.importEnrollments(Paths.get("test-data/enrollments.csv"));
        }
    }

    private static void manageStudents() {
        System.out.println("Enter student ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter registration number:");
        String regNo = scanner.nextLine();
        System.out.println("Enter full name:");
        String fullName = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter status (e.g., active/inactive):");
        String status = scanner.nextLine();
        try {
            Student student = new Student(id, regNo, new Name(fullName), email, status, LocalDate.now());
            studentService.addStudent(student);
            System.out.println("Added student.");
            System.out.println(student);
            ioService.exportStudents(dataDir.resolve("students.csv"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void manageCourses() {
        System.out.println("Enter course code:");
        String code = scanner.nextLine();
        System.out.println("Enter course title:");
        String title = scanner.nextLine();
        System.out.println("Enter credits:");
        int credits = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter semester (SPRING, SUMMER, FALL):");
        Semester semester = Semester.valueOf(scanner.nextLine().toUpperCase());
        System.out.println("Enter department:");
        String department = scanner.nextLine();
        try {
            Course course = new Course.Builder()
                    .code(code)
                    .title(title)
                    .credits(credits)
                    .semester(semester)
                    .department(department)
                    .build();
            courseService.addCourse(course);
            System.out.println("Added course.");
            List<Course> filtered = courseService.searchByDepartment(department);
            System.out.println("Filtered courses: " + filtered);
            ioService.exportCourses(dataDir.resolve("courses.csv"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void manageEnrollments() {
        System.out.println("Enter student ID to enroll:");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter course code to enroll:");
        String courseCode = scanner.nextLine();
        System.out.println("Enter marks:");
        double marks = scanner.nextDouble();
        scanner.nextLine();
        try {
            Student s = studentService.findById(studentId);
            if (s == null) {
                System.out.println("Student not found.");
                return;
            }
            Course c = courseService.findByCode(new CourseCode(courseCode));
            if (c == null) {
                System.out.println("Course not found.");
                return;
            }
            enrollmentService.enroll(s, c);
            Enrollment e = s.getEnrolledCourses().get(s.getEnrolledCourses().size() - 1);
            enrollmentService.recordMarks(e, marks);
            System.out.println("Enrollment and grading complete.");
            System.out.println(transcriptService.generateTranscript(s));
            ioService.exportEnrollments(dataDir.resolve("enrollments.csv"));
        } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception ex) {
            if (ex instanceof RuntimeException) { 
                System.out.println("Runtime error.");
            }
        } finally {
            System.out.println("Enrollment operation complete.");
        }
    }

    private static void manageIO() {
        System.out.println("1. Import Data");
        System.out.println("2. Export Data (students, courses, enrollments to separate files)");
        int ioChoice = scanner.nextInt();
        scanner.nextLine();
        try {
            Files.createDirectories(dataDir);
        } catch (IOException e) {
            System.out.println("Error creating data directory: " + e);
            return;
        }
        if (ioChoice == 1) {
            Path studentsPath = Paths.get("test-data/students.csv");
            Path coursesPath = Paths.get("test-data/courses.csv");
            Path enrollmentsPath = Paths.get("test-data/enrollments.csv");
            try {
                ioService.importStudents(studentsPath);
                ioService.importCourses(coursesPath);
                ioService.importEnrollments(enrollmentsPath);
                System.out.println("Data imported successfully.");
            } catch (IOException e) {
                System.out.println("Import error: " + e);
            }
        } else if (ioChoice == 2) {
            Path studentsExportPath = dataDir.resolve("students.csv");
            Path coursesExportPath = dataDir.resolve("courses.csv");
            Path enrollmentsExportPath = dataDir.resolve("enrollments.csv");
            try {
                ioService.exportStudents(studentsExportPath);
                ioService.exportCourses(coursesExportPath);
                ioService.exportEnrollments(enrollmentsExportPath);
                System.out.println("Data exported to separate files in 'data' folder.");
            } catch (IOException e) {
                System.out.println("Export error: " + e);
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static void manageBackup() {
        try {
            backupService.backup();
            Path backupDir = Paths.get("backups"); 
            long size = RecursionUtil.computeDirectorySize(backupDir);
            System.out.println("Backup size: " + size + " bytes");
        } catch (IOException e) {
            System.out.println("Backup error: " + e);
        }
    }

    private static void generateReports() {
        List<Student> students = studentService.listStudents();
        double averageGPA = students.stream()
                .mapToDouble(enrollmentService::computeGPA)
                .average()
                .orElse(0);
        System.out.println("Average GPA: " + averageGPA);

        List<Student> top = students.stream()
                .sorted((s1, s2) -> Double.compare(enrollmentService.computeGPA(s2), enrollmentService.computeGPA(s1)))
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("Top students: " + top);
    }

    private static void demoAnonymous() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
            }
        };
        r.run();
    }
}