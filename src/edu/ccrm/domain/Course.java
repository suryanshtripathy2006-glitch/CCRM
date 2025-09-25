package edu.ccrm.domain;

public class Course {
    private CourseCode code;
    private String title;
    private int credits;
    private Instructor instructor;
    private Semester semester;
    private String department;

    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.instructor = builder.instructor;
        this.semester = builder.semester;
        this.department = builder.department;
        assert credits > 0 && credits <= 5 : "Credits out of bounds"; 
    }

    public CourseCode getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Semester getSemester() {
        return semester;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Course{code=" + code + ", title='" + title + "', credits=" + credits + "}";
    }

    public static class Builder {
        private CourseCode code;
        private String title;
        private int credits;
        private Instructor instructor;
        private Semester semester;
        private String department;

        public Builder code(String code) {
            this.code = new CourseCode(code);
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder credits(int credits) {
            this.credits = credits;
            return this;
        }

        public Builder instructor(Instructor instructor) {
            this.instructor = instructor;
            return this;
        }

        public Builder semester(Semester semester) {
            this.semester = semester;
            return this;
        }

        public Builder department(String department) {
            this.department = department;
            return this;
        }

        public Course build() {
            return new Course(this);
        }
    }
}