package academy.school.levchenko.lms.model;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private List<Course> courses = new ArrayList<>();

    public Student(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    private Student(StudentBuilder builder) {

        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public static class StudentBuilder {

        private int id;
        private String firstName;
        private String lastName;
        private int age;
        private List<Course> courses = new ArrayList<>();

        public StudentBuilder() {
        }

        public StudentBuilder id(int id) {
            this.id = id;
            return this;
        }

        public StudentBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public StudentBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public StudentBuilder age(int age) {
            this.age = age;
            return this;
        }

        public StudentBuilder courses(List<Course> courses) {
            this.courses = courses;
            return this;
        }

        public Student build() {
            return new Student(this);
        }

    }

}

