package Scenes;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.w3c.dom.css.RGBColor;

import Main.Window;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

public class Home extends Scene {

	private GridPane layout;

	private HBox hbTopScene;
	private HBox projectsColumns;
	private VBox columnLeft;
	private VBox columnMiddle;
	private VBox columnRight;
	private Button btnExit;
	private Button btnEditProfile;
	private VBox vbBtnOptions;
	private VBox vbNameUserImage;
	private Label lblName;
	private Label lblUserName;
	private Label lblNewProject;

	private ImageView imagem;
	private Label lblProjects;
	private Hyperlink myProjects;
	private Label lblMyCompleteProjects;

	public Home() {
		super(new HBox());
		/*
		 * the css
		 */
		String css = this.getClass().getResource("/cssStyles/style.css").toExternalForm();
		this.getStylesheets().add(css);

		Window.mainStage.setTitle("Principal");
		Window.mainStage.setWidth(750);
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
		this.btnExit.setMinWidth(vbBtnOptions.getPrefWidth());

		this.vbBtnOptions.getChildren().addAll(btnEditProfile, btnExit);
		this.vbBtnOptions.setSpacing(10);
		/*
		 * setting the vbox in the right
		 */
		this.vbBtnOptions.setTranslateX(200);

		this.lblName = new Label("nome");
		this.lblName.setFont(new Font(25));
		this.lblUserName = new Label("userName");
		this.lblUserName.setFont(new Font(15));

		/*
		 * this vbox have will stay on the lef top of the scene contain the image, name
		 * and username of the user
		 */
		this.vbNameUserImage = new VBox();
		this.vbNameUserImage.setPadding(new Insets(10));
		this.vbNameUserImage.getChildren().addAll(lblName, lblUserName);

		/*
		 * this hbox will contain all the vbox above going to stay on the top of the
		 * scene, defined in the gridpane
		 */
		this.hbTopScene = new HBox();

		this.layout.add(hbTopScene, 0, 0, 1, 1);
		/*
		 * i put the alignment in the top left because i couldn't setting the image
		 * above the left column with another way
		 */
		this.hbTopScene.setAlignment(Pos.TOP_LEFT);
		this.hbTopScene.setPadding(new Insets(20));
		this.hbTopScene.setTranslateX(30);
		this.hbTopScene.setTranslateY(20);

		this.hbTopScene.setSpacing(10);

		/*
		 * setting the things in the top part of the layout
		 */
		this.hbTopScene.getChildren().addAll(imagem, vbNameUserImage, vbBtnOptions);

		/*
		 * the idea is making three columns below the image and the name of the user and
		 * the buttons exit and edit profile in the first column starting from the left,
		 * will contain a Vbox who will lead to other scene, for start new project and
		 * etc the middle column, like the previous one, has a vbox with his stuff, this
		 * column are from the projects in progress the last column, in the right, has
		 * the project finished
		 */

		this.projectsColumns = new HBox();
		this.projectsColumns.setId("hbox");

		/*
		 * is on the same column that the top hbox, but one line below
		 */
		this.layout.add(projectsColumns, 0, 2, 1, 1);

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
		this.columnMiddle = new VBox();
		this.columnLeft = new VBox();
		this.columnRight = new VBox();

		/*
		 * css stuff
		 */
		columnRight.setId("vbox");
		columnMiddle.setId("vbox");
		this.btnExit.setId("exitbtn");
		columnLeft.setId("vbox");

		/*
		 * the left column
		 */
		this.columnLeft.setTranslateX(20);
		this.columnLeft.setTranslateY(40);
		this.columnLeft.setPadding(new Insets(10));
		this.lblNewProject = new Label("NEW PROJECT");
		this.lblNewProject.setTextFill(Color.BLACK);
		this.lblNewProject.setAlignment(Pos.CENTER);
		this.lblNewProject.setTranslateY(25);
		this.columnLeft.getChildren().addAll(lblNewProject);
		/*
		 * if this vbox be clicked a form for create a new project will be called an the
		 * scene will be changed
		 */
		this.columnLeft.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {

				/*
				 * CALL THE FORM OF NEW PROJECT HERE
				 */
			}
		});

		this.lblProjects = new Label("Projetos em andamento");
		this.lblProjects.setFont(new Font(20));

		/*
		 * i dont think that this hyperlink will stay here for to long time
		 */
		this.myProjects = new Hyperlink("(buscar nome dos projetos no banco de \n dados e colocar aqui");

		/*
		 * css stuff
		 */
		this.lblProjects.setId("lblvbox");
		this.myProjects.setId("lblvbox");

		this.myProjects.setFont(new Font(15));
		this.columnMiddle.setTranslateX(20);
		this.columnMiddle.setTranslateY(40);
		this.columnMiddle.setPadding(new Insets(10));
		this.columnMiddle.getChildren().addAll(lblProjects, myProjects);

		this.lblMyCompleteProjects = new Label("Projetos Concluidos");
		this.lblMyCompleteProjects.setFont(new Font(20));

		/*
		 * css stuff
		 */
		this.lblMyCompleteProjects.setId("lblvbox");

		this.columnRight.setTranslateX(20);
		this.columnRight.setTranslateY(40);
		this.columnRight.setPadding(new Insets(10));
		this.columnRight.getChildren().addAll(lblMyCompleteProjects);

		this.projectsColumns.setPadding(new Insets(10));
		this.projectsColumns.setSpacing(5);
		this.projectsColumns.getChildren().addAll(columnLeft, columnMiddle, columnRight);

		this.setRoot(layout);

	}

}
