package Scenes;
/*
 * LoginScreen.java
 * 
 * Created on: 30 jun de 2018
 * 		Autor: jefter66
 * 
 */


import Main.Window;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class newProjectScene extends Scene {

	private GridPane layout;
	
	private HBox hbTopScene;
	private HBox hbCenterScene;
	private HBox hbDownScene;

	private VBox vbProjectNameDescription;
	private VBox vbTeamMembers;

	private Button btnExit;
	private Button btnComplete;
	
	
	
	public newProjectScene() {
		super(new HBox());

		this.layout = new GridPane();

		String css = this.getClass().getResource("/cssStyles/style.css").toExternalForm();
		this.getStylesheets().add(css);
	
		Window.mainStage.setWidth(600);
		Window.mainStage.setHeight(600);
		
		this.hbTopScene = new HBox();
		this.hbCenterScene = new HBox();
		this.hbDownScene = new  HBox();
		
		this.vbProjectNameDescription = new VBox();
		this.vbTeamMembers = new VBox();
		
		this.layout.add(hbTopScene, 0, 0, 3, 2);
		
		
		
		
		this.btnExit = new Button("EXIT");
		this.btnExit.setMaxSize(800, 800);
		this.hbTopScene.getChildren().add(btnExit);
		
		this.btnExit.setOnAction(actionEvent -> Platform.exit());
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		this.setRoot(layout);
	}

}
