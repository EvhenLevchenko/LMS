package academy.school.levchenko.lms.dao;


import academy.school.levchenko.lms.model.*;

import java.util.List;

public interface StudentCourseDAO {

    boolean addStudentToCourse(Student student, Course course) throws Exception;

    boolean removeStudentFromCourse(Student student, Course course) throws Exception;

    List<Student> findStudentsByCourseId(int id) throws Exception;

    List<Course> findCoursesByStudentId(int id) throws Exception;

}
