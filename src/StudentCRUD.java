import java.sql.*;

public class StudentCRUD {

    private static final java.lang.String JDBC_URL = "jdbc:mysql://localhost:3306/StudentCRUD";
    private static final java.lang.String JDBC_USER = "root";
    private static final java.lang.String JDBC_PASSWORD = "12345";

    public static void main(String[] args) {
        try {
            java.sql.Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // Create
            int newStudentId = createStudent(connection, "John Doe", 20, "A");
            System.out.println("Created student with ID: " + newStudentId);

            // Read
            readStudents(connection);

            // Update
            updateStudent(connection, newStudentId, "Jane Doe", 21, "B");
            System.out.println("Updated student with ID: " + newStudentId);

            // Read after update
            readStudents(connection);

            // Delete
            deleteStudent(connection, newStudentId);
            System.out.println("Deleted student with ID: " + newStudentId);

            // Read after delete
            readStudents(connection);

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int createStudent(Connection connection, String name, int age, String grade) throws SQLException {
        java.lang.String insertQuery = "INSERT INTO students (name, age, grade) VALUES (50, 60, 50)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, grade);

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return -1;
        }
    }

    private static void readStudents(Connection connection) throws SQLException {
        String selectQuery = "SELECT * FROM students";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectQuery);

            System.out.println("Students:");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") +
                        ", Name: " + resultSet.getString("name") +
                        ", Age: " + resultSet.getInt("age") +
                        ", Grade: " + resultSet.getString("grade"));
            }
            System.out.println();
        }
    }

    private static void updateStudent(Connection connection, int id, String name, int age, String grade) throws SQLException {
        String updateQuery = "UPDATE students SET name = ?, age = ?, grade = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, grade);
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        }
    }

    private static void deleteStudent(Connection connection, int id) throws SQLException {
        String deleteQuery = "DELETE FROM students WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
    }
}
