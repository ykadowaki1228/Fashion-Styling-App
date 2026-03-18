/*
Yuna Kadowaki
2600250088-3
9 of Jan.

JavaFX GUI for the Fashion Styling App
*/
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class FashionStylingGUI extends Application {
    private Wardrobe wardrobe = new Wardrobe();
    private final FileHandler fileHandler = new FileHandler();
    private final ObservableList<Clothing> tableData = FXCollections.observableArrayList();

    private TableView<Clothing> tableView;
    private ComboBox<String> seasonCombo;
    private TextField colorField;
    private Label statusLabel;
    private TextArea detailsArea = new TextArea();

    @Override
    public void start(Stage primaryStage) {
        seasonCombo = new ComboBox<>();
        seasonCombo.getItems().addAll("All", "Spring", "Summer", "Autumn", "Winter");
        seasonCombo.setValue("All");

        colorField = new TextField();
        colorField.setPromptText("Color (e.g., Black)");

        Button btnFilter = new Button("Filter");
        Button btnClear = new Button("Clear");
        Button btnLoad = new Button("Load");
        Button btnSave = new Button("Save");
        Button btnAppend = new Button("Append Sample");
        Button btnWear = new Button("Wear Selected");
        Button btnStats = new Button("Show Stats");

        HBox topBar = new HBox(8,
                new Label("Season:"), seasonCombo,
                new Label("Color:"), colorField,
                btnFilter, btnClear, btnLoad, btnSave, btnAppend, btnWear, btnStats
        );
        topBar.setPadding(new Insets(10));

        tableView = new TableView<>();
        tableView.setItems(tableData);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Clothing, String> colCategory = new TableColumn<>("Category");
        colCategory.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCategory()));

        TableColumn<Clothing, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));

        TableColumn<Clothing, String> colColor = new TableColumn<>("Color");
        colColor.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getColor()));

        TableColumn<Clothing, String> colSeason = new TableColumn<>("Season");
        colSeason.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSeason()));

        TableColumn<Clothing, String> colBrand = new TableColumn<>("Brand");
        colBrand.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getBrand()));

        tableView.getColumns().addAll(colCategory, colName, colColor, colSeason, colBrand);

        detailsArea.setEditable(false);
        detailsArea.setWrapText(true);
        detailsArea.setPrefWidth(250);
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, newSel) -> {
                    if (newSel != null) detailsArea.setText(newSel.toString());
                    else detailsArea.clear();
                }
        );
        VBox rightPane = new VBox(10, new Label("Details:"), detailsArea);
        rightPane.setPadding(new Insets(10));

        statusLabel = new Label("Ready.");
        VBox bottomBox = new VBox(statusLabel);
        bottomBox.setPadding(new Insets(6));

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(tableView);
        root.setRight(rightPane);
        root.setBottom(bottomBox);

        Scene scene = new Scene(root, 900, 520);
        primaryStage.setTitle("Fashion Styling App - JavaFX GUI");
        primaryStage.setScene(scene);
        primaryStage.show();

        btnFilter.setOnAction(e -> applyFilter());
        btnClear.setOnAction(e -> {
            seasonCombo.setValue("All");
            colorField.clear();
            refreshTable(wardrobe.getAllClothing());
            statusLabel.setText("Filters cleared.");
        });

        btnLoad.setOnAction(e -> {
            wardrobe = fileHandler.loadWardrobeFromFile("wardrobe_data.txt");
            refreshTable(wardrobe.getAllClothing());
            statusLabel.setText("Loaded " + tableData.size() + " items.");
        });

        btnSave.setOnAction(e -> {
            fileHandler.saveWardrobeToFile(wardrobe, "wardrobe_data.txt");
            statusLabel.setText("Saved to wardrobe_data.txt");
        });

        btnAppend.setOnAction(e -> {
            Top sample = new Top("Striped Shirt", "Blue", "Summer", "H&M", "Long", "Button");
            wardrobe.addClothing(sample);
            fileHandler.appendClothingToFile(sample, "wardrobe_data.txt");
            refreshTable(wardrobe.getAllClothing());
            statusLabel.setText("Appended a sample item.");
        });

        btnWear.setOnAction(e -> {
            Clothing selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                statusLabel.setText("Select an item to wear.");
            } else {
                selected.wear();
                statusLabel.setText("Wearing: " + selected.getName());
            }
        });

        btnStats.setOnAction(e -> {
            int total = wardrobe.getAllClothing().size();
            int tops = wardrobe.getClothingByType(Top.class).size();
            int pants = wardrobe.getClothingByType(Pants.class).size();
            int shoes = wardrobe.getClothingByType(Shoes.class).size();
            int bags = wardrobe.getClothingByType(Bag.class).size();
            int accessories = wardrobe.getClothingByType(Accessory.class).size();
            String stats = String.format(
                    "Total: %d%nTops: %d%nPants: %d%nShoes: %d%nBags: %d%nAccessories: %d",
                    total, tops, pants, shoes, bags, accessories
            );
            detailsArea.setText(stats);
        });

        wardrobe = fileHandler.loadWardrobeFromFile("wardrobe_data.txt");
        if (wardrobe.getAllClothing().isEmpty()) seedInitialItems();
        refreshTable(wardrobe.getAllClothing());
    }

    private void applyFilter() {
        String season = seasonCombo.getValue();
        String color = (colorField.getText() == null) ? "" : colorField.getText().trim();

        List<Clothing> filtered = new ArrayList<>();
        for (Clothing c : wardrobe.getAllClothing()) {
            boolean seasonOk = c.getSeason().equalsIgnoreCase(season)
                    || c.getSeason().equalsIgnoreCase("All")
                    || "All".equalsIgnoreCase(season);
            boolean colorOk = color.isEmpty() || c.getColor().toLowerCase().contains(color.toLowerCase());
            if (seasonOk && colorOk) filtered.add(c);
        }
        refreshTable(filtered);
        statusLabel.setText("Filtered: " + filtered.size() + " items.");
    }

    private void refreshTable(List<Clothing> items) {
        tableData.setAll(items);
    }

    private void seedInitialItems() {
        Clothing[] initial = {
                new Top("White T-Shirt", "White", "Summer", "Uniqlo", "Short", "Round"),
                new Top("Blue Button-Down", "Blue", "All", "Zara", "Long", "Button"),
                new Top("Black Polo", "Black", "All", "Ralph Lauren", "Short", "Polo"),
                new Pants("Black Jeans", "Black", "All", "Levi's", "Slim", "Full"),
                new Pants("Beige Chinos", "Beige", "Spring", "Gap", "Regular", "Full"),
                new Shoes("White Sneakers", "White", "All", "Nike", "Sneakers", 0),
                new Bag("Leather Tote", "Brown", "All", "Coach", "Tote", "Large"),
                new Accessory("Silver Watch", "Silver", "All", "Casio", "Watch", "Metal")
        };
        for (Clothing c : initial) wardrobe.addClothing(c);
    }

    public static void main(String[] args) {
        launch(args);
    }
}