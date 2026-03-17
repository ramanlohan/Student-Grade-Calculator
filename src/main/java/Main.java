import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Connection conn = DBConnection.getConnection();

        if (conn != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Connection failed.");
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Select one of the following-");
        System.out.println("1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Update Student Marks");
        System.out.println("4. Delete Student");
        System.out.println("5. Exit Program");

        int choice = sc.nextInt();
        sc.nextLine();
        while(true){
        if (choice == 1) {

            System.out.println("Enter student name:");
            String name = sc.nextLine();

            System.out.println("Enter marks:");
            int marks = sc.nextInt();

            String grade = calculateGrade(marks);

            Student student = new Student(name, marks, grade);

            insertStudent(student);

        } else if (choice == 2) {

            viewStudents();

        } else if (choice == 3) {

            System.out.println("Enter student id:");
            int id = sc.nextInt();

            System.out.println("Enter new marks:");
            int marks = sc.nextInt();

            updateStudentMarks(id, marks);

        } else if (choice == 4) {

            System.out.println("Enter student id:");
            int id = sc.nextInt();

            deleteStudent(id);

        } else if (choice == 5) {

            System.out.println("Exiting program...");
            break;
        } else {
            System.out.println("Invalid choice!");
        }
    }
    }
    public static String calculateGrade(int marks) {

        if (marks >= 90) return "A";
        else if (marks >= 75) return "B";
        else if (marks >= 60) return "C";
        else return "F";
    }
    public static void insertStudent(Student student) {

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO students(name, marks, grade) VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, student.getName());
            ps.setInt(2, student.getMarks());
            ps.setString(3, student.getGrade());

            ps.executeUpdate();

            System.out.println("Student saved successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void viewStudents() {

        try {
            Connection conn = DBConnection.getConnection();

            String query = "SELECT * FROM students";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getInt("marks") + " | " +
                                rs.getString("grade")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void deleteStudent(int id) {

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "DELETE FROM students WHERE id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Student not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void updateStudentMarks(int id, int newMarks) {

        try {
            Connection conn = DBConnection.getConnection();

            String newGrade = calculateGrade(newMarks);

            String sql = "UPDATE students SET marks=?, grade=? WHERE id=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, newMarks);
            ps.setString(2, newGrade);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Student not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
