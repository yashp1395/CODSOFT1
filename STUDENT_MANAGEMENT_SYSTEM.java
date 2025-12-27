import java.io.*;
import java.util.*;

class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public int getRollNumber() { return rollNumber; }
    public String getName() { return name; }
    public String getGrade() { return grade; }

    public void setName(String name) { this.name = name; }
    public void setGrade(String grade) { this.grade = grade; }

    @Override
    public String toString() {
        return String.format("Roll No: %d | Name: %-15s | Grade: %s", rollNumber, name, grade);
    }
}

class ManagementSystem {
    private List<Student> students = new ArrayList<>();
    private final String FILE_NAME = "students.txt";

    public ManagementSystem() {
        loadData();
    }

    public void addStudent(Student s) {
        students.add(s);
        saveData();
    }

    public boolean removeStudent(int roll) {
        boolean removed = students.removeIf(s -> s.getRollNumber() == roll);
        if (removed) saveData();
        return removed;
    }

    public Student searchStudent(int roll) {
        for (Student s : students) {
            if (s.getRollNumber() == roll) return s;
        }
        return null;
    }

    public void displayAll() {
        if (students.isEmpty()) System.out.println("No records found.");
        else students.forEach(System.out::println);
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (List<Student>) ois.readObject();
        } catch (Exception e) {
            System.out.println("No existing data found, starting fresh.");
        }
    }
}

public class StudentApp {
    private static final Scanner sc = new Scanner(System.in);
    private static final ManagementSystem sms = new ManagementSystem();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- STUDENT MANAGEMENT SYSTEM ---");
            System.out.println("1. Add Student  2. Remove  3. Search  4. Display All  5. Edit  6. Exit");
            System.out.print("Select: ");
            
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> addStudent();
                case "2" -> removeStudent();
                case "3" -> searchStudent();
                case "4" -> sms.displayAll();
                case "5" -> editStudent();
                case "6" -> { System.out.println("Exiting..."); return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter Name: ");
        String name = validateString();
        System.out.print("Enter Roll Number: ");
        int roll = validateInt();
        System.out.print("Enter Grade: ");
        String grade = validateString();

        sms.addStudent(new Student(name, roll, grade));
        System.out.println("Student added successfully.");
    }

    private static void removeStudent() {
        System.out.print("Enter Roll Number to remove: ");
        int roll = validateInt();
        if (sms.removeStudent(roll)) System.out.println("Removed successfully.");
        else System.out.println("Student not found.");
    }

    private static void searchStudent() {
        System.out.print("Enter Roll Number to search: ");
        int roll = validateInt();
        Student s = sms.searchStudent(roll);
        System.out.println(s != null ? s : "Student not found.");
    }

    private static void editStudent() {
        System.out.print("Enter Roll Number to edit: ");
        int roll = validateInt();
        Student s = sms.searchStudent(roll);
        if (s != null) {
            System.out.print("Enter New Name (leave blank to keep current): ");
            String name = sc.nextLine();
            if (!name.isEmpty()) s.setName(name);
            System.out.print("Enter New Grade (leave blank to keep current): ");
            String grade = sc.nextLine();
            if (!grade.isEmpty()) s.setGrade(grade);
            System.out.println("Record updated.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static String validateString() {
        String input = sc.nextLine();
        while (input.trim().isEmpty()) {
            System.out.print("Field cannot be empty. Enter again: ");
            input = sc.nextLine();
        }
        return input;
    }

    private static int validateInt() {
        while (true) {
            try {
                int val = Integer.parseInt(sc.nextLine());
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a numeric roll number: ");
            }
        }
    }
}
