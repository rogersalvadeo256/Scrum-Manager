package scenes.popoups;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class StandartLayoutPOPOUP extends Stage {
	protected Scene scene;
	protected VBox layout;
	public StandartLayoutPOPOUP(Window owner) {
		
		
//		this.scene.getStylesheets().add(this.getClass().getResource("/css/STANDART_LAYOUT.css").toExternalForm());

		this.initOwner(owner);
		this.initModality(Modality.WINDOW_MODAL);
		this.setWidth(400);
		this.setHeight(600);
		this.setResizable(false);
		this.layout = new VBox();
		this.scene = new Scene(layout);
		this.setScene(scene);
//		this.initStyle(StageStyle.UNDECORATED);

	}
}
