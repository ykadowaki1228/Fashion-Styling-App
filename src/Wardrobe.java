
/*
Yuna Kadowaki
2600250088-3
28 of Dec.
A program that manages a dynamic collection of clothing items, offering filtering, categorization, statistics, and type‑based retrieval features for a smart wardrobe system.
*/
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Wardrobe {
    private List<Clothing> clothingItems;

    public Wardrobe() {
        clothingItems = new ArrayList<>();
    }

    public void addClothing(Clothing item) {
        clothingItems.add(item);
    }

    public void removeClothing(Clothing item) {
        clothingItems.remove(item);
    }

    public List<Clothing> getAllClothing() {
        return new ArrayList<>(clothingItems);
    }

    public List<Clothing> getClothingBySeason(String season) {
        List<Clothing> result = new ArrayList<>();
        for (Clothing item : clothingItems) {
            if (item.getSeason().equalsIgnoreCase(season) || item.getSeason().equalsIgnoreCase("All")) {
                result.add(item);
            }
        }
        return result;
    }

    public List<Clothing> getClothingByColor(String color) {
        return filterClothing(item -> item.getColor().equalsIgnoreCase(color));
    }

    public List<Clothing> filterClothing(Predicate<Clothing> filter) {
        List<Clothing> result = new ArrayList<>();
        for (Clothing item : clothingItems) {
            if (filter.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public <T extends Clothing> List<T> getClothingByType(Class<T> type) {
        List<T> result = new ArrayList<>();
        for (Clothing item : clothingItems) {
            if (type.isInstance(item)) {
                result.add(type.cast(item));
            }
        }
        return result;
    }

    public void printStatistics() {
        System.out.println("=== Wardrobe Statistics ===");
        System.out.println("Total items: " + clothingItems.size());
        int tops = getClothingByType(Top.class).size();
        int pants = getClothingByType(Pants.class).size();
        int shoes = getClothingByType(Shoes.class).size();
        int bags = getClothingByType(Bag.class).size();
        int accessories = getClothingByType(Accessory.class).size();
        System.out.println("Tops: " + tops);
        System.out.println("Pants: " + pants);
        System.out.println("Shoes: " + shoes);
        System.out.println("Bags: " + bags);
        System.out.println("Accessories: " + accessories);
    }
}