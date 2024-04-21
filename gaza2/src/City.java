
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class City {
	private String name;

	private double lat;
	private double lng;
	RadioButton r;
	private Label label;

	public City(String name) {
		super();
		this.name = name;
	}

	public City(String name, double latitude, double longitude) {
		this.name = name;
		this.lat = latitude;
		this.lng = longitude;
		DrawMap(Gaza.map, 31.0, 32.0, 34.0, 35.0);

	}

	void DrawMap(Image map, double minLat, double maxLat, double minLng, double maxLng) {
		this.label = new Label(getName());
		this.label.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 8));
		this.label.setTextFill(Color.BLACK);

		Tooltip tip = new Tooltip(this.getName());
		tip.setFont(new Font(16));
		tip.setStyle("-fx-background-color: grey;");
		this.label.setTooltip(tip);

		this.r = new RadioButton();

		Image image = new Image("placeholder1.png");
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(17);
		imageView.setFitWidth(16);

		this.label.setPadding(new Insets(-5));
		this.label.setGraphic(imageView);

//		this.r.setGraphic(new ImageView(image));

		Image selectedImage = new Image("placeholder.png");

		this.r.setOnAction(o -> {
//			ImageView selectedImageView = new ImageView(selectedImage);
//			selectedImageView.setFitHeight(17);
//			selectedImageView.setFitWidth(16);
			this.label.setTextFill(Color.DARKRED);
//			this.r.setGraphic(selectedImageView);
			this.r.setSelected(true);
		});

		Point2D pixelPosition = Gaza.calculatePixelPosition(map, minLat, maxLat, minLng, maxLng, this.getLat(),
				this.getLng());

		if (getName().startsWith("ST.")) {
			// Street point without label
			Circle streetPoint = new Circle(pixelPosition.getX(), pixelPosition.getY(), 2, Color.WHITE);
//			Gaza.root.getChildren().add(streetPoint);
//			// City point with label
//			this.labels.setLayoutX(pixelPosition.getX());
//			this.labels.setLayoutY(pixelPosition.getY());
//			Gaza.root.getChildren().add(this.labels);
		} else {
//			Circle streetPoint = new Circle(pixelPosition.getX(), pixelPosition.getY(), 2, Color.WHITE);
//			Gaza.root.getChildren().add(streetPoint);
			// City point with label
			this.label.setLayoutX(pixelPosition.getX());
			this.label.setLayoutY(pixelPosition.getY());
			Gaza.root.getChildren().add(this.label);

			Gaza.root.getChildren().add(this.r);
		}
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public City() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RadioButton getR() {
		return r;
	}

	public void setR(RadioButton r) {
		this.r = r;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public String getLabelText() {
		return this.label.getText();
	}

	public void setLabelText(String labelText) {
		this.label.setText(labelText);
	}

	@Override
	public String toString() {
		return name;
	}

//	ImageView radioImageView = new ImageView(image);
//	radioImageView.setFitHeight(17);
//	radioImageView.setFitWidth(16);
//	radioImageView.setLayoutX(pixelPosition.getX());
//	radioImageView.setLayoutY(pixelPosition.getY());
//	Gaza.root.getChildren().add(radioImageView);
////
//	this.r.setGraphic(radioImageView);
}
