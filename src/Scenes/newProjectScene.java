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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class newProjectScene extends Scene {

	private GridPane layout;

	private HBox hbTopLeftScene;
	private HBox hbCenterScene;
	private HBox hbDownScene;

	/*
	 * this vboxs will stay in the hbox hbTopScene;
	 */
	private VBox vbLabelsProjectNameDescription;
	private VBox vbTxtProjectNameDescription;
	private VBox vbTeamMembers;

	private HBox vbToggleLeft;
	private HBox vbToggleRight;

	private VBox hbToggleGroup;

	private Label lblProjetcName;
	private Label lblProjectDescription;
	private Label lblSelectTeam;
	private TextField txtProjectName;
	private TextArea txtDescription;

	private Button btnExit;
	private Button btnComplete;

	private TextField txt1, txt2, txt3, txt4, txt5, txt6;
	private Label lbl1, lbl2, lbl3, lbl4, lbl5, lbl6;

	private Label title;
	
	private VBox vbTeamLabel;
	private VBox vbTeamTextField;
	private HBox hbTeam;

	/*
	 * used to choose the size of the team
	 */
	private final ToggleGroup group;
	private ToggleButton tb1;
	private ToggleButton tb2;
	private ToggleButton tb3;
	private ToggleButton tb4;
	private ToggleButton tb5;
	private ToggleButton tb6;

	public newProjectScene() {
		super(new HBox());

		this.layout = new GridPane();

		String css = this.getClass().getResource("/cssStyles/style.css").toExternalForm();
		this.getStylesheets().add(css);

		Window.mainStage.setWidth(1000);
		Window.mainStage.setHeight(800);

		this.hbTopLeftScene = new HBox();
		this.hbCenterScene = new HBox();
		this.hbDownScene = new HBox();

		this.title = new Label();
		title.setText("OI PORRA");
		title.setFont(new Font(30));
		title.setTextFill( Color.BLUEVIOLET.brighter());
		title.setAlignment(Pos.CENTER);
		layout.add(title, 0, 5, 1, 1);
		title.setTranslateY(100);
		title.setTranslateX(500);
		
		/*
		 * the name of the members of the project
		 */
		this.txt1 = new TextField();
		this.txt2 = new TextField();
		this.txt3 = new TextField();
		this.txt4 = new TextField();
		this.txt5 = new TextField();
		this.txt6 = new TextField();

		this.lbl1 = new Label("Member Name");
		this.lbl2 = new Label("Member Name");
		this.lbl3 = new Label("Member Name");
		this.lbl4 = new Label("Member Name");
		this.lbl5 = new Label("Member Name");
		this.lbl6 = new Label("Member Name");

		this.vbTeamTextField = new VBox();
		this.vbTeamTextField.setSpacing(10);

		this.vbTeamLabel = new VBox();
		this.vbTeamLabel.setSpacing(20);

		this.hbTeam = new HBox();
		this.hbTeam.setSpacing(5);

		this.hbToggleGroup = new VBox();

		this.vbToggleLeft = new HBox();
		this.vbToggleRight = new HBox();
		/*
		 * 
		 * SELECIONAR TAMANHO DA EQUIPE E FAZER APARECER TEXTFIELDS DE ACORDO COM O
		 * NUMERO SELECIONADO
		 */
		this.group = new ToggleGroup();

		this.tb1 = new ToggleButton("1");
		tb1.setToggleGroup(group);
		tb1.setSelected(false);

		this.tb2 = new ToggleButton("2");
		tb2.setToggleGroup(group);
		tb2.setSelected(false);

		this.tb3 = new ToggleButton("3");
		tb3.setToggleGroup(group);
		tb3.setSelected(false);

		this.tb4 = new ToggleButton("4");
		tb4.setToggleGroup(group);
		tb4.setSelected(false);

		this.tb5 = new ToggleButton("5");
		tb5.setToggleGroup(group);
		tb5.setSelected(false);

		this.tb6 = new ToggleButton("6");
		tb6.setToggleGroup(group);
		tb6.setSelected(false);

		this.tb1.setToggleGroup(group);
		this.tb2.setToggleGroup(group);
		this.tb3.setToggleGroup(group);
		this.tb4.setToggleGroup(group);
		this.tb5.setToggleGroup(group);
		this.tb6.setToggleGroup(group);

		this.vbToggleLeft.getChildren().add(tb1);
		this.vbToggleLeft.getChildren().add(tb2);
		this.vbToggleLeft.getChildren().add(tb3);
		this.vbToggleLeft.setSpacing(10);

		this.vbToggleRight.getChildren().add(tb4);
		this.vbToggleRight.getChildren().add(tb5);
		this.vbToggleRight.getChildren().add(tb6);
		this.vbToggleRight.setSpacing(10);

		this.hbToggleGroup.getChildren().addAll(vbToggleLeft, vbToggleRight);
		this.hbToggleGroup.setSpacing(10);

		/*
		 * will contain 2 label pointing out the text fields
		 */
		this.vbLabelsProjectNameDescription = new VBox();
		/*
		 * this contain the txtfield and textarea where the labels are pointing
		 */
		this.vbTxtProjectNameDescription = new VBox();

		this.lblProjetcName = new Label("Name of the project");
		/*
		 * to set the labels and textfield aligned, i had to move a the label a little
		 */
		this.lblProjetcName.setTranslateY(-70);

		this.lblProjectDescription = new Label("Talk about your project");

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

		this.txtDescription.setTranslateY(20);

		this.vbLabelsProjectNameDescription.setSpacing(20);
		this.vbLabelsProjectNameDescription.setAlignment(Pos.CENTER_LEFT);

		this.vbLabelsProjectNameDescription.getChildren().addAll(lblProjetcName, lblProjectDescription);

		this.vbTxtProjectNameDescription.setSpacing(10);
		this.vbTxtProjectNameDescription.setAlignment(Pos.CENTER_LEFT);

		this.vbTxtProjectNameDescription.getChildren().addAll(txtProjectName, txtDescription);
		

		this.hbTopLeftScene.setPadding(new Insets(150, 0, 0, 50));
		this.hbTopLeftScene.setAlignment(Pos.TOP_LEFT);
		this.hbTopLeftScene.setSpacing(20);
		this.hbTopLeftScene.getChildren().addAll(vbLabelsProjectNameDescription, vbTxtProjectNameDescription);

		this.layout.add(hbTopLeftScene, 0, 0, 1, 1);

		this.vbTeamMembers = new VBox();

		this.lblSelectTeam = new Label("Select the number of members of the team");

		this.vbTeamMembers.setAlignment(Pos.TOP_RIGHT);

		this.vbTeamMembers.setTranslateY(50);
		this.vbTeamMembers.setTranslateX(150);
		this.vbTeamMembers.setSpacing(10);
		
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
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
		
		newProjectScene.this.hbTeam.getChildren().addAll(vbTeamLabel, vbTeamTextField);
		this.vbTeamMembers.getChildren().addAll(lblSelectTeam, hbToggleGroup, hbTeam);
		this.vbTeamMembers.setPadding(new Insets(100, 0, 0, 0));
		this.layout.add(vbTeamMembers, 1, 0, 1, 2);

		
		this.btnExit = new Button("EXIT");

		this.btnExit.setOnAction(actionEvent -> Platform.exit());

		this.btnExit.setPrefWidth(100);
		this.btnExit.setTranslateY(166);
		this.layout.add(btnExit, 0, 8, 3, 2);

		this.setRoot(layout);
	}

	public void removeChildrens() {
		newProjectScene.this.vbTeamTextField.getChildren().removeAll();
		newProjectScene.this.vbTeamLabel.getChildren().removeAll();
		newProjectScene.this.hbTeam.getChildren().removeAll();
	}

	public void selectChildren(int i) {

		if (i == 1) {
			newProjectScene.this.vbTeamLabel.getChildren().add(lbl1);
			newProjectScene.this.vbTeamTextField.getChildren().add(txt1);
			return;
		}
		if (i == 2) {
			newProjectScene.this.vbTeamLabel.getChildren().add(lbl2);
			newProjectScene.this.vbTeamTextField.getChildren().add(txt2);
			return;
		}
		if (i == 3) {
			newProjectScene.this.vbTeamLabel.getChildren().add(lbl3);
			newProjectScene.this.vbTeamTextField.getChildren().add(txt3);
			return;
		}
		if (i == 4) {
			newProjectScene.this.vbTeamLabel.getChildren().add(lbl4);
			newProjectScene.this.vbTeamTextField.getChildren().add(txt4);
			return;
		}
		if (i == 5) {
			newProjectScene.this.vbTeamLabel.getChildren().add(lbl5);
			newProjectScene.this.vbTeamTextField.getChildren().add(txt5);
			return;
		}
		if (i == 6) {
			newProjectScene.this.vbTeamLabel.getChildren().add(lbl6);
			newProjectScene.this.vbTeamTextField.getChildren().add(txt6);
			return;
		}

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
