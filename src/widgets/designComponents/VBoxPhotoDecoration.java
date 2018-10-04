package widgets.designComponents;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VBoxPhotoDecoration extends VBox {

	private VBox vbContainer;
	private ImageView image;

	public VBoxPhotoDecoration(ImageView image, String label) {
		this.image = image;
		init();
		this.getChildren().addAll(this.image, vbContainer);
		this.vbContainer.getChildren().add(new Label(label));
	}

	public VBoxPhotoDecoration(ImageView image) {
		this.getStylesheets().add(this.getClass().getResource("/css/DECORATION_IMAGE.css").toExternalForm());
		this.image = image;
		init();
	}

	private void init() {
		this.vbContainer = new VBox();

		this.setAlignment(Pos.CENTER);
		
		this.vbContainer.setAlignment(Pos.CENTER);

		this.vbContainer.setPrefHeight(500);

		this.vbContainer.setTranslateY(-20);

		this.vbContainer.getStyleClass().add("hbox");

		this.vbContainer.setVisible(false);

		this.setOnMouseMoved(e -> {
			this.vbContainer.setVisible(true);

		});
		this.setOnMouseExited(e -> {
			this.vbContainer.setVisible(false);
		});
	}

	public VBox changePhoto() {
		return this.vbContainer;

	}
}








