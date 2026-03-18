/*
Yuna Kadowaki
2600250088-3
28 of Dec.

A program that manages saving, loading, appending, and displaying wardrobe data by converting clothing objects to and from file‑friendly formats.
*/
import java.io.*;
import java.util.*;

public class FileHandler {
    public void saveWardrobeToFile(Wardrobe wardrobe, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Clothing item : wardrobe.getAllClothing()) {
                writer.println(serializeClothing(item));
            }
            System.out.println(" Wardrobe saved to: " + filename);
        } catch (IOException e) {
            System.err.println(" Error saving wardrobe: " + e.getMessage());
        }
    }

    public Wardrobe loadWardrobeFromFile(String filename) {
        Wardrobe wardrobe = new Wardrobe();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Clothing item = deserializeClothing(line);
                if (item != null) {
                    wardrobe.addClothing(item);
                }
            }
            System.out.println("Wardrobe loaded from: " + filename);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename + " Creating new file.");
            return new Wardrobe();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return wardrobe;
    }

    private String serializeClothing(Clothing item) {
        StringBuilder sb = new StringBuilder();
        sb.append(item.getCategory()).append(",");
        sb.append(item.getName()).append(",");
        sb.append(item.getColor()).append(",");
        sb.append(item.getSeason()).append(",");
        sb.append(item.getBrand());

        if (item instanceof Top) {
            Top top = (Top) item;
            sb.append(",").append(top.getSleeveType());
            sb.append(",").append(top.getNeckline());
        } else if (item instanceof Pants) {
            Pants pants = (Pants) item;
            sb.append(",").append(pants.getFit());
            sb.append(",").append(pants.getLength());
        } else if (item instanceof Shoes) {
            Shoes shoes = (Shoes) item;
            sb.append(",").append(shoes.getShoeType());
            sb.append(",").append(shoes.getHeelHeight());
        } else if (item instanceof Bag) {
            Bag bag = (Bag) item;
            sb.append(",").append(bag.getBagType());
            sb.append(",").append(bag.getSize());
        } else if (item instanceof Accessory) {
            Accessory accessory = (Accessory) item;
            sb.append(",").append(accessory.getAccessoryType());
            sb.append(",").append(accessory.getMaterial());
        }
        return sb.toString();
    }

    private Clothing deserializeClothing(String line) {
        String[] parts = line.split(",");
        if (parts.length < 5) return null;

        String category = parts[0];
        String name = parts[1];
        String color = parts[2];
        String season = parts[3];
        String brand = parts[4];

        try {
            switch (category) {
                case "TOP":
                    if (parts.length >= 7) {
                        return new Top(name, color, season, brand, parts[5], parts[6]);
                    }
                    break;
                case "PANTS":
                    if (parts.length >= 7) {
                        return new Pants(name, color, season, brand, parts[5], parts[6]);
                    }
                    break;
                case "SHOES":
                    if (parts.length >= 7) {
                        return new Shoes(name, color, season, brand, parts[5], Integer.parseInt(parts[6]));
                    }
                    break;
                case "BAG":
                    if (parts.length >= 7) {
                        return new Bag(name, color, season, brand, parts[5], parts[6]);
                    }
                    break;
                case "ACCESSORY":
                    if (parts.length >= 7) {
                        return new Accessory(name, color, season, brand, parts[5], parts[6]);
                    }
                    break;
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
        }
        return null;
    }

    public void appendClothingToFile(Clothing item, String filename) {
        try (FileWriter fw = new FileWriter(filename, true);
             PrintWriter writer = new PrintWriter(fw)) {
            writer.println(serializeClothing(item));
            System.out.println("Item appended to: " + filename);
        } catch (IOException e) {
            System.err.println("Error appending to file: " + e.getMessage());
        }
    }

    public void displayFileContents(String filename) {
        System.out.println("=== File Contents: " + filename + " ===");
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNum = 1;
            while ((line = reader.readLine()) != null) {
                System.out.println(lineNum + ": " + line);
                lineNum++;
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}