package lab1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Author: James Konieczny
 * Course: Programming Concept I (CSD121) - Sault College
 * Date: May 16, 2025
 * Description:
 * This class calculates your bowling score average.
 * Takes input on numberOfGames bowled and their respective scores.
 * Repeats questions if no games were bowled or scores aren't between 0 and 300.
 * Writes scores to file, along with average.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // new scanner object that listens for input from keyboard
        int numberOfGames;
        int[] scores;

       while (true) { // repeats the question if numberOfGames is less than or equal to 0 (if true)
           System.out.println("How many games did you bowl?: ");
           numberOfGames = scanner.nextInt(); // get user input for how many games they bowled

           if (numberOfGames <= 0) {
               System.out.println("You must bowl in order to calculate your bowling average.");
           } else
               break;
       }

        scores = new int[numberOfGames];

        for (int i = 0; i < numberOfGames; i++) { // for every i - starting at i = 0 - ask user for game score then add 1 to i. Do this as long as i is less than numberOfGames
            while (true) { // ask question again if conditions aren't met
                System.out.println("Enter score of game " + (i + 1) + ": "); // i + 1 because i starts at 0, but this needs to start at 1 here
                int score = scanner.nextInt(); // get user input for score
                if (score > 300) {
                    System.out.println("Only the bowling gods can get greater than a perfect game!"); // the bowling gods is an inside joke :)
                } else if (score < 0) {
                    System.out.println("Must be a positive integer.");
                } else {
                    scores[i] = score;
                    break;
                }
            }
        }

        try {
            FileWriter writer = new FileWriter("bowling_scores.txt", true); // creates a new text file named bowling_scores.txt (if it doesn't already exist), and gets it ready for writing. If the file already exists, the true tells Java to append instead of overwrite.

            writer.write("Bowling Scores:\n"); // write title
            for (int i = 0; i < numberOfGames; i++) {
                writer.write("Game " + (i + 1) + ": " + scores[i] + "\n"); // write scores
            }
            double average = calculateAverage(scores); // call method to calculate average of scores and store result in 'average'

            writer.write("Average Score: " + average + "\n"); // write average
            System.out.println("Average Score: " + average); // print average

            writer.close();
            System.out.println("Scores saved to bowling_scores.txt");

        } catch (IOException e) { // IO means input output, IOException is a specific type of error, chosen here because it happens during input/output operations — like reading or writing files.
            System.out.println("Error writing to file.");
        }
        scanner.close(); // all done getting input, so close scanner object
    }

    /**
     * Calculates the average of the given array of bowling scores.
     * @param scores An array of integers representing bowling scores.
     * @return The average score as a double.
     */
    public static double calculateAverage(int[] scores) {
        int total = 0;

        for (int score : scores) {
            total += score;
        }

        return (double) total / scores.length;
    }
}
