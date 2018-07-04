package Scenes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import Database.DbLoadProfileHome;
import Database.Login;
import Main.Window;
import SpecialObjects.RegistrationForm;
import Validations.ValidationLogin;
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
import javafx.scene.text.Font;

/*
 * LoginScreen.java
 * 
 * Created on: 28 jun de 2018
 * 		Autor: jefter66
 * 
 */

public class LoginScene extends Scene {

	/*
	 * the label message will be hidden, and just going to show a message if the
	 * login requests data are right, otherwise, show a error message
	 */
	private final Label messageLoginValidation;

	/*
	 * labels that will be setted up close to the fields email or user and password
	 */
	private Label lblUser, lblSignIn, lblPassword;
	private TextField txtUser;
	private PasswordField passwordField;
	private Button btnLogin, btnExit, btnSignIn;
	private Button btnCancel;
	/*
	 * the scene have a GridPane like the layout
	 */
	private GridPane layout;

	/*
	 * Hyperlink will lead the user to a screen in order to recover his profile
	 */
	private Hyperlink forgotPassword;

	/*
	 * this is a class created by my, use to make some functions with the database,
	 * in here, will be used to make validation of login
	 */

	private VBox left;
	private VBox leftRegistration;
	private HBox hbInformations;
	
	
	private HBox right;

	private HBox rightContainer;
	private VBox vbLogin;

	private ImageView imagem;

	private Label wellcome;


	private ArrayList<RegistrationForm> form;
	
	public LoginScene() throws ClassNotFoundException, SQLException {
		/*
		 * i don't know why, but had to be this super(some object()); in the first line
		 * of the construct
		 */
		super(new HBox());
		/*
		 * 
		 */
		// String css =
		// this.getClass().getResource("/cssStyles/style.css").toExternalForm();
		String css = this.getClass().getResource("/cssStyles/teste.css").toExternalForm();
		this.getStylesheets().add(css);

		this.left = new VBox();
		this.leftRegistration = new VBox();
		this.hbInformations = new HBox();
		
		
		this.right = new HBox();
		this.vbLogin = new VBox();

		this.rightContainer = new HBox();

		this.form = new ArrayList<RegistrationForm>();
		
		
		/*
		 * instantiation of the object declared upon
		 */
		this.layout = new GridPane();

		this.messageLoginValidation = new Label("");

		this.forgotPassword = new Hyperlink("Forgot my password");

		this.lblUser = new Label("USERNAME");
		this.txtUser = new TextField();

		this.lblPassword = new Label("PASSWORD");
		this.passwordField = new PasswordField();

		this.btnLogin = new Button("LOGIN");
		this.btnExit = new Button("EXIT");
		this.btnSignIn = new Button("SIGN UP");
		this.btnCancel = new Button("CANCEL");
		/*
		 * the main class of the program have a Stage that is static, so, i can access
		 * him in every place how i want i'm setting the size of my stage for this scene
		 * that is being building
		 */
		Window.mainStage.setWidth(1200);
		Window.mainStage.setHeight(600);

		/*
		 * below is setting up the position of the attributes that go to the screen
		 */

		// this.layout.add(lblUser, 0, 2, 1, 1);
		// this.layout.add(txtUser, 1, 2, 2, 1);
		//
		// this.layout.add(lblPassword, 0, 3, 1, 1);
		// this.layout.add(passwordField, 1, 3, 2, 1);
		//
		// this.layout.add(messageLoginValidation, 0, 4, 1, 1);
		//
		// this.layout.add(forgotPassword, 1, 4, 1, 1);
		//
		// this.layout.add(btnLogin, 0, 5, 5, 1);
		// this.layout.add(btnSignIn, 0, 6, 5, 1);
		// this.layout.add(btnExit, 0, 7, 5, 1);
		/*
		 * this method below will allow me make the button size bigger
		 */
		this.btnSignIn.setMaxWidth(500);
		this.btnLogin.setMaxWidth(500);
		this.btnExit.setMaxWidth(500);
		/*
		 * this is here because of the CSS
		 */
		this.btnExit.setId("exitbtn");

		this.btnLogin.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				// Window.mainStage.setScene(new newProjectScene());

				/*
				 * the contend of the textfield and passwordfield are used in a database query
				 * that check if it is right if the return boolean value is true, so, go switch
				 * scene to the homepage of the software, else, will show the label message in
				 * red, saying that the data is wrong
				 */
				logar();
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
		
		
		
		this.lblSignIn = new Label("SIGN IN");
		this.lblSignIn.setFont(Font.font(30));

		this.layout.add(right, 1, 0, 1, 1);

		this.lblSignIn.setTranslateY(-60);
		//
//		this.vbLogin.getStyleClass().add("vbox");
//		this.vbLogin.setId("vbLogin");
		this.vbLogin.getChildren().addAll(lblSignIn, lblUser, txtUser, lblPassword, passwordField,	messageLoginValidation, forgotPassword, btnLogin, btnExit);
		this.vbLogin.setAlignment(Pos.CENTER);
		this.vbLogin.setSpacing(20);
		this.vbLogin.setPrefWidth(400);

		
		
//		 this.rightContainer.getStyleClass().add("vbox");
		this.rightContainer.getChildren().add(vbLogin);
		this.rightContainer.setPrefWidth(600);
		this.rightContainer.setAlignment(Pos.CENTER);
		this.right.setTranslateX(0);

		this.right.getChildren().add(rightContainer);
		this.right.setPrefHeight(00);
		
		this.right.getStyleClass().add("hbox");
		this.right.setId("rightSide");
		
		this.layout.add(left, 0, 0, 1, 1);

		this.left.setTranslateX(0);


//		this.left.setId("hboxLeftSide");
		this.left.setPrefHeight(600);
		this.left.setAlignment(Pos.CENTER_LEFT);

		this.wellcome = new Label("Wellcome To \n Scrum Manager");
		this.wellcome.setFont(new Font(30));

		Label cadastre = new Label("Register Now");
		cadastre.setFont(new Font(20));

		this.imagem = new ImageView();
		this.imagem.setFitWidth(200);
		this.imagem.setFitHeight(200);

		File f = new File("/home/jefter66/Área de Trabalho/ptcc/scrum.png");

		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			this.imagem.setImage(new Image(fis));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.left.setPrefWidth(650);

		this.left.setMaxWidth(800);
		this.left.setAlignment(Pos.CENTER);
//
//		this.wellcome.setTranslateY(-200);
//		cadastre.setTranslateY(-100);
//		cadastre.setTranslateX(-220);
//
		btnSignIn.setTranslateY(-15);
		btnCancel.setTranslateY(-15);
//		this.hbInformations.setTranslateY(-220);
//		this.hbInformations.getStyleClass().add("hbox");
		this.hbInformations.setSpacing(10);
		hbInformations.getChildren().addAll(wellcome, cadastre, btnSignIn, btnCancel);
		this.btnCancel.setVisible(false);
		
		
//		this.leftRegistration.getStyleClass().add("vbox");
		this.leftRegistration.setPrefWidth(150);
		this.left.getChildren().addAll(hbInformations,leftRegistration);
		
		
		/*
		 * the code below are the functions of the buttons in the screen
		 * 
		 */

		/*
		 * the button exit implement a interface that only serves to exit the program
		 */
		this.btnExit.setOnAction(actionEvent -> Platform.exit());

		/*
		 * button sing in open a scene who contains a registration form to create a
		 * profile to use the software
		 */
		this.btnSignIn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
					
						
				LoginScene.this.form.clear();
				btnCancel.setVisible(true);
				
				
				RegistrationForm t;
				try {
					t = new RegistrationForm();
					form.add(t);
					if(!form.isEmpty()) {
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
				LoginScene.this.hbInformations.getChildren().get(3).setVisible(false);
				
				
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
			ValidationLogin checkForEmptyField = new ValidationLogin(LoginScene.this.txtUser,
					LoginScene.this.passwordField);

			/*
			 * checking for empty fields, return the label with a error mensage
			 */
			if (checkForEmptyField.checkForEmptyField()) {
				messageLoginValidation.setText("There is nothing typed");
				messageLoginValidation.setTextFill(Color.rgb(210, 39, 30));
			}
			/*
			 * connect is the object of the class Login() that extends a DatabaseConnection
			 */

			if (new Login().enterLogin(LoginScene.this.txtUser, LoginScene.this.passwordField)) {
				// messageLoginValidation.setText("Right");
				// messageLoginValidation.setTextFill(Color.rgb(524, 117, 84));
				/*
				 * to switch scene had to access the mainStage, that is static here is changing
				 * the scene to the homepage
				 */

				String user = LoginScene.this.txtUser.getText().toString();

				Window.mainStage.setScene(new HomePageScene(new DbLoadProfileHome(user)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
