
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.converter.StringConverter;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import java.util.List;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Gaza extends Application {
	public static FileChooser fileChooser;
	public static File file; // The input file
	public static ArrayList<City> citie = new ArrayList<>();
	List<Line> lines = new ArrayList<>();

	ComboBox<City> source = new ComboBox<>();
	ComboBox<City> target = new ComboBox<>();
	Button reset;
	Line line;

	static int mouseClick = 0;
	Background bg;
	Color c4;
	public static Image map;
	public static double minLat = 31.0;
	static double maxLat = 32.0;
	static double minLng = 34.0;
	static double maxLng = 35.0;
	GridPane pane;
	static Pane imagePane = new Pane();
	static Pane root = new Pane();
	City c = new City();
	Graph countriesGraph = new Graph();

	@Override
	public void start(Stage stage) throws Exception {

		// A color that is repeatedly used in the project
		c4 = Color.web("#353535");
//		map = new Image("gaza.jpg");
		map = new Image("gaza2.png");

		try {
			Image backgroundImage = new Image("gazam.png");

			if (backgroundImage.isError()) {
				System.out.println("Error loading background image: " + backgroundImage.getException());
			}

			// Adjust BackgroundSize to cover both width and height
			BackgroundSize backgroundSize = new BackgroundSize(1.0, 1.0, true, true, false, false);

			// Adjust BackgroundRepeat to NO_REPEAT if you don't want the background to
			// repeat
			BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

			// Apply background to GridPane
			Background gridBackground = new Background(background);

			// Creating grid pane
			pane = new GridPane();
			pane.setAlignment(javafx.geometry.Pos.CENTER);
			pane.setPadding(new Insets(100, 210, 100, 210));
			pane.setHgap(10.5);
			pane.setVgap(10.5);

			// Set background to the GridPane
			pane.setBackground(gridBackground);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Label Greeting User
		Label hello = new Label("Hello , select your cities file...");
		// Label Greeting User

		hello.setFont(javafx.scene.text.Font.font(20));
		hello.setTextFill(Color.WHITE);
		pane.add(hello, 0, 0);

		// Set initial style (white text without background)
		hello.setStyle("-fx-background-radius: 0;-fx-background-color: transparent;");

		// Create ScaleTransition for animation
		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), hello);
		scaleTransition.setFromX(1.0);
		scaleTransition.setFromY(1.0);
		scaleTransition.setToX(0.5);
		scaleTransition.setToY(0.5);
		scaleTransition.setAutoReverse(true);
		scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);

		// Start the animation automatically
		scaleTransition.play();

		// User Button to browse file
		Button add = new Button("Browse");
		add.setFont(Font.font(14));
		add.setStyle("-fx-background-radius: 18, 7;-fx-background-color:lightgreen;");
		add.setTextFill(c4);
		add.setPrefSize(100, 30);
		add.setFont((Font.font("Algerian", FontWeight.BOLD, FontPosture.REGULAR, 17)));
		pane.add(add, 3, 0);
		add.setOnAction(d -> {
//			Table graph = new Table();
			Stage stage3 = new Stage();
			fileChooser = new FileChooser();
			file = fileChooser.showOpenDialog(stage3);
			if (file == (null)) {
				showAlert(Alert.AlertType.ERROR, "Error", "Operation Failed", "You haven't chosen a file yet!");
				System.out.println("null pointer");
			} else {
				if (readFile(file) == -1) {
					showAlert(Alert.AlertType.ERROR, "Error", "Operation Failed", "Number of cities doesn't match");
				} else {
					createPalestineMapStage();
					// Update ComboBox items
				}
			}
		});

		// User button to exit
		Button cancel = new Button("Cancel");
		cancel.setFont(Font.font(14));
		cancel.setTextFill(c4);
		cancel.setPrefSize(100, 30);
		cancel.setFont((Font.font("Algerian", FontWeight.BOLD, FontPosture.REGULAR, 17)));
		cancel.setStyle("-fx-background-radius: 18, 7;-fx-background-color:lightgreen;");
		pane.add(cancel, 4, 0);
		cancel.setOnAction(e -> javafx.application.Platform.exit());

		// Create Scene
		Scene scene = new Scene(pane);
		// Set minimum size for the stage
		stage.setMinWidth(800);
		stage.setMinHeight(600);
		stage.setScene(scene);
		stage.setTitle("Gaza Map file");
		stage.getIcons().add(new Image("icon.png"));
		stage.show();
	}

	private void createPalestineMapStage() {
		Stage stage2 = new Stage();
		BorderPane pane2 = new BorderPane();
		pane2.setPadding(new Insets(20, 10, 20, 10));
		pane2.setBackground(bg);
		pane2.setStyle("-fx-background-color: #1B4242;");

//		pane2.setBackground(C);
		GridPane pane20 = new GridPane();
		pane20.setPadding(new Insets(20, 10, 20, 10));
		pane20.setHgap(17);
		pane20.setVgap(17);
		pane2.setRight(pane20);

		String style = " -fx-background-radius: 20; -fx-padding: 20px; -fx-text-fill: white; -fx-font-family: 'Algerian';";

		// Label Greeting User
		Label mapp = new Label(" 'GAZA Map' ");
		mapp.setFont(Font.font(20));
		mapp.setTextFill(c4);
		mapp.setStyle(style);
		pane20.add(mapp, 0, 0);

		BackgroundFill bgf1 = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
		Background bg1 = new Background(bgf1);

		Label cityl = new Label("Choose City by:");
		cityl.setFont(Font.font(16));
		cityl.setTextFill(c4);
		cityl.setStyle(style);
		pane20.add(cityl, 0, 1);

		ComboBox<String> choice = new ComboBox<>();
		choice.setPrefHeight(30);
		choice.setPrefWidth(150);
		choice.setBackground(bg1);
		choice.getItems().add("List");
		choice.getItems().add("Click");
//		choice.setOnAction(event -> {
//			String selectedValue = choice.getValue();
//
//			// Assuming radioButton is the RadioButton you want to toggle
//			if ("List".equals(selectedValue)) {
//				// If "List" is selected, disable the radio button
//				if (c.getR() != null) {
//					c.getR().setDisable(true);
//				} else {
////					showAlert(Alert.AlertType.ERROR, "Error", "Radio button is null",
////							"Radio button is not initialized.");
//				}
//			} else if ("Click".equals(selectedValue)) {
//				// If "Click" is selected, enable the radio button
//				if (c.getR() != null) {
//					c.getR().setDisable(false);
//				} else {
//					showAlert(Alert.AlertType.ERROR, "Error", "Radio button is null",
//							"Radio button is not initialized.");
//				}
//			} else {
//				showAlert(Alert.AlertType.ERROR, "Please Choose choice", "Warning!", "Click or List");
//			}
//
//			// Add additional actions based on the selected value if needed
//		});

//		choice.setValue("Click");
		pane20.add(choice, 1, 1);

		Label sourcel = new Label("Source:");
		sourcel.setFont(Font.font(16));
		sourcel.setTextFill(c4);
		sourcel.setStyle(style);
		pane20.add(sourcel, 0, 2);

//		source = new ComboBox<>();
		source.setPrefHeight(30);
		source.setPrefWidth(150);
		source.setBackground(bg1);

		pane20.add(source, 1, 2);

		// This label will be updated by the process of file importing
		Label targetl = new Label("Target:");
		targetl.setFont(Font.font(16));
		targetl.setStyle(style);
		pane20.add(targetl, 0, 3);

		target.setPrefHeight(30);
		target.setPrefWidth(150);
		target.setBackground(bg1);

		pane20.add(target, 1, 3);

		// This label will be updated by the process of file importing
		Label pathl = new Label("Shortest Path:");
		pathl.setFont(Font.font(16));
		pathl.setStyle(style);
		pane20.add(pathl, 0, 8);

		TextArea path = new TextArea();
		path.setPrefHeight(150);
		path.setPrefWidth(180);
		path.setEditable(false);
		pane20.add(path, 1, 8);

		// This label will be updated by the process of file importing
		Label distl = new Label("Distance:");
		distl.setFont(Font.font(16));
		distl.setStyle(style);
		pane20.add(distl, 0, 9);

		TextField dist = new TextField();
		dist.setPrefHeight(30);
		dist.setPrefWidth(150);
		dist.setEditable(false);
		pane20.add(dist, 1, 9);

		// User button to reset map
		reset = new Button("Clear");
		reset.setFont(Font.font(14));
		reset.setPrefSize(100, 30);
		reset.setStyle(style);
		GridPane.setHalignment(reset, HPos.CENTER);
		reset.setStyle("-fx-background-radius: 10, 5;-fx-background-color:lightgrey;");
		pane20.add(reset, 1, 4);

		reset.setOnAction(l -> {
			choice.setValue(null);
			source.setValue(null);
			target.setValue(null);
			Image Image = new Image("placeholder1.png");
			for (City city : citie) {
				ImageView vi = new ImageView(Image);
				vi.setFitHeight(17);
				vi.setFitWidth(16);
				city.getR().setGraphic(vi);
				city.getR().setSelected(false);
				city.getLabel().setTextFill(Color.BLACK);
				mouseClick = 0;
				path.setText("");
				dist.setText("");
			}
			// Remove the lines from the root
			// Clear all lines
			root.getChildren().removeAll(lines);
			lines.clear();
		});
		Image Imagee = new Image("placeholder.png");
		ImageView vi = new ImageView(Imagee);
		vi.setFitHeight(17);
		vi.setFitWidth(16);
		ImageView vi0 = new ImageView(Imagee);
		vi0.setFitHeight(17);
		vi0.setFitWidth(16);

		// User button to run the shortest path
		Button run0 = new Button("Run");
		run0.setFont(Font.font(14));
		run0.setPrefSize(100, 30);
		run0.setStyle("-fx-background-radius: 10, 5;-fx-background-color:lightgrey;");
		pane20.add(run0, 0, 4);

		run0.setOnAction(l -> {
			ArrayList<City> list = new ArrayList<>(2);

			if (choice.getSelectionModel().getSelectedItem().equals("List")) {
				City sourceCity = search(source.getSelectionModel().getSelectedItem());
				City targetCity = search(target.getSelectionModel().getSelectedItem());

				if (sourceCity != null && targetCity != null) {
					list.add(0, sourceCity);
					list.add(1, targetCity);

					if (list.size() >= 2) {
						list.get(0).getR().fire();
						list.get(1).getR().fire();
					}
				}
			}

			else if (choice.getSelectionModel().getSelectedItem().equals("Click")) {

				for (City city : citie) {
					if (city.getR().isSelected()) {
						list.add(city);
						mouseClick++;
					}
				}

				if (mouseClick == 2) {
					City startObj = list.get(0);
					City targetObj = list.get(1);
					source.setValue(startObj);
					target.setValue(targetObj);
				} else {
					reset.fire();
				}
			} else {
				showAlert(Alert.AlertType.ERROR, "Please Choose choice", "Warning!", "Click or List");
			}
			if (source.getSelectionModel().getSelectedItem() != null
					&& target.getSelectionModel().getSelectedItem() != null) {


				Dijkstra dijkstraAlgo = new Dijkstra();
				List<City> visited = dijkstraAlgo.getShortestPath(source.getSelectionModel().getSelectedItem(),
						target.getSelectionModel().getSelectedItem(), countriesGraph);
				if (visited != null) {
					
					visited.get(0).getR().setGraphic(vi0);

					// Set graphic for the destination city's radio button
					visited.get(visited.size() - 1).getR().setGraphic(vi);
					path.appendText("Source : " + visited.get(0) + '\n');
					path.appendText("destination : " + visited.get(visited.size() - 1) + '\n');
					path.appendText("Path from source to destination:" + '\n');
					for (int i = 0; i < visited.size() - 1; i++) {
						City current = visited.get(i);
						City next = visited.get(i + 1);
						System.out.println("Painting image for: " + current.getName() + " and " + next.getName());

						path.appendText("-> " + current.getName() + "-> " + next.getName() + '\n');
						DrawLine(current, next);

					}
					double distance = countriesGraph.getDistances().get(target.getValue());
					String formattedDistance = String.format("%.3f KM", distance);
					dist.setText(formattedDistance);

				} else {
					showAlert(Alert.AlertType.ERROR, " ", "No path ", "warning ");
				}

			} else
				showAlert(Alert.AlertType.ERROR, "Please Choose Countries", "Warning !", "");
		});

		// User button to exit
		Button cancel = new Button("Exit");
		cancel.setFont(Font.font(14));
		cancel.setPrefSize(100, 30);
		GridPane.setHalignment(cancel, HPos.CENTER);
		cancel.setStyle("-fx-background-radius: 10, 5;-fx-background-color:lightgrey;");
		pane20.add(cancel, 1, 10);
		cancel.setOnAction(l -> javafx.application.Platform.exit());

		System.out.println("Image loaded successfully. Width: " + map.getWidth() + ", Height: " + map.getHeight());

		// Assuming 'map' is your Image
		ImageView v = new ImageView(map);
		v.setFitWidth(720);
		v.setFitHeight(790);
		v.setStyle("-fx-border-radius: 18, 7;");

		// Create a Pane to hold the ImageView
		imagePane.getChildren().add(v);
		imagePane.getChildren().add(root);

		pane2.setLeft(imagePane);

		for (City city : citie) {
			addCityToMap(root, map, city);
		}

		Scene scene = new Scene(pane2);
		scene.getStylesheets().add("style.css");

		stage2.setTitle("Gaza Strip Map");
		stage2.getIcons().add(new Image("icon.png"));
		stage2.setScene(scene);
		stage2.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private int readFile(File file) {
//		Graph graph = new Graph();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			// Read the first line to get the number of cities and adjacents
			String[] sizes = reader.readLine().trim().split(",");
			int numCities = Integer.parseInt(sizes[0]);
			int numAdjacents = Integer.parseInt(sizes[1]);

			// Read city information and add them to the graph
			for (int i = 0; i < numCities; i++) {
				String[] cityInfo = reader.readLine().trim().split(",");
				if (cityInfo.length != 3) {
					showAlert(Alert.AlertType.ERROR, "Error", "Invalid Data", "Invalid data format  , Skipping...");
					continue;
				}

				String cityName = cityInfo[0];
				System.out.println(cityName);
				double cityLat;
				double cityLng;

				try {
					cityLat = Double.parseDouble(cityInfo[1]);
					cityLng = Double.parseDouble(cityInfo[2]);
				} catch (NumberFormatException e) {
					showAlert(Alert.AlertType.ERROR, "Error", "Invalid Data",
							"Invalid latitude or longitude in line . Skipping...");
					continue;
				}

				// Create City object and add it to the list
				City city = new City(cityName, cityLat, cityLng);
				citie.add(city);
				countriesGraph.addCity(city, new ArrayList<>());
				countriesGraph.addCity(city);

				if (!cityName.startsWith("ST.")) {
					source.getItems().add(city);
					target.getItems().add(city);
				}

			}
			// Read adjacent information and add them to the graph
			for (int i = 0; i < numAdjacents; i++) {
				String[] adjacentInfo = reader.readLine().trim().split(",");
				String city1Name = adjacentInfo[0];
				String city2Name = adjacentInfo[1];
				City city1 = countriesGraph.getCityByName(city1Name);
				City city2 = countriesGraph.getCityByName(city2Name);

				// Check if either city1 or city2 is null before proceeding
				if (city1 != null && city2 != null) {
					// Correct
					DecimalFormat df = new DecimalFormat("#.##");
					double distance1 = Double.parseDouble(df.format(distance(city1, city2)));
					countriesGraph.addEdge(city1, city2, distance1);
					System.out.println(city1.getName() + " " + city2.getName() + " Distance: " + distance1 + " km");
				} else {
					System.out.println("City not found in countryMap: " + city1Name + " or " + city2Name);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			showAlert(Alert.AlertType.ERROR, "Error", "File Reading Error", "Error reading the file.");
			return -1;
		}
		return 0;
	}

	public static City search(City city) {
		for (City cityl : citie) {
			if (cityl.getName().equals(city)) {
				return cityl;
			}
		}
		return null;
	}

	private static double distance(City start, City end) {
		int earthR = 6371;

		double startLat = start.getLat();
		double startLong = start.getLng();
		double endLat = end.getLat();
		double endLong = end.getLng();

		double dLat = Math.toRadians((endLat - startLat));
		double dLong = Math.toRadians((endLong - startLong));

		startLat = Math.toRadians(startLat);
		endLat = Math.toRadians(endLat);

		double a = solve(dLat) + Math.cos(startLat) * Math.cos(endLat) * solve(dLong);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return earthR * c;
	}

	private static double solve(double n) {
		return Math.pow(Math.sin(n / 2), 2);
	}

	private static void showAlert(Alert.AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	static Point2D calculatePixelPosition(Image image, double minLat, double maxLat, double minLng, double maxLng,
			double cityLat, double cityLng) {
		int imageWidth = (int) image.getWidth();
		int imageHeight = (int) image.getHeight();

		// Adjust these scale factors to control the image size and position spacing
		double imageScale = 0.6; // Experiment with this value to control the overall image size
		double spacingScale = 2.7; // Experiment with this value to control the position spacing

		// Calculate the center of the scaled map in pixel coordinates
		int centerX = (int) (imageWidth * 0.89 * imageScale);
		int centerY = (int) (imageHeight * 0.26 * imageScale);

		// Calculate the center of the latitude and longitude of the cities
		double centerLat = (maxLat + minLat) / 2.0;
		double centerLng = (maxLng + minLng) / 2.0;

		// Calculate the percentage of latitude and longitude for the city relative to
		// the center
		double latPercentage = (cityLat - centerLat) / (maxLat - minLat);
		double lngPercentage = (cityLng - centerLng) / (maxLng - minLng);

		// Calculate pixel position relative to the center with adjusted scaling
		double x = centerX + lngPercentage * imageWidth * imageScale * spacingScale;
		double y = centerY - latPercentage * imageHeight * imageScale * spacingScale;
//		System.out.println("Latitude Percentage: " + latPercentage);
//		System.out.println("Longitude Percentage: " + lngPercentage);
//		System.out.println("X: " + x);
//		System.out.println("Y: " + y);
		return new Point2D(x, y);
	}

	private void addCityToMap(Pane root, Image map, City city) {
		// Calculate pixel position for the city
		Point2D pixelPosition = calculatePixelPosition(map, 31.0, 32.0, 34.0, 35.0, city.getLat(), city.getLng());

		// Set the position of the radio button using the same position as the label
		city.getR().setLayoutX(pixelPosition.getX());
		city.getR().setLayoutY(pixelPosition.getY());
	}

	private void DrawLine(City c1, City c2) {
		System.out.println("paint image");

		Point2D pixelPositionC1 = Gaza.calculatePixelPosition(map, minLat, maxLat, minLng, maxLng, c1.getLat(),
				c1.getLng());
		Point2D pixelPositionC2 = Gaza.calculatePixelPosition(map, minLat, maxLat, minLng, maxLng, c2.getLat(),
				c2.getLng());

		line = new Line();
		line.setStartX(pixelPositionC1.getX());
		line.setStartY(pixelPositionC1.getY());
		line.setEndX(pixelPositionC2.getX());
		line.setEndY(pixelPositionC2.getY());

		line.setFill(Color.BLACK);
		line.setSmooth(true);
		line.setStroke(Color.RED);
		line.setStrokeWidth(5);
		lines.add(line);
		
		// Add arrowhead
	    double arrowSize = 8;
	    double arrowWidth = 5;

	    double angle = Math.atan2((pixelPositionC2.getY() - pixelPositionC1.getY()),
	            (pixelPositionC2.getX() - pixelPositionC1.getX())) - Math.PI / 2.0;

	    double x1 = pixelPositionC2.getX() - arrowSize * Math.cos(angle) + arrowWidth * Math.sin(angle);
	    double y1 = pixelPositionC2.getY() - arrowSize * Math.sin(angle) - arrowWidth * Math.cos(angle);
	    double x2 = pixelPositionC2.getX() - arrowSize * Math.cos(angle) - arrowWidth * Math.sin(angle);
	    double y2 = pixelPositionC2.getY() - arrowSize * Math.sin(angle) + arrowWidth * Math.cos(angle);

	    Polygon arrowhead = new Polygon(pixelPositionC2.getX(), pixelPositionC2.getY(), x1, y1, x2, y2);
	    arrowhead.setFill(Color.BLACK);

	    root.getChildren().addAll(line, arrowhead);
//		root.getChildren().add(line);
	}

}
