package lab7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Get all recipes that can be made in 15 minutes or less
     * @param dataService a service that provides access to recipe data
     * @return a list of quick recipes
     */
    public static List<Recipe> getQuickRecipes(DataService dataService) {
        try {
            var recipes = dataService.getRecipes();
            return recipes.stream().filter( r -> r.totalTime() <= 15 ).toList();
        } catch(Exception e ) {
            logger.error("Error while getting quick recipes: " + e.getMessage());
            logger.debug("Stack trace: " + Arrays.toString(e.getStackTrace()));
            return List.of();
        }
    }

    /**
     * Search for recipes that contain the given term in name or description
     * @param searchTerm the term to search for (case-insensitive)
     * @param dataService a service that provides access to recipe data
     * @return a list of matching recipes (empty list if no matches or error occurs)
     */
    public static List<Recipe> searchRecipes(String searchTerm, DataService dataService) {
        try {
            // 1.3 Get all recipes from the data service
            List<Recipe> allRecipes = dataService.getRecipes();

            // 1.3.1 & 1.3.2 Filter recipes where name or description contains the term (case-insensitive)
            return allRecipes.stream()
                    .filter(recipe ->
                            recipe.name().toLowerCase().contains(searchTerm.toLowerCase()) ||
                                    recipe.description().toLowerCase().contains(searchTerm.toLowerCase()))
                    .toList();

        } catch(Exception e) {
            // 1.4 Log errors and return empty list
            logger.error("Error while searching recipes: " + e.getMessage());
            logger.debug("Stack trace: " + Arrays.toString(e.getStackTrace()));
            return List.of();
        }
    }

    public static void main(String[] args) {
        // Here, we INJECT a concrete implementation of the DataService interface
        // that allows us to get data from an SQLite database
        var quickRecipes = getQuickRecipes(new SqliteDataService());
        System.out.println("Quick Recipes:");
        quickRecipes.forEach(System.out::println);

        // New search functionality
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\nEnter recipe name or description (or 'quit' to exit): ");
            String searchTerm = scanner.nextLine();

            if (searchTerm.equals("quit")) {
                break;
            }
            List<Recipe> results = searchRecipes(searchTerm, new SqliteDataService());

            if (results.isEmpty()) {
                System.out.println("No recipes found");
            } else {
                results.forEach(System.out::println);
            }
        }
        scanner.close();
    }
}
