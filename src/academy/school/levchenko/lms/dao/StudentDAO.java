package academy.school.levchenko.lms.dao;

import academy.school.levchenko.lms.model.Student;

import java.util.List;

public interface StudentDAO {

    int create(Student student) throws Exception;

    Student searchById(int id) throws Exception;

    Student searchByName(String firstName, String lastName) throws Exception;

    boolean isStudentPresence(Student student) throws Exception;

    boolean update(Student student) throws Exception;

    List<Student> getAll() throws Exception;

    void delete(Student student) throws Exception;

    Student get(Student student) throws Exception;
}
