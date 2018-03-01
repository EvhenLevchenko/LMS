package academy.school.levchenko.lms.dao.database;

import academy.school.levchenko.lms.dao.StudentDAO;
import academy.school.levchenko.lms.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl extends Query implements StudentDAO {

    @Override
    public int create(Student student) throws SQLException {

        int result = -1;

        try (Connection connection = getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(CREATE_STUDENT,
                             Statement.RETURN_GENERATED_KEYS)) {

            create(ps, student);

            try (ResultSet rs = ps.getGeneratedKeys()) {
                while (rs.next()) {
                    result = rs.getInt(1);
                }
            }

        }

        return result;

    }

    private void create(PreparedStatement ps, Student student) throws SQLException {
        ps.setString(1, student.getFirstName());
        ps.setString(2, student.getLastName());
        ps.setInt(3, student.getAge());
        ps.execute();

    }

    @Override
    public void delete(Student student) throws Exception {
        try {
            Connection connection = getConnection();
            Throwable var3 = null;

            try {
                Statement statement = connection.createStatement();
                Throwable var5 = null;

                try {
                    if (this.isStudentPresence(student)) {
                        String QUERY = String.format("DELETE FROM student WHERE student.id = ? %d", student.getId());
                        statement.executeUpdate(QUERY);
                    }
                } catch (Throwable var30) {
                    var5 = var30;
                    throw var30;
                } finally {
                    if (statement != null) {
                        if (var5 != null) {
                            try {
                                statement.close();
                            } catch (Throwable var29) {
                                var5.addSuppressed(var29);
                            }
                        } else {
                            statement.close();
                        }
                    }

                }
            } catch (Throwable var32) {
                var3 = var32;
                throw var32;
            } finally {
                if (connection != null) {
                    if (var3 != null) {
                        try {
                            connection.close();
                        } catch (Throwable var28) {
                            var3.addSuppressed(var28);
                        }
                    } else {
                        connection.close();
                    }
                }

            }
        } catch (NullPointerException | SQLException var34) {
            System.out.println("Exception: " + var34.getMessage());
        }

    }

    @Override
    public Student get(Student student) throws Exception {
        try {
            if (this.isStudentPresence(new Student(student.getFirstName(), student.getLastName(), student.getAge()))) {
                return new Student(student.getFirstName(), student.getLastName(), student.getAge());
            }
        } catch (NullPointerException var3) {
            System.out.println("Exception: " + var3.getMessage());
        }

        return null;
    }

    @Override
    public Student searchById(int id) throws SQLException {

        Student student = null;

        try (Connection connection = getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(SEARCH_STUDENT_BY_ID)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    student = new Student.StudentBuilder()
                            .id(rs.getInt("id"))
                            .firstName(rs.getString("first_name"))
                            .lastName(rs.getString("last_name"))
                            .age(rs.getInt("age"))
                            .build();
                }
            }

        }

        return student;

    }

    @Override
    public Student searchByName(String firstName, String lastName) throws SQLException {
        Student student = null;

        try (Connection connection = getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(SEARCH_STUDENT_BY_NAME)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    student = new Student.StudentBuilder()
                            .id(rs.getInt("id"))
                            .firstName(rs.getString("first_name"))
                            .lastName(rs.getString("last_name"))
                            .age(rs.getInt("age"))
                            .build();
                }
            }

        }

        return student;
    }

    @Override
    public boolean isStudentPresence(Student student) throws SQLException {
        try {
            Connection connection = getConnection();
            Throwable var3 = null;

            boolean var8;
            try {
                Statement statement = connection.createStatement();
                Throwable var5 = null;

                try {
                    String QUERY = String.format("SELECT * FROM student WHERE student.id = ? %d", student.getId());
                    ResultSet resultSet = statement.executeQuery(QUERY);
                    var8 = resultSet.next();
                } catch (Throwable var33) {
                    var5 = var33;
                    throw var33;
                } finally {
                    if (statement != null) {
                        if (var5 != null) {
                            try {
                                statement.close();
                            } catch (Throwable var32) {
                                var5.addSuppressed(var32);
                            }
                        } else {
                            statement.close();
                        }
                    }

                }
            } catch (Throwable var35) {
                var3 = var35;
                throw var35;
            } finally {
                if (connection != null) {
                    if (var3 != null) {
                        try {
                            connection.close();
                        } catch (Throwable var31) {
                            var3.addSuppressed(var31);
                        }
                    } else {
                        connection.close();
                    }
                }

            }

            return var8;
        } catch (NullPointerException | SQLException var37) {
            System.out.println("Exception: " + var37.getMessage() + " test");
            return false;
        }
    }

    @Override
    public boolean update(Student student) throws SQLException {
        throw new UnsupportedOperationException();

    }

    @Override
    public List<Student> getAll() throws SQLException {

        List<Student> students = new ArrayList<>();
        Student student;

        try (Connection connection = getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(SEARCH_ALL_STUDENTS)) {
            getAll(rs,  students);
        }
        return students;
    }



    private void getAll(ResultSet rs,  List<Student> students) throws SQLException {
        while (rs.next()) {
          Student  student = new Student.StudentBuilder()
                    .id(rs.getInt("id"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .build();
            students.add(student);
        }
    }
}
