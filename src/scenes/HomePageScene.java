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
import java.sql.SQLException;

import Database.DbLoadProfileHome;
import custom.objects.LabelWithIcon;
import events.ExitButtonListener;
import javafx.event.ActionEvent;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.Window;

public class HomePageScene extends Scene {

	private GridPane layout;
	private Button btnExit;
	private Button btnEditProfile;
	private Label lblName;
	private Label lblUserName;
	private ImageView imagem;
	private Label lblNewProject;
	private Label lblCurrentProject;
	private Label lblProjectsDone;

	private Label lblDescription;
	private Button btnEditBio;
	private LabelWithIcon lblEmail;

	public HomePageScene() throws ClassNotFoundException, SQLException {
		super(new HBox());

		this.layout = new GridPane();
		this.getStylesheets().add(this.getClass().getResource("/cssStyles/homeScene.css").toExternalForm());

		Window.mainStage.setTitle("Home");
		Window.mainStage.setWidth(800);
		Window.mainStage.setHeight(600);

		/*
		 * instantiate and configuration of the design components
		 */
		this.btnExit = new Button("EXIT");
		this.imagem = new ImageView();

		this.lblCurrentProject = new Label("CURRENT PROJECTS");
		this.lblProjectsDone = new Label("DONE");
		this.lblNewProject = new Label("NEW PROJECT");
		this.btnExit.setId("exitbtn");

		this.btnExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ExitButtonListener exit = new ExitButtonListener() {
				};
				exit.handle(event);
			}
		});

		this.btnEditProfile = new Button("EDIT PROFILE");
		this.btnEditProfile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

			}

		});

		this.lblCurrentProject.setId("lblvbox");

		this.imagem.setFitWidth(150);
		this.imagem.setFitHeight(150);
		this.imagem.setTranslateX(10);

			
		
		// FileChooser fc = new FileChooser();
		// File f = fc.showOpenDialog(Window.mainStage);
		File f = new File("/home/jefter66/Área de Trabalho/ptcc/scrum.png");
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			this.imagem.setImage(new Image(fis));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		this.lblCurrentProject.setFont(new Font(20));
		this.lblProjectsDone.setFont(new Font(20));
		this.lblNewProject.setTextFill(Color.WHITE);
		this.lblNewProject.setFont(new Font(20));
		this.lblNewProject.setAlignment(Pos.CENTER);
		this.lblDescription = new Label();
		this.lblEmail = new LabelWithIcon(12, LabelWithIcon.LabelType.Type.EMAIL_ICON,
				LabelWithIcon.LabelType.BackgroundColor.GREY,
				LabelWithIcon.LabelType.BackgroundHoverColor.DARK_GREY_HOVER);
		this.btnEditBio = new Button("Editar Mensagem");

		this.layout.add(this.imagem, 0, 0, 1, 1);
		this.imagem.setFitWidth(400);
		this.imagem.setFitHeight(400);
		this.imagem.getStyleClass().add("image");

		this.lblName = new Label();
		this.lblName.setText("a");
		this.lblName.setFont(new Font(25));
		this.layout.add(this.lblName, 0, 1, 1, 1);

		this.lblUserName = new Label();
		this.lblUserName.setFont(new Font(15));
		this.layout.add(this.lblUserName, 0, 2, 1, 1);

		this.layout.add(this.lblDescription, 0, 3, 1, 1);
		this.layout.add(this.btnEditBio, 0, 4, 1, 1);
		this.layout.add(this.lblEmail, 0, 5, 1, 1);
		
		
		
		
		this.layout.setHgap(15);
		this.layout.setVgap(20);
		this.setRoot(layout);

	}

}
