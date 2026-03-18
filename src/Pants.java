/*
Yuna Kadowaki
2600250088-3
28 of Dec.

A program that defines pants as a specific type of clothing with attributes describing their fit and length.
*/
public class Pants extends Clothing {
    private String fit;
    private String length;

    public Pants(String name, String color, String season, String brand, String fit, String length) {
        super(name, color, season, brand);
        this.fit = fit;
        this.length = length;
    }

    @Override
    public String getCategory() {
        return "PANTS";
    }

    public String getFit() {
        return fit;
    }

    public String getLength() {
        return length;
    }

    @Override
    public String toString() {
        return super.toString() + " | Fit: " + fit + " | Length: " + length;
    }
}