package academy.school.levchenko.lms.dao;

import academy.school.levchenko.lms.model.Teacher;


import java.util.List;

public interface TeacherDAO {

    int create(Teacher teacher) throws Exception;

    boolean delete(int id) throws Exception;

    Teacher searchById(int id) throws Exception;

    Teacher searchByName(String firstName, String lastName) throws Exception;

    void update(Teacher teacher) throws Exception;

    List<Teacher> getAll() throws Exception;

}
