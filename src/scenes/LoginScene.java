package scenes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import events.ExitButtonListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import main.Window;
import referring.css.ReferringCss;
import referring.css.ReferringCss.cssFile.cssFiles;
/*
 * LoginScreen.java
 * 
 * Created on: 28 jun de 2018
 * 		Autor: jefter66
 * 
 */
public class LoginScene extends Scene {

	private final Label messageLoginValidation;

	private Label lblSignIn, lblPassword, lblWelcome, lblSignUp, lblUser;

	private TextField txtUser;
	private PasswordField passwordField;

	private Button btnLogin, btnExit;
	private Button btnCancel, btnSignUp;

	private Hyperlink forgotPassword;

	private VBox left;
	private VBox leftRegistration;
	private HBox right;
	private HBox rightContainer;
	private VBox vbLogin;

	private ImageView imgIcon;
	private FileInputStream fis;
	private Line line;

	private ArrayList<RegistrationFormScene> form;

	private GridPane layout;
	private File iconPath;
	private ReferringCss cssReferrer;

	public LoginScene() throws ClassNotFoundException, SQLException, FileNotFoundException {
		super(new HBox());
		this.cssReferrer = new ReferringCss();
		this.cssReferrer.referringScene(this, cssFiles.LOGIN);

		this.layout = new GridPane();

		Window.mainStage.setWidth(1200);
		Window.mainStage.setHeight(600);
		Window.mainStage.setTitle("TCC");

		/*
		 * layout
		 */
		this.left = new VBox();
		this.leftRegistration = new VBox();
		this.form = new ArrayList<RegistrationFormScene>();
		this.right = new HBox();
		this.vbLogin = new VBox();
		this.rightContainer = new HBox();


		this.vbLogin.setAlignment(Pos.CENTER);
		this.left.setAlignment(Pos.CENTER_LEFT);
		this.left.setAlignment(Pos.CENTER);
		this.rightContainer.setAlignment(Pos.CENTER);
		this.vbLogin.setSpacing(20);
		
		this.left.setPrefWidth(650);
		this.left.setPrefHeight(600);
		this.rightContainer.setPrefWidth(600);
		this.right.setPrefHeight(00);
		this.leftRegistration.setPrefWidth(150);
		this.vbLogin.setPrefWidth(400);

		this.layout.setMaxHeight(500);
		this.layout.setMaxWidth(500);
		this.layout.setMinWidth(500);
		this.layout.setMinHeight(500);
		this.left.setMaxWidth(800);

		this.right.setTranslateX(0);
		this.left.setTranslateY(100);
	
		/*
		 * text fields
		 */
		this.txtUser = new TextField();
		this.txtUser.setPromptText("Username");
		this.passwordField = new PasswordField();
		this.txtUser.setPromptText("Username");
		this.passwordField.setPromptText("Digite sua senha");
		
		this.txtUser.setAlignment(Pos.CENTER);
		this.passwordField.setAlignment(Pos.CENTER);

		
		/*
		 * labels
		 */
		this.lblUser = new Label("Username");
		this.lblPassword = new Label("Senha");
		this.lblWelcome = new Label("Bem Vindo Ao \n Scrum Manager");
		this.lblSignUp = new Label("Registre-se Agora");
		this.lblSignIn = new Label("SIGN IN");
		this.lblSignUp = new Label("Registre-se Agora");
		this.lblSignIn = new Label("SIGN IN");

		this.messageLoginValidation = new Label(new String());
		this.messageLoginValidation.setId("messageWrongData");
		this.forgotPassword = new Hyperlink("Esqueci minha senha");

		this.lblWelcome.setTranslateX(-450);
		this.lblSignUp.setTranslateY(-400);
		this.lblSignUp.setTranslateX(400);

		this.lblWelcome.setTranslateY(-200);
		
		/*
		 */

		/*
		 * buttons
		 */

		this.btnLogin = new Button("LOGIN");
		this.btnExit = new Button("SAIR");
		this.btnExit.setId("exitbtn");
		this.btnSignUp = new Button("SIGN UP");
		this.btnSignUp.setId("btnSingUp");
		this.btnCancel = new Button("CANCELAR");
		this.btnCancel.setVisible(false);

		this.btnSignUp.setTranslateY(-15);
		this.btnCancel.setTranslateY(-15);
		this.btnSignUp.setTranslateY(-400);
		this.btnCancel.setTranslateY(-400);
		this.btnCancel.setTranslateX(-30);
		this.btnSignUp.setTranslateX(-30);
		
		this.btnCancel.setMaxSize(150, 100);
		this.btnSignUp.setMaxSize(150, 100);
		this.btnLogin.setMaxSize(200, 100);
		this.btnSignUp.setMaxWidth(90);
		this.btnCancel.setMaxWidth(100);
		/*
		 * 
		 */

		this.line = new Line();
		this.line.setEndX(0.0f);
		this.line.setEndY(350.0f);
		this.line.setStroke(Color.valueOf("#ffff"));
		/*
		 * image
		 */
		this.imgIcon = new ImageView();
		this.iconPath = new File("/home/jefter66/java-workspace/TCC/resources/images/icons/scrum_icon.png");
		this.fis = new FileInputStream(iconPath);
		this.imgIcon.setImage(new Image(fis));

		this.imgIcon.setFitWidth(400);
		this.imgIcon.setFitHeight(400);
		
		this.imgIcon.setTranslateX(100);
		this.imgIcon.setTranslateX(100);
		this.imgIcon.setTranslateY(100);
		this.imgIcon.setTranslateY(100);
		/*
		 * buttons handler
		 */
		this.btnExit.setOnAction(new EventHandler<ActionEvent>() {
			ExitButtonListener exit = new ExitButtonListener() {};
			@Override
			public void handle(ActionEvent event) {
				exit.handle(event);
			}
		});
		this.btnSignUp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				signUpPressed();
				RegistrationFormScene t;
				try {
					t = new RegistrationFormScene();
					form.add(t);
					if (!form.isEmpty()) {
						LoginScene.this.leftRegistration.getChildren().clear();
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				LoginScene.this.leftRegistration.getChildren().add(form.get(0));
			}
		});
		this.btnCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LoginScene.this.leftRegistration.getChildren().clear();
				LoginScene.this.btnCancel.setVisible(false);
				LoginScene.this.form.clear();
				cancelPressed();
			}
		});
		this.btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/*
				 * tests area
				 */
				try {
					logar();
				} catch (ClassNotFoundException | FileNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				 */
			}
		});
		this.passwordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					try {
						logar();
					} catch (FileNotFoundException | ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		this.forgotPassword.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
			}
		});
		this.rightContainer.setSpacing(40);
		this.imgIcon.setId("logoImage");

		this.vbLogin.getChildren().addAll(lblSignIn, lblUser, txtUser, lblPassword, passwordField, messageLoginValidation, forgotPassword, btnLogin, btnExit);
		this.right.getChildren().add(rightContainer);
		this.rightContainer.getChildren().addAll(line, vbLogin);
		this.left.getChildren().addAll(leftRegistration);

		this.layout.add(right, 1, 0, 1, 1);
		this.layout.add(imgIcon, 0, 0, 1, 1);
		this.layout.add(btnSignUp, 1, 1, 1, 1);
		this.layout.add(btnCancel, 1, 1, 1, 1);
		this.layout.add(lblWelcome, 1, 0, 1, 1);
		this.layout.add(lblSignUp, 0, 1, 1, 1);
		this.layout.add(left, 0, 0, 1, 1);
		
		this.setRoot(layout);
	}

	private void logar() throws FileNotFoundException, ClassNotFoundException, SQLException {
		
		

	}

	private void cancelPressed() {
		LoginScene.this.imgIcon.setTranslateX(100);
		LoginScene.this.imgIcon.setTranslateY(100);
		LoginScene.this.imgIcon.setFitWidth(400);
		LoginScene.this.imgIcon.setFitHeight(400);
	}

	private void signUpPressed() {
		LoginScene.this.form.clear();
		LoginScene.this.btnCancel.setVisible(true);
		LoginScene.this.imgIcon.setFitWidth(200);
		LoginScene.this.imgIcon.setFitHeight(200);
		LoginScene.this.imgIcon.setTranslateY(-180);
		LoginScene.this.imgIcon.setTranslateX(-30);
	}


}
