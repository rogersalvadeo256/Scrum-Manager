package scenes;

/**
 * 		@author jefter66: jefter66
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import DB.database.functions.definition.UserOnline;
import events.ExitButtonListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Window;

public class HomePageScene extends Scene {

	private GridPane layout;
	private Button btnExit;
	private Button btnEditProfile;
	private ImageView profileImg;

	private Label lblName, lblUsername, lblCurrentProject, lblProjectsDone, lblDescription;
	private Button btnEditBio;
	private Label lblEmail;

	private HBox hbHeader;
	private TextField txtSearch;
	private Button btnSearch;
	private ImageView scrumIcon; //, messageIcon, friendRequestIcon, settingsIcon;
	private ArrayList<File> iconPath;
	private ArrayList<FileInputStream> fis;
	private VBox vbProfileInfo;

	private HBox hbMenu;
	private Button btnProject, btnStartProject, btnfriends;

	private HBox hbProjectArea;
	private VBox vbLeftColumn, vbRightColumn;

	public HomePageScene() throws ClassNotFoundException, SQLException, FileNotFoundException {
		super(new HBox());
//		IndicatorOfCss.referringScene(this,IndicatorOfCss.cssFile.HOME_PAGE_SCENE);
		
		this.layout = new GridPane();
		Window.mainStage.setTitle("Home");
		Window.mainStage.setWidth(1000);
		Window.mainStage.setHeight(800);
		
		this.iconPath = new ArrayList<File>();
		this.fis = new ArrayList<FileInputStream>();

		this.lblCurrentProject=new Label("Projetos Atuais");
		this.lblProjectsDone=new Label("Projetos Concluidos");
		this.lblEmail=new Label();
		
		
		this.lblName = new Label("nome"); //UserOnline.getProfile().getName());
		this.lblUsername = new Label("usernome"); //UserOnline.getUserLogged().getUserName());
		this.lblCurrentProject = new Label("Projetos em andamento");
		this.lblProjectsDone = new Label("Projetos finalizados");
		/*
		 * the user can edit this
		 */

		/*
		 * images for the scene
		 */
		this.iconPath.add(new File("resources/images/icons/scrum_icon.png"));
		this.iconPath.add(new File("resources/images/icons/envelope.png"));
		this.iconPath.add(new File("resources/images/icons/friend_request.png"));
		/*
		 * 
		 */
		this.iconPath.add(new File("resources/images/icons/profile_picture.png"));
		/*
		 * 
		 */

		this.fis.add(new FileInputStream(iconPath.get(0)));
		this.fis.add(new FileInputStream(iconPath.get(1)));
		this.fis.add(new FileInputStream(iconPath.get(2)));
		this.fis.add(new FileInputStream(iconPath.get(3)));

		this.hbHeader = new HBox();
		this.hbHeader.getStyleClass().add("hbox");

		this.scrumIcon = new ImageView();
		this.setImage(scrumIcon, 0);

		this.hbHeader.setId("header");
		this.hbHeader.setPrefWidth(Window.mainStage.getMaxWidth());
		this.txtSearch = new TextField();
		this.btnSearch = new Button("Pesquisar");
		this.hbHeader.setSpacing(20);
		this.hbHeader.setAlignment(Pos.CENTER);
		this.btnEditProfile = new Button("Editar Perfil");
		this.btnEditProfile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/*
				 * open new window with the profile informations and options to edit
				 */
			}
		});
		this.btnExit = new Button("Sair");
		this.btnExit.setOnAction(new EventHandler<ActionEvent>() {
			ExitButtonListener exit = new ExitButtonListener() {};
				/*
				 * close the program and serialization
				 */
					@Override
					public void handle(ActionEvent event) {
						exit.handle(event);
					}
				});	
	
		this.hbHeader.getChildren().addAll(scrumIcon, txtSearch, btnSearch,	btnEditProfile, btnExit);

		this.layout.add(hbHeader, 0, 0, 5, 1);

		this.vbProfileInfo = new VBox();
		this.vbProfileInfo.getStyleClass().add("vbox");
		this.vbProfileInfo.setId("profile-info");

		this.vbProfileInfo.setPadding(new Insets(0, 0, 0, 10));
		this.vbProfileInfo.setAlignment(Pos.TOP_CENTER);
		this.vbProfileInfo.setSpacing(25);
		this.vbProfileInfo.setPrefWidth(300);

		this.lblDescription = new Label();

		this.profileImg = new ImageView();
		this.profileImg.setFitHeight(200);
		this.profileImg.setFitWidth(200);

		this.btnEditBio = new Button("Editar Bio");

		this.vbProfileInfo.getChildren().addAll(profileImg, lblName, lblUsername, lblDescription, btnEditBio);

		this.btnEditBio.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				TextField bio = new TextField();
				HomePageScene.this.lblDescription.setVisible(false);
				HomePageScene.this.btnEditBio.setVisible(false);
				HomePageScene.this.vbProfileInfo.getChildren().add(bio);
				bio.setTranslateY(-90);	
				
			
			}
		});
			
		
		/*
		 * database
		 */
		this.profileImg.setImage(new Image(new FileInputStream("resources/images/icons/profile_picture.png")));
		this.lblDescription
				.setText("something asfokajflakalskfjalkfjasflksaflkjs \n lafskjalfkjlkasfjljk \n aksfjjalfjk");

		this.layout.add(vbProfileInfo, 0, 1, 1, 5);
		this.vbProfileInfo.setPrefHeight(Window.mainStage.getMaxHeight());

		this.hbMenu = new HBox();
		this.hbMenu.getStyleClass().add("hbox");
		this.hbMenu.setId("menu");
		this.hbMenu.setSpacing(20);

		this.btnStartProject = new Button("Começar projeto");
		this.btnProject = new Button("Meus Projetos");
		this.btnfriends = new Button("Amigos");
		this.hbMenu.setAlignment(Pos.CENTER);
		this.hbMenu.setSpacing(20);
		this.hbMenu.getChildren().addAll(btnStartProject, btnProject, btnfriends);

		this.layout.add(hbMenu, 1, 1, 1, 1);

		this.hbProjectArea = new HBox();

		this.vbLeftColumn = new VBox();
		this.vbLeftColumn.getStyleClass().add("vbox");
		this.vbLeftColumn.setId("vbLeft");
		this.vbLeftColumn.getChildren().add(this.lblCurrentProject);

		this.vbLeftColumn.setAlignment(Pos.TOP_CENTER);
		this.vbLeftColumn.setPrefWidth(400);
		
		this.vbRightColumn = new VBox();
		this.vbRightColumn.getStyleClass().add("vbox");
		this.vbRightColumn.setId("vbRight");
		this.vbRightColumn.getChildren().add(this.lblProjectsDone);
		
		this.vbRightColumn.setPrefWidth(400);
		this.vbRightColumn.setAlignment(Pos.TOP_CENTER);
		
		
		this.hbProjectArea.setPrefWidth(Window.mainStage.getWidth());
		this.hbProjectArea.setPrefHeight(Window.mainStage.getHeight());
		
		this.hbProjectArea.getStyleClass().add("hbox");
		this.hbProjectArea.setId("hbProjectArea");
		this.hbProjectArea.getChildren().addAll(vbLeftColumn, vbRightColumn);

		
		this.layout.add(hbProjectArea, 1, 2, 1, 1);

		/*
		 * espaço horizontal e vertical entre os componentes
		 */
		
		// this.layout.setHgap(15);
		 this.layout.setVgap(20);

		this.setRoot(layout);

	}

	private void setImage(ImageView image, int fis) {
		image.setFitHeight(80);
		image.setFitWidth(80);
		image.setImage(new Image(this.fis.get(fis)));
	}

}
