package view.popoups;

import db.pojos.PROJECT;
import db.pojos.PROJECT_TASK;
import db.pojos.USER_PROFILE;
import javafx.geometry.Pos;
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

public class TaskDonePopUp extends Stage {

	VBox layout = new VBox();
	TextField txtTaskName;
	TextArea txtTask;
	Button btnCancel,btnFinish;
	Label lblPontuation, lblDoingName, lblStatus;
	HBox hbxOrg1 = new HBox(), hbxOrg2 = new HBox(), hbxOrg3 = new HBox(),hbxOrg4 = new HBox();

	PROJECT_TASK tk;
	PROJECT pj;
	
	ScrumFrame parentFrame;
	TaskComponent taskc;
	
	
	public TaskDonePopUp(PROJECT_TASK task, USER_PROFILE p, ScrumFrame sc,TaskComponent tc,PROJECT proj) {
		this.initStyle(StageStyle.DECORATED);
		pj=proj;
		this.setWidth(240);
		this.setHeight(240);
		btnCancel = new Button("Cancelar");
		btnCancel.setId("back");
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
		this.lblStatus.setText(task.getTaskStatus().getValue());
		
		
		hbxOrg1.getChildren().add(txtTaskName);
		hbxOrg2.getChildren().add(txtTask);
		hbxOrg3.getChildren().addAll(lblDoingName,lblStatus);
		hbxOrg4.getChildren().addAll(btnCancel,btnFinish);
		layout.getChildren().addAll(hbxOrg1,hbxOrg2,hbxOrg3,lblPontuation,hbxOrg4);
		
		this.layout.setAlignment(Pos.CENTER);
		this.layout.setSpacing(10);
		
		
		this.btnFinish.setOnAction(e->{
			PROJECT_TASK doneT = new PROJECT_TASK();
			doneT.setTaskCod(this.tk.getTaskCod());
			doneT.setTaskTitle(this.tk.getTaskTitle());
			doneT.setTask(this.tk.getTask());
			doneT.setTaskCreator(this.tk.getTaskCreator());
			doneT.setTaskExecutor(p.getCod());
			doneT.setTaskDateStart(this.tk.getTaskDateStart());
			doneT.setTaskPontuation(this.tk.getTaskPontuation());
			doneT.setTaskStatus(ENUMS.PROJECT_FRAMEWORK.DONE);
			parentFrame.removeDoing(task, taskc);
			parentFrame.addDone(doneT);
			DB_OPERATION.MERGE(doneT);
			
//			proj.getProjTasks().add(doneT);
//			DB_OPERATION.MERGE(proj);
			
			this.close();
		});
		btnCancel.setOnAction(e->{
			this.close();
		});
		
		this.initStyle(StageStyle.UNDECORATED);
		Scene cena = new Scene(layout);
		this.layout.getStylesheets().add(this.getClass().getResource("/css/TASKDDPOPOUP.css").toExternalForm());
		
		this.setScene(cena);
		this.show();

	}

}
