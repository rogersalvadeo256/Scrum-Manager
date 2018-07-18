package events;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.Window;

public abstract class ExitButtonListener implements EventHandler<ActionEvent> { 

	@Override
	public void handle(ActionEvent event) {
		Platform.exit();
		Window.mainStage.close();
	}
}
