package academy.school.levchenko.lms.model;

import java.util.ArrayList;
import java.util.List;

public class Teacher {

    private int id;
    private String firstName;
    private String lastName;
    private List<Course> courses = new ArrayList<>();

    private Teacher(TeacherBuilder builder) {

        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.courses = builder.courses;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public static class TeacherBuilder {

        private int id;
        private String firstName;
        private String lastName;
        private List<Course> courses = new ArrayList<>();

        public TeacherBuilder() {
        }

        public TeacherBuilder id(int id) {
            this.id = id;
            return this;
        }

        public TeacherBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public TeacherBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public TeacherBuilder courses(List<Course> courses) {
            this.courses = courses;
            return this;
        }

        public Teacher build() {
            return new Teacher(this);
        }

    }

}
