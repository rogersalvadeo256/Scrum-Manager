package view.popoups;

import java.io.IOException;

import application.controllers.ActivateAccountController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Window;

public class ActivateAccount extends StandartLayoutPOPOUP {


	private Label lblInformation;
	private TextField txtUserNameOrEmail;
	private Button btnQuery, btnCancel;

	private ActivateAccountController controller;


	public ActivateAccount(Window owner) throws IOException {
		super(owner);

		this.controller = new ActivateAccountController(this, this.layout);

		this.lblInformation= new Label("Digite seu login ou email");
		this.txtUserNameOrEmail = new TextField();
		this.txtUserNameOrEmail.setPromptText("Informe seu login ou email");
		this.btnQuery = new Button("Enviar");
		this.btnCancel= new Button("Cancelar");

		this.layout.getChildren().addAll(lblInformation,txtUserNameOrEmail, btnQuery,btnCancel);
		this.layout.setAlignment(Pos.CENTER);
		
		
		this.txtUserNameOrEmail.setOnKeyPressed(e -> {

			try {
				this.controller.setTextFieldEvent(e, txtUserNameOrEmail.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		});

		this.btnQuery.setOnAction(e -> {
			try {
				this.controller.setButtonQueryEvent(e, txtUserNameOrEmail.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});


		this.btnCancel.setOnAction(e ->{
			ActivateAccount.this.close();
		});
		return;
	}

}





























