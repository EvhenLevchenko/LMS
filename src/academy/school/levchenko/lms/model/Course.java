package academy.school.levchenko.lms.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Course {

    private int id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String daysOfWeek;
    private Teacher teacher;
    private List<Student> students = new ArrayList<>();

    private Course(CourseBuilder builder) {

        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.daysOfWeek = builder.daysOfWeek;
        this.teacher = builder.teacher;
        this.students = builder.students;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public static class CourseBuilder {

        private int id;
        private String name;
        private String description;
        private LocalDate startDate;
        private LocalDate endDate;
        private String daysOfWeek;
        private Teacher teacher;
        private List<Student> students = new ArrayList<>();

        public CourseBuilder() {
        }

        public CourseBuilder id(int id) {
            this.id = id;
            return this;
        }

        public CourseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CourseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CourseBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public CourseBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public CourseBuilder daysOfWeek(String daysOfWeek) {
            this.daysOfWeek = daysOfWeek;
            return this;
        }

        public CourseBuilder teacher(Teacher teacher) {
            this.teacher = teacher;
            return this;
        }

        public CourseBuilder students(List<Student> students) {
            this.students = students;
            return this;
        }

        public Course build() {
            return new Course(this);
        }

    }

}
