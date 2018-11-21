package listeners;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class CloseWindowEsc implements EventHandler<KeyEvent> {

	private Stage stage;

	public CloseWindowEsc(Stage stage) {
		this.stage = stage;
	}
	@Override
	public void handle(KeyEvent e) {
		if (e.getCode() == KeyCode.ESCAPE)
			this.stage.close();
	}

}
