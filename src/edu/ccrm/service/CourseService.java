package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.CourseCode;

import java.util.List;

public interface CourseService {
    void addCourse(Course course);
    List<Course> listCourses();
    void updateCourse(Course course);
    void deactivateCourse(CourseCode code);
    Course findByCode(CourseCode code);
    List<Course> searchByDepartment(String department);
}