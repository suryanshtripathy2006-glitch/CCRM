package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.*;

import java.time.LocalDate;
import java.util.List;

public class EnrollmentServiceImpl implements EnrollmentService {
    private static final int MAX_CREDITS_PER_SEMESTER = 20;

    @Override
    public void enroll(Student student, Course course) throws DuplicateEnrollmentException, MaxCreditLimitExceededException {
        List<Enrollment> enrolls = student.getEnrolledCourses();
        if (enrolls.stream().anyMatch(e -> e.getCourse().getCode().equals(course.getCode()))) {
            throw new DuplicateEnrollmentException("Already enrolled");
        }
        int currentCredits = enrolls.stream()
                .filter(e -> e.getCourse().getSemester() == course.getSemester())
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();
        if (currentCredits + course.getCredits() > MAX_CREDITS_PER_SEMESTER) {
            throw new MaxCreditLimitExceededException("Max credits exceeded");
        }
        Enrollment enrollment = new Enrollment(student, course, LocalDate.now());
        enrolls.add(enrollment);
        DataStore.getInstance().getEnrollments().add(enrollment);
    }

    @Override
    public void unenroll(Student student, Course course) {
    }

    @Override
    public void recordMarks(Enrollment enrollment, double marks) {
        enrollment.setMarks(marks);
    }

    @Override
    public double computeGPA(Student student) {
        List<Enrollment> enrolls = student.getEnrolledCourses();
        if (enrolls.isEmpty()) return 0;
        double totalPoints = enrolls.stream()
                .filter(e -> e.getGrade() != null)
                .mapToDouble(e -> e.getGrade().getPoints() * e.getCourse().getCredits())
                .sum();
        int totalCredits = enrolls.stream()
                .filter(e -> e.getGrade() != null)
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();
        return totalCredits == 0 ? 0 : totalPoints / totalCredits;
    }
}