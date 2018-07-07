package Scenes;
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

import java.sql.SQLException;
import java.util.ArrayList;

import Database.DbCreateProject;
import Main.Window;
import SpecialObjects.CreateHBoxWithTextFields;
import javafx.application.Platform;
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

public class newProjectScene extends Scene {

	private GridPane layout;

	/*
	 * 
	 * this scene have a layout divided in two vertically with two HBox in which
	 * side
	 *
	 * the left HBox contain two labels, one textfield and one textarea
	 * 
	 * and three buttons, "exit" ,"complete" and "back
	 * 
	 */

	/*
	 * 
	 * this components will stay in the left side
	 *
	 */
	private HBox hbLeftSide;

	/*
	 * the labels pointed to the text field and textarea will stay here
	 */
	private VBox vbLabels;

	private Label lblProjetcName;
	private Label lblProjectDescription;

	/*
	 * textfield and textarea here
	 */
	private VBox vbTextfields;

	private TextField txtProjectName;
	private TextArea txtDescription;
	/*
	 * that is the component will keep the two VBox and going to stay on the hbox of
	 * the left side
	 */
	private HBox hbLeftContainer;
	/*
	 * 
	 * 
	 */

	/*
	 * the right side
	 */
	private HBox hbRightSide;

	/*
	 * every thing will stay here, i did this because was the best way that i found
	 * for making easy some future change in the scene
	 */
	private VBox vbRightContainer;

	/*
	 * the vbox upward going to stay here
	 */

	/*
	 * over the container will stay this label informed for what the textfield is
	 * used for
	 */

	private Label lblSelectTeam;
	/*
	 * the list for the hbox that contain the text fields for the name of memebers
	 */

	private ArrayList<String> listMembers;
	private ArrayList<CreateHBoxWithTextFields> listHBmembers;
	/*
	 * the hbox(s) that are instantiante when some number are typed in the
	 * txtNumberMembers
	 */
	private VBox vbMembers;

	/*
	 * 
	 */
	private TextField txtNumberMembers;
	private Button btnNumberMembers;

	private HBox hbNumberMembers;
	/*
	 */

	private HBox hbButtons;
	private Button btnExit;
	private Button btnComplete;
	private Button btnBack;

	private ScrollPane painel;

	public newProjectScene() {
		super(new HBox());

		this.painel = new ScrollPane();

		this.listHBmembers = new ArrayList<CreateHBoxWithTextFields>();
		this.listMembers = new ArrayList<String>();

		this.layout = new GridPane();

		String css = this.getClass().getResource("/cssStyles/style.css").toExternalForm();
		this.getStylesheets().add(css);

		Window.mainStage.setWidth(900);
		Window.mainStage.setHeight(600);
		Window.mainStage.setTitle("New Project");

		/*
		 * 
		 * left
		 * 
		 */
		this.hbLeftContainer = new HBox();
		this.hbLeftContainer.setFillHeight(true);
		this.hbLeftContainer.setPrefHeight(400);

		this.hbLeftSide = new HBox();

		this.vbLabels = new VBox();

		this.lblProjetcName = new Label("Name of the project");
		/*
		 * to line up with the textfields
		 */
		this.lblProjetcName.setTranslateY(-70);

		this.lblProjectDescription = new Label("Talk about your project");

		this.vbLabels.setSpacing(20);

		/*
		 * to line up with the textfields
		 */
		this.vbLabels.setTranslateY(120);

		this.vbLabels.getChildren().addAll(lblProjetcName, lblProjectDescription);

		this.vbTextfields = new VBox();

		this.txtProjectName = new TextField();

		this.txtProjectName.setPrefColumnCount(20);

		this.txtDescription = new TextArea();
		this.txtDescription.setPrefColumnCount(10);
		this.txtDescription.setPrefRowCount(10);
		/*
		 * if a text exceeds the width of the textarea makes the text go to onto another
		 * line
		 */
		this.txtDescription.setWrapText(true);

		/*
		 * to line up with the labels
		 */
		this.vbTextfields.setSpacing(50);

		this.vbTextfields.setTranslateY(50);

		this.vbTextfields.getChildren().addAll(txtProjectName, txtDescription);

		this.hbLeftContainer.getChildren().addAll(vbLabels, vbTextfields);

		/*
		 * setting up the left side in the layout
		 */
		this.hbLeftSide.setPadding(new Insets(0, 0, 0, 0));

		this.hbLeftSide.getChildren().addAll(hbLeftContainer);

		this.hbLeftSide.setFillHeight(true);
		this.hbLeftSide.prefHeight(500);
		/*
		 * to start in the right position
		 */
		this.hbLeftSide.setTranslateX(50);
		this.hbLeftSide.setTranslateY(50);

		// hbTopLeftScene.getStyleClass().add("hbox");
		// this.hbTopLeftScene.setId("hboxLeftSide");

		this.vbMembers = new VBox();
		this.painel.setContent(this.vbMembers);
		painel.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		painel.setPrefSize(120, 400);

		this.layout.add(hbLeftSide, 0, 0, 1, 1);

		/*
		 * 
		 * right side
		 * 
		 */
		this.hbRightSide = new HBox();

		// hbRightSide.getStyleClass().add("hbox");

		this.hbRightSide.setFillHeight(true);
		hbRightSide.prefHeight(100);

		this.vbRightContainer = new VBox();

		// vbRightContainer.getStyleClass().add("vbox");
		// this.vbRightContainer.setId("rightContainer");

		this.vbRightContainer.setFillWidth(true);
		this.vbRightContainer.setPrefWidth(350);

		/*
		 * contain vboxs with labels and textfields
		 */

		// this.vbMembers.setStyle(css);

		// vbMembers.getStyleClass().add("vbox");
		// this.vbMembers.setId("teamMembers");

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

		this.btnExit.setOnAction(actionEvent -> Platform.exit());

		this.btnComplete = new Button("COMPLETE");

		this.btnBack = new Button("BACK");

		this.btnBack.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					Window.mainStage.setScene(new HomePageScene());

				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		this.btnExit.setId("exitbtn");

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
					// TODO Auto-generated catch block
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
			CreateHBoxWithTextFields t = new CreateHBoxWithTextFields("Member " + j);

			this.listHBmembers.add(t);

			this.vbMembers.getChildren().add(listHBmembers.get(i));

			j++;
		}
	}

}
