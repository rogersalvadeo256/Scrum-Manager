package scenes.scenes;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import application.main.Window;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import scenes.popoups.StandartLayoutPOPOUP;

public class NewProjectScene extends StandartLayoutPOPOUP {
	private Label lblProjectName, lblDescProject;
	private TextField txtProjectName;
	private TextArea txtDescProject;
	/*
	 * btnCancel - close the window and delete everthing
	 * btnGoBack - serialization of the defined things - if the user try to create another project, show a message
	 */
	private Button btnFinish,btnCancel,btnInvite,btnGoBack;
	private HBox hbButtons;
	private HBox hbHeader;
	
	public NewProjectScene(javafx.stage.Window owner) throws FileNotFoundException {
		super(owner);
		
		this.lblProjectName = new Label("Nome do Projeto");
		this.lblDescProject = new Label("Descrição do Projeto");
		this.txtProjectName = new TextField();
		this.txtDescProject = new TextArea();
		this.txtDescProject.setId("descProject");
		this.txtDescProject.setId("txtDescProject");
		this.hbButtons = new HBox();
		
		this.scene.getStylesheets().add(this.getClass().getResource("/css/NEW_PROJECT.css").toExternalForm()); 
		
		this.txtProjectName.setMaxWidth(300);
		this.txtDescProject.setMaxWidth(300);
		this.txtDescProject.setPrefRowCount(10);
		this.txtDescProject.setWrapText(true);
		
		int SIZE  = 30;
		ImageView buttonImage = new  ImageView(new Image(new FileInputStream(new File("resources/images/icons/back_arrow_icon.png"))));
		buttonImage.setFitWidth(SIZE);
		buttonImage.setFitHeight(SIZE);
		
		this.btnGoBack = new Button();
		this.btnGoBack.setGraphic(buttonImage);
		this.btnGoBack.setMaxWidth(100);
		this.btnGoBack.setMaxHeight(100);
		this.hbHeader = new  HBox();
		this.hbHeader.getChildren().add(this.btnGoBack);
		this.hbHeader.prefWidth(this.getWidth());
		this.hbHeader.setAlignment(Pos.CENTER_LEFT);
		
		this.txtProjectName.setAlignment(Pos.CENTER);
		this.btnFinish = new Button("Salvar");
		this.btnFinish.setId("btnSalve");
		
		this.btnInvite = new Button("Convidar amigos");
		this.btnInvite.setId("btnInvite");
		this.btnCancel = new Button("Cancelar");
		this.btnCancel.setId("btnCancel");
		
		hbButtons.getChildren().addAll(btnFinish,btnCancel);
		hbButtons.setSpacing(10);
		hbButtons.setAlignment(Pos.CENTER);
				
		
		this.layout.setAlignment(Pos.CENTER);
		this.layout.setSpacing(10);
		
		this.layout.getChildren().addAll(this.hbHeader);
		this.layout.getChildren().addAll(this.lblProjectName, this.txtProjectName);
		this.layout.getChildren().addAll(this.lblDescProject, this.txtDescProject);
		this.layout.getChildren().addAll(this.btnInvite);
		this.layout.getChildren().addAll(this.hbButtons);
	}
	
	public void setEventInvite(EventHandler<ActionEvent> e) { 
		this.btnInvite.setOnAction(e);
	}
	
	
	
	
	
	
}


















