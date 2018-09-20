package widgets.designComponents;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HBoxPhotoDecoration extends VBox {

	private HBox hbContainer;

	public HBoxPhotoDecoration(ImageView image, String label) {
		this.getStylesheets().add(this.getClass().getResource("/css/DECORATION_IMAGE.css").toExternalForm());

		this.hbContainer = new HBox();

		this.getChildren().addAll(image, hbContainer);
		this.hbContainer.getChildren().add(new Label(label));

		this.setAlignment(Pos.CENTER);
		this.prefWidth(image.getFitWidth());
		this.prefHeight(image.getFitHeight());

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