
/*
Yuna Kadowaki
2600250088-3
28 of Dec.
A program that generates outfit recommendations by matching colors, seasons, and clothing types using compatibility rules and randomized selection.
*/
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class StyleRecommender {
    private static final Map<String, List<String>> COLOR_COMPATIBILITY = new HashMap<>();
    private static final String ALL = "All";

    static {
        COLOR_COMPATIBILITY.put("White", Arrays.asList("Black", "Blue", "Red", "Gray", "Navy", "Beige", "Brown"));
        COLOR_COMPATIBILITY.put("Black", Arrays.asList("White", "Red", "Gray", "Pink", "Gold", "Silver", "Beige"));
        COLOR_COMPATIBILITY.put("Blue", Arrays.asList("White", "Beige", "Gray", "Navy", "Brown", "Khaki"));
        COLOR_COMPATIBILITY.put("Red", Arrays.asList("White", "Black", "Blue", "Gray", "Denim"));
        COLOR_COMPATIBILITY.put("Beige", Arrays.asList("Brown", "Blue", "White", "Black", "Navy"));
        COLOR_COMPATIBILITY.put("Brown", Arrays.asList("Beige", "White", "Blue", "Green", "Cream"));
        COLOR_COMPATIBILITY.put("Gray", Arrays.asList("White", "Black", "Red", "Blue", "Pink"));
        COLOR_COMPATIBILITY.put("Navy", Arrays.asList("White", "Beige", "Red", "Pink", "Gray"));
    }

    public List<Clothing> recommendOutfit(Wardrobe wardrobe, String season) {
        List<Clothing> outfit = new ArrayList<>();

        List<Top> tops = wardrobe.getClothingByType(Top.class);
        Top selectedTop = selectRandomItem(tops, season);
        if (selectedTop != null) {
            outfit.add(selectedTop);

            List<Pants> pants = wardrobe.getClothingByType(Pants.class);
            Pants selectedPants = selectMatchingPants(pants, selectedTop, season);
            if (selectedPants != null) {
                outfit.add(selectedPants);
            }

            List<Shoes> shoes = wardrobe.getClothingByType(Shoes.class);
            Shoes selectedShoes = selectRandomItem(shoes, season);
            if (selectedShoes != null) {
                outfit.add(selectedShoes);
            }

            List<Accessory> accessories = wardrobe.getClothingByType(Accessory.class);
            if (!accessories.isEmpty()) {
                Accessory selectedAccessory = selectRandomItem(accessories, season);
                if (selectedAccessory != null) {
                    outfit.add(selectedAccessory);
                }
            }
        }
        return outfit;
    }

    private <T extends Clothing> T selectRandomItem(List<T> items, String season) {
        if (items.isEmpty()) return null;

        List<T> seasonalItems = new ArrayList<>();
        for (T item : items) {
            if (item.getSeason().equalsIgnoreCase(season) || item.getSeason().equalsIgnoreCase(ALL)) {
                seasonalItems.add(item);
            }
        }

        if (seasonalItems.isEmpty()) {
            return items.get(ThreadLocalRandom.current().nextInt(items.size()));
        }
        return seasonalItems.get(ThreadLocalRandom.current().nextInt(seasonalItems.size()));
    }

    private Pants selectMatchingPants(List<Pants> pants, Top top, String season) {
        if (pants.isEmpty() || top == null) return selectRandomItem(pants, season);

        List<Pants> compatiblePants = new ArrayList<>();
        for (Pants pant : pants) {
            if (isColorCompatible(top.getColor(), pant.getColor())) {
                if (pant.getSeason().equalsIgnoreCase(season)
                        || pant.getSeason().equalsIgnoreCase(ALL)) {
                    compatiblePants.add(pant);
                }
            }
        }

        if (!compatiblePants.isEmpty()) {
            return compatiblePants.get(ThreadLocalRandom.current().nextInt(compatiblePants.size()));
        }
        return selectRandomItem(pants, season);
    }

    private boolean isColorCompatible(String color1, String color2) {
        if (color1.equalsIgnoreCase(color2)) return true;
        List<String> compatibleColors = COLOR_COMPATIBILITY.getOrDefault(color1, Collections.emptyList());
        for (String color : compatibleColors) {
            if (color.equalsIgnoreCase(color2)) {
                return true;
            }
        }

        List<String> reverse = COLOR_COMPATIBILITY.getOrDefault(color2, Collections.emptyList());
        for (String color : reverse) {
            if (color.equalsIgnoreCase(color1)) {
                return true;
            }
        }
        return false;
    }

    public List<List<Clothing>> recommendMultipleOutfits(Wardrobe wardrobe, String season, int count) {
        List<List<Clothing>> outfits = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            List<Clothing> outfit = recommendOutfit(wardrobe, season);
            if (!outfit.isEmpty()) {
                outfits.add(outfit);
            }
        }
        return outfits;
    }

    public void displayOutfit(List<Clothing> outfit) {
        System.out.println("\n=== Recommended Outfit ===");
        if (outfit.isEmpty()) {
            System.out.println("No items to create an outfit.");
            return;
        }
        for (Clothing item : outfit) {
            System.out.println("• " + item);
        }
        System.out.println("\nComplete the look:");
        for (Clothing item : outfit) {
            System.out.print("- ");
            item.wear();
        }
    }

}
