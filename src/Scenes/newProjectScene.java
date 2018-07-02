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

import Main.Window;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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
	 * and two buttons, "exit" and "complete"
	 * 
	 * 
	 * the right contain a toggle group with some toggle buttons that, when ate
	 * press show a textfield and labels
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
	 * the right side
	 */
	private HBox hbRightSide;

	/*
	 * every thing will stay here, i did this because was the best way that i found
	 * for making easy some future change in the scene
	 */
	private VBox vbRightContainer;

	/*
	 * contain 6 labels and 6 textfields, and going to show them just with the user
	 * press some toggle button
	 */
	private VBox vbMembersLabel;
	private VBox vbMembersTextField;

	/*
	 * the vbox upward going to stay here
	 */
	private HBox hbMembers;

	private TextField member1, member2, member3, member4, member5, member6;
	private Label lbl1, lbl2, lbl3, lbl4, lbl5, lbl6;

	/*
	 * there is two lines with 3 toggle button in each one of them 3 toggle buttons
	 * will stay in each hbox of this
	 */
	private HBox hbToggleUp;
	private HBox hbToggleDown;

	/*
	 * the two hbox stay in this vbox
	 */
	private VBox vbToggleGroup;

	/*
	 * over the container will stay this label informed what the toggle buttons do
	 */
	private Label lblSelectTeam;

	private ToggleGroup toggleButtonsGroup;

	private ToggleButton tb1;
	private ToggleButton tb2;
	private ToggleButton tb3;
	private ToggleButton tb4;
	private ToggleButton tb5;
	private ToggleButton tb6;

	/*
	 * the buttons
	 */
	private HBox hbButtons;
	private Button btnExit;
	private Button btnComplete;

	/*
	 * used to choose the size of the team
	 */

	public newProjectScene() {
		super(new HBox());

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

		/*
		 * labels
		 */
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

		/*
		 * textfields
		 */
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

		this.layout.add(hbLeftSide, 0, 0, 1, 1);

		/*
		 * right
		 */
		this.hbRightSide = new HBox();

		this.vbRightContainer = new VBox();

		this.vbRightContainer.setFillWidth(true);
		this.vbRightContainer.setPrefWidth(350);

		/*
		 * the hboxs and the vbox fot the toggle buttons group
		 */
		this.vbToggleGroup = new VBox();

		this.hbToggleUp = new HBox();

		this.hbToggleDown = new HBox();

		this.toggleButtonsGroup = new ToggleGroup();

		this.tb1 = new ToggleButton("1");
		/*
		 * no button will be selected when the scene start
		 */
		tb1.setSelected(false);

		this.tb2 = new ToggleButton("2");
		tb2.setSelected(false);

		this.tb3 = new ToggleButton("3");
		tb3.setSelected(false);

		this.tb4 = new ToggleButton("4");
		tb4.setSelected(false);

		this.tb5 = new ToggleButton("5");
		tb5.setSelected(false);

		this.tb6 = new ToggleButton("6");
		tb6.setSelected(false);

		/*
		 * setting up the buttons in a toggle group
		 */
		this.tb1.setToggleGroup(toggleButtonsGroup);
		this.tb2.setToggleGroup(toggleButtonsGroup);
		this.tb3.setToggleGroup(toggleButtonsGroup);
		this.tb4.setToggleGroup(toggleButtonsGroup);
		this.tb5.setToggleGroup(toggleButtonsGroup);
		this.tb6.setToggleGroup(toggleButtonsGroup);

		this.hbToggleUp.getChildren().add(tb1);
		this.hbToggleUp.getChildren().add(tb2);
		this.hbToggleUp.getChildren().add(tb3);
		this.hbToggleUp.setSpacing(10);

		this.hbToggleDown.getChildren().add(tb4);
		this.hbToggleDown.getChildren().add(tb5);
		this.hbToggleDown.getChildren().add(tb6);
		this.hbToggleDown.setSpacing(10);

		this.vbToggleGroup.getChildren().addAll(hbToggleUp, hbToggleDown);
		this.vbToggleGroup.setFillWidth(true);
		this.vbToggleGroup.setSpacing(10);
		this.vbToggleGroup.setMaxWidth(Double.MAX_VALUE);
		this.vbToggleGroup.setTranslateX(70);

		/*
		 * ending with the toggle stuffs
		 */

		/*
		 * the text fields and labels that are shown when the buttons are clicked
		 * 
		 */

		/*
		 * contain vboxs with labels and textfields
		 */
		this.hbMembers = new HBox();

		/*
		 * the name of the members of the project
		 */
		
		this.member1 = new TextField();
		this.member2 = new TextField();
		this.member3 = new TextField();
		this.member4 = new TextField();
		this.member5 = new TextField();
		this.member6 = new TextField();

		this.lbl1 = new Label("Member Name");
		this.lbl2 = new Label("Member Name");
		this.lbl3 = new Label("Member Name");
		this.lbl4 = new Label("Member Name");
		this.lbl5 = new Label("Member Name");
		this.lbl6 = new Label("Member Name");
		this.vbMembersTextField = new VBox();
		this.vbMembersTextField.setSpacing(10);

		this.vbMembersLabel = new VBox();
		this.vbMembersLabel.setSpacing(20);
		this.vbMembersLabel.setMaxWidth(Double.MAX_VALUE);


		this.lblSelectTeam = new Label("Select the number of  members of the team");

		
		this.hbMembers.getChildren().addAll(vbMembersLabel, vbMembersTextField);
		this.hbMembers.setSpacing(10);
		

		toggleButtonsGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) {

				if (toggle.equals(newProjectScene.this.tb1)) {
					selectChildren(1);
				}
				if (toggle.equals(newProjectScene.this.tb2)) {
					selectChildren(2);
				}
				if (toggle.equals(newProjectScene.this.tb3)) {
					selectChildren(3);
				}
				if (toggle.equals(newProjectScene.this.tb4)) {
					selectChildren(4);
				}
				if (toggle.equals(newProjectScene.this.tb5)) {
					selectChildren(5);
				}
				if (toggle.equals(newProjectScene.this.tb6)) {
					selectChildren(6);
				}
			}
		});


		this.vbRightContainer.getChildren().addAll(lblSelectTeam, vbToggleGroup, hbMembers);
		this.vbRightContainer.setSpacing(10);

		this.hbRightSide.getChildren().add(vbRightContainer);
		this.hbRightSide.setFillHeight(true);
		this.hbRightSide.setPrefHeight(450);
		this.hbRightSide.setAlignment(Pos.TOP_RIGHT);

		this.hbRightSide.setTranslateY(50);

		this.hbRightSide.setTranslateX(100);

		// hbTopRightScene.getStyleClass().add("hbox");
		// this.hbTopRightScene.setId("hboxRightSide");

		this.layout.add(hbRightSide, 1, 0, 1, 1);

		this.hbButtons = new HBox();

		this.btnExit = new Button("EXIT");

		this.btnExit.setOnAction(actionEvent -> Platform.exit());

		this.btnComplete = new Button("COMPLETE");

		this.btnExit.setId("exitbtn");

		this.hbButtons.getChildren().addAll(btnExit, btnComplete);

		this.hbButtons.setSpacing(10);
		this.layout.add(hbButtons, 0, 2, 1, 1);

		this.setRoot(layout);
	}

	public void removeChildrens() {
		newProjectScene.this.vbMembersTextField.getChildren().removeAll();
		newProjectScene.this.vbMembersLabel.getChildren().removeAll();
		newProjectScene.this.hbMembers.getChildren().removeAll();
	}

	public void selectChildren(int i) {

		if (i == 1) {
			newProjectScene.this.vbMembersLabel.getChildren().add(lbl1);
			newProjectScene.this.vbMembersTextField.getChildren().add(member1);
			return;
		}
		if (i == 2) {
			newProjectScene.this.vbMembersLabel.getChildren().add(lbl2);
			newProjectScene.this.vbMembersTextField.getChildren().add(member2);
			return;
		}
		if (i == 3) {
			newProjectScene.this.vbMembersLabel.getChildren().add(lbl3);
			newProjectScene.this.vbMembersTextField.getChildren().add(member3);
			return;
		}
		if (i == 4) {
			newProjectScene.this.vbMembersLabel.getChildren().add(lbl4);
			newProjectScene.this.vbMembersTextField.getChildren().add(member4);
			return;
		}
		if (i == 5) {
			newProjectScene.this.vbMembersLabel.getChildren().add(lbl5);
			newProjectScene.this.vbMembersTextField.getChildren().add(member5);
			return;
		}
		if (i == 6) {
			newProjectScene.this.vbMembersLabel.getChildren().add(lbl6);
			newProjectScene.this.vbMembersTextField.getChildren().add(member6);
			return;
		}
	}

}
