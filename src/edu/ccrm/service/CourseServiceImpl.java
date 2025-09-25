package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.CourseCode;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CourseServiceImpl implements CourseService {
    private final List<Course> courses = DataStore.getInstance().getCourses();

    @Override
    public void addCourse(Course course) {
        courses.add(course);
    }

    @Override
    public List<Course> listCourses() {
        return courses;
    }

    @Override
    public void updateCourse(Course course) {
    }

    @Override
    public void deactivateCourse(CourseCode code) {
    }

    @Override
    public Course findByCode(CourseCode code) {
        return courses.stream().filter(c -> c.getCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public List<Course> searchByDepartment(String department) {
        Predicate<Course> byDept = c -> c.getDepartment().equalsIgnoreCase(department);
        return courses.stream().filter(byDept).collect(Collectors.toList()); 
    }
}