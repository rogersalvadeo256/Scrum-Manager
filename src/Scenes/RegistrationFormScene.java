package Scenes;

import java.sql.SQLException;

import Database.QuerysDataValidation;
import Main.Window;
import Validations.ValidationOfRegistration;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/*
 * LoginScreen.java
 * 
 * Created on: 28 jun de 2018
 * 		Autor: jefter66
 * 
 */

public class RegistrationFormScene extends Scene {

	private Label lblName, lblUserName, lblEmail, lblPassword, lblConfirmPassword;
	private TextField txtName, txtUserName, txtEmail;
	private PasswordField passwordField, confirmPasswordField;
	private Button btnRegister, btnCancel, btnExit;
	/*
	 * the layout of the scene is made using a single gridpane
	 */
	private GridPane layout;
	private Insets borders;
	/*
	 * this object are used to validate the fields of the form, making a query in
	 * the database and sending a message with the result
	 */
	private ValidationOfRegistration validation;

	public RegistrationFormScene() throws ClassNotFoundException, SQLException {
		super(new HBox());

		/*
		 * setting the CSS style from the scene
		 */
		String css = this.getClass().getResource("/cssStyles/registration.css").toExternalForm();
		this.getStylesheets().add(css);
		/*
		 * instantiate the objects
		 */
		this.layout = new GridPane();

		Window.mainStage.setWidth(600);
		Window.mainStage.setHeight(600);

		this.validation = new ValidationOfRegistration();

		this.lblName = new Label("NAME");
		this.lblUserName = new Label("USERNAME");
		this.lblEmail = new Label("EMAIL");
		this.lblPassword = new Label("PASSWORD");
		this.lblConfirmPassword = new Label("CONFIRM PASSWORD");

		this.txtName = new TextField();
		this.txtEmail = new TextField();
		this.txtUserName = new TextField();

		this.passwordField = new PasswordField();
		this.confirmPasswordField = new PasswordField();

		this.btnExit = new Button("EXIT");
		this.btnRegister = new Button("OK");
		this.btnCancel = new Button("CANCEL");

		this.borders = new Insets(50);

		/*
		 * setting up the position of the attributes that go to the scene
		 */
		this.layout.add(lblName, 0, 0, 1, 1);
		this.layout.add(txtName, 1, 0, 4, 1);

		this.layout.add(lblUserName, 0, 1, 1, 1);
		this.layout.add(txtUserName, 1, 1, 4, 1);

		this.layout.add(lblEmail, 0, 2, 1, 1);
		this.layout.add(txtEmail, 1, 2, 4, 1);

		this.layout.add(lblPassword, 0, 3, 1, 1);
		this.layout.add(passwordField, 1, 3, 4, 1);

		this.layout.add(lblConfirmPassword, 0, 4, 1, 1);
		this.layout.add(confirmPasswordField, 1, 4, 4, 1);

		this.btnRegister.setMaxWidth(700);
		this.btnCancel.setMaxWidth(700);
		this.btnExit.setMaxWidth(700);

		this.layout.add(btnRegister, 0, 5, 6, 1);
		this.layout.add(btnCancel, 0, 6, 4, 1);
		this.btnCancel.setTranslateX(-50);
		this.layout.add(btnExit, 1, 6, 4, 1);
		this.btnExit.setTranslateX(70);

		/*
		 * the actions of the buttons
		 */

		this.btnCancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					/*
					 * the button cancel change the scene for the login scene
					 */
					Window.mainStage.setScene(new LoginScene());
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		/*
		 * setId is used in CSS code the button exit use the same class that the other
		 * button exit in the login screen
		 */
		this.btnExit.setId("exitbtn");
		this.btnExit.setOnAction(actionEvent -> Platform.exit());

		this.btnRegister.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					/*
					 * before the stuff typed from the registration, will be calling a validation,
					 * using a class named ValidationOfRegistration, go to check if are some field
					 * are empty and if the informed data are acceptable (if already exist in the database and etc)
					 */
					validation.validation(RegistrationFormScene.this.txtName, RegistrationFormScene.this.txtEmail,
							RegistrationFormScene.this.txtUserName, RegistrationFormScene.this.passwordField,
							RegistrationFormScene.this.confirmPasswordField);

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		/*
		 * setHgap will set a space in the horizontal , Vgap space in the vertical
		 */
		this.layout.setHgap(5);
		this.layout.setVgap(10);
		this.layout.setAlignment(Pos.CENTER);
		this.layout.setPadding(borders);
		this.layout.setMinSize(400, 200);

		this.setRoot(layout);

	}
}
