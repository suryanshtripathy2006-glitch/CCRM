package edu.ccrm.service;

import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Person;
import edu.ccrm.domain.Student;

public class TranscriptServiceImpl implements TranscriptService {

    @Override
    public String generateTranscript(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append("Transcript for ").append(student.getName()).append("\n");
        sb.append("Role: ").append(student.getRole()).append("\n"); 
        sb.append("GPA: ").append(new EnrollmentServiceImpl().computeGPA(student)).append("\n");
        for (Enrollment e : student.getEnrolledCourses()) {
            sb.append(e.toString()).append("\n"); 
        }

        Person p = student; 
        if (p instanceof Student) {
            Student s = (Student) p; 
            sb.append("Reg No: ").append(s.getRegNo()).append("\n");
        }

        return sb.toString();
    }

    interface Persistable {
        default void save() {
            System.out.println("Persistable save");
        }
    }

    interface Searchable {
        default void save() {
            System.out.println("Searchable save");
        }
    }

    class Demo implements Persistable, Searchable {
        @Override
        public void save() {
            Persistable.super.save(); 
        }
    }
}