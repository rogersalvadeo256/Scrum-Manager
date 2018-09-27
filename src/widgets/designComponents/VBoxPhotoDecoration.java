package widgets.designComponents;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HBoxPhotoDecoration extends VBox {

	private HBox hbContainer;
	private ImageView image;

	public HBoxPhotoDecoration(ImageView image, String label) {
		this.image = image;
		init();
		this.getChildren().addAll(this.image, hbContainer);
		this.hbContainer.getChildren().add(new Label(label));
	}

	public HBoxPhotoDecoration(ImageView image) {
//		this.getStylesheets().add(this.getClass().getResource("/css/DECORATION_IMAGE.css").toExternalForm());
		this.image = image;
		init();
	}

	private void init() {
		this.hbContainer = new HBox();

		this.setAlignment(Pos.CENTER);
		
		this.hbContainer.setAlignment(Pos.CENTER);

		this.hbContainer.setPrefHeight(500);

		this.hbContainer.setTranslateY(-20);

		this.hbContainer.getStyleClass().add("hbox");

		this.hbContainer.setVisible(false);

		this.setOnMouseMoved(e -> {
			this.hbContainer.setVisible(true);

		});
		this.setOnMouseExited(e -> {

			this.hbContainer.setVisible(false);
		});
	}

	public HBox changePhoto() {
		return this.hbContainer;

	}
}








