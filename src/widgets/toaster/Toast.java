package widgets.toaster;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class Toast extends Stage {
	Scene scene;
	HBox layout;

	public Toast(Window owner, String text) {
		this.layout = new HBox();
		this.layout.getChildren().add(new Label(text));
		this.scene = new Scene(layout);
		this.setScene(scene);
		this.initStyle(StageStyle.UNDECORATED);
		this.show();
		
	}
	
}
