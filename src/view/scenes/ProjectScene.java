package view.scenes;

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
import statics.SESSION;
import widgets.designComponents.projectContents.ScrumFrame;
import widgets.designComponents.projectContents.TaskComponent;

public class ProjectScene extends Scene {

	private Button btnSprints, btnStartSprint, btnTeam;

	private VBox content;
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

		this.getStylesheets().add(this.getClass().getResource("/css/PROJECT_SCENE.css").toExternalForm());

		init();

		PROJECT_TASK task = new PROJECT_TASK();

		task.setTask("lasfkalfkalsf gakslajçj laksjlagkjlgkjs lkgajlk j");
		task.setTaskTitle(" isso isso isso ");

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
		hHeader.getChildren().add(new Label("Nome do Projeto"));
		hHeader.setAlignment(Pos.CENTER);

		AnchorPane.setLeftAnchor(hHeader, this.widthProperty().get());
		AnchorPane.setRightAnchor(hHeader, this.widthProperty().get());
		anchor.getChildren().add(hHeader);

		this.btnStartSprint = new Button("Definir novo sprint");
		this.btnSprints = new Button("Ver sprints anteriores");
		this.btnTeam = new Button("Equipe");

		this.hInfo = new HBox();
		hInfo.setId("hInfo");
		

		hInfo.getChildren().addAll(btnStartSprint, btnSprints, btnTeam);
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
		this.btnLeaveProject = new Button("Abandonar projeto");
		
		this.projectInformations = new VBox();
		projectInformations.setId("vbProject-info");
		projectInformations.getChildren().addAll(new Label("Data de inicio"), new Label("Sprint atual"));

		vMemberActions.setAlignment(Pos.TOP_CENTER);
		vMemberActions.setSpacing(20);

		vMemberActions.setId("member-actions");

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

}
