package academy.school.levchenko.lms.major;

import academy.school.levchenko.lms.dao.*;
import academy.school.levchenko.lms.model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabaseLms {

    private static final String DB_URL = "jdbc:h2:./storage";
    private static final String DB_USERNAME = "";
    private static final String DB_USERPASSWORD = "";
    private static final String DRIVER = "org.h2.Driver";

    private static final String CREATE_TRAINER_TABLE =
            "CREATE TABLE IF NOT EXISTS trainer(" +
                    "id INT(4) NOT NULL AUTO_INCREMENT, " +
                    "first_name VARCHAR(20), " +
                    "last_name VARCHAR(20), " +
                    "PRIMARY KEY (id)" +
                    ")";

    private static final String CREATE_COURSE_TABLE =
            "CREATE TABLE IF NOT EXISTS course(" +
                    "id int NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(50), " +
                    "description VARCHAR(255), " +
                    "start_date VARCHAR(10), " +
                    "end_date VARCHAR(10), " +
                    "days_of_week VARCHAR(50), " +
                    "trainer_id INT(4) DEFAULT 0, " +
                    "PRIMARY KEY (id), " +
                    ")";

    private static final String CREATE_STUDENT_TABLE =
            "CREATE TABLE IF NOT EXISTS student(" +
                    "id INT(4) NOT NULL AUTO_INCREMENT, " +
                    "first_name VARCHAR(20), " +
                    "last_name VARCHAR(20), " +
                    "age INT(2), " +
                    "PRIMARY KEY (id)" +
                    ")";

    private static final String CREATE_STUDENT_COURSE_TABLE =
            "CREATE TABLE IF NOT EXISTS student_course(" +
                    "student_id INT(4) REFERENCES student(id), " +
                    "course_id INT(4) REFERENCES course(id), " +
                    "PRIMARY KEY (student_id, course_id), " +
                    ")";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: Can't get driver for database");
            throw new NullPointerException();
        }
    }

    private static CourseDAO courseDAO;
    private static TeacherDAO teacherDAO;
    private static StudentDAO studentDAO;
    private static StudentCourseDAO studentCourseDAO;

    public CreateDatabaseLms() {
        DAOFactory daoFactory = DAOFactory.getInstance(DAOFactory.Type.DATABASE);
        courseDAO = daoFactory.getCourseDAO();
        teacherDAO = daoFactory.getTeacherDAO();
        studentDAO = daoFactory.getStudentDAO();
        studentCourseDAO = daoFactory.getStudentCourseDAO();
    }


    public static void main(String[] args) {

        new CreateDatabaseLms();

        try {
            createTables();
        } catch (SQLException e) {
            System.err.println("ERROR while creating initial tables");
            return;
        }

        try {
            createTrainersForTest();
            createCoursesForTest();
            createStudentsForTest();
        } catch (Exception e) {
            System.err.println("ERROR while creating initial tables");
        }

    }

    public static void createTables() throws SQLException {
        try (Connection connection =
                     DriverManager.getConnection(DB_URL, DB_USERNAME, DB_USERPASSWORD);
             Statement st = connection.createStatement()) {
            st.execute(CREATE_TRAINER_TABLE);
            st.execute(CREATE_COURSE_TABLE);
            st.execute(CREATE_STUDENT_TABLE);
            st.execute(CREATE_STUDENT_COURSE_TABLE);
        }

    }


    public static void createCoursesForTest() throws Exception {

        Course course;

        course = new Course.CourseBuilder()
                .name("Java")
                .description("description")
                .startDate(Controller.convertStringToDate("20.10.1997"))
                .endDate(Controller.convertStringToDate("20.01.1998"))
                .daysOfWeek("Mon,Tue,Wed,Thu,Fri")
                .teacher(teacherDAO.searchById(1))
                .build();

        courseDAO.create(course);

        course = new Course.CourseBuilder()
                .name("C#")
                .description("no description")
                .startDate(Controller.convertStringToDate("20.10.1997"))
                .endDate(Controller.convertStringToDate("20.01.1998"))
                .daysOfWeek("Mon,Tue,Wed,Thu,Fri")
                .teacher(teacherDAO.searchById(2))
                .build();

        courseDAO.create(course);


    }

    public static void createTrainersForTest() throws Exception {

        Teacher teacher;

        teacher = new Teacher.TeacherBuilder()
                .firstName("Lil")
                .lastName("Konor")
                .build();

        teacherDAO.create(teacher);

        teacher = new Teacher.TeacherBuilder()
                .firstName("Tom")
                .lastName("Miller")
                .build();

        teacherDAO.create(teacher);



    }

    public static void createStudentsForTest() throws Exception {

        Student student;
        int studentId;

        student = new Student.StudentBuilder()
                .firstName("John")
                .lastName("Smith")
                .age(18)
                .build();

        studentId = studentDAO.create(student);
        studentCourseDAO.addStudentToCourse(studentDAO.searchById(studentId),
                courseDAO.searchById(1));

        student = new Student.StudentBuilder()
                .firstName("Bob")
                .lastName("Smith")
                .age(18)
                .build();

        studentId = studentDAO.create(student);
        studentCourseDAO.addStudentToCourse(studentDAO.searchById(studentId),
                courseDAO.searchById(2));


    }

}