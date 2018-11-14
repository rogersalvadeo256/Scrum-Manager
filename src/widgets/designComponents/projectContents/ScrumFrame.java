package widgets.designComponents.projectContents;

import db.pojos.PROJECT_TASK;
import javafx.scene.layout.HBox;
import statics.ENUMS;

public class ScrumFrame extends HBox {

	private ScrollColumn toDo;
	private ScrollColumn doing;
	private ScrollColumn done;

	public ScrumFrame() {

		this.getStylesheets().add(this.getClass().getResource("/css/SCRUM_FRAME.css").toExternalForm());

		this.setId("hbox");

		this.toDo = new ScrollColumn(ENUMS.PROJECT_FRAMEWORK.TO_DO, this);
		this.doing = new ScrollColumn(ENUMS.PROJECT_FRAMEWORK.DOING, this);
		this.done = new ScrollColumn(ENUMS.PROJECT_FRAMEWORK.DONE, this);
		this.getChildren().addAll(toDo, doing, done);

	}

	public void addTodo(PROJECT_TASK task) {
		this.toDo.setNewTask(task);
	}

	public void removeTodo(PROJECT_TASK task,TaskComponent tc) {
		this.toDo.deleteTask(task,tc);
	}
	
	public void addDoing(PROJECT_TASK task) {
		this.doing.setNewTask(task);
	}

	public void removeDoing(PROJECT_TASK task,TaskComponent tc) {
		this.toDo.deleteTask(task,tc);
	}
	
	public void addDone(PROJECT_TASK task) {
		this.done.setNewTask(task);
	}

	public void removeDone(PROJECT_TASK task,TaskComponent tc) {
		this.toDo.deleteTask(task,tc);
	}
	
}
