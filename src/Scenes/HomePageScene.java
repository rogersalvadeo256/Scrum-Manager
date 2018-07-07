package Scenes;

/*
 * LoginScreen.java
 * 
 * Created on: 27 jun de 2018
 * 		Autor: jefter66
 * 
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;


import Database.DbLoadProfileHome;
import Main.Window;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class HomePageScene extends Scene {

	private GridPane layout;

	private HBox hbTop;
	private HBox hbDown;

	private VBox columnLeft;
	private VBox columnCenter;
	private VBox columnRight;

	private Button btnExit;
	private Button btnEditProfile;

	private VBox vbBtnOptions;
	private VBox vbNameUser;

	private Label lblName;
	private Label lblUserName;

	private ImageView imagem;

	
	private Label lblNewProject;
	private Label lblCurrentProject;
	private Label lblProjectsDone;
	
	private DbLoadProfileHome e;
	
	/*
	 * the construct of the class requires a object LoadProfileHome, this object
	 * will be used to bring the data from de database and load them on the profile
	 * of the user, this data include the projects, name, photo etc...
	 */
	public HomePageScene() throws ClassNotFoundException, SQLException {
		super(new HBox());
		this.e = new DbLoadProfileHome();
		e.bringUser();

		
		/*
		 * the css
		 */
		String css = this.getClass().getResource("/cssStyles/homeScene.css").toExternalForm();
		this.getStylesheets().add(css);

		Window.mainStage.setTitle("Home");
		Window.mainStage.setWidth(800);
		Window.mainStage.setHeight(600);

		/*
		 * the main layout will be a gridpane who will contain a lot of hbox and vbox
		 */
		this.layout = new GridPane();

		/*
		 * this image will contain a profile image of the user the size of the image is
		 * setting here
		 */
		this.imagem = new ImageView();
		this.imagem.setFitWidth(150);
		this.imagem.setFitHeight(150);

		this.btnExit = new Button("EXIT");
		this.btnExit.setOnAction(actionEvent -> Platform.exit());
		this.btnEditProfile = new Button("EDIT PROFILE");

		/*
		 * this vbox will have just two buttons and going to stay in the top right of
		 * the scene
		 */
		this.vbBtnOptions = new VBox();
//		this.vbBtnOptions.getStyleClass().add("vbox");

		this.vbBtnOptions.getChildren().addAll(btnEditProfile, btnExit);
		this.vbBtnOptions.setSpacing(10);
		this.vbBtnOptions.setPrefWidth(220);
		this.vbBtnOptions.setAlignment(Pos.CENTER);
		this.vbBtnOptions.setTranslateX(90);

		/*
		 * setting the vbox in the right
		 */

		this.lblName = new Label();
		this.lblName.setText(DbLoadProfileHome.User.getName());
		this.lblName.setFont(new Font(25));
	
		this.lblUserName = new Label();
		this.lblUserName.setText(DbLoadProfileHome.User.getUser());
		this.lblUserName.setFont(new Font(15));

		/*
		 * this vbox have will stay on the lef top of the scene contain the image, name
		 * and username of the user
		 */
		this.vbNameUser = new VBox();
		this.vbNameUser.setPadding(new Insets(10));
		this.vbNameUser.getStyleClass().add("vbox");
		this.vbNameUser.getChildren().addAll(lblName, lblUserName);
		this.vbNameUser.setPrefWidth(300);

		
		/*
		 * this hbox will contain all the vbox above going to stay on the top of the
		 * scene, defined in the gridpane
		 */
		this.hbTop = new HBox();
//		this.hbTop.getStyleClass().add("hbox");

		this.layout.add(hbTop, 0, 0, 1, 1);
		/*
		 * i put the alignment in the top left because i couldn't setting the image
		 * above the left column with another way
		 */

		this.hbTop.setPrefWidth(800);
		this.hbTop.setPrefHeight(200);

		this.hbTop.setSpacing(10);

		/*
		 * setting the things in the top part of the layout
		 */
		
		imagem.setTranslateX(10);
		this.hbTop.getChildren().addAll(imagem, vbNameUser, vbBtnOptions);

		/*
		 * the idea is making three columns below the image and the name of the user and
		 * the buttons exit and edit profile in the first column starting from the left,
		 * will contain a Vbox who will lead to other scene, for start new project and
		 * etc the middle column, like the previous one, has a vbox with his stuff, this
		 * column are from the projects in progress the last column, in the right, has
		 * the project finished
		 */

		this.hbDown = new HBox();
		this.hbDown.setPrefWidth(800);
		this.hbDown.setPrefHeight(500);

		this.hbDown.getStyleClass().add("hbox");
		this.hbDown.setId("hbDown");

		/*
		 * is on the same column that the top hbox, but one line below
		 */
		this.layout.add(hbDown, 0, 2, 1, 1);

		// FileChooser fc = new FileChooser();
		// File f = fc.showOpenDialog(Window.janela);
		File f = new File("/home/jefter66/Área de Trabalho/35401559_1839819186083425_5919617720290115584_n.jpg");

		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			this.imagem.setImage(new Image(fis));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * instantiate the columns
		 */
		this.columnCenter = new VBox();
		this.columnCenter.getStyleClass().add("vbox");
		this.columnCenter.setId("columnCenter");
		this.columnCenter.setPrefWidth(400);

		this.columnLeft = new VBox();
		this.columnLeft.getStyleClass().add("vbox");
		this.columnLeft.setId("columnLeft");
		this.columnLeft.setPrefWidth(300);

		this.columnRight = new VBox();
		this.columnRight.getStyleClass().add("vbox");
		this.columnRight.setId("columnRight");
		this.columnRight.setPrefWidth(400);
		/*
		 * css stuff
		 */
		this.btnExit.setId("exitbtn");

		/*
		 * the left column
		 */
		this.lblNewProject = new Label("NEW PROJECT");
		this.lblNewProject.setTextFill(Color.WHITE);
		this.lblNewProject.setFont(new Font(20));
		this.lblNewProject.setAlignment(Pos.CENTER);
		this.columnLeft.setAlignment(Pos.CENTER);
		this.columnLeft.getChildren().addAll(lblNewProject);
		/*
		 * if this vbox be clicked a form for create a new project will be called an the
		 * scene will be changed
		 */
		this.columnLeft.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				/*
				 * the scene newProjectScene is a form where the user will start a new project
				 */
				Window.mainStage.setScene(new newProjectScene());

			}
		});

		this.lblCurrentProject = new Label("CURRENT PROJECTS");
		this.lblCurrentProject.setFont(new Font(20));
		
		this.lblProjectsDone = new Label("DONE");
		this.lblProjectsDone.setFont(new Font(20));	
		
		this.lblCurrentProject.setId("lblvbox");
		this.columnCenter.setAlignment(Pos.TOP_CENTER);
		this.columnCenter.getChildren().addAll(lblCurrentProject);
		
		this.columnRight.getChildren().add(lblProjectsDone);
		this.columnRight.setAlignment(Pos.TOP_CENTER);

		this.hbDown.setSpacing(400);
		this.hbDown.getChildren().addAll(columnLeft, columnCenter, columnRight);

		this.setRoot(layout);

	}

}
