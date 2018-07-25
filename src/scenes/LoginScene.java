package scenes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import Database.DbLoadProfileHome;
import Database.Login;
import design.objects.LabelWithIcon;
import design.objects.LabelWithIcon.LabelType.BackgroundColor;
import design.objects.LabelWithIcon.LabelType.BackgroundHoverColor;
import design.objects.LabelWithIcon.LabelType.Type;
import events.ExitButtonListener;
import fields.FieldValidation;
import hibernatebook.annotations.Register;
import hibernatebook.inserts.Insert;
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
	private LabelWithIcon  lblSignIn, lblPassword, lblWelcome, lblSignUp, lblUser;
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
	private ArrayList<RegistrationForm> form;
	private GridPane layout;
	private File iconPath;
	private ReferringCss cssReferrer;
	public LoginScene() throws ClassNotFoundException, SQLException {
		super(new HBox());
		this.cssReferrer = new ReferringCss();
		this.cssReferrer.referringScene(this, cssFiles.LOGIN);
		
		this.layout = new GridPane();
		
		Window.mainStage.setWidth(1200);
		Window.mainStage.setHeight(600);	
		
		this.left = new VBox();
		this.leftRegistration = new VBox();
		this.form = new ArrayList<RegistrationForm>();
		this.right = new HBox();
		this.vbLogin = new VBox();
		this.rightContainer = new HBox();

		this.messageLoginValidation = new Label(new String());
		this.messageLoginValidation.setId("messageWrongData");
		this.forgotPassword = new Hyperlink("Esqueci minha senha");
		this.txtUser = new TextField();
		this.txtUser.setPromptText("Username");
		this.passwordField = new PasswordField();
		this.txtUser.setPromptText("Username");
		this.passwordField.setPromptText("Digite sua senha");
		this.lblUser = new LabelWithIcon("Username", 15, Type.TITLE, BackgroundColor.WHITE, BackgroundHoverColor.DARK_GREY_HOVER );
		this.lblPassword = new LabelWithIcon("Senha",  15, Type.TITLE, BackgroundColor.WHITE, BackgroundHoverColor.DARK_GREY_HOVER );
		this.lblWelcome = new LabelWithIcon("Bem Vindo Ao \n Scrum Manager", 30, Type.TITLE, BackgroundColor.WHITE, BackgroundHoverColor.DARK_GREY_HOVER );
		this.lblSignUp = new LabelWithIcon("Registre-se Agora", 20, Type.TITLE, BackgroundColor.WHITE, BackgroundHoverColor.DARK_GREY_HOVER );
		this.lblSignIn = new LabelWithIcon("SIGN IN" ,35, Type.TITLE, BackgroundColor.WHITE, BackgroundHoverColor.DARK_GREY_HOVER );
		this.lblWelcome = new LabelWithIcon("Bem Vindo Ao \n Scrum Manager", 30, Type.TITLE, BackgroundColor.WHITE, BackgroundHoverColor.DARK_GREY_HOVER );
		this.lblSignUp = new LabelWithIcon("Registre-se Agora", 20, Type.TITLE, BackgroundColor.WHITE, BackgroundHoverColor.DARK_GREY_HOVER );
		this.lblSignIn = new LabelWithIcon("SIGN IN" ,30, Type.TITLE, BackgroundColor.WHITE, BackgroundHoverColor.DARK_GREY_HOVER );

		this.btnLogin = new Button("LOGIN");
		this.btnExit = new Button("SAIR");
		this.btnExit.setId("exitbtn");
		this.btnSignUp = new Button("SIGN UP");
		this.btnSignUp.setId("btnSingUp");
		this.btnCancel = new Button("CANCELAR");
		this.btnCancel.setVisible(false);

		this.line = new Line();
		this.line.setEndX(0.0f);
		this.line.setEndY(350.0f);
		this.line.setStroke(Color.valueOf("#ffff"));

		this.btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/*
				 * tests area
				 */
				Insert insert = new Insert();	
				Register registrar = new Register();
				registrar.setName(LoginScene.this.txtUser.getText());
				insert.insertRegister(registrar);
			/*
			 */
			}
		});
		this.passwordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					logar();
				}
			}
		});
		this.rightContainer.setSpacing(40);
		this.imgIcon = new ImageView();
		this.imgIcon.setId("logoImage");
		this.iconPath = new File("/home/jefter66/java-workspace/SCRUM/resources/images/icons/scrum_icon.png");
		try {
			fis = new FileInputStream(iconPath);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.imgIcon.setImage(new Image(fis));
		/*
		 * this is is important
		 */
				this.btnExit.setOnAction(new EventHandler<ActionEvent>() {
				ExitButtonListener exit = new ExitButtonListener(){};
				@Override
				public void handle(ActionEvent event) {
					exit.handle(event);
				}
		});
		this.btnSignUp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				signUpPressed();
				RegistrationForm t;
				try {
					t = new RegistrationForm();
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
		settingAligment();
		transaleAxes();
		addingChildrens();
		componentsPrefSize();
		componentsMaxSize();
		positioningComponentsOnLayout();
		this.setRoot(layout);
	}
	private void logar() {
		try {
			FieldValidation checkField = new FieldValidation();
			if (checkField.isTextFieldEmpty(LoginScene.this.txtUser) || checkField.isPasswordFieldEmpty(LoginScene.this.passwordField)){
				messageLoginValidation.setText("There is nothing typed");
			}
			if (new Login().enterLogin(LoginScene.this.txtUser, LoginScene.this.passwordField)) {
				messageLoginValidation.setText("Right");
				messageLoginValidation.setTextFill(Color.rgb(524, 117, 84));
				/*
					going to be changed by hibernate stuff
				*/
				DbLoadProfileHome.User.setUser(LoginScene.this.txtUser.getText().toString());

				Window.mainStage.setScene(new HomePageScene());
			} else {
				messageLoginValidation.setText("Username or password wrog");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private void cancelPressed() { 
		LoginScene.this.imgIcon.setTranslateX(100);
		LoginScene.	this.imgIcon.setTranslateY(100);
		LoginScene.	this.imgIcon.setFitWidth(400);
		LoginScene.	this.imgIcon.setFitHeight(400);
	}
	private void signUpPressed() { 
		LoginScene.this.form.clear();
		LoginScene.this.btnCancel.setVisible(true);
		LoginScene.	this.imgIcon.setFitWidth(200);
		LoginScene.	this.imgIcon.setFitHeight(200);
		LoginScene.this.imgIcon.setTranslateY(-180);
		LoginScene.this.imgIcon.setTranslateX(-30);
	}
	private void positioningComponentsOnLayout() { 
		LoginScene.this.layout.add(right, 1, 0, 1, 1);
		LoginScene.this.layout.add(imgIcon, 0, 0, 1, 1);
		LoginScene.this.layout.add(btnSignUp, 1, 1, 1, 1);
		LoginScene.this.layout.add(btnCancel, 1, 1, 1, 1);
		LoginScene.this.layout.add(lblWelcome, 1, 0, 1, 1);
		LoginScene.this.layout.add(lblSignUp, 0, 1, 1, 1);
		LoginScene.this.layout.add(left, 0, 0, 1, 1);
	}
	private void transaleAxes() { 
		LoginScene.this.imgIcon.setTranslateX(100);
		LoginScene.this.lblWelcome.setTranslateX(-450);
		LoginScene.this.lblSignUp.setTranslateY(-400);
		LoginScene.this.btnCancel.setTranslateX(-30);
		LoginScene.this.imgIcon.setTranslateX(100);
		LoginScene.this.btnSignUp.setTranslateX(-30);
		LoginScene.this.lblSignUp.setTranslateX(400);
		LoginScene.this.right.setTranslateX(0);

		LoginScene.this.imgIcon.setTranslateY(100);
		LoginScene.this.lblWelcome.setTranslateY(-200);
		LoginScene.this.btnSignUp.setTranslateY(-15);
		LoginScene.this.btnCancel.setTranslateY(-15);
		LoginScene.this.imgIcon.setTranslateY(100);
		LoginScene.this.btnSignUp.setTranslateY(-400);
		LoginScene.this.btnCancel.setTranslateY(-400);
		LoginScene.this.left.setTranslateY(100);
	}
	private void componentsMaxSize() { 
		LoginScene.this.layout.setMaxHeight(500);
		LoginScene.this.layout.setMaxWidth(500);
		LoginScene.this.layout.setMinWidth(500);
		LoginScene.this.layout.setMinHeight(500);
		LoginScene.this.btnCancel.setMaxSize(150,100);
		LoginScene.this.btnSignUp.setMaxSize(150, 100);
		LoginScene.this.btnLogin.setMaxSize(200, 100);
		LoginScene.this.btnSignUp.setMaxWidth(90);
		LoginScene.this.btnCancel.setMaxWidth(100);
		LoginScene.this.left.setMaxWidth(800);	
	}
	private void componentsPrefSize() { 
		LoginScene.this.imgIcon.setFitWidth(400);
		LoginScene.this.imgIcon.setFitHeight(400);
		LoginScene.this.left.setPrefWidth(650);
		LoginScene.this.left.setPrefHeight(600);
		LoginScene.this.rightContainer.setPrefWidth(600);
		LoginScene.this.right.setPrefHeight(00);
		LoginScene.this.leftRegistration.setPrefWidth(150);
		LoginScene.this.vbLogin.setPrefWidth(400);
	}
	private void addingChildrens() { 
		LoginScene.this.vbLogin.getChildren().addAll(lblSignIn, lblUser, txtUser, lblPassword, passwordField,messageLoginValidation, forgotPassword, btnLogin, btnExit);
		LoginScene.this.right.getChildren().add(rightContainer);
		LoginScene.this.rightContainer.getChildren().addAll(line, vbLogin);
		LoginScene.this.left.getChildren().addAll(leftRegistration);
	}
	private void settingAligment() { 
		LoginScene.this.vbLogin.setAlignment(Pos.CENTER);
		LoginScene.this.left.setAlignment(Pos.CENTER_LEFT);
		LoginScene.this.left.setAlignment(Pos.CENTER);
		LoginScene.this.rightContainer.setAlignment(Pos.CENTER);
		LoginScene.this.vbLogin.setSpacing(20);
		LoginScene.this.txtUser.setAlignment(Pos.CENTER);
		LoginScene.this.passwordField.setAlignment(Pos.CENTER);
	}
	
	
}
