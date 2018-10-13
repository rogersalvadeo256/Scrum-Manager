package view.popoups;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import application.controllers.NewProjectSceneController;
import friendship.QUERYs_FRIENDSHIP;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import validation.CheckEmptyFields;
import widgets.designComponents.projectContents.HBColumnsInvite;
import widgets.toaster.Toast;

public class NewProjectPOPOUP extends StandartLayoutPOPOUP {
	private Label lblProjectName, lblAboutTheProject;
	private TextField txtProjectName;
	private TextArea txtAboutTheProject;
	
	private NewProjectSceneController controller;
	
	private Button btnFinish, btnInvite, btnGoBack, btnAboutTheProject;
	
	private HBox layoutForInviteComponents;
	private Toast toast;
	
	public NewProjectPOPOUP(Stage mainStage) throws IOException {
		super(mainStage);
		
		this.initStyle(StageStyle.DECORATED);
		
		this.setWidth(500);
		this.setHeight(500);
		
		this.controller = new NewProjectSceneController(this);
		
		this.lblProjectName = new Label("Nome do Projeto");
		this.lblAboutTheProject = new Label("Descrição do Projeto");
		this.txtProjectName = new TextField();
		this.txtAboutTheProject = new TextArea();
		this.txtAboutTheProject.setId("descProject");
		this.txtAboutTheProject.setId("txtDescProject");
		
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
		
//		int SIZE = 30;
//		ImageView buttonImage = new ImageView(new Image(new FileInputStream(new File("resources/images/icons/back_arrow_icon.png"))));
//		buttonImage.setFitWidth(SIZE);
//		buttonImage.setFitHeight(SIZE);
		
		this.btnGoBack = new Button("Cancelar");
		this.btnGoBack.setId("back");
//		this.btnGoBack.setGraphic(buttonImage);
		
		this.txtProjectName.setAlignment(Pos.CENTER);
		
		this.btnGoBack.setOnAction(e -> {
			controller.actionBack();
		});
		
		this.btnFinish.setOnAction(e -> {
			
			CheckEmptyFields cef = new CheckEmptyFields();
			
			if (!cef.isTextFieldEmpty(txtProjectName)) {
				this.controller.actionFinish(txtProjectName.getText(), txtAboutTheProject.getText());
			}
		});
		this.btnInvite.setOnAction(e -> {
			
			if (QUERYs_FRIENDSHIP.friendsList().isEmpty()) {
				this.toast = new Toast(this, "Voce não tem amigos");
				this.scene.setOnMouseMoved(e1 -> {
					toast.close();
					return;
				});
				return;
			}
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
		
		if (t)
			layoutForInviteComponents = new HBox();
		
		boolean isTheVBOX = this.scene.getRoot() == layout ? true : false;
		
		if (isTheVBOX) {
			layoutForInviteComponents.getChildren().add(layout);
			this.scene.setRoot(layoutForInviteComponents);
			this.btnInvite.setText("Finalizar");
			
			
			
			HBColumnsInvite component = new HBColumnsInvite();
			
			this.layoutForInviteComponents.getChildren().add(component);
			
			this.sizeToScene();
			
			return;
		}
		boolean isTheHBOX = this.scene.getRoot() == layoutForInviteComponents ? true : false;
		if (isTheHBOX) {
			layoutForInviteComponents.getChildren().clear();
			this.scene.setRoot(layout);
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
		this.sizeToScene();
		
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

