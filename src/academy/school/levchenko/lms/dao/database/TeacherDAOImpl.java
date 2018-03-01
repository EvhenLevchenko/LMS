package academy.school.levchenko.lms.dao.database;

import academy.school.levchenko.lms.major.Controller;
import academy.school.levchenko.lms.dao.TeacherDAO;
import academy.school.levchenko.lms.model.Course;
import academy.school.levchenko.lms.model.Teacher;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAOImpl extends Query implements TeacherDAO {

    public int create(Teacher trainer) throws SQLException {

        int result = -1;
        try (Connection connection = getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(CREATE_TRAINER,
                             Statement.RETURN_GENERATED_KEYS)) {
            create(ps, trainer);
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    result = rs.getInt(1);
                }
            }
        }
        return result;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        throw new UnsupportedOperationException();

    }

    public Teacher searchById(int id) throws SQLException {

        Teacher teacher = null;
        List<Course> courses = new ArrayList<>();

        try (Connection connection = getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SEARCH_COURSES_BY_TRAINER_ID)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    findById(rs, courses);
                }
            }

            try (PreparedStatement ps =
                         connection.prepareStatement(SEARCH_TRAINER_BY_ID)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    findByIdd(rs, courses);
                }
            }
        }
        return teacher;
    }

    public Teacher searchByName(String firstName, String lastName) throws SQLException {

        Teacher teacher = null;

        try (Connection connection = getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(SEARCH_TRAINER_BY_NAME)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);

            try (ResultSet rs = ps.executeQuery()) {
                findName(rs);
            }
        }
        return teacher;
    }

    @Override
    public void update(Teacher teacher) throws SQLException {

    }

    public List<Teacher> getAll() throws SQLException {

        List<Teacher> teachers = new ArrayList<>();
        Teacher teacher;

        try (Connection connection = getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(SEARCH_ALL_TRAINERS)) {
            findAll(rs, teachers);
        }
        return teachers;
    }


    private void create(PreparedStatement ps, Teacher teacher) throws SQLException {
        ps.setString(1, teacher.getFirstName());
        ps.setString(2, teacher.getLastName());
        ps.execute();
    }

    private void findAll(ResultSet rs, List<Teacher> teachers) throws SQLException {
        while (rs.next()) {
            Teacher trainer = new Teacher.TeacherBuilder()
                    .id(rs.getInt("id"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .build();
            teachers.add(trainer);
        }
    }

    private void findName(ResultSet rs) throws SQLException {
        if (rs.next()) {
            Teacher teacher = new Teacher.TeacherBuilder()
                    .id(rs.getInt("id"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .build();

        }
    }

    private void findById(ResultSet rs, List<Course> courses) throws SQLException {
        while (rs.next()) {
            Course course = new Course.CourseBuilder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .startDate(Controller.convertStringToDate(rs.getString("start_date")))
                    .endDate(Controller.convertStringToDate(rs.getString("end_date")))
                    .daysOfWeek(rs.getString("days_of_week"))
                    .build();
            courses.add(course);
        }
    }

    private void findByIdd(ResultSet rs, List<Course> courses) throws SQLException {
        if (rs.next()) {
            Teacher teacher = new Teacher.TeacherBuilder()
                    .id(rs.getInt("id"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .courses(courses)
                    .build();
        }
    }
}
