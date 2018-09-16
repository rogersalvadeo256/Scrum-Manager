package scenes.scenes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import application.main.Window;
import db.util.Login;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import listeners.Close;
import scenes.popoups.ForgotPasswordPOPOUP;

/*
 * LoginScreen.java
 * 
 * Created on: 28 jun de 2018 Autor: jefter66
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
	private HBox hbStayConnected;
	
	private Hyperlink forgotPassword;
	
	private VBox leftSide;
	private VBox leftRegistrationForm;
	private HBox rightSide;
	private VBox vbLogin;
	private AnchorPane layout;
	private ImageView imgIcon;
	private FileInputStream fis;
	private File iconPath;
	
	private ArrayList<RegistrationFormScene> form;
	
	private Login login;
	
	public LoginScene() throws ClassNotFoundException, SQLException, FileNotFoundException {
		super(new HBox());
		
		 this.getStylesheets().add(this.getClass().getResource("/css/LOGIN_SCENE.css").toExternalForm());
		
		Window.mainStage.setWidth(1200);
		Window.mainStage.setHeight(600);
		Window.mainStage.setTitle("Tela Login");
		
		this.layout = new AnchorPane();
		
		this.vbLogin = new VBox();
		this.rightSide = new HBox();
		
		this.leftSide = new VBox();
		this.leftRegistrationForm = new VBox();
		
		this.login = new Login();
		
		/*
		 * layout
		 */
		this.form = new ArrayList<RegistrationFormScene>();
		
		this.leftSide.getStyleClass().add("left");
		this.rightSide.getStyleClass().add("right");
		
		/*
		 * text fields
		 */
		this.txtLogin = new TextField();
		this.txtLogin.setPromptText("Username");
		this.txtLogin.setAlignment(Pos.CENTER);
		this.passwordField = new PasswordField();
		this.passwordField.setAlignment(Pos.CENTER);
		
		this.txtLogin.setPromptText("Username");
		this.passwordField.setPromptText("Digite sua senha");
		
		/*
		 * labels
		 */
		this.lblUser = new Label("Username");
		this.lblPassword = new Label("Senha");
		
		this.lblWelcome = new Label("Bem Vindo Ao  Scrum Manager");
		this.lblSignIn = new Label("Entrar");
		
		this.lblWelcome.getStyleClass().add("title");
		this.lblSignIn.getStyleClass().add("title");
		
		this.messageLoginValidation = new Label(new String());
		this.messageLoginValidation.setId("messageWrongData");
		this.forgotPassword = new Hyperlink("Esqueci minha senha");
		this.forgotPassword.setOnMouseClicked(e -> {
			if(!LoginScene.this.txtLogin.getText().isEmpty()) { 
				try {
					new ForgotPasswordPOPOUP(Window.mainStage, LoginScene.this.txtLogin).showAndWait();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if(LoginScene.this.txtLogin.getText().isEmpty()) { 
				new ForgotPasswordPOPOUP(Window.mainStage).showAndWait();
			}
		});
		/*
		 */
		this.btnStayConnected = new RadioButton();
		this.lblStayConnected = new Label("Mantenha-me conentado");
		this.hbStayConnected = new HBox();
		this.hbStayConnected.getChildren().addAll(btnStayConnected, lblStayConnected);
		
		/*
		 * buttons
		 */
		
		this.btnLogin = new Button("LOGIN");
		this.btnExit = new Button("SAIR");
		this.btnSignUp = new Button("Cadastre-se");
		
		this.btnExit.setId("exitbtn");
		this.btnSignUp.setId("btnSingUp");
		/*
		 * 
		 */
		/*
		 * image
		 */
		this.imgIcon = new ImageView();
		this.iconPath = new File("resources/images/icons/scrum_icon.png");
		this.fis = new FileInputStream(iconPath);
		this.imgIcon.setImage(new Image(fis));
		
		this.imgIcon.setFitWidth(400);
		this.imgIcon.setFitHeight(400);
		
		/*
		 * buttons handler
		 */
		this.btnExit.setOnAction(new Close(Window.mainStage));
		
		this.btnLogin.setOnAction(e -> {
			if (LoginScene.this.login.doLogin(LoginScene.this.txtLogin, LoginScene.this.passwordField)) {
				LoginScene.this.messageLoginValidation.setText(new String());
				try {
					Window.mainStage.setScene(new HomePageScene());
				} catch (ClassNotFoundException | FileNotFoundException | SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			LoginScene.this.messageLoginValidation.setText("Nome de usuario ou senha errado");
		});
		this.passwordField.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				if (LoginScene.this.login.doLogin(LoginScene.this.txtLogin, LoginScene.this.passwordField)) {
					LoginScene.this.messageLoginValidation.setText(new String());
					try {
						Window.mainStage.setScene(new HomePageScene());
					} catch (ClassNotFoundException | FileNotFoundException | SQLException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			LoginScene.this.messageLoginValidation.setText("Nome de usuario ou senha errado");
		});
		this.forgotPassword.setOnAction(event -> {
			
		});
		
		this.imgIcon.setId("logoImage");
		
		HBox.setHgrow(txtLogin, Priority.ALWAYS);
		HBox.setHgrow(passwordField, Priority.ALWAYS);
		HBox.setHgrow(btnLogin, Priority.ALWAYS);
		HBox.setHgrow(btnExit, Priority.ALWAYS);
		this.txtLogin.setMaxWidth(Double.MAX_VALUE);
		this.passwordField.setMaxWidth(Double.MAX_VALUE);
		this.btnExit.setMaxWidth(Double.MAX_VALUE);
		this.btnLogin.setMaxWidth(Double.MAX_VALUE);
		
		this.vbLogin.getChildren().addAll(lblSignIn, lblUser, txtLogin, lblPassword, passwordField,
				messageLoginValidation, forgotPassword, hbStayConnected, btnLogin, btnExit);
		
		HBox.setHgrow(vbLogin, Priority.ALWAYS);
		this.vbLogin.setMaxWidth(Double.MAX_VALUE);
		this.vbLogin.setMaxHeight(Double.MAX_VALUE);
		this.vbLogin.setAlignment(Pos.CENTER);
		this.vbLogin.setSpacing(20);
		this.rightSide.getChildren().add(vbLogin);
		
		this.rightSide.setAlignment(Pos.CENTER);
		AnchorPane.setTopAnchor(this.rightSide, 40.0);
		AnchorPane.setLeftAnchor(this.rightSide, 800.0);
		AnchorPane.setRightAnchor(this.rightSide, 50.0);
		AnchorPane.setBottomAnchor(this.rightSide, 30.0);
		this.layout.getChildren().add(rightSide);
		
		settingLeftSideComponents();
		
		this.btnSignUp.setOnAction(event -> {
			this.form.clear();
			this.leftSide.getChildren().clear();
			
			RegistrationFormScene t;
			try {
				t = new RegistrationFormScene();
				form.add(t);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (form.size() == 1)
				LoginScene.this.leftRegistrationForm.getChildren().add(form.get(0));
			
			AnchorPane.setTopAnchor(this.leftRegistrationForm, 90.0);
			AnchorPane.setLeftAnchor(this.leftRegistrationForm, 80.0);
			AnchorPane.setRightAnchor(this.leftRegistrationForm, 600.0);
			AnchorPane.setBottomAnchor(this.leftRegistrationForm, 30.0);
			layout.getChildren().add(leftRegistrationForm);
			
			if (form.size() == 1) {
				form.get(0).btnCancel.setText("Cancelar");
				form.get(0).btnCancel.setOnAction(e -> {
					form.clear();
					leftRegistrationForm.getChildren().clear();
					
					layout.getChildren().remove(leftRegistrationForm);
					LoginScene.this.form.clear();
					LoginScene.this.settingLeftSideComponents();
				});
			}
		});
		this.setRoot(layout);
	}
	
	private void settingLeftSideComponents() {
		HBox.setHgrow(lblWelcome, Priority.ALWAYS);
		HBox.setHgrow(btnSignUp, Priority.ALWAYS);
		HBox.setHgrow(imgIcon, Priority.ALWAYS);
		this.lblWelcome.setMaxWidth(Double.MAX_VALUE);
		this.btnSignUp.setMaxWidth(Double.MAX_VALUE);
		this.leftSide.getChildren().addAll(lblWelcome, btnSignUp, imgIcon);
		
		this.leftSide.setAlignment(Pos.CENTER);
		HBox.setHgrow(leftSide, Priority.ALWAYS);
		this.leftSide.setMaxWidth(Double.MAX_VALUE);
		this.leftSide.setMaxHeight(Double.MAX_VALUE);
		
		AnchorPane.setTopAnchor(this.leftSide, 40.0);
		AnchorPane.setLeftAnchor(this.leftSide, 80.0);
		AnchorPane.setRightAnchor(this.leftSide, 600.0);
		AnchorPane.setBottomAnchor(this.leftSide, 30.0);
		this.layout.getChildren().add(leftSide);
	}
}