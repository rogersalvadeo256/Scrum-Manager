package view.scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import widgets.designComponents.projectContents.ScrumFrame;

public class ProjectScene extends Scene{ 
	
	
	private Button btnSprints, btnTeam;
	private VBox vLeft;
	
	private VBox content;
	private HBox layout;
	
	private VBox vMiddle;
	private ScrumFrame frame;
	
	private VBox vRight;
	private Button btnLeaveProject, btnBack;
	
	
	private AnchorPane anchor;
	public ProjectScene () { 
		super(new AnchorPane());
		
		this.getStylesheets().add(this.getClass().getResource("/css/PROJECT_SCENE.css").toExternalForm());
		
		this.content = new VBox();
		this.layout=new HBox();
		content.setAlignment(Pos.CENTER);
		layout.setAlignment(Pos.CENTER);
		
		
		
		content.getChildren().add(layout);
		

		
		this.btnSprints=new Button("ver sprints anteriores");
		this.btnTeam=new Button("equipe");
	
		this.vLeft = new VBox();
		
		Text name = new Text();
		
		name.setWrappingWidth(vLeft.getMinWidth());
		name.setText("aslfkjalfjkaslfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaakajflaskjkjgkdkdkdkdkdkdkdkkdkdkdkdkkAALFJSDLAKJL");
		
		vLeft.getChildren().addAll(name, btnSprints, btnTeam);
		vLeft.setAlignment(Pos.CENTER_LEFT);
		vLeft.setSpacing(50);
		layout.getChildren().add(vLeft);
		
		
		this.vMiddle=new VBox();
		this.frame=new ScrumFrame();
		
		vMiddle.setPrefHeight(vLeft.heightProperty().get());
		
		vMiddle.getChildren().add(frame);
		
		layout.getChildren().add(vMiddle);
		
		this.vRight = new VBox();
		this.btnBack = new Button("Voltar");
		this.btnLeaveProject= new Button("Abandonar projeto");
		
		vRight.getChildren().addAll(btnBack,btnLeaveProject);
		vRight.setAlignment(Pos.CENTER_LEFT);
		vRight.setSpacing(50);

		layout.getChildren().add(vRight);
		
		
		AnchorPane.setBottomAnchor(content, 0d);
		AnchorPane.setTopAnchor(content, 0d);
		AnchorPane.setRightAnchor(content, 0d);
		AnchorPane.setLeftAnchor(content, 0d);
		
		this.anchor  = new AnchorPane();
		
		anchor.getChildren().add(content);
		
		this.setRoot(anchor);
		
		
	}
	
}









