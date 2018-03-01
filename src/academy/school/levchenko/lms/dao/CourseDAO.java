package academy.school.levchenko.lms.dao;

import academy.school.levchenko.lms.model.Course;

import java.util.List;

public interface CourseDAO {

    int create(Course course) throws Exception;

    boolean delete(int id) throws Exception;

    Course searchById(int id) throws Exception;

    Course searchByName(String name) throws Exception;

    boolean update(Course course) throws Exception;

    List<Course> getAll() throws Exception;


}
