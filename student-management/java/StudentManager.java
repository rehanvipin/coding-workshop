import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentManager {

    static class Student {
        String name;
        Double gpa;

        Student(String name) {
            this.name = name;
            this.gpa = null;
        }
    }

    static final List<Student> students = new ArrayList<>();
    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\nWelcome to Student Manager!\n");

        while (true) {
            showMenu();
            String choice = scanner.nextLine().strip();

            switch (choice) {
                case "1" -> addStudent();
                case "2" -> listStudents();
                case "3" -> removeStudent();
                case "4" -> assignGpa();
                case "5" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Please choose 1 to 5.\n");
            }
        }
    }

    static void showMenu() {
        System.out.println("""
                ==========================
                  Student Manager
                ==========================
                1. Add a student
                2. List all students
                3. Remove a student
                4. Assign GPA
                5. Quit
                --------------------------""");
    }

    static String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine().strip();
    }

    static Student findStudent(String name) {
        for (Student student : students) {
            if (student.name.equals(name)) {
                return student;
            }
        }
        return null;
    }

    static void addStudent() {
        String name = prompt("Enter student name: ");
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        students.add(new Student(name));
        System.out.println("'" + name + "' has been added to the class.");
    }

    static void listStudents() {
        if (students.isEmpty()) {
            System.out.println("No students in the class yet.");
            return;
        }
        System.out.println("\nClass List (" + students.size() + " students):");
        for (int i = 0; i < students.size(); i++) {
            String gpaDisplay = students.get(i).gpa != null ? String.valueOf(students.get(i).gpa) : "N/A";
            System.out.println("  " + (i + 1) + ". " + students.get(i).name + "  |  GPA: " + gpaDisplay);
        }
        int count = 0;
        double total = 0;
        for (Student student : students) {
            if (student.gpa != null) {
                total += student.gpa;
                count++;
            }
        }
        String average = count > 0 ? String.format("%.2f", total / count) : "N/A";
        System.out.println("\n  Class Average GPA: " + average + "\n");
    }

    static void removeStudent() {
        String name = prompt("Enter student name to remove: ");
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        Student student = findStudent(name);
        if (student != null) {
            students.remove(student);
            System.out.println("'" + name + "' has been removed from the class.");
        } else {
            System.out.println("'" + name + "' was not found in the class.");
        }
    }

    static void assignGpa() {
        String name = prompt("Enter student name: ");
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        Student student = findStudent(name);
        if (student == null) {
            System.out.println("'" + name + "' was not found in the class.");
            return;
        }
        String gpaInput = prompt("Enter GPA (0.0 - 10.0): ");
        double gpa;
        try {
            gpa = Double.parseDouble(gpaInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid GPA. Please enter a number.");
            return;
        }
        if (gpa < 0 || gpa > 10) {
            System.out.println("GPA must be between 0 and 10.");
            return;
        }
        student.gpa = gpa;
        System.out.println("GPA for '" + name + "' set to " + gpa + ".");
    }
}