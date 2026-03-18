/*
Yuna Kadowaki
2600250088-3
28 of Dec.

A program that represents different kinds of shoes with details such as shoe type and heel height as specialized clothing items.
*/
public class Shoes extends Clothing {
    private String shoeType;
    private int heelHeight;

    public Shoes(String name, String color, String season, String brand, String shoeType, int heelHeight) {
        super(name, color, season, brand);
        this.shoeType = shoeType;
        this.heelHeight = heelHeight;
    }

    @Override
    public String getCategory() {
        return "SHOES";
    }

    public String getShoeType() {
        return shoeType;
    }

    public int getHeelHeight() {
        return heelHeight;
    }

    @Override
    public String toString() {
        return super.toString() + " | Type: " + shoeType + " | Heel: " + heelHeight + "cm";
    }
}