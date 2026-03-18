/*
Yuna Kadowaki
2600250088-3
28 of Dec.

A program that models various types of bags with attributes like size and style, built as a specialized form of Clothing.
*/
public class Bag extends Clothing {
    private String bagType;
    private String size;

    public Bag(String name, String color, String season, String brand, String bagType, String size) {
        super(name, color, season, brand);
        this.bagType = bagType;
        this.size = size;
    }

    @Override
    public String getCategory() {
        return "BAG";
    }

    public String getBagType() {
        return bagType;
    }

    public String getSize() {
        return size;
    }

    @Override
    public String toString() {
        return super.toString() + " | Type: " + bagType + " | Size: " + size;
    }
}