/*
Yuna Kadowaki
2600250088-3
28 of Dec.

A program that models tops as a type of clothing with specific neckline and sleeve‑type attributes.
*/
public class Top extends Clothing {
    private String sleeveType;
    private String neckline;

    public Top(String name, String color, String season, String brand, String sleeveType, String neckline) {
        super(name, color, season, brand);
        this.sleeveType = sleeveType;
        this.neckline = neckline;
    }

    @Override
    public String getCategory() {
        return "TOP";
    }

    public String getSleeveType() {
        return sleeveType;
    }

    public String getNeckline() {
        return neckline;
    }

    @Override
    public String toString() {
        return super.toString() + " | Sleeve: " + sleeveType + " | Neckline: " + neckline;
    }
}