package view.popoups;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import application.controllers.NewProjectSceneController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import validation.CheckEmptyFields;
import widgets.designComponents.ComponentInvite;
import widgets.designComponents.HBFriendInviteComponent;

public class NewProjectPOPOUP extends StandartLayoutPOPOUP {
	private Label lblProjectName, lblAboutTheProject;
	private TextField txtProjectName;
	private TextArea txtAboutTheProject;


	private NewProjectSceneController controller;

	private Button btnFinish, btnInvite, btnGoBack, btnAboutTheProject;
	private HBox hbButtons;

	private HBox layoutForInviteComponents;


	public NewProjectPOPOUP(Stage mainStage) throws FileNotFoundException {
		super(mainStage);

		this.setWidth(300);
		this.setHeight(500);

		this.controller = new NewProjectSceneController();

		this.lblProjectName = new Label("Nome do Projeto");
		this.lblAboutTheProject = new Label("Descrição do Projeto");
		this.txtProjectName = new TextField();
		this.txtAboutTheProject = new TextArea();
		this.txtAboutTheProject.setId("descProject");
		this.txtAboutTheProject.setId("txtDescProject");
		this.hbButtons = new HBox();

		this.scene.getStylesheets().add(this.getClass().getResource("/css/NEW_PROJECT.css").toExternalForm());

		this.txtProjectName.setMaxWidth(300);
		this.txtAboutTheProject.setMaxWidth(300);
		this.txtAboutTheProject.setPrefRowCount(10);
		this.txtAboutTheProject.setWrapText(true);

		this.btnAboutTheProject = new Button();
		this.btnFinish = new Button("Criar Projeto");
		this.btnFinish.setId("btnSalve");

		this.btnInvite = new Button("Convidar Amigos");
		this.btnInvite.setId("btnInvite");

		int SIZE = 30;
		ImageView buttonImage = new ImageView(new Image(new FileInputStream(new File("resources/images/icons/back_arrow_icon.png"))));
		buttonImage.setFitWidth(SIZE);
		buttonImage.setFitHeight(SIZE);

		this.btnGoBack = new Button();
		this.btnGoBack.setId("back");
		this.btnGoBack.setGraphic(buttonImage);
		this.btnGoBack.setMaxWidth(100);
		this.btnGoBack.setMaxHeight(100);
		// this.hbHeader = new HBox();
		// this.hbHeader.getChildren().add(this.btnGoBack);
		// this.hbHeader.prefWidth(this.getWidth());
		// this.hbHeader.setAlignment(Pos.CENTER_LEFT);

		this.txtProjectName.setAlignment(Pos.CENTER);


		this.btnGoBack.setOnAction(e -> {
			controller.actionBack(NewProjectPOPOUP.this);
		});

		this.btnFinish.setOnAction(e -> {

			CheckEmptyFields cef = new CheckEmptyFields();

			if (!cef.isTextFieldEmpty(txtProjectName) && !cef.isTextAreaEmpty(txtAboutTheProject)) {
				// controller.actionFinish(txtProjectName.getText(),
				// txtDescProject.getText());
			}
		});

		this.btnInvite.setOnAction(e -> {
			try {
				inviteFriendLayoutState();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		initialLayout();
	}

	private void inviteFriendLayoutState() throws IOException {
		boolean t = layoutForInviteComponents == null ? true : false;

		if (t)	layoutForInviteComponents = new HBox();

		boolean isTheVBOX = this.scene.getRoot() == layout ? true : false;

		if (isTheVBOX) {
			layoutForInviteComponents.getChildren().add(layout);
			this.scene.setRoot(layoutForInviteComponents);	
			this.btnInvite.setText("Cancelar");

			ComponentInvite component = new ComponentInvite();

			this.layoutForInviteComponents.getChildren().add(component);

			return;
		}
		boolean isTheHBOX = this.scene.getRoot() == layoutForInviteComponents ? true : false;
		if (isTheHBOX) {
			layoutForInviteComponents.getChildren().clear();
			this.scene.setRoot(layout);
			this.sizeToScene();
			this.setWidth(300);
			this.setHeight(500);
			this.btnInvite.setText("Convidar amigos");
			return;
		}
	}

	private void aboutTheProject() {

		this.layout.getChildren().clear();

		this.layout.getChildren().addAll(this.lblProjectName, this.txtProjectName);
		this.layout.getChildren().addAll(lblAboutTheProject);
		this.layout.getChildren().add(txtAboutTheProject);
		this.layout.getChildren().add(btnAboutTheProject);

		this.btnAboutTheProject.setText("Cancelar ");
		this.btnAboutTheProject.setOnAction(e -> {
			initialLayout();
		});

		this.layout.getChildren().addAll(this.btnInvite);
		this.layout.getChildren().addAll(this.btnFinish);
		this.layout.getChildren().add(this.btnGoBack);
	}

	private void initialLayout() {

		this.layout.getChildren().clear();

		this.btnAboutTheProject.setText("Sobre o projeto..");

		this.btnAboutTheProject.setOnAction(e -> {
			aboutTheProject();
		});

		this.layout.getChildren().addAll(this.lblProjectName, this.txtProjectName, this.btnAboutTheProject);
		this.layout.getChildren().addAll(this.btnInvite);
		this.layout.getChildren().addAll(this.btnFinish);
		this.layout.getChildren().add(this.btnGoBack);

		this.layout.setAlignment(Pos.CENTER);
		this.layout.setSpacing(10);
	}
	
	
	
	


}

