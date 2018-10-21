package widgets.designComponents.projectContents;

import db.pojos.PROJECT_TASK;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TaskComponent  extends VBox { 
	
	
	private Label lblTitle, lblPontuation;
	private Text lblTask;
	private PROJECT_TASK task;
	
	public TaskComponent(PROJECT_TASK task) {
		this.task = task;
		
		this.getStylesheets().add(this.getClass().getResource("/css/TASK_COMPONENT.css").toExternalForm());

		
		
		
		
		this.lblTitle=new Label();
		this.lblPontuation =new Label();
		this.lblTask=new Text();
		
		lblTask.setWrappingWidth(this.widthProperty().get());
		
		this.getChildren().addAll(lblTitle,lblTask,lblPontuation);

	
	
	}
	
	
	
	
	
	
	
	

	public PROJECT_TASK getTask() {
		return task;
	}

	public void setTask(PROJECT_TASK task) {
		this.task = task;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}