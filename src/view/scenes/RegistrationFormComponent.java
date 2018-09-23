package view.scenes;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import application.controllers.RegistrationFromSceneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RegistrationFormComponent extends VBox {

	private TextField txtName, txtUserName, txtEmail, txtQuestion, txtAnswer;
	private PasswordField txtPasswordField, txtPasswordConfirmation;
	public Button btnRegister, btnCancel;
	private ArrayList<TextField> field;
	private ArrayList<PasswordField> passwordField;
	private ArrayList<String> fieldName;
	private ArrayList<String> confirmationMessage;
	private HBox hbButtons;
	private RegistrationFromSceneController controller;

	public RegistrationFormComponent() throws ClassNotFoundException, SQLException {

		this.fieldName = new ArrayList<String>();
		this.field = new ArrayList<TextField>();
		this.passwordField = new ArrayList<PasswordField>();
		
		
		this.txtName = new TextField();
		this.txtName.setPromptText("Name");
		this.txtUserName = new TextField();
		this.txtUserName.setPromptText("UserName");
		this.txtEmail = new TextField();
		this.txtEmail.setPromptText("Email");
		this.txtQuestion = new TextField();
		this.txtQuestion.setPromptText("Pergunta de segurança");
		this.txtAnswer = new TextField();
		this.txtAnswer.setPromptText("Resposta");
		this.txtPasswordField = new PasswordField();
		this.txtPasswordField.setPromptText("Senha");
		this.txtPasswordConfirmation = new PasswordField();
		this.txtPasswordConfirmation.setPromptText("Confirmação da senha");

		this.txtName.setMaxWidth(300);
		this.txtUserName.setMaxWidth(300);
		this.txtEmail.setMaxWidth(300);
		this.txtPasswordField.setMaxWidth(300);
		this.txtPasswordConfirmation.setMaxWidth(300);
		this.txtName.setAlignment(Pos.CENTER);
		this.txtUserName.setAlignment(Pos.CENTER);
		this.txtEmail.setAlignment(Pos.CENTER);
		this.txtPasswordField.setAlignment(Pos.CENTER);
		this.txtPasswordConfirmation.setAlignment(Pos.CENTER);
		this.txtAnswer.setAlignment(Pos.CENTER);
		this.txtQuestion.setAlignment(Pos.CENTER);

		
		
		this.hbButtons = new HBox(10);
		this.btnRegister = new Button("Cadastrar");
		this.btnCancel = new Button();
		this.btnCancel.setId("btnCancel");

		this.hbButtons.getChildren().addAll(btnCancel, btnRegister);
		this.hbButtons.setAlignment(Pos.CENTER);

		this.field.add(txtName);
		this.field.add(txtEmail);
		this.field.add(txtUserName);
		this.field.add(txtQuestion);
		this.field.add(txtAnswer);
		this.passwordField.add(txtPasswordField);
		this.passwordField.add(txtPasswordConfirmation);

		this.fieldName.add("nome");
		this.fieldName.add("email");
		this.fieldName.add("nome de usuario");
		this.fieldName.add(" pergunta de segurança");
		this.fieldName.add(" resposta de segurança");

		this.confirmationMessage = new ArrayList<String>();

		this.confirmationMessage.add("Cadastro realizado com sucesso");
		this.confirmationMessage.add("Voce está cadastrado no Scrum Manager");
		this.confirmationMessage.add("boa");

		this.controller = new RegistrationFromSceneController();

		this.btnRegister.setOnAction(e -> {
			try {
				this.controller.setEventBtnLogin(e, field, fieldName, passwordField, txtName, confirmationMessage,
						txtUserName, txtEmail, txtQuestion, txtAnswer, txtPasswordField, txtPasswordConfirmation);
			} catch (ClassNotFoundException | FileNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		});

		this.txtPasswordConfirmation.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				try {
					this.controller.setEventPasswordField(e, field, fieldName, passwordField, txtName, confirmationMessage,
							txtUserName, txtEmail, txtQuestion, txtAnswer, txtPasswordField, txtPasswordConfirmation);
				} catch (ClassNotFoundException | FileNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		this.getChildren().addAll(new Label("Cadastro"),txtName,txtUserName, txtEmail,
				txtQuestion,txtAnswer);
		this.getChildren().addAll(txtPasswordField,txtPasswordConfirmation,
				hbButtons);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(25	);
	}
	public void setEventCancel (EventHandler<ActionEvent> e) {
		this.btnCancel.setOnAction(e);
	}


	
	
}




























