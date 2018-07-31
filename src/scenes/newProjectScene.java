package scenes;
/*
 * newProjectScene.java
 * 
 * Created on: 30 jun de 2018
 * 		Autor: jefter66
 * 
 * this scene is a form for start a new project, for now, will be needed just a name,
 *  a brief description of what the project is and the number of people involved
 * 
 */

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import Database.Obsoleto.DbCreateProject;
import auto.instance.objects.HBoxWithTextFields;
import events.ExitButtonListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Window;

public class newProjectScene extends Scene {

	private GridPane layout;
	private HBox hbLeftSide;
	private VBox vbLabels;
	private VBox vbTextfields;
	private HBox hbLeftContainer;
	private HBox hbRightSide;
	private VBox vbRightContainer;
	private VBox vbMembers;
	private HBox hbNumberMembers;
	private HBox hbButtons;


	private TextField txtProjectName;
	private TextArea txtDescription;
	private TextField txtNumberMembers;

	private Label lblProjetcName;
	private Label lblProjectDescription;
	private Label lblSelectTeam;

	private ArrayList<String> listMembers;
	private ArrayList<HBoxWithTextFields> listHBmembers;

	private Button btnNumberMembers;
	private Button btnExit;
	private Button btnComplete;
	private Button btnBack;

	private ScrollPane painel;

	public newProjectScene() {
		super(new HBox());
		Window.mainStage.setWidth(900);
		Window.mainStage.setHeight(600);
		Window.mainStage.setTitle("New Project");
		this.painel = new ScrollPane();
		this.listHBmembers = new ArrayList<HBoxWithTextFields>();
		this.listMembers = new ArrayList<String>();
		this.layout = new GridPane();
		String css = this.getClass().getResource("/cssStyles/style.css").toExternalForm();
		this.getStylesheets().add(css);
		this.hbLeftContainer = new HBox();
		this.hbLeftContainer.setFillHeight(true);
		this.hbLeftContainer.setPrefHeight(400);
		this.hbLeftSide = new HBox();
		this.vbLabels = new VBox();
		this.lblProjetcName = new Label("Name of the project");
		this.lblProjetcName.setTranslateY(-70);
		this.lblProjectDescription = new Label("Talk about your project");
		this.vbLabels.setSpacing(20);
		this.vbLabels.setTranslateY(120);

		this.vbLabels.getChildren().addAll(lblProjetcName, lblProjectDescription);

		this.vbTextfields = new VBox();
		this.txtProjectName = new TextField();
		this.txtProjectName.setPrefColumnCount(20);
		this.txtDescription = new TextArea();
		this.txtDescription.setPrefColumnCount(10);
		this.txtDescription.setPrefRowCount(10);
		this.txtDescription.setWrapText(true);
		this.vbTextfields.setSpacing(50);
		this.vbTextfields.setTranslateY(50);

		this.vbTextfields.getChildren().addAll(txtProjectName, txtDescription);
		this.hbLeftContainer.getChildren().addAll(vbLabels, vbTextfields);

		this.hbLeftSide.setPadding(new Insets(0, 0, 0, 0));
		this.hbLeftSide.getChildren().addAll(hbLeftContainer);
		this.hbLeftSide.setFillHeight(true);
		this.hbLeftSide.prefHeight(500);
		this.hbLeftSide.setTranslateX(50);
		this.hbLeftSide.setTranslateY(50);

		this.vbMembers = new VBox();
		this.painel.setContent(this.vbMembers);
		this.painel.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		this.painel.setPrefSize(120, 400);

		this.layout.add(hbLeftSide, 0, 0, 1, 1);

		this.hbRightSide = new HBox();
		this.hbRightSide.setFillHeight(true);
		this.hbRightSide.prefHeight(100);
		this.vbRightContainer = new VBox();
		this.vbRightContainer.setFillWidth(true);
		this.vbRightContainer.setPrefWidth(350);
		this.vbMembers.setSpacing(20);

		this.btnNumberMembers = new Button();
		this.txtNumberMembers = new TextField();
		this.hbNumberMembers = new HBox();

		this.hbNumberMembers.getChildren().addAll(txtNumberMembers, btnNumberMembers);

		this.hbNumberMembers.setSpacing(10);
		this.hbNumberMembers.setAlignment(Pos.CENTER);
		this.hbNumberMembers.setSpacing(10);
		this.hbNumberMembers.setPrefHeight(5);

		txtNumberMembers.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {

				if (event.getCode() == KeyCode.ENTER) {

					Integer txtfields = Integer.parseInt(txtNumberMembers.getText());
					drawMembersTextFields(txtfields.intValue());
				}
			}
		});
		this.btnNumberMembers.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Integer txtfields = Integer.parseInt(txtNumberMembers.getText());
				drawMembersTextFields(txtfields.intValue());
			}
		});
		this.lblSelectTeam = new Label("Select the number of  members in the team");
		this.vbRightContainer.getChildren().addAll(lblSelectTeam, hbNumberMembers, painel);
		this.vbRightContainer.setSpacing(20);
		this.hbRightSide.getChildren().add(vbRightContainer);
		this.hbRightSide.setFillHeight(true);
		this.hbRightSide.setPrefHeight(450);
		this.hbRightSide.setAlignment(Pos.TOP_RIGHT);
		this.hbRightSide.setTranslateY(50);
		this.hbRightSide.setTranslateX(100);

		this.layout.add(hbRightSide, 1, 0, 1, 1);

		this.hbButtons = new HBox();
		this.btnExit = new Button("EXIT");
		this.btnExit.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				ExitButtonListener exit = new ExitButtonListener(){};				
				exit.handle(event);
			}});
		this.btnExit.setId("exitbtn");
		this.btnBack = new Button("BACK");
		this.btnBack.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Window.mainStage.setScene(new HomePageScene());
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		this.btnComplete = new Button("COMPLETE");
		this.btnComplete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					newProjectScene.this.listMembers.clear();
					for (int i = 0; i < listHBmembers.size(); i++) {
						newProjectScene.this.listMembers.add(newProjectScene.this.listHBmembers.get(i).getText());
					}
					DbCreateProject a = new DbCreateProject();
					a.createProject(newProjectScene.this.txtProjectName.getText(),	newProjectScene.this.txtDescription.getText(), listMembers);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
		this.hbButtons.getChildren().addAll(btnExit, btnComplete, btnBack);
		this.hbButtons.setSpacing(10);

		this.layout.add(hbButtons, 0, 2, 1, 1);
		this.setRoot(layout);
	}
	/*
	 * Created on 02 Jul
	 * 
	 * 
	 * Autor: André Furlan
	 * 
	 */
	private void drawMembersTextFields(int amount) {
		/*
		 * remove all the text fields on the vbox
		 */
		this.vbMembers.getChildren().clear();
		this.listHBmembers.clear();
		/*
		 * the parameter are the value typed on a textfield, referring to the number of
		 * members on the project, and will bring the same numbers of textfields
		 */
		int j = 1;
		for (int i = 0; i < amount; i++) {
			/*
			 * my class extends Hbox and have just a label and a text field
			 */
			HBoxWithTextFields t = new HBoxWithTextFields("Member " + j);
			this.listHBmembers.add(t);
			this.vbMembers.getChildren().add(listHBmembers.get(i));
			j++;
		}
	}
}
