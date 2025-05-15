package lab1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfGames;
        int[] scores;

        System.out.println("How many games did you bowl?: ");
        numberOfGames = scanner.nextInt();

        scores = new int[numberOfGames];

        for (int i = 0; i < numberOfGames; i++) {
            System.out.println("Enter score of game " + (i + 1) + ": ");
            scores[i] = scanner.nextInt();
        }

        try {
            FileWriter writer = new FileWriter("bowling_scores.txt");

            writer.write("Bowling Scores:\n");
            for (int i = 0; i < numberOfGames; i++) {
                writer.write("Game " + (i + 1) + ": " + scores[i] + "\n");
            }
            double average = calculateAverage(scores);

            writer.write("Average Score: " + average + "\n");
            System.out.println("Average Score: " + average);

            writer.close();
            System.out.println("Scores saved to bowling_scores.txt");

        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
        scanner.close();
    }
    public static double calculateAverage(int[] scores) {
        int total = 0;

        for (int score : scores) {
            total += score; // Add each score to total
        }

        return (double) total / scores.length; // Return average as decimal
    }
}
