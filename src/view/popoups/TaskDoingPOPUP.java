package view.popoups;

import db.pojos.PROJECT_TASK;
import db.pojos.USER_PROFILE;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import statics.DB_OPERATION;
import statics.ENUMS;
import statics.SESSION;
import widgets.designComponents.projectContents.ScrumFrame;
import widgets.designComponents.projectContents.TaskComponent;

public class TaskDoingPOPUP extends Stage {

	VBox layout = new VBox();
	TextField txtTaskName;
	TextArea txtTask;
	Button btnCancel,btnFinish;
	Label lblPontuation, lblDoingName, lblStatus;
	HBox hbxOrg1 = new HBox(), hbxOrg2 = new HBox(), hbxOrg3 = new HBox(),hbxOrg4 = new HBox();

	PROJECT_TASK tk;
	
	ScrumFrame parentFrame;
	TaskComponent taskc;
	
	public TaskDoingPOPUP(PROJECT_TASK task, USER_PROFILE p, ScrumFrame sc,TaskComponent tc) {

		btnCancel = new Button("Cancelar");
		btnFinish = new Button("Finalizar");
		
		
		this.txtTaskName= new TextField();
		this.txtTask=new TextArea();
		this.lblPontuation=new Label();
		this.lblDoingName=new Label();
		this.lblStatus=new Label();
		
		
		
		tk = task;
		
		taskc = tc;
		
		parentFrame = sc;
		
		
		this.txtTaskName.setText(tk.getTaskTitle());
		this.txtTask.setText(tk.getTask());
		this.lblPontuation.setText(Integer.toString(tk.getTaskPontuation()));
		this.lblDoingName.setText(SESSION.getUserLogged().getUserName());
		this.lblStatus.setText(task.getTaskStatus());
		
		
		hbxOrg1.getChildren().add(txtTaskName);
		hbxOrg2.getChildren().add(txtTask);
		hbxOrg3.getChildren().addAll(lblDoingName,lblStatus);
		hbxOrg4.getChildren().addAll(btnCancel,btnFinish);
		layout.getChildren().addAll(hbxOrg1,hbxOrg2,hbxOrg3,lblPontuation,hbxOrg4);
		
		this.btnFinish.setOnAction(e->{
			PROJECT_TASK doingT = new PROJECT_TASK();
			doingT.setTaskCod(this.tk.getTaskCod());
			doingT.setTaskTitle(this.tk.getTaskTitle());
			doingT.setTask(this.tk.getTask());
			doingT.setTaskCreator(this.tk.getTaskCreator());
			doingT.setTaskExecutor(p.getCod());
			doingT.setTaskDateStart(this.tk.getTaskDateStart());
			doingT.setTaskPontuation(this.tk.getTaskPontuation());
			doingT.setTaskStatus(ENUMS.PROJECT_FRAMEWORK.DOING.getValue());
			parentFrame.removeTodo(task, taskc);
			parentFrame.addDoing(doingT);
			DB_OPERATION.MERGE(doingT);
			this.close();
		});
		
		btnCancel.setOnAction(e->{
			this.close();
		});
		
		this.initStyle(StageStyle.UNDECORATED);
		Scene cena = new Scene(layout);
		this.layout.getStylesheets().add(this.getClass().getResource("/css/NEW_PROJECT.css").toExternalForm());
		
		this.setScene(cena);
		this.show();

	}

}
