package view.popoups;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import listeners.CloseWindowEsc;

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

		this.addEventHandler(KeyEvent.KEY_PRESSED, new CloseWindowEsc(this));
	}
}















