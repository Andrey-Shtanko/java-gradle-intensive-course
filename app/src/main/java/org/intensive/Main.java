package org.intensive;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static void main() throws SQLException {

//        Scanner scanner = new Scanner(System.in);
//
//        String inputText = scanner.nextLine();
//
//        WordWrap wrappedContent = new WordWrap(20, inputText);
//
//        wrappedContent.wrap();

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/student_db");
        config.setUsername("postgres");
        config.setPassword("postgres");

        HikariDataSource dataSource = new HikariDataSource(config);

        System.out.println("""
                ==============================
                 Student Management System
                ==============================
                
                1. Show students
                2. Add student
                3. Update student
                4. Delete student
                5. Show courses
                6. Show students in group
                7. Transfer student
                0. Exit
                
                Choose option:
                """);

        Scanner scanner = new Scanner(System.in);
        int choosenOption = scanner.nextInt();

        switch (choosenOption) {
            case 1: showStudents(dataSource); break;
            case 2: {
                System.out.print("Enter student name: ");
                String name = scanner.next();
                System.out.print("Enter student surname: ");
                String surname = scanner.next();
                System.out.print("Enter student email: ");
                String email = scanner.next();
                System.out.print("Enter group ID: ");
                int groupId = scanner.nextInt();
                addStudent(dataSource, name, surname, email, groupId);
                break;
            }
            case 3: {
                System.out.print("Enter student ID: ");
                int studentId = scanner.nextInt();
                System.out.print("Enter student name: ");
                String name = scanner.next();
                System.out.print("Enter student surname: ");
                String surname = scanner.next();
                System.out.print("Enter student email: ");
                String email = scanner.next();
                updateStudent(dataSource, studentId, name, surname, email);
                break;
            }
            case 4: {
                System.out.print("Enter student ID: ");
                int studentId = scanner.nextInt();
                deleteStudent(dataSource, studentId);
                break;
            }
            case 5: showCourses(dataSource); break;
            case 6: {
                System.out.print("Enter student ID: ");
                int studentId = scanner.nextInt();
                showStudentsInGroup(dataSource, studentId);
                break;
            }
            case 7: {
                System.out.println("Enter student ID to transfer:");
                int studentId = scanner.nextInt();
                System.out.println("Enter new group ID:");
                int newGroupId = scanner.nextInt();
                transferStudent(dataSource, studentId, newGroupId);
            } break;
            case 0: System.exit(0); break;
            default: System.out.println("Invalid option. Please try again.");
        }


        transferStudent(dataSource, 1, 2);

        Map<String, List<String>> studentsWithCourses = findStudentsWithCourses(dataSource);

       studentsWithCourses.forEach((student, courses) -> {
            System.out.println(student + " is enrolled in: " + String.join(", ", courses));
        });

        Flyway flyway = Flyway.configure()
                .dataSource(
                        "jdbc:postgresql://localhost:5432/student_db",
                        "postgres",
                        "postgres"
                )
                .load();

        flyway.migrate();

    }

    private static void transferStudent(DataSource dataSource, int studentId, int newGroupId) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try {
                String selectOldGroupQuery = "SELECT group_id FROM students WHERE id = ?";
                String updateQuery = "UPDATE students SET group_id = ? WHERE id = ?";
                String insertHistoryQuery = "INSERT INTO student_history (student_id, old_group_id, new_group_id, changed_at)VALUES (?, ?, ?, NOW())";

                int oldGroupId;

                try (PreparedStatement selectStatement = connection.prepareStatement(selectOldGroupQuery)) {
                    selectStatement.setInt(1, studentId);

                    try (ResultSet resultSet = selectStatement.executeQuery()) {
                        if (!resultSet.next()) {
                            connection.rollback();
                            System.out.println("No student found with the given ID.");
                            return;
                        }

                        oldGroupId = resultSet.getInt("group_id");
                    }
                }

                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setInt(1, newGroupId);
                    updateStatement.setInt(2, studentId);

                    updateStatement.executeUpdate();
                }

                try (PreparedStatement historyStatement = connection.prepareStatement(insertHistoryQuery)) {
                    historyStatement.setInt(1, studentId);
                    historyStatement.setInt(2, oldGroupId);
                    historyStatement.setInt(3, newGroupId);

                    historyStatement.executeUpdate();
                }

                connection.commit();
                System.out.println("Student transferred successfully.");
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    private static Map<String, List<String>> findStudentsWithCourses(DataSource dataSource) throws SQLException {
        Map<String, List<String>> studentsCourses = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {
            try {
                String selectCoursesForEveryStudentQuery = """
                                                                SELECT
                                                                    s.id AS student_id,
                                                                    s.first_name,
                                                                    s.last_name,
                                                                    c.name AS course_name
                                                                FROM students s
                                                                JOIN student_courses sc
                                                                    ON s.id = sc.student_id
                                                                JOIN courses c
                                                                    ON sc.course_id = c.id
                                                                GROUP BY s.id, s.first_name, s.last_name, c.name
                                                                ORDER BY s.id
                                                            """;

                PreparedStatement selectStatement = connection.prepareStatement(selectCoursesForEveryStudentQuery);
                ResultSet resultSet = selectStatement.executeQuery();

                while (resultSet.next()) {
                    int studentId = resultSet.getInt("student_id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String courseName = resultSet.getString("course_name");

                    String studentInfo = String.format("ID: %d, Name: %s %s", studentId, firstName, lastName);
                    studentsCourses.computeIfAbsent(studentInfo, k -> new ArrayList<>()).add(courseName);
                }
            }catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }

        return studentsCourses;
    }

    private static void showStudents(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM students");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                System.out.printf("ID: %d, Name: %s, %s%n", id, firstName, lastName);
            }
        }
    }

    private static void addStudent(DataSource dataSource, String firstName, String lastName, String email, int groupId) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            String addStudentQuery = "INSERT INTO students (first_name, last_name, email, group_id)";

            try (PreparedStatement statement = connection.prepareStatement(addStudentQuery)) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, email);
                statement.setInt(4, groupId);
                statement.executeUpdate();

                System.out.println("Student added successfully");
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    private static void updateStudent(DataSource dataSource, int studentId, String firstName, String lastName, String email) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            String updateStudentQuery = """
                    UPDATE students
                    SET first_name = ?, last_name = ?, email = ?,
                    WHERE id = ?
                    """;
            try (PreparedStatement statement = connection.prepareStatement(updateStudentQuery)) {
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, email);
                statement.setInt(4, studentId);

                statement.executeUpdate();
                System.out.println("Student updated successfully.");
                connection.commit();
            }catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }


    private static void deleteStudent(DataSource dataSource, int studentId) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            String deleteStudentQuery = "DELETE FROM students WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteStudentQuery)) {
                statement.setInt(1, studentId);
                statement.executeUpdate();
                System.out.println("Student deleted successfully.");
            }catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    private static void showCourses(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM courses");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int courseId = resultSet.getInt("course_id");
                String courseName = resultSet.getString("course_name");
                System.out.printf("ID: %d, Name: %s, %s%n", courseId, courseName, courseName);
            }
        }
    }

    private static void showStudentsInGroup (DataSource dataSource, int groupId) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE group_id = ?");
            statement.setInt(1, groupId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                System.out.printf("ID: %d, Name: %s, %s%n", studentId, firstName, lastName);
            }
        }
    }
}




