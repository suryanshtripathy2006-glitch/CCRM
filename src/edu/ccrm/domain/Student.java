package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private String regNo;
    private String status;
    private List<Enrollment> enrolledCourses = new ArrayList<>();
    private LocalDate registrationDate;

    public Student(int id, String regNo, Name name, String email, String status, LocalDate registrationDate) {
        super(id, name, email);
        assert id > 0 : "ID must be positive"; 
        this.regNo = regNo;
        this.status = status;
        this.registrationDate = registrationDate;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Enrollment> getEnrolledCourses() {
        return enrolledCourses;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public String toString() {
        return "Student{" +
                "regNo='" + regNo + '\'' +
                ", status='" + status + '\'' +
                ", registrationDate=" + registrationDate +
                "} " + super.toString();
    }
}