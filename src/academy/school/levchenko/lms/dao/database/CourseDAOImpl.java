package academy.school.levchenko.lms.dao.database;

import academy.school.levchenko.lms.major.Controller;
import academy.school.levchenko.lms.dao.CourseDAO;
import academy.school.levchenko.lms.model.Course;
import academy.school.levchenko.lms.model.Teacher;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl extends Query implements CourseDAO {


    @Override
    public int create(Course course) throws SQLException {

        int result = -1;

        try (Connection connection = getConnection();
                PreparedStatement ps =
                     connection.prepareStatement(CREATE_COURSE,
                             Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, course.getName());
            ps.setString(2, course.getDescription());
            ps.setString(3, Controller.convertDateToString(course.getStartDate()));
            ps.setString(4, Controller.convertDateToString(course.getEndDate()));
            ps.setString(5, course.getDaysOfWeek());
            ps.setInt(6, course.getTeacher() != null ? course.getTeacher().getId() : 0);
            ps.execute();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                while (rs.next()) {
                    result = rs.getInt(1);
                }
            }

        }

        return result;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Course searchById(int id) throws SQLException {

        Course course = null;
        Teacher teacher;

        try (Connection connection = getConnection();
                PreparedStatement ps =
                        connection.prepareStatement(SEARCH_COURSE_BY_ID)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    if (rs.getInt("trainer_id") != 0) {
                        teacher = new Teacher.TeacherBuilder()
                                .id(rs.getInt("trainer_id"))
                                .firstName(rs.getString("first_name"))
                                .lastName(rs.getString("last_name"))
                                .build();

                    } else {
                        teacher = null;
                    }

                    course = new Course.CourseBuilder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .description(rs.getString("description"))
                            .startDate(Controller.convertStringToDate(rs.getString("start_date")))
                            .endDate(Controller.convertStringToDate(rs.getString("end_date")))
                            .daysOfWeek(rs.getString("days_of_week"))
                            .teacher(teacher)
                            .build();
                }
            }

        }

        return course;

    }

    @Override
    public Course searchByName(String name) throws SQLException {

        Course course = null;
        Teacher teacher;

        try (Connection connection = getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(SEARCH_COURSE_BY_NAME)) {

            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    if (rs.getInt("trainer_id") != 0) {
                        teacher = new Teacher.TeacherBuilder()
                                .id(rs.getInt("trainer_id"))
                                .firstName(rs.getString("first_name"))
                                .lastName(rs.getString("last_name"))
                                .build();

                    } else {
                        teacher = null;
                    }

                    course = new Course.CourseBuilder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("name"))
                            .description(rs.getString("description"))
                            .startDate(Controller.convertStringToDate(rs.getString("start_date")))
                            .endDate(Controller.convertStringToDate(rs.getString("end_date")))
                            .daysOfWeek(rs.getString("days_of_week"))
                            .teacher(teacher)
                            .build();
                }
            }

        }

        return course;

    }

    @Override
    public boolean update(Course course) throws SQLException {

        boolean result;

        try (Connection connection = getConnection();
                PreparedStatement ps = connection.prepareStatement(UPDATE_COURSE)) {

            ps.setString(1, course.getName());
            ps.setString(2, course.getDescription());
            ps.setString(3, Controller.convertDateToString(course.getStartDate()));
            ps.setString(4, Controller.convertDateToString(course.getEndDate()));
            ps.setString(5, course.getDaysOfWeek());
            ps.setInt(6, course.getTeacher() != null ? course.getTeacher().getId() : 0);
            ps.setInt(7, course.getId());
            result = ps.execute();

        }

        return result;

    }

    @Override
    public List<Course> getAll() throws SQLException {

        List<Course> courses = new ArrayList<>();
        Course course;
        Teacher teacher;

        try (Connection connection = getConnection();
                Statement st = connection.createStatement();
                    ResultSet rs = st.executeQuery(SEARCH_ALL_COURSES)) {

            while (rs.next()) {

                if (rs.getInt("trainer_id") != 0) {
                    teacher = new Teacher.TeacherBuilder()
                            .id(rs.getInt("trainer_id"))
                            .firstName(rs.getString("first_name"))
                            .lastName(rs.getString("last_name"))
                            .build();
                } else {
                    teacher = null;
                }

                course = new Course.CourseBuilder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .startDate(Controller.convertStringToDate(rs.getString("start_date")))
                        .endDate(Controller.convertStringToDate(rs.getString("end_date")))
                        .daysOfWeek(rs.getString("days_of_week"))
                        .teacher(teacher)
                        .build();
                courses.add(course);
            }

        }

        return courses;

    }
}
