package scenes;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.dialect.function.TemplateRenderer;

import design.objects.MyButton;
import design.objects.MyLabel;
import design.objects.MyLabel.LabelType.BackgroundColor;
import design.objects.MyLabel.LabelType.BackgroundHoverColor;
import design.objects.MyLabel.LabelType.Type;
import events.ExitButtonListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.Window;
import referring.css.ReferringCss;
import referring.css.ReferringCss.cssFile.cssFiles;

public class HomePageScene extends Scene {

	private GridPane layout;
	private Button btnExit;
	private Button btnEditProfile;
	private ImageView profileImg;

	private Label lblName, lblUsername, lblNewProject, lblCurrentProject, lblProjectsDone, lblDescription;
	private Button btnEditBio;
	private MyLabel lblEmail;

	private HBox hbHeader;
	private TextField txtSearch;
	private MyButton btnSearch;
	private ImageView scrumIcon, messageIcon, friendRequestIcon, settingsIcon;
	private ArrayList<File> iconPath;
	private ArrayList<FileInputStream> fis;
	private VBox vbProfileInfo;

	private HBox hbMenu;
	private Button btnProject, btnStartProject,btnfriends; 

	private HBox hbProjectsArea;
	private VBox vbLeftColumn,vbMiddleColumn,vbRightColumn;
	
	private ReferringCss referrer;

	public HomePageScene() throws ClassNotFoundException, SQLException, FileNotFoundException {
		super(new HBox());
		this.layout = new GridPane();
		Window.mainStage.setTitle("Home");
		Window.mainStage.setWidth(1000);
		Window.mainStage.setHeight(800);
		this.referrer = new ReferringCss();
		this.iconPath =new ArrayList<File>();
		this.fis=new ArrayList<FileInputStream>();
		
		referrer.referringScene(this, cssFiles.HOME_PAGE_SCENE);

		this.lblName = new Label("name come from the DB");
		this.lblUsername = new Label("user come from the DB");
		this.lblNewProject = new Label("Começar um novo projeto");
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

		this.scrumIcon=new ImageView();
		this.messageIcon=new ImageView();
		this.friendRequestIcon=new ImageView();
		this.setImage(scrumIcon,0);
		this.setImage(messageIcon,1);
		this.setImage(friendRequestIcon,2);
	
		
		this.hbHeader.setId("header");
		this.hbHeader.setPrefWidth(Window.mainStage.getMaxWidth());
		this.txtSearch=new TextField();
		this.btnSearch=new MyButton();
		this.hbHeader.setSpacing(20);
		this.hbHeader.setAlignment(Pos.CENTER);
		this.hbHeader.getChildren().addAll(scrumIcon, txtSearch, btnSearch,messageIcon,friendRequestIcon);

		this.layout.add(hbHeader, 0, 0, 5, 1);


		this.vbProfileInfo=new VBox();
		this.vbProfileInfo.getStyleClass().add("vbox");
		this.vbProfileInfo.setId("profile-info");

		this.vbProfileInfo.setPadding(new Insets(0, 0, 0, 10));
		this.vbProfileInfo.setAlignment(Pos.TOP_CENTER);
		this.vbProfileInfo.setSpacing(25);
		this.vbProfileInfo.setPrefWidth(300);

		this.lblDescription=new Label();
		
		this.profileImg=new ImageView();
		this.profileImg.setFitHeight(200);
		this.profileImg.setFitWidth(200);
	
		this.btnEditBio=new Button("Editar Mensagem");
		this.btnEditBio.setPrefWidth(200);
		this.btnEditBio.setPrefHeight(80);

		this.vbProfileInfo.getChildren().addAll(profileImg, lblName, lblUsername, lblDescription, btnEditBio);

		/*
		 * database
		 */
		this.profileImg.setImage(new Image(new FileInputStream("resources/images/icons/profile_picture.png")));
		this.lblDescription.setText("something asfokajflakalskfjalkfjasflksaflkjs \n lafskjalfkjlkasfjljk \n aksfjjalfjk");

		this.layout.add(vbProfileInfo, 0, 1, 1, 5);
		this.vbProfileInfo.setPrefHeight(Window.mainStage.getMaxHeight());
		
		
		this.hbMenu=new HBox();
		this.hbMenu.getStyleClass().add("hbox");
		this.hbMenu.setId("menu");
		this.hbMenu.setSpacing(20);
		this.hbMenu.setPrefWidth(800);
		
		this.btnStartProject=new Button("Começar projeto");		
		this.btnProject=new Button("Meus Projetos");
		this.btnfriends=new Button("Amigos");
		this.hbMenu.setAlignment(Pos.CENTER);
		this.hbMenu.setSpacing(20);
		this.hbMenu.getChildren().addAll(btnStartProject,btnProject,btnfriends);
		
		Label a = new Label("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		this.hbMenu.getChildren().add(a);
		this.layout.add(hbMenu, 1, 1, 1, 1);
		
		this.hbProjectsArea=new HBox();
		
		
		this.vbLeftColumn=new VBox();
		this.vbLeftColumn.getStyleClass().add("vbox");
		this.vbLeftColumn.setId("vbLeft");
		
		this.vbLeftColumn.setPrefWidth(250);
		this.vbLeftColumn.setPrefHeight(700);
		
		this.vbMiddleColumn=new VBox(); 
		this.vbMiddleColumn.getStyleClass().add("vbox");
		this.vbMiddleColumn.setId("vbMiddle");
		
		
		this.vbMiddleColumn.setPrefWidth(250);
		this.vbMiddleColumn.setPrefHeight(700);
		
		this.vbRightColumn=new VBox();
		this.vbRightColumn.getStyleClass().add("vbox");
		this.vbRightColumn.setId("vbRight");
		
		this.vbRightColumn.setPrefWidth(250);
		this.vbRightColumn.setPrefHeight(700);
		
		this.vbLeftColumn.getChildren().add(new Label("aaaaaaaaaaaaaaaa"));
		this.vbRightColumn.getChildren().add(new Label("bbbbbbbbbbbbbbbb"));
		this.vbMiddleColumn.getChildren().add(new Label("ccccccccccccccc"));
		
		
		
		this.hbProjectsArea.getChildren().addAll(vbLeftColumn,vbMiddleColumn,vbRightColumn);
		this.layout.add(hbProjectsArea, 1, 2, 1, 1);

		
		
		
		
		//		this.layout.setHgap(15);
//		this.layout.setVgap(20);
		this.setRoot(layout);

	}
	private void setImage(ImageView image, int fis) {
		image.setFitHeight(80);
		image.setFitWidth(80);
		image.setImage(new Image(this.fis.get(fis)));		
	}

}



















































