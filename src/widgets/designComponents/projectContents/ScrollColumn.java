package widgets.designComponents.projectContents;

import db.pojos.PROJECT_TASK;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import statics.ENUMS.PROJECT_FRAMEWORK;

public class ScrollColumn extends ScrollPane {
	private VBox column;

	public ScrollColumn(PROJECT_FRAMEWORK title) {
		this.getStylesheets().add(this.getClass().getResource("/css/SCROLL.css").toExternalForm());
		this.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);

		this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

		this.column = new VBox();
		this.column.getStyleClass().add(this.getClass().getResource("/css/SCRUM_FRAME.css").toExternalForm());

		column.setId("column");

		column.setPrefWidth(220);
		column.getChildren().add(new Label(title.getValue()));
		column.setAlignment(Pos.CENTER);

		this.setContent(column);

	}

	public VBox get_column() {
		return this.column;
	}

	public void setNewTask(PROJECT_TASK task) {
		this.column.getChildren().add(new TaskComponent(task));
		this.setContent(column);
	}
	
}
