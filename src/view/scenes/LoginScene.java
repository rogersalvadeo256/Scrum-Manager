package view.scenes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import application.controllers.LoginSceneController;
import application.main.Window;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import listeners.Close;
import view.popoups.ForgotPasswordPOPOUP;

/*
 * LoginScreen.java
 * Created on: 28 jun de 2018 Autor: jefter66 and changed a million times after be created ( 16 september) 
 * 
 */
public class LoginScene extends Scene {

	private final Label messageLoginValidation;
	private Label lblSignIn, lblPassword, lblWelcome, lblUser;
	private TextField txtLogin;
	private PasswordField passwordField;
	private Button btnLogin, btnExit;
	private Button btnSignUp;
	private RadioButton btnStayConnected;
	private Label lblStayConnected;
	private HBox layoutContent, hbStayConnected;
	private Hyperlink forgotPassword;
	private VBox vbLogin;
	private AnchorPane layout;
	private ImageView imgIcon;

	private LoginSceneController controller;

	private RegistrationFormComponent vbRegistration;

	public LoginScene() throws ClassNotFoundException, SQLException, FileNotFoundException {
		super(new HBox());

		this.getStylesheets().add(this.getClass().getResource("/css/LOGIN_SCENE.css").toExternalForm());

		Window.mainStage.setTitle("Tela Login");

		this.controller = new LoginSceneController();
		this.layout = new AnchorPane();
		this.layoutContent = new HBox();

		this.vbRegistration = new RegistrationFormComponent();

		this.vbLogin = new VBox();

		this.txtLogin = new TextField();
		this.txtLogin.setPromptText("Username");
		this.txtLogin.setAlignment(Pos.CENTER);
		this.passwordField = new PasswordField();
		this.passwordField.setAlignment(Pos.CENTER);

		this.txtLogin.setPromptText("Username");
		this.passwordField.setPromptText("Digite sua senha");

		this.lblUser = new Label("Username");
		this.lblPassword = new Label("Senha");

		this.lblWelcome = new Label("Bem Vindo Ao  Scrum Manager");
		this.lblSignIn = new Label("Entrar");

		this.lblWelcome.getStyleClass().add("title");
		this.lblSignIn.getStyleClass().add("title");

		this.messageLoginValidation = new Label("Login ou senha incorretos");
		this.messageLoginValidation.setVisible(false);
		this.messageLoginValidation.setId("messageWrong");
		this.forgotPassword = new Hyperlink("Esqueci minha senha");

		this.btnStayConnected = new RadioButton();
		this.lblStayConnected = new Label("Mantenha-me conentado");
		this.hbStayConnected = new HBox();
		this.hbStayConnected.getChildren().addAll(btnStayConnected, lblStayConnected);

		this.btnLogin = new Button("LOGIN");
		this.btnExit = new Button("SAIR");
		this.btnSignUp = new Button("Cadastre-se");

		this.btnExit.setId("exitbtn");
		this.btnSignUp.setId("btnSingUp");

		this.imgIcon = new ImageView();
		this.imgIcon.setImage(new Image(new FileInputStream(new File("resources/images/icons/scrum_icon.png"))));

		this.imgIcon.setFitWidth(400);
		this.imgIcon.setFitHeight(400);

		this.txtLogin.setOnMouseClicked(e -> {
			this.messageLoginValidation.setVisible(false);
		});
		this.passwordField.setOnMouseClicked(e -> {
			this.messageLoginValidation.setVisible(false);
		});
		this.btnLogin.setOnAction(e -> {
			try {
				this.controller.setEventBtnLogin(e, txtLogin, passwordField, btnStayConnected);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if (this.controller.isInvalidLogin()) {
				this.messageLoginValidation.setVisible(true);
			}
		});
		this.passwordField.setOnKeyPressed(e -> {
			try {
				this.controller.setEventPasswordField(e, txtLogin, passwordField, btnStayConnected);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if (this.controller.isInvalidLogin()) {
				this.messageLoginValidation.setVisible(true);
			}
		});
		this.forgotPassword.setOnMouseClicked(e -> {
			new ForgotPasswordPOPOUP(Window.mainStage).showAndWait();
		});

		this.btnSignUp.setOnAction(e -> {
			this.layoutContent.getChildren().clear();
			this.registration();
		});

		this.vbRegistration.setEventCancel(e -> {
			this.layoutContent.getChildren().clear();
			this.login();
		});

		this.btnExit.setOnAction(new Close(Window.mainStage));

		this.imgIcon.setId("logoImage");

		HBox.setHgrow(txtLogin, Priority.ALWAYS);
		HBox.setHgrow(passwordField, Priority.ALWAYS);
		HBox.setHgrow(btnLogin, Priority.ALWAYS);
		HBox.setHgrow(btnExit, Priority.ALWAYS);
		this.txtLogin.setMaxWidth(Double.MAX_VALUE);
		this.passwordField.setMaxWidth(Double.MAX_VALUE);
		this.btnExit.setMaxWidth(Double.MAX_VALUE);
		this.btnLogin.setMaxWidth(Double.MAX_VALUE);

		this.vbLogin.getChildren().addAll(lblSignIn, lblUser, txtLogin, lblPassword);
		this.vbLogin.getChildren().addAll(passwordField, messageLoginValidation, forgotPassword, hbStayConnected, btnLogin, btnExit, btnSignUp);

		VBox.setVgrow(vbLogin, Priority.ALWAYS);
		this.vbLogin.setMaxWidth(Double.MAX_VALUE);
		this.vbLogin.setMaxHeight(Double.MAX_VALUE);
		this.vbLogin.setAlignment(Pos.CENTER);

		VBox.setVgrow(vbRegistration, Priority.ALWAYS);
		this.vbRegistration.setMaxWidth(Double.MAX_VALUE);
		this.vbRegistration.setMaxHeight(Double.MAX_VALUE);
		this.vbRegistration.setAlignment(Pos.CENTER);

		this.vbLogin.setSpacing(30);
		this.vbRegistration.setSpacing(30);

		this.layoutContent.setAlignment(Pos.CENTER);

		AnchorPane.setTopAnchor(this.layoutContent, 0.0);
		AnchorPane.setLeftAnchor(this.layoutContent, 0.0);
		AnchorPane.setRightAnchor(this.layoutContent, 0.0);
		AnchorPane.setBottomAnchor(this.layoutContent, 0.0);
		this.layout.getChildren().add(layoutContent);

		login();

		this.setRoot(layout);
	}

	private void login() {
		this.layoutContent.setSpacing(50);
		this.layoutContent.getChildren().add(vbLogin);
	}

	private void registration() {
		this.layoutContent.setSpacing(50);
		this.layoutContent.getChildren().add(vbRegistration);
	}

}
