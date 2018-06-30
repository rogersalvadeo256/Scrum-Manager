package Scenes;

import java.sql.SQLException;

import Database.DatabaseConnection;
import Main.Window;
import Validations.ValidationLogin;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/*
 * LoginScreen.java
 * 
 * Created on: 28 jun de 2018
 * 		Autor: jefter66
 * 
 */

public class LoginScreen extends Scene {

	/*
	 * the label message will be hidden, and just going to show a message if the
	 * login requests data are right, otherwise, show a error message
	 */
	private final Label messageLoginValidation;

	/*
	 * labels that will be setted up close to the fields email or user and password
	 */
	private Label lblUser, lblPassword;
	private TextField txtUser;
	private PasswordField passwordField;

	private Button btnLogin, btnExit, btnSingIn;

	/*
	 * the scene have a GridPane like the layout
	 */
	private GridPane layout;

	/*
	 * Hyperlink will lead the user to a screen in order to recover his profile
	 */
	private Hyperlink forgotPassword;

	private Insets borders;

	/*
	 * this is a class created by my, use to make some functions with the database,
	 * in here, will be used to make validation of login
	 */
	private DatabaseConnection connect;

	public LoginScreen() throws ClassNotFoundException, SQLException {
		/*
		 * i don't know why, but had to be this super(some object()); in the first line
		 * of the construct
		 */
		super(new HBox());
		/*
		 * 
		 */
		String css = this.getClass().getResource("/cssStyles/style.css").toExternalForm();
		this.getStylesheets().add(css);

		/*
		 * instantiation of the object declared upon
		 */
		this.layout = new GridPane();

		this.messageLoginValidation = new Label("");

		this.forgotPassword = new Hyperlink("Forgot my password");

		this.connect = new DatabaseConnection();

		this.lblUser = new Label("USERNAME OR EMAIL");
		this.txtUser = new TextField();

		this.lblPassword = new Label("PASSWORD");
		this.passwordField = new PasswordField();

		this.btnLogin = new Button("LOGIN");
		this.btnExit = new Button("EXIT");
		this.btnSingIn = new Button("SIGN UP");

		/*
		 * the main class of the program have a Stage that is static, so, i can access
		 * him in every place how i want i'm setting the size of my stage for this scene
		 * that is being building
		 */
		Window.mainStage.setWidth(500);
		Window.mainStage.setHeight(500);

		/*
		 * below is setting up the position of the attributes that go to the screen
		 */

		this.layout.add(lblUser, 0, 2, 1, 1);
		this.layout.add(txtUser, 1, 2, 2, 1);

		this.layout.add(lblPassword, 0, 3, 1, 1);
		this.layout.add(passwordField, 1, 3, 2, 1);

		this.layout.add(messageLoginValidation, 0, 4, 1, 1);

		this.layout.add(forgotPassword, 1, 4, 1, 1);

		this.layout.add(btnLogin, 0, 5, 5, 1);
		this.layout.add(btnSingIn, 0, 6, 5, 1);
		this.layout.add(btnExit, 0, 7, 5, 1);
		/*
		 * this method below will allow me make the button size bigger
		 */
		this.btnSingIn.setMaxWidth(500);
		this.btnLogin.setMaxWidth(500);
		this.btnExit.setMaxWidth(500);
		/*
		 * this is here because of the CSS
		 */
		this.btnExit.setId("exitbtn");

		/*
		 * the code below are the functions of the buttons in the screen
		 * 
		 */

		this.btnLogin.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				 Window.mainStage.setScene(new Home());

				/*
				 * the contend of the textfield and passwordfield are used in a database query
				 * that check if it is right if the return boolean value is true, so, go switch
				 * scene to the homepage of the software, else, will show the label message in
				 * red, saying that the data is wrong
				 */

				try {
					ValidationLogin checkForEmptyField = new ValidationLogin(LoginScreen.this.txtUser,
							LoginScreen.this.passwordField);

					/*
					 * checking for empty fields, return the label with a error mensage
					 */
					if (checkForEmptyField.checkForEmptyField()) {
						messageLoginValidation.setText("There is nothing typed");
						messageLoginValidation.setTextFill(Color.rgb(210, 39, 30));
					}
					/*
					 * connect is the object of the class DatabaseConnection
					 */

					if (connect.enterLogin(LoginScreen.this.txtUser, LoginScreen.this.passwordField)) {
						messageLoginValidation.setText("Right");
						messageLoginValidation.setTextFill(Color.rgb(524, 117, 84));
						/*
						 * to switch scene had to access the mainStage, that is static here is changing
						 * the scene to the homepage
						 */
						Window.mainStage.setScene(new Home());

					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		/*
		 * the button exit implement a interface that only serves to exit the program
		 */
		this.btnExit.setOnAction(actionEvent -> Platform.exit());

		/*
		 * button sing in open a scene who contains a registration form to create a
		 * profile to use the software
		 */
		this.btnSingIn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					/*
					 * change the scene to the RegistrationFom scene, a class that i made
					 */
					Window.mainStage.setScene(new RegistrationForm());
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
		/*
		 * the insents are the spacing that i put i the padding of the layout
		 * 
		 * i had to use a Hgap set a horizontal space between the stuffs in the scene,
		 * the same for the Vgap, but vertical
		 */
		this.borders = new Insets(10);
		this.layout.setHgap(10); // espaço colocado horizontalmente
		this.layout.setVgap(10); // espaço colocado verticalmente
		this.layout.setAlignment(Pos.CENTER);
		this.layout.setPadding(borders);

		this.layout.setMaxHeight(500);
		this.layout.setMaxWidth(500);
		this.layout.setMinWidth(250);
		this.layout.setMinHeight(250);

		this.setRoot(layout);

	}

}
