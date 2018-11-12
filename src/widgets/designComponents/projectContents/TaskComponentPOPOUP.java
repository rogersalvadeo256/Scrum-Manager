package widgets.designComponents.projectContents;

import java.util.Calendar;

import db.pojos.PROJECT_TASK;
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
import statics.DB_OPERATION;
import statics.SESSION;

public class TaskComponentPOPOUP extends Stage {

	private TextField txtTittle;
	private TextArea txtTask;
	private ToggleButton t1, t3, t5, t8;
	private ToggleGroup group;
	private HBox hToggle;
	private HBox hButtons;
	private Button btnCancel, btnFinish;
	private Label lblPontuation;

	private int pontuation = 0;
	
	private VBox content;

	private ScrumFrame sprintColumns;

	public TaskComponentPOPOUP() {
		init();
	}

	public TaskComponentPOPOUP(PROJECT_TASK task, ScrumFrame f) {
		init();
		sprintColumns = f;
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
			t8.setSelected(true);
			break;
		default:
			break;
		}
		blockContents();
	}

	public TaskComponentPOPOUP(PROJECT_TASK task, USER_PROFILE p) {
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
		case 8:
			t8.setSelected(true);
			break;
		default:
			break;
		}
		blockContents();
	}

	public TaskComponentPOPOUP(PROJECT_TASK task) {
		init();
	}

	private void blockContents() {
		txtTittle.setEditable(true);
		txtTask.setEditable(true);
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				newValue = newValue == null ? oldValue : newValue;
				newValue.setSelected(true);
			}
		});
		;
	}

	private void init() {

		this.initStyle(StageStyle.UNDECORATED);
		this.setWidth(400);

		this.txtTittle = new TextField();
		txtTittle.setAlignment(Pos.CENTER);
		txtTittle.setPromptText("Titulo");

		this.txtTask = new TextArea();
		txtTask.setWrapText(true);
		txtTask.setPromptText(" Digite .. ");

		t1 = new ToggleButton("1");
		t3 = new ToggleButton("3");
		t5 = new ToggleButton("5");
		t8 = new ToggleButton("8");

		group = new ToggleGroup();

		t1.setToggleGroup(group);
		t3.setToggleGroup(group);
		t5.setToggleGroup(group);
		t8.setToggleGroup(group);

		t1.setSelected(true);

		this.lblPontuation = new Label("Defina a pontuação: ");

		hToggle = new HBox();
		hToggle.getChildren().addAll(lblPontuation, t1, t3, t5, t8);
		hToggle.setAlignment(Pos.CENTER);

		content = new VBox();

		this.txtTittle.setOnMouseClicked(e -> {

		});

		content.getChildren().addAll(txtTittle, txtTask, hToggle);

		this.btnCancel = new Button("Cancelar");
		this.btnCancel.setId("back");
		this.btnCancel.setOnAction(e -> {
			this.close();
		});
		this.btnFinish = new Button("Finalizar");
		this.btnFinish.setOnAction(e -> {
			
			
			if(t1.isSelected()==true) {
				pontuation=1;
			}else if(t3.isSelected()==true) {
				pontuation=3;
			}else if(t5.isSelected()==true) {
				pontuation=5;
			}else if(t8.isSelected()==true) {
				pontuation=8;
			}
			

			PROJECT_TASK task = new PROJECT_TASK();
			task.setTask(txtTask.getText());
			task.setTaskCreator(SESSION.getProfileLogged());
			task.setTaskTitle(txtTittle.getText());
			task.setTaskDateStart(Calendar.getInstance().getTime());
			task.setTaskPontuation(pontuation);
			task.setTaskStatus("FAZER");
			sprintColumns.addTodo(task);
			DB_OPERATION.PERSIST(task);
			this.close();
		});
		this.hButtons = new HBox();

		hButtons.getChildren().addAll(btnCancel, btnFinish);
		hButtons.setAlignment(Pos.CENTER);
		hButtons.setSpacing(20);

		content.getChildren().add(hButtons);
		content.setSpacing(10);

		Scene scene = new Scene(content);

		scene.getStylesheets().add(this.getClass().getResource("/css/CREATE_STORY_COMPONENT.css").toExternalForm());

		this.setScene(scene);
		this.show();
	}

	public void setCancelAction(EventHandler<ActionEvent> e) {
		this.btnCancel.setOnAction(e);
	}
}
