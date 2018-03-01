package academy.school.levchenko.lms.dao.database;

import academy.school.levchenko.lms.dao.StudentCourseDAO;
import academy.school.levchenko.lms.model.Course;
import academy.school.levchenko.lms.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentCourseDAOImpl extends Query implements StudentCourseDAO {

    @Override
    public boolean addStudentToCourse(Student student, Course course) throws SQLException {

        boolean result;

        try (Connection connection = getConnection();
                PreparedStatement ps =
                     connection.prepareStatement(ADD_STUDENT_TO_COURSE)) {

            ps.setInt(1, student.getId());
            ps.setInt(2, course.getId());
            result = ps.execute();

        }

        return result;
    }

    @Override
    public boolean removeStudentFromCourse(Student student, Course course) throws SQLException {

        boolean result;

        try (Connection connection = getConnection();
                PreparedStatement ps =
                     connection.prepareStatement(REMOVE_STUDENT_FROM_COURSE)) {

            ps.setInt(1, student.getId());
            ps.setInt(2, course.getId());
            result = ps.execute();

        }

        return result;
    }

    @Override
    public List<Student> findStudentsByCourseId(int id) throws SQLException {

        List<Student> students = new ArrayList<>();
        Student student;

        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(FIND_STUDENTS_BY_COURSE_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    student = new Student.StudentBuilder()
                            .id(rs.getInt("id"))
                            .firstName(rs.getString("first_name"))
                            .lastName(rs.getString("last_name"))
                            .age(rs.getInt("age"))
                            .build();
                    students.add(student);
                }
            }

        }

        return students;
    }

    @Override
    public List<Course> findCoursesByStudentId(int id) throws SQLException {

        List<Course> courses = new ArrayList<>();
        Course course;

        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(FIND_COURSES_BY_STUDENT_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    course = new Course.CourseBuilder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .build();
                    courses.add(course);
                }
            }

        }

        return courses;

    }

}