//SELECT students.id AS student_id,
//students.first_name,
//students.last_name,
//students.email
//FROM students;
//
//SElECT s.id AS student_id,
//s.first_name,
//s.last_name,
//g.name AS group_name,
//c.name AS course_name
//FROM students s
//JOIN groups g ON s.group_id = g.id
//JOIN student_courses sc ON s.id = sc.student_id
//JOIN courses c ON sc.course_id = c.id
//WHERE g.name = 'Java-101';
//
//SELECT c.id AS course_id,
//c.name AS course_name,
//s.first_name,
//s.last_name
//
//FROM courses c
//INNER JOIN student_courses sc ON c.id = sc.course_id
//InNER JOIN students s ON sc.student_id = s.id;
//
//SELECT s.id AS student_id,
//s.first_name,
//s.last_name,
//sc.course_id
//FROM students s
//LEFT JOIN student_courses sc ON s.id = sc.student_id
//WHERE sc.course_id IS NULL;
//
//
//
//SELECT COUNT(*) AS student_count_in_group, g.name AS group_name
//FROM students
//JOIN groups g ON students.group_id = g.id
//GROUP BY group_id, g.name;
//
//SELECT COUNT(*) AS student_count_in_course, c.name AS course_name
//FROM student_courses sc
//JOIN courses c ON sc.course_id = c.id
//GROUP BY sc.course_id, c.name;
//
//SELECT COUNT(*) AS student_count_in_group, g.name AS group_name
//FROM students
//JOIN groups g ON students.group_id = g.id
//GROUP BY group_id, g.name
//ORDER BY student_count_in_group DESC
//LIMIT 1;
//
//SELECT COUNT(*) AS student_count_in_course, c.name AS course_name
//FROM student_courses sc
//JOIN courses c ON sc.course_id = c.id
//GROUP BY sc.course_id, c.name
//ORDER BY student_count_in_course DESC
//LIMIT 1;

