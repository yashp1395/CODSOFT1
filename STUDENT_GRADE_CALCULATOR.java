import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Subject {
    private final String name;
    private final double marks;

    public Subject(String name, double marks) {
        this.name = name;
        this.marks = marks;
    }

    public double getMarks() {
        return marks;
    }
}

class GradeService {
    public double calculateTotal(List<Subject> subjects) {
        return subjects.stream().mapToDouble(Subject::getMarks).sum();
    }

    public double calculateAverage(double total, int count) {
        return (count == 0) ? 0 : total / count;
    }

    public String determineGrade(double percentage) {
        if (percentage >= 90) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 70) return "B";
        if (percentage >= 60) return "C";
        if (percentage >= 50) return "D";
        return "F";
    }
}

public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GradeService service = new GradeService();
        List<Subject> subjects = new ArrayList<>();

        System.out.print("Enter number of subjects: ");
        int numSubjects = sc.nextInt();

        for (int i = 1; i <= numSubjects; i++) {
            System.out.print("Subject " + i + " Name: ");
            String name = sc.next();
            
            double marks;
            do {
                System.out.print("Marks for " + name + " (0-100): ");
                marks = sc.nextDouble();
            } while (marks < 0 || marks > 100);
            
            subjects.add(new Subject(name, marks));
        }

        double total = service.calculateTotal(subjects);
        double average = service.calculateAverage(total, subjects.size());
        String grade = service.determineGrade(average);

        printReport(total, average, grade);
        sc.close();
    }

    private static void printReport(double total, double avg, String grade) {
        System.out.println("\n--- RESULTS ---");
        System.out.printf("Total Marks: %.2f\n", total);
        System.out.printf("Average:     %.2f%%\n", avg);
        System.out.println("Grade:       " + grade);
    }
}
