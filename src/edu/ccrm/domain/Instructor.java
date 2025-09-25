package edu.ccrm.domain;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends Person {
    private List<Course> taughtCourses = new ArrayList<>();

    public Instructor(int id, Name name, String email) {
        super(id, name, email);
    }

    public List<Course> getTaughtCourses() {
        return taughtCourses;
    }

    @Override
    public String getRole() {
        return "Instructor";
    }

    @Override
    public String toString() {
        return "Instructor{} " + super.toString();
    }
}