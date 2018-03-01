package academy.school.levchenko.lms.dao.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Query {

    static final String CREATE_COURSE = "INSERT INTO course (name, description, start_date, end_date, " + "days_of_week, trainer_id) VALUES (?, ?, ?, ?, ?, ?)";
    static final String CREATE_TRAINER = "INSERT INTO trainer (first_name, last_name) VALUES (?, ?)";
    static final String CREATE_STUDENT = "INSERT INTO student (first_name, last_name, age) VALUES (?, ?, ?)";

    static final String SEARCH_COURSE_BY_ID = "SELECT * FROM course LEFT JOIN trainer " + "ON course.trainer_id = trainer.id " + "WHERE course.id = ?";
    static final String SEARCH_TRAINER_BY_ID = "SELECT * FROM trainer WHERE trainer.id = ?";
    static final String SEARCH_COURSES_BY_TRAINER_ID = "SELECT * FROM course WHERE course.trainer_id = ?";
    static final String SEARCH_STUDENT_BY_ID = "SELECT * FROM student WHERE student.id = ?";

    static final String SEARCH_STUDENT_BY_NAME = "SELECT * FROM student " + "WHERE student.first_name = ? AND student.last_name = ?";
    static final String SEARCH_TRAINER_BY_NAME = "SELECT * FROM trainer " + "WHERE trainer.first_name = ? AND trainer.last_name = ?";
    static final String SEARCH_COURSE_BY_NAME = "SELECT * FROM course WHERE course.name = ?";

    static final String SEARCH_ALL_COURSES = "SELECT * FROM course LEFT JOIN trainer " + "ON course.trainer_id = trainer.id";
    static final String SEARCH_ALL_TRAINERS = "SELECT * FROM trainer";
    static final String SEARCH_ALL_STUDENTS = "SELECT * FROM student";

    static final String UPDATE_COURSE = "UPDATE course SET name = ?, description = ?, start_date = ?, " + "end_date = ?, days_of_week = ?, trainer_id = ? " + "WHERE id = ?";
    static final String ADD_STUDENT_TO_COURSE = "INSERT INTO student_course (student_id, course_id) VALUES (?, ?)";
    static final String REMOVE_STUDENT_FROM_COURSE = "DELETE FROM student_course WHERE student_id = ? AND course_id = ?";

    static final String FIND_COURSES_BY_STUDENT_ID = "SELECT * FROM course LEFT JOIN student_course " + "ON course.id = student_course.course_id " + "WHERE student_id = ? ORDER BY id";
    static final String FIND_STUDENTS_BY_COURSE_ID = "SELECT * FROM student LEFT JOIN student_course " + "ON student.id = student_course.student_id " + "WHERE course_id = ? ORDER BY id";

    private static final String DB_URL = "jdbc:h2:./storage";
    private static final String DB_USERNAME = "";
    private static final String DB_USERPASSWORD = "";
    private static final String DRIVER = "org.h2.Driver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: Can't get driver for database");
            throw new NullPointerException();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_USERPASSWORD);
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }


}
