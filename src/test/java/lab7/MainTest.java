package lab7;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testGetQuickRecipesReturnsEmptyListIfNoData() {
        var recipes = Main.getQuickRecipes(List::of);
        assertEquals(0, recipes.size());
    }

    @Test
    void testGetQuickRecipesReturnsEmptyListIfNoQuickRecipes() {
        var recipes = Main.getQuickRecipes(() -> List.of(
                new Recipe(0, "", "", "", 4, 10, 10, 16),
                new Recipe(1, "", "", "", 4, 10, 10, 20),
                new Recipe(2, "", "", "", 4, 10, 10, 200)
        ));
        assertEquals(0, recipes.size());
    }

    @Test
    void testGetQuickRecipesReturnsAllRecipesIfAllQuick() {

        var recipes = Main.getQuickRecipes(() -> List.of(
                new Recipe(0, "", "", "", 4, 10, 10, 15),
                new Recipe(1, "", "", "", 4, 10, 10, 1),
                new Recipe(2, "", "", "", 4, 10, 10, 10)
        ));

        assertEquals(3, recipes.size());
    }

    @Test
    void testGetQuickRecipesWorksOnTypicalData() {

        var recipes = Main.getQuickRecipes(() -> List.of(
                new Recipe(0, "", "", "", 4, 10, 10, 10),
                new Recipe(1, "", "", "", 4, 10, 10, 15),
                new Recipe(2, "", "", "", 4, 10, 10, 16),
                new Recipe(3, "", "", "", 4, 10, 10, 20),
                new Recipe(4, "", "", "", 4, 10, 10, 2343)
        ));

        assertEquals(2, recipes.size());

        // Verify that the two recipes we expected are in fact in the list
        assertEquals(0, recipes.get(0).id());
        assertEquals(1, recipes.get(1).id());
    }

    // Tests for searchRecipes()
    @Test
    void testSearchRecipes_FindsCorrectRecipes() {
        DataService mockService = () -> List.of(
                new Recipe(1, "Chicken Curry", "Flavorful Indian-inspired curry with tender chicken pieces", "", 4, 15, 20, 35),
                new Recipe(2, "Chicken Parmesan", "Tender chicken breasts coated in breadcrumbs and marinara sauce", "", 4, 15, 25, 40),
                new Recipe(3, "Chicken Stir Fry", "Healthy and flavorful stir-fried chicken and vegetables", "", 4, 15, 25, 40)
        );

        List<Recipe> results = Main.searchRecipes("curry", mockService);
        assertEquals(1, results.size());
        assertEquals("Chicken Curry", results.getFirst().name());
    }

    @Test
    void testSearchRecipes_ReturnsEmptyListWhenNoMatches() {
        DataService mockService = () -> List.of(
                new Recipe(1, "Margarita Pizza", "Classic Italian pizza topped with tomato sauce, mozzarella, and basil", "", 8, 15, 12, 27),
                new Recipe(2, "Caesar Salad", "Refreshing salad made with romaine lettuce and Caesar dressing", "", 4, 10, 0, 10)
        );

        List<Recipe> results = Main.searchRecipes("pasta", mockService);
        assertTrue(results.isEmpty());
    }

    // 3.2.3
    @Test
    void testSearchRecipes_SearchesBothNameAndDescription() {
        DataService mockService = () -> List.of(
                new Recipe(1, "Chicken Noodle Soup", "Classic soup made with tender chicken, vegetables, and noodles", "", 4, 10, 20, 30),
                new Recipe(2, "Vegetarian Curry", "Spicy curry made with chicken and vegetables", "", 4, 15, 25, 40),
                new Recipe(3, "Beef Stew", "Hearty stew with chicken broth and vegetables", "", 6, 20, 60, 80),
                new Recipe(4, "Pasta Alfredo", "Creamy pasta without meat", "", 2, 5, 10, 15)
        );

        List<Recipe> results = Main.searchRecipes("ChIcKeN", mockService);
        assertEquals(3, results.size());
        assertTrue(results.stream().anyMatch(r -> r.name().equals("Chicken Noodle Soup")));
        assertTrue(results.stream().anyMatch(r -> r.description().contains("chicken")));
        assertTrue(results.stream().anyMatch(r -> r.description().contains("chicken broth")));
        assertFalse(results.stream().anyMatch(r -> r.id() == 4));
    }

    // Test 3.2.4: Case insensitive search
    @Test
    void testSearchRecipes_IsCaseInsensitive() {
        DataService mockService = () -> List.of(
                new Recipe(1, "CHICKEN SOUP", "Homemade chicken broth", "", 4, 10, 30, 40),
                new Recipe(2, "chicken stir fry", "Asian-style chicken dish", "", 4, 15, 10, 25),
                new Recipe(3, "Pasta", "Italian noodles", "", 4, 10, 15, 25)
        );

        List<Recipe> results = Main.searchRecipes("ChIcKeN", mockService);
        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(r -> r.id() == 1));
        assertTrue(results.stream().anyMatch(r -> r.id() == 2));
    }
}