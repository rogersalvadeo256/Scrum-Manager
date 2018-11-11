package view.scenes;

import java.io.IOException;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import application.main.Window;
import db.pojos.PROJECT;
import db.pojos.PROJECT_TASK;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import project.PROJECT_SESSION;
import widgets.designComponents.projectContents.ScrumFrame;
import widgets.designComponents.projectContents.TaskComponent;
import widgets.designComponents.projectContents.TaskComponentPOPOUP;

public class ProjectScene extends Scene {

	private Button btnSprints, btnStartTask, btnTeam;

	private VBox content;
	private Label lblProjDate;
	private java.util.Date projDateStart;
	private HBox layout;

	private HBox vFrame;
	private ScrumFrame frame;

	private VBox vMemberActions;
	private Button btnLeaveProject, btnBack;

	private HBox hHeader;
	private Label lblProjectName;

	private HBox hInfo;
	private VBox projectInformations;

	private AnchorPane anchor;
	private Label lblFuncion;

	private Button btnTaskDone;

	public ProjectScene(PROJECT p) {
		super(new AnchorPane());
		Window.mainStage.setResizable(true);
		PROJECT_SESSION.initSession(p);
		
		
		this.getStylesheets().add(this.getClass().getResource("/css/PROJECT_SCENE.css").toExternalForm());

		init();

		PROJECT_TASK task = new PROJECT_TASK();

		task.setTask("Defina uma tarefa aqui.");
		task.setTaskTitle(" Defina título da tarefa aqui. ");
		
		vMemberActions.getChildren().addAll(lblFuncion, new TaskComponent(task), btnTaskDone, btnLeaveProject, btnBack);

	}

	private void init() {
		this.content = new VBox();
		this.layout = new HBox();
		content.setAlignment(Pos.CENTER);
		layout.setAlignment(Pos.CENTER);

		
		Window.mainStage.setWidth(1050);
		Window.mainStage.setHeight(768);

		content.getChildren().add(layout);
		this.anchor = new AnchorPane();

		// this.lblProjectName = new Label(projectName);

		this.hHeader = new HBox();
		hHeader.setId("header");
		hHeader.getChildren().add(new Label(PROJECT_SESSION.getProject().getProjName()));
		hHeader.setAlignment(Pos.CENTER);

		AnchorPane.setLeftAnchor(hHeader, this.widthProperty().get());
		AnchorPane.setRightAnchor(hHeader, this.widthProperty().get());
		anchor.getChildren().add(hHeader);

		this.btnStartTask = new Button("Definir nova tarefa");
		
		this.btnSprints = new Button("Ver sprints anteriores");
		this.btnTeam = new Button("Equipe");

		this.hInfo = new HBox();
		hInfo.setId("hInfo");
		

		hInfo.getChildren().addAll(btnStartTask, btnSprints, btnTeam);
		hInfo.setSpacing(20);

		AnchorPane.setTopAnchor(hInfo, 60d);
		AnchorPane.setLeftAnchor(hInfo, this.widthProperty().get());
		AnchorPane.setRightAnchor(hInfo, this.widthProperty().get());
		anchor.getChildren().add(hInfo);

		this.vFrame = new HBox();
		HBox.setHgrow(vFrame, Priority.ALWAYS);
		this.frame = new ScrumFrame();

		vFrame.getChildren().add(frame);

		AnchorPane.setTopAnchor(vFrame, 170d);
		AnchorPane.setLeftAnchor(vFrame, this.widthProperty().get());
		AnchorPane.setBottomAnchor(vFrame, 0d);
		anchor.getChildren().add(vFrame);

		this.btnTaskDone = new Button("Historias feitas \n nesse sprint");

		this.vMemberActions = new VBox();
		this.lblFuncion = new Label("Função");
		this.btnBack = new Button("Voltar");
		this.btnBack.setOnAction(e -> {
			try {
				Window.mainStage.setScene(new HomePageScene());
			} catch (ClassNotFoundException | SQLException | IOException e1) {
				(e1).printStackTrace();
			}

		});
		this.btnLeaveProject = new Button("Abandonar projeto");
//		this.btnLeaveProject.setOnAction(e ->{
//			if (QUERY_PROJECT.USER_PROJECTS_MEMBER().size() == 1){
//				Optional<ButtonType> result = new CustomAlert(AlertType.INFORMATION, "Projeto será excluído", "O projeto só possui um membro se você sair, o projeto será exluído", null).showAndWait();
//				if (result.get() == ButtonType.OK) {
//					
//					
//				}
//				}};
		lblProjDate = new Label("");
		
		Format formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date now = PROJECT_SESSION.getProject().getProjDateStart();
		String date = formatter.format(now);
		
		lblProjDate.setText(date);
		
		this.projectInformations = new VBox();
		projectInformations.setId("vbProject-info");
		projectInformations.getChildren().addAll(lblProjDate, new Label("Sprint atual"));

		vMemberActions.setAlignment(Pos.TOP_CENTER);
		vMemberActions.setSpacing(20);

		vMemberActions.setId("member-actions");
		
		btnStartTask.setOnAction(e->{
			new TaskComponentPOPOUP(getTask(), this.frame);
		});

		AnchorPane.setTopAnchor(projectInformations, 50d);
		AnchorPane.setRightAnchor(projectInformations, this.widthProperty().get());
		AnchorPane.setBottomAnchor(projectInformations, 50d);
		AnchorPane.setTopAnchor(vMemberActions, 200d);
		AnchorPane.setRightAnchor(vMemberActions, this.widthProperty().get());
		AnchorPane.setBottomAnchor(vMemberActions, 100d);
		anchor.getChildren().addAll(projectInformations, vMemberActions);

		anchor.getChildren().add(content);

		this.setRoot(anchor);

	}

	private PROJECT_TASK getTask() {
		
		return task();
	}

	private PROJECT_TASK task() {
		PROJECT_TASK task = new PROJECT_TASK();
		return task;
	}
}

