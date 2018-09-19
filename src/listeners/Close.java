package listeners;

import db.hibernate.factory.Database;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class Close implements  EventHandler<ActionEvent> {

	private Stage parent;

	public Close(Stage parent) {
		this.parent = parent;
	}

	@Override
	public void handle(ActionEvent event) {
		Database.close();
		this.parent.close();
	}

}
