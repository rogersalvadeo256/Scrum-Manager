package widgets.designComponents;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
public class HBoxPhotoDecoration extends HBox{
	private Label lblChangePhoto;
	public HBoxPhotoDecoration() {
		this.getStylesheets().add(this.getClass().getResource("/css/DECORATION_IMAGE.css").toExternalForm());
		this.lblChangePhoto = new Label("Mudar foto");
		this.getChildren().add(lblChangePhoto);
	}
}
