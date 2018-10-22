package widgets.designComponents.projectContents;

import db.pojos.TASK;
import db.pojos.USER_PROFILE;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TaskComponentPOPOUP extends Stage{

	
	private TextField txtTittle;
	private TextArea txtTask;
	private ToggleButton t1,t3,t5,t7;
	private ToggleGroup group;
	private HBox hToggle;
	private HBox hButtons;
	private Button btnCancel, btnFinish;
	private Label lblPontuation;
	
	private VBox content;
		
	public TaskComponentPOPOUP () { 
		init();
	}
	public TaskComponentPOPOUP(TASK task) { 
		init();	
		this.txtTittle.setText(task.getTaskTitle());
		this.txtTask.setText(task.getTask());
		switch (task.getTaskPontuation()) {
		case 1:
			t1.setSelected(true);
			break;
		case 3:
			t3.setSelected(true);
			break;
		case 5:
			t5.setSelected(true);
			break;
		case 7:
			t7.setSelected(true);
			break;
		default:
			break;
		}
		blockContents();
	}
	public TaskComponentPOPOUP(TASK task, USER_PROFILE p) {
		init();
		this.txtTittle.setText(task.getTaskTitle());
		this.txtTask.setText(task.getTask());
		switch (task.getTaskPontuation()) {
		case 1:
			t1.setSelected(true);
			break;
		case 3:
			t3.setSelected(true);
			break;
		case 5:
			t5.setSelected(true);
			break;
		case 7:
			t7.setSelected(true);
			break;
		default:
			break;
		}
		blockContents();
	}
	private void blockContents() { 
		txtTittle.setEditable(false);
		txtTask.setEditable(false);
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle> () {
			Toggle x = group.getSelectedToggle();
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				newValue = newValue == null ? oldValue : newValue;
				newValue = newValue == x ? newValue : x;
				newValue.setSelected(true);
			}
		});;
	}
	private void init() {
		
		this.initStyle(StageStyle.UNDECORATED);
		this.setWidth(400);
	
		this.txtTittle  = new TextField();
		txtTittle.setAlignment(Pos.CENTER);
		txtTittle.setPromptText("Titulo");
		
		this.txtTask = new  TextArea();
		txtTask.setWrapText(true);
		txtTask.setPromptText( " Digite .. ");
		
		t1 = new ToggleButton("1");
		t3 = new ToggleButton("3");
		t5 = new ToggleButton("5");
		t7 = new ToggleButton("7");
		
		group = new ToggleGroup();
		
		t1.setToggleGroup(group);
		t3.setToggleGroup(group);
		t5.setToggleGroup(group);
		t7.setToggleGroup(group);
		
		t3.setSelected(true);
		
		this.lblPontuation = new  Label("Defina a pontuação: ");
		
		hToggle=new HBox();
		hToggle.getChildren().addAll(lblPontuation,t1,t3,t5,t7);
		hToggle.setAlignment(Pos.CENTER);
		
		
		content = new VBox();
		
		content.getChildren().addAll(txtTittle, txtTask,hToggle);
		
		this.btnCancel = new Button("Cancelar");
		this.btnFinish = new Button("Finalizar");

		this.hButtons = new HBox();
		
		hButtons.getChildren().addAll(btnCancel,btnFinish);
		hButtons.setAlignment(Pos.CENTER);
		hButtons.setSpacing(20);
		
		content.getChildren().add(hButtons);
		content.setSpacing(10);

		Scene scene = new Scene(content);
		
		scene.getStylesheets().add(this.getClass().getResource("/css/CREATE_STORY_COMPONENT.css").toExternalForm());
		
		this.setScene(scene);
		this.show();		
	}

	public void setFinishAction (EventHandler<ActionEvent> e) { 
		this.btnFinish.setOnAction(e);
	}
	public void setCancelAction (EventHandler<ActionEvent> e) { 
		this.btnCancel.setOnAction(e);
	}
}