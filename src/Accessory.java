/*
Yuna Kadowaki
2600250088-3
28 of Dec.

A program that defines an accessory item with specific type and material details as an extension of the general Clothing class.
*/
public class Accessory extends Clothing {
    private String accessoryType;
    private String material;

    public Accessory(String name, String color, String season, String brand, String accessoryType, String material) {
        super(name, color, season, brand);
        this.accessoryType = accessoryType;
        this.material = material;
    }

    @Override
    public String getCategory() {
        return "ACCESSORY";
    }

    public String getAccessoryType() {
        return accessoryType;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public String toString() {
        return super.toString() + " | Type: " + accessoryType + " | Material: " + material;
    }
}