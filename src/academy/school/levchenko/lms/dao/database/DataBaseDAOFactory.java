package academy.school.levchenko.lms.dao.database;

import academy.school.levchenko.lms.dao.*;

public class DataBaseDAOFactory implements DAOFactory {

    @Override
    public StudentDAO getStudentDAO() {
        return new StudentDAOImpl();
    }

    @Override
    public CourseDAO getCourseDAO() {
        return new CourseDAOImpl();
    }

    @Override
    public TeacherDAOImpl getTeacherDAO() {
        return new TeacherDAOImpl();
    }

    @Override
    public StudentCourseDAO getStudentCourseDAO() {
        return new StudentCourseDAOImpl();
    }
}
