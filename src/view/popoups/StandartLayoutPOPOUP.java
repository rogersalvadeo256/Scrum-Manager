package view.popoups;

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
		
		

		this.initOwner(owner);
		this.initModality(Modality.WINDOW_MODAL);
		this.setResizable(true);
		this.layout = new VBox();
		this.scene = new Scene(layout);
		
		
		this.setScene(scene);
		this.initStyle(StageStyle.UNDECORATED);

		this.scene.getStylesheets().add(this.getClass().getResource("/css/STANDART_LAYOUT.css").toExternalForm());
	}
}
