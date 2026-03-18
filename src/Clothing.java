/*
Yuna Kadowaki
2600250088-3
28 of Dec.

A program that provides a shared abstract blueprint for wearable items, defining common properties such as name, color, season, and brand.
*/
public abstract class Clothing implements Wearable {
    protected String name;
    protected String color;
    protected String season;
    protected String brand;

    public Clothing(String name, String color, String season, String brand) {
        this.name = name;
        this.color = color;
        this.season = season;
        this.brand = brand;
    }

    public abstract String getCategory();

    public String getSeason() {
        return season;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public void wear() {
        System.out.println("Wearing " + name);
    }

    @Override
    public String toString() {
        return getCategory() + " - " + name + " | Color: " + color + " | Season: " + season + " | Brand: " + brand;
    }
}