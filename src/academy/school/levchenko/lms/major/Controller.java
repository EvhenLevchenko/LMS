package academy.school.levchenko.lms.major;

import academy.school.levchenko.lms.dao.*;
import academy.school.levchenko.lms.dao.database.StudentDAOImpl;

import academy.school.levchenko.lms.model.*;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Controller {

    public Controller() {
        DAOFactory daoFactory = DAOFactory.getInstance(DAOFactory.Type.DATABASE);
        courseDAO = daoFactory.getCourseDAO();
        teacherDAO = daoFactory.getTeacherDAO();
        studentDAO = daoFactory.getStudentDAO();
        studentCourseDAO = daoFactory.getStudentCourseDAO();
    }



    private final String COURSE_CREATED_OK =  "Курс занесен в базу:";
    private final String TEACHER_CREATED_OK = "Учитель занесен в базу:";
    private final String STUDENT_CREATED_OK = "Студент занесен в базу:";


    private final int studentLimit = 3;

    // ------------

    private CourseDAO courseDAO;
    private TeacherDAO teacherDAO;
    private StudentDAO studentDAO;
    private StudentCourseDAO studentCourseDAO;

    public void please() {

        Scanner sc = new Scanner(System.in);
        String inputCommand = sc.nextLine();
        while (true) {
            String choice = sc.nextLine();
            int id;

            switch (choice) {
                case "1":
                    showCourses();
                    break;
                case "2":
                    showTrainers();
                    break;
                case "3":
                    showStudents();
                    break;
                case "4":
                    id = createCourse();//Todo
                    System.out.println(COURSE_CREATED_OK);
                    showCourseById(id);
                    break;
                case "5":
                    id = createTrainer();
                    System.out.println(TEACHER_CREATED_OK);
                    break;
                case "6":
                    id = createStudent();
                    System.out.println(STUDENT_CREATED_OK);
                    showStudentById(id);
                    break;

                case "Меню":
                    System.out.println("   Список команд: ");
                    System.out.println("1. Список курсов");
                    System.out.println("2. Список учителей");
                    System.out.println("3. Список студентов");
                    System.out.println("4. Cоздание курса");
                    System.out.println("5. Cоздание учителя");
                    System.out.println("6. Cоздание студента");
                    System.out.println("7. Удаление студента");
                    System.out.println("8. Удаление учителя");
                    System.out.println("9. Удаление курса");
                    System.out.println("7: Выход");
                    break;
                case "7":
                    System.out.println("\uD83C\uDF7B");
                    sc.close();
                    return;
            }
        }
    }

    public static void main(String[] args) {
        new Controller().please();

    }

    private ObservableList<Student> entities;
    private StudentDAOImpl persistence = new StudentDAOImpl();
    public void delete(Student student ) throws Exception {
        this.persistence.delete(student);
        System.out.println(this.persistence.get(student));
        if (this.persistence.get(student) == null) {
            this.entities.remove(this.entities.indexOf(student));
        }

    }

    private void showStudentById(int id) {

        Student student = null;
        List<Course> courses = null;

        try {
            student = studentDAO.searchById(id);
        } catch (Exception e) {
            System.err.println("\uD83C\uDF7A");
        }

        if (student == null) {
            System.out.printf("Студент с id %d не существует%n", id);
            System.out.println();
            return;
        }

        try {
            courses = studentCourseDAO.findCoursesByStudentId(id);
        } catch (Exception e) {
            System.err.println("\uD83C\uDF7A");
        }

        student.setCourses(courses);

        System.out.printf("ID студента : %d%n", student.getId());
        System.out.printf("Имя        : %s %s%n",
                student.getFirstName(), student.getLastName());
        System.out.println("Курс(сы)  : ");
        courses = student.getCourses();
        if (courses == null || courses.size() == 0) {
        System.out.println("\tНе учится");
        } else {
            courses.forEach(course ->
                    System.out.println("\t" + course.getId() + " : " +
                            course.getName()));
        }

        System.out.println();

    }

    private void showCourseById(int id) {

        Course course = null;

        try {
            course = courseDAO.searchById(id);
        } catch (Exception e) {
            System.err.println("\uD83C\uDF7A");
        }

        if (course == null) {
            System.out.printf("Курс с id %d не существует%n", id);
            System.out.println();
            return;
        }

        System.out.printf("ID курса            : %d%n", course.getId());
        System.out.printf("Название курса      : %s%n", course.getName());
        System.out.printf("Описание курса      : %s%n", course.getDescription());
        System.out.printf("Дата начала         : %s%n", convertDateToString(course.getStartDate()));
        System.out.printf("Дата окончания      : %s%n", convertDateToString(course.getEndDate()));
        System.out.printf("Дни               : %s%n", course.getDaysOfWeek());
        if (course.getTeacher() != null) {
            System.out.printf("Trainer             : %d : %s %s%n",
                    course.getTeacher().getId(),
                    course.getTeacher().getFirstName(),
                    course.getTeacher().getLastName());
        } else {
            System.out.println("Учитель            : еще не назначен");
        }

        System.out.println();

    }

    private void showTrainers() {

        List<Teacher> teachers = null;

        try {
            teachers = teacherDAO.getAll();
        } catch (Exception e) {
            System.err.println("\uD83C\uDF7A");
        }

        if (teachers == null || teachers.size() == 0) {
            System.out.println("No trainers available yet");
            System.out.println();
            return;
        }

        System.out.println("Trainer(s):");
        teachers.forEach(trainer ->
                System.out.printf("\t%d : %s %s%n",
                        trainer.getId(), trainer.getLastName(),
                        trainer.getFirstName()));

        System.out.println();

    }

    private void showCourses() {

        List<Course> courses = null;

        try {
            courses = courseDAO.getAll();
        } catch (Exception e) {
            System.err.println("\uD83C\uDF7A");
        }

        if (courses == null || courses.size() == 0) {
            System.out.println("Нет добавленых курсов");
            System.out.println();
            return;
        }

        System.out.println("Курс(сы):");
        courses.forEach(course -> {
            System.out.printf("\t%d : %s%n", course.getId(), course.getName());
            System.out.printf("\t\t%s%n", course.getDescription());
        });

        System.out.println();

    }

    private void showStudents() {

        List<Student> students = null;

        try {
            students = studentDAO.getAll();
        } catch (Exception e) {
            System.err.println("\uD83C\uDF7A");
        }

        if (students == null || students.size() == 0) {
            System.out.println("Студенты осутсвуют");
            System.out.println();
            return;
        }

        System.out.println("Студент(ты):");
        students.forEach(student ->
                System.out.printf("\t%d : %s %s%n", student.getId(),
                        student.getLastName(), student.getFirstName()));
        System.out.println();

    }

    private int createCourse() {

        Scanner sc = new Scanner(System.in);

        String name = "";
        String description;
        String startDate;
        String endDate;
        String daysOfWeek;

        System.out.print("Название курса            : ");
        name = sc.nextLine();
        System.out.print("Описание курса            : ");
        description = sc.nextLine();
        System.out.print("Дата начала (dd.MM.yyyy)  : ");
        startDate = sc.nextLine();
        System.out.print("Дата окончания            : ");
        endDate = sc.nextLine();
        System.out.print("Дни                       : ");
        daysOfWeek = sc.nextLine();

        Course course = new Course.CourseBuilder()
                .name(name)
                .description(description)
                .startDate(convertStringToDate(startDate))
                .endDate(convertStringToDate(endDate))
                .daysOfWeek(daysOfWeek)
                .build();

        int result = -1;
        try {
            result = courseDAO.create(course);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();

        return result;

    }

    private int createTrainer() {

        Scanner sc = new Scanner(System.in);

        String firstName;
        String lastName;

        System.out.println();

        while (true) {
            System.out.print("Имя      : ");
            firstName = sc.nextLine();
            System.out.print("Фамилия  : ");
            lastName = sc.nextLine();

            Teacher teacher = null;
            try {
                teacher = teacherDAO.searchByName(firstName, lastName);
            } catch (Exception e) {
                System.err.println("\uD83C\uDF7A");
            }
            if (teacher != null) {
                System.out.println("\u26A0"+"Извините но название уже занято. \n"+"\u26A0\n");
                continue;
            }
            break;
        }

        System.out.print("Курс(сы)  : ");
        String coursesStr = sc.nextLine();

        Teacher teacher = new Teacher.TeacherBuilder()
                .firstName(firstName)
                .lastName(lastName)
                .build();

        int result = -1;
        try {
            result = teacherDAO.create(teacher);
        } catch (Exception e) {
            System.err.println("\uD83C\uDF7A");
        }

        String[] courseIds = coursesStr.split(",");
        int id;
        for (String courseId : courseIds) {
            try {
                id = Integer.parseInt(courseId);
            } catch (NumberFormatException e) {
                System.out.printf("Ошибка в id Курса '%s'", courseId);
                continue;
            }

            Course course = null;
            try {
                course = courseDAO.searchById(id);
            } catch (Exception e) {
                System.err.println("\uD83C\uDF7A");
            }

            if (course == null) {
                System.out.printf("Курс с таким id %d не существует%n", id);
            } else {
                if (course.getTeacher() != null) {
                    System.out.printf("У %d курса уже есть учитель%n", id);
                } else {

                    try {
                        course.setTeacher(teacherDAO.searchById(result));
                        courseDAO.update(course);
                    } catch (Exception e) {
                        System.err.println("\uD83C\uDF7A");
                    }

                }
            }
        }

        System.out.println();

        return result;
    }

    private int createStudent() {

        Scanner sc = new Scanner(System.in);

        String firstName;
        String lastName;
        int age;

        System.out.println();


        while (true) {
            System.out.print("Имя        : ");
            firstName = sc.nextLine();
            System.out.print("Фамилия    : ");
            lastName = sc.nextLine();

            Student student = null;
            try {
                student = studentDAO.searchByName(firstName, lastName);
            } catch (Exception e) {
                System.err.println("\uD83C\uDF7A");
            }
            if (student != null) {
                System.out.println("\u26A0"+"Извините но имя уже занято. \n"+"\u26A0\n");
                continue;
            }
            break;
        }

        while (true) {
            try {
                System.out.print("Возраст    : ");
                age = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("'Возраст' это все еще цыфра");
            }
        }

        System.out.print("Курс(сы)      : ");
        String coursesStr = sc.nextLine();

        Student student = new Student.StudentBuilder()
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .build();

        int result = -1;
        try {
            result = studentDAO.create(student);
        } catch (Exception e) {
            System.err.println("\uD83C\uDF7A");
        }

        String[] courseIds = coursesStr.split(",");
        int id;
        for (String courseId : courseIds) {
            try {
                id = Integer.parseInt(courseId);
            } catch (NumberFormatException e) {
                System.out.printf("\u26A0\n"+"Ошибка в id Курса '%s'", courseId);
                continue;
            }

            Course course = null;
            List<Student> studentsInCourse = null;

            try {
                course = courseDAO.searchById(id);
                studentsInCourse = studentCourseDAO.findStudentsByCourseId(id);
            } catch (Exception e) {
                System.err.println("\uD83C\uDF7A");
            }

            if (course == null) {
                System.out.printf("Курс с id %d не существует%n", id);
            } else if (studentsInCourse != null
                    && studentsInCourse.size() >= studentLimit) {
                System.out.printf("Course %d cannot have more than %d students%n",
                        id, studentLimit);
            } else {
                try {
                    studentCourseDAO.addStudentToCourse(
                            studentDAO.searchById(result),
                            course);
                } catch (Exception e) {
                    System.err.println("\uD83C\uDF7A");
                }
            }
        }

        System.out.println();

        return result;
    }

    public static String convertDateToString(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static LocalDate convertStringToDate(String string) {
        return LocalDate.parse(string, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}



