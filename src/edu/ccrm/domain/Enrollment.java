package edu.ccrm.domain;

import java.time.LocalDate;

public class Enrollment {
    private Student student;
    private Course course;
    private double marks;
    private Grade grade;
    private LocalDate enrollmentDate;

    public Enrollment(Student student, Course course, LocalDate enrollmentDate) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
        this.grade = computeGrade(marks);
    }

    public Grade getGrade() {
        return grade;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    private Grade computeGrade(double marks) {
        if (marks >= 90) return Grade.S;
        if (marks >= 80) return Grade.A;
        if (marks >= 70) return Grade.B;
        if (marks >= 60) return Grade.C;
        if (marks >= 50) return Grade.D;
        if (marks >= 40) return Grade.E;
        return Grade.F;
    }

    @Override
    public String toString() {
        return "Enrollment{course=" + course + ", marks=" + marks + ", grade=" + grade + "}";
    }
}