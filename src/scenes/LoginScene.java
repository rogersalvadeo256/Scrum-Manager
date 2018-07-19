package scenes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import Database.DbLoadProfileHome;
import Database.Login;
import auto.instance.objects.RegistrationForm;
import events.ExitButtonListener;
import fields.validation.FieldValidation;
import hibernatebook.annotations.Register;
import hibernatebook.entity.provider.EntityProvider;
import javafx.application.Platform;
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
import javafx.scene.text.Font;
import main.Window;

/*
 * LoginScreen.java
 * 
 * Created on: 28 jun de 2018
 * 		Autor: jefter66
 * 
 */

public class LoginScene extends Scene {

	private final Label messageLoginValidation;

	private Label lblUser, lblSignIn, lblPassword, lblWelcome, lblCadastre;
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
	
	FileInputStream fis;



	private Line line;


	private ArrayList<RegistrationForm> form;

	private GridPane layout;

	public LoginScene() throws ClassNotFoundException, SQLException {
		super(new HBox());
	
		Window.mainStage.setWidth(1200);
		Window.mainStage.setHeight(600);	


		String css = this.getClass().getResource("/cssStyles/loginScene.css").toExternalForm();
		this.getStylesheets().add(css);
		this.layout = new GridPane();

	
		this.left = new VBox();
		this.leftRegistration = new VBox();
		this.form = new ArrayList<RegistrationForm>();


		this.right = new HBox();
		this.vbLogin = new VBox();

		this.rightContainer = new HBox();

		
		this.messageLoginValidation = new Label("");
		this.messageLoginValidation.setId("messageWrongData");
		this.forgotPassword = new Hyperlink("Esqueci minha senha");
		this.lblUser = new Label("Username");
		this.txtUser = new TextField();
		this.txtUser.setAlignment(Pos.CENTER);
		this.txtUser.setPromptText("Username");
		this.lblPassword = new Label("Senha");
		this.passwordField = new PasswordField();
		this.passwordField.setAlignment(Pos.CENTER);
		this.passwordField.setPromptText("Digite sua senha");
		this.lblWelcome = new Label("Bem Vindo Ao Scrum Manager");
		this.lblWelcome.setFont(new Font(30));
		this.lblCadastre = new Label("Registre-se Agora");
		this.lblCadastre.setFont(new Font(20));
		this.lblSignIn = new Label("SIGN IN");
		this.lblSignIn.setFont(Font.font(30));
		this.lblSignIn.setTranslateY(-30);

		this.btnLogin = new Button("LOGIN");
		this.btnExit = new Button("SAIR");
		this.btnExit.setId("exitbtn");
		this.btnSignUp = new Button("SIGN UP");
		this.btnSignUp.setId("btnSingUp");
		this.btnCancel = new Button("CANCELAR");
		this.btnCancel.setVisible(false);
		
		this.btnSignUp.setMaxWidth(500);
		this.btnLogin.setMaxWidth(500);
		this.btnExit.setMaxWidth(500);

		

		this.line = new Line();
		this.line.setEndX(0.0f);
		this.line.setEndY(350.0f);
		this.line.setStroke(Color.valueOf("#ffff"));

	
		

		this.btnLogin.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				
				EntityProvider provedor = new EntityProvider();
				Register registrar = new Register();
				
			
				registrar.setName(LoginScene.this.txtUser.getText());
				provedor.entityManager.getTransaction().begin();
				provedor.entityManager.persist(registrar);
				provedor.entityManager.getTransaction().commit();
				provedor.entityManager.close();
				
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

		
		
		this.layout.add(right, 1, 0, 1, 1);

		this.vbLogin.getChildren().addAll(lblSignIn, lblUser, txtUser, lblPassword, passwordField,messageLoginValidation, forgotPassword, btnLogin, btnExit);
		this.vbLogin.setAlignment(Pos.CENTER);
		this.vbLogin.setSpacing(20);
		this.vbLogin.setPrefWidth(400);

		this.rightContainer.getChildren().addAll(line, vbLogin);
		this.rightContainer.setSpacing(40);
		this.rightContainer.setPrefWidth(600);
		this.rightContainer.setAlignment(Pos.CENTER);
		this.right.setTranslateX(0);
		this.right.getChildren().add(rightContainer);
		this.right.setPrefHeight(00);
		this.right.getStyleClass().add("hbox");
		this.right.setId("rightSide");

		this.layout.add(left, 0, 0, 1, 1);

	
		this.imgIcon = new ImageView();
		this.imgIcon.getStyleClass().add("logoImage");
		this.imgIcon.setId("logoImage");
		this.imgIcon.setFitWidth(200);
		this.imgIcon.setFitHeight(200);
		
		/*
		this.iconPath = new File("/home/jefter66/java-workspace/SCRUM/images/logo/scrum_icon.png");

		try {
			fis = new FileInputStream(iconPath);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.imgIcon.setImage(new Image(fis));
		*/

		this.btnSignUp.setTranslateY(-15);
		this.btnCancel.setTranslateY(-15);
		
		
		this.layout.add(imgIcon, 0, 0, 1, 1);
		this.imgIcon.setTranslateY(-180);
		this.layout.add(lblWelcome, 1, 0, 1, 1);
		this.lblWelcome.setTranslateY(-200);
		this.lblWelcome.setTranslateX(-450);

		this.layout.add(lblCadastre, 0, 1, 1, 1);
		lblCadastre.setTranslateY(-400);
		lblCadastre.setTranslateX(400);

		this.layout.add(btnSignUp, 1, 1, 1, 1);
		this.layout.add(btnCancel, 1, 1, 1, 1);
		
		this.btnSignUp.setTranslateY(-400);
		this.btnSignUp.setTranslateX(-30);
		this.btnCancel.setTranslateY(-400);
		this.btnCancel.setTranslateX(-30);
		this.btnSignUp.setMaxWidth(90);
		this.btnCancel.setMaxWidth(100);
	

		
		this.leftRegistration.setPrefWidth(150);
		
		this.left.getChildren().addAll(leftRegistration);
		this.left.setAlignment(Pos.CENTER_LEFT);
		this.left.setTranslateY(100);
		this.left.setAlignment(Pos.CENTER);
		this.left.setMaxWidth(800);
		this.left.setPrefWidth(650);
		this.left.setPrefHeight(600);
		
		
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

				LoginScene.this.form.clear();
				btnCancel.setVisible(true);

				RegistrationForm t;
				try {
					t = new RegistrationForm();
					form.add(t);
					if (!form.isEmpty()) {
						LoginScene.this.leftRegistration.getChildren().clear();
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
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

			}
		});

		/*
		 * the insents are the spacing that i put i the padding of the layout
		 * 
		 * i had to use a Hgap set a horizontal space between the stuffs in the scene,
		 * the same for the Vgap, but vertical
		 */
		// this.borders = new Insets(10);
		// this.layout.setHgap(10); // espaço colocado horizontalmente
		// this.layout.setVgap(10); // espaço colocado verticalmente
		// this.layout.setAlignment(Pos.CENTER);
		// this.layout.setPadding(borders);

		this.layout.setMaxHeight(500);
		this.layout.setMaxWidth(500);
		this.layout.setMinWidth(500);
		this.layout.setMinHeight(500);

		this.setRoot(layout);

	}

	private void logar() {

		try {
			FieldValidation checkField = new FieldValidation();

			/*
			 * checking for empty fields, return the label with a error mensage
			 */
			if (checkField.isTextFieldEmpty(LoginScene.this.txtUser) || checkField.isPasswordFieldEmpty(LoginScene.this.passwordField)){
				messageLoginValidation.setText("There is nothing typed");
			}
			/*
			 * connect is the object of the class Login() that extends a DatabaseConnection
			 */

			if (new Login().enterLogin(LoginScene.this.txtUser, LoginScene.this.passwordField)) {
				messageLoginValidation.setText("Right");
				messageLoginValidation.setTextFill(Color.rgb(524, 117, 84));
				/*
				 * to switch scene had to access the mainStage, that is static here is changing
				 * the scene to the homepage
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

}
