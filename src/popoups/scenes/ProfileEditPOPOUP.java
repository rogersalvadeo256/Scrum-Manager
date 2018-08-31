package popoups.scenes;

import javafx.scene.control.TextArea;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfileEditPOPOUP extends Stage {

	VBox layout = new VBox();
	HBox hbxNm = new HBox(), hbxB = new HBox(), hbxP = new HBox();
	ImageView imgProfile;
	Label lblName, lblBio, lblPass;
	TextField txtName;
	TextArea txtBio;
	PasswordField txtPass;
	Button btnSave;

	public ProfileEditPOPOUP() {

		imgProfile = new ImageView();

		lblName = new Label("Nome");
		lblBio = new Label("Bio:");
		lblPass = new Label("Senha");

		txtName = new TextField();
		txtName.setPrefWidth(150);
		txtBio = new TextArea();
		txtBio.setPrefWidth(300);
		txtBio.setPrefHeight(100);
		txtPass = new PasswordField();
		txtPass.setPrefWidth(150);

		btnSave = new Button("Slavar");

		hbxNm.getChildren().addAll(lblName, txtName);
		hbxNm.setSpacing(15);
		hbxB.getChildren().addAll(lblBio, txtBio);
		hbxB.setSpacing(15);
		hbxP.getChildren().addAll(lblPass, txtPass);
		hbxP.setSpacing(15);

		layout.getChildren().addAll(imgProfile, hbxNm, hbxB, hbxP, btnSave);
		Scene cena = new Scene(layout);
		this.setScene(cena);
		this.show();
	}

}
