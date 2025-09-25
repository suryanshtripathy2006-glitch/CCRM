package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.DuplicateEnrollmentException;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.MaxCreditLimitExceededException;
import edu.ccrm.domain.Student;

public interface EnrollmentService {
    void enroll(Student student, Course course) throws DuplicateEnrollmentException, MaxCreditLimitExceededException;
    void unenroll(Student student, Course course);
    void recordMarks(Enrollment enrollment, double marks);
    double computeGPA(Student student);
}