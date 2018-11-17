package widgets.designComponents.projectContents;

import db.pojos.PROJECT_TASK;
import db.pojos.USER_PROFILE;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import statics.DB_OPERATION;
import statics.ENUMS;
import statics.SESSION;
import view.popoups.TaskDoingPOPUP;
import view.popoups.TaskDonePopUp;

public class TaskComponent extends VBox {

	private Label lblTitle, lblPontuation;
	private Text lblTask;
	private PROJECT_TASK task;

	private ScrumFrame parentScrumFrame;
	
	private HBox hbExecutor;

	public TaskComponent(PROJECT_TASK task, USER_PROFILE p, ScrumFrame sc) {
		this.task = task;
		init();
		hbExecutor.getChildren().addAll(lblPontuation, new Label(p.getName()));
		this.parentScrumFrame = sc;
	}

	public TaskComponent(PROJECT_TASK task, ScrumFrame sc) {
		this.task = task;
		init();
		this.parentScrumFrame = sc;
	}

	private void init() {
		this.getStylesheets().add(this.getClass().getResource("/css/TASK_COMPONENT.css").toExternalForm());
		this.setId("content");

		this.lblTitle = new Label(task.getTaskTitle());
		this.lblPontuation = new Label(String.valueOf(task.getTaskPontuation()));
		this.lblTask = new Text(task.getTask());

		lblTask.setWrappingWidth(this.widthProperty().get());

		lblTitle.setId("title");
		lblPontuation.setId("pontuation");
		lblTask.setId("task");

		this.hbExecutor = new HBox();
		hbExecutor.setAlignment(Pos.CENTER_RIGHT);
		hbExecutor.setSpacing(20);

		this.getChildren().addAll(lblTitle, lblTask, hbExecutor);
		
		this.setOnMouseClicked(e -> {
//			new TaskComponentPOPOUP(getTask());
			
			if (task.getTaskStatus()==ENUMS.PROJECT_FRAMEWORK.TO_DO.getValue()) {
				new TaskDoingPOPUP(this.task, SESSION.getProfileLogged(), parentScrumFrame, this);
			}else if(task.getTaskStatus()==ENUMS.PROJECT_FRAMEWORK.DOING.getValue()) {
				new TaskDonePopUp(this.task, SESSION.getProfileLogged(), parentScrumFrame, this);
			}
			
			
		});
	}

	public PROJECT_TASK getTask() {
		return task;
	}

	public void setTask(PROJECT_TASK task) {
		this.task = task;
	}

}