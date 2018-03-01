package academy.school.levchenko.lms.dao;

import academy.school.levchenko.lms.dao.database.DataBaseDAOFactory;
import academy.school.levchenko.lms.dao.database.TeacherDAOImpl;

public interface DAOFactory {

    enum Type { DATABASE, LIST, FILE }

    StudentDAO getStudentDAO();

    CourseDAO getCourseDAO();

    TeacherDAOImpl getTeacherDAO();

    StudentCourseDAO getStudentCourseDAO();

    static DAOFactory getInstance(Type type) {
        switch (type) {
            case DATABASE:
                return new DataBaseDAOFactory();
            case LIST:
                // return new ListDAOFactory();
                return null;
            case FILE:
                // return new FileDAOFactory();
            default:
                return null;
        }
    }

}
