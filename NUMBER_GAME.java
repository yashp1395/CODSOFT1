import java.util.Scanner;
import java.util.Random;

class GameEngine {
    // Generates a random number within the specified range
    public int generateRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
}

public class NumberGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameEngine engine = new GameEngine();
        
        int totalAttempts = 0;
        int totalWins = 0;
        int roundsPlayed = 0;

        System.out.println("Welcome to the Number Guessing Game!");

        while (true) {
            roundsPlayed++;
            System.out.println("\n--- Round " + roundsPlayed + " ---");
            
            // Get valid range from user
            int min = getValidInt(scanner, "Enter the minimum number: ");
            int max = getValidInt(scanner, "Enter the maximum number: ");
            
            // Ensure max is greater than min
            if (max <= min) {
                System.out.println("Invalid range! Setting max to " + (min + 10));
                max = min + 10;
            }

            int targetNumber = engine.generateRandomNumber(min, max);
            int attemptsInRound = 0;
            int maxAttemptsAllowed = 5; // Difficulty limit
            boolean wonRound = false;

            System.out.println("I've picked a number between " + min + " and " + max + ".");
            System.out.println("You have " + maxAttemptsAllowed + " attempts!");

            // Game Loop
            while (attemptsInRound < maxAttemptsAllowed) {
                int guess = getValidInt(scanner, "Attempt " + (attemptsInRound + 1) + " - Guess: ");
                attemptsInRound++;
                totalAttempts++;

                if (guess == targetNumber) {
                    System.out.println("Correct! You found it in " + attemptsInRound + " tries.");
                    totalWins++;
                    wonRound = true;
                    break;
                } else if (guess > targetNumber) {
                    System.out.println("Too high!");
                } else {
                    System.out.println("Too low!");
                }
            }

            if (!wonRound) {
                System.out.println("Game Over! The number was: " + targetNumber);
            }

            // Statistics
            double winRate = ((double) totalWins / roundsPlayed) * 100;
            System.out.println("\n--- Session Stats ---");
            System.out.println("Wins: " + totalWins + " | Total Rounds: " + roundsPlayed);
            System.out.printf("Current Win Rate: %.2f%%\n", winRate);

            // Replay Logic
            System.out.print("Do you want to play again? (y/n): ");
            String playAgain = scanner.next();
            if (!playAgain.equalsIgnoreCase("y")) {
                break;
            }
        }

        System.out.println("Thanks for playing! Final Score: " + totalWins + " wins.");
        scanner.close();
    }

    // Helper method to prevent crashes if user enters a string instead of an int
    private static int getValidInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                return sc.nextInt();
            } else {
                System.out.println("Error: Please enter a valid whole number.");
                sc.next(); // clear the invalid input
            }
        }
    }
}
