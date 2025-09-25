package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Student;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private final List<Student> students = DataStore.getInstance().getStudents();

    @Override
    public void addStudent(Student student) {
        if (students.stream().anyMatch(s -> s.getId() == student.getId())) {
            throw new IllegalArgumentException("Duplicate student ID");
        }
        students.add(student);
    }

    @Override
    public List<Student> listStudents() {
        return students;
    }

    @Override
    public void updateStudent(Student student) {
    }

    @Override
    public void deactivateStudent(int id) {
        Student s = findById(id);
        if (s != null) s.setStatus("inactive");
    }

    @Override
    public Student findById(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    private class StudentUpdater {
    }
}