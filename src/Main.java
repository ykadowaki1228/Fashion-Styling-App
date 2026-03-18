/*
Yuna Kadowaki
2600250088-3
28 of Dec.

A program that runs the full smart wardrobe application, demonstrating item management,
filtering, recommendations, file operations, and Java concepts like inheritance,
polymorphism, and lambdas.
*/
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("===== FASHION STYLING APP =====");
        System.out.println("A smart wardrobe management system\n");

        // Create wardrobe and add clothing items
        Wardrobe myWardrobe = new Wardrobe();

        Clothing[] initialClothes = {
                new Top("White T-Shirt", "White", "Summer", "Uniqlo", "Short", "Round"),
                new Top("Blue Button-Down", "Blue", "All", "Zara", "Long", "Button"),
                new Top("Black Polo", "Black", "All", "Ralph Lauren", "Short", "Polo"),
                new Pants("Black Jeans", "Black", "All", "Levi's", "Slim", "Full"),
                new Pants("Beige Chinos", "Beige", "Spring", "Gap", "Regular", "Full"),
                new Pants("Blue Denim", "Blue", "All", "Wrangler", "Straight", "Full"),
                new Shoes("White Sneakers", "White", "All", "Nike", "Sneakers", 0),
                new Shoes("Brown Boots", "Brown", "Winter", "Timberland", "Boots", 3),
                new Shoes("Black Loafers", "Black", "All", "Cole Haan", "Loafers", 1),
                new Bag("Leather Tote", "Brown", "All", "Coach", "Tote", "Large"),
                new Bag("Canvas Backpack", "Navy", "All", "Fjallraven", "Backpack", "Medium"),
                new Accessory("Silver Watch", "Silver", "All", "Casio", "Watch", "Metal"),
                new Accessory("Leather Belt", "Brown", "All", "Gucci", "Belt", "Leather")
        };

        for (Clothing item : initialClothes) {
            myWardrobe.addClothing(item);
        }

        // 1. Wardrobe statistics
        System.out.println("1. Wardrobe Statistics:");
        myWardrobe.printStatistics();

        // 2. Polymorphism demonstration
        System.out.println("\n2. Polymorphism demonstration:");
        System.out.println("Trying on first 3 items:");
        for (int i = 0; i < 3 && i < myWardrobe.getAllClothing().size(); i++) {
            Clothing item = myWardrobe.getAllClothing().get(i);
            item.wear();
        }

        // 3. Filter by season (using loops)
        System.out.println("\n3. Summer clothing (using loops):");
        List<Clothing> summerItems = myWardrobe.getClothingBySeason("Summer");
        for (Clothing item : summerItems) {
            System.out.println("  - " + item.getName());
        }

        // 4. Filter using lambda expressions
        System.out.println("\n4. White clothing (using lambda expressions):");
        List<Clothing> whiteItems = myWardrobe.filterClothing(
                item -> item.getColor().equalsIgnoreCase("White")
        );
        whiteItems.forEach(item -> System.out.println("  - " + item));

        // 5. Using generics
        System.out.println("\n5. All tops (using generics):");
        List<Top> allTops = myWardrobe.getClothingByType(Top.class);
        allTops.forEach(top ->
                System.out.println("  - " + top.getName() + " (" + top.getNeckline() + " neckline)")
        );

        // 6. Style recommendations
        System.out.println("\n6. Style Recommendations:");
        StyleRecommender recommender = new StyleRecommender();
        List<Clothing> recommendedOutfit = recommender.recommendOutfit(myWardrobe, "Summer");
        recommender.displayOutfit(recommendedOutfit);

        // 7. Multiple outfit suggestions
        System.out.println("\n7. Multiple outfit suggestions:");
        List<List<Clothing>> multipleOutfits = recommender.recommendMultipleOutfits(myWardrobe, "All", 2);

        for (int i = 0; i < multipleOutfits.size(); i++) {
            System.out.println("\nOutfit " + (i + 1) + ":");
            for (Clothing item : multipleOutfits.get(i)) {
                System.out.println("  • " + item.getName());
            }
        }

        // 8. File operations
        System.out.println("\n8. File Operations:");
        FileHandler fileHandler = new FileHandler();

        // Save wardrobe to file
        fileHandler.saveWardrobeToFile(myWardrobe, "wardrobe_data.txt");

        // Display file contents
        fileHandler.displayFileContents("wardrobe_data.txt");

        // Append new item to file
        Top newTop = new Top("Striped Shirt", "Blue", "Summer", "H&M", "Long", "Button");
        fileHandler.appendClothingToFile(newTop, "wardrobe_data.txt");

        // Load wardrobe from file
        Wardrobe loadedWardrobe = fileHandler.loadWardrobeFromFile("wardrobe_data.txt");
        System.out.println("Loaded wardrobe has " + loadedWardrobe.getAllClothing().size() + " items.");

        // 9. Advanced lambda examples
        System.out.println("\n9. Advanced Lambda Examples:");

        // Filter with multiple conditions
        System.out.println("Blue tops for all seasons:");
        List<Clothing> blueTops = myWardrobe.filterClothing(
                item -> item.getColor().equalsIgnoreCase("Blue") && item instanceof Top
        );
        blueTops.forEach(item -> System.out.println("  - " + item.getName()));

        // Sort by name
        System.out.println("\nClothing sorted by name:");
        List<Clothing> sortedByName = new ArrayList<>(myWardrobe.getAllClothing());
        sortedByName.sort((a, b) -> a.getName().compareTo(b.getName()));
        sortedByName.forEach(item -> System.out.println("  - " + item.getName()));
    }
}