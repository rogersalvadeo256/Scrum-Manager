package application.controllers;

import java.io.IOException;

import javax.persistence.EntityManager;

import db.hibernate.factory.Database;
import db.pojos.Profile;
import db.pojos.UserRegistration;
import db.util.GENERAL_STORE;
import db.util.SESSION;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import scenes.popoups.ProfileEditPOPOUP;
import validation.CheckEmptyFields;
import widgets.alertMessage.CustomAlert;

public class EditProfileController {

	private EntityManager em;
	private CheckEmptyFields check;

	public EditProfileController() {
		this.check = new CheckEmptyFields();
	}
	public void setEventAdvancedOptions(ActionEvent e, ProfileEditPOPOUP screen, VBox layout,
																					HBox hbChangeAnswer,
																					HBox hbChangeQuestion,
																					HBox hbButtons,
																					Button btnBack) {
		layout.getChildren().clear();
		layout.getChildren().add(hbChangeQuestion);
		layout.getChildren().add(hbChangeAnswer);
		layout.getChildren().add(hbButtons);
		
		backToNormalOptions(e, btnBack, screen);

	}

	public void setEventFinish(ActionEvent e, TextField txtName, TextArea txtBio, PasswordField txtCurrentPassword,
																					PasswordField txtNewPassword) {
		UserRegistration u = SESSION.getUserLogged();
		Profile p = SESSION.getProfileLogged();
		if (em == null)
			em = Database.createEntityManager();

		if (!check.isPasswordFieldEmpty(txtCurrentPassword) && !check.isPasswordFieldEmpty(txtNewPassword)) {
			if (txtCurrentPassword.getText().equals(SESSION.getUserLogged().getPassword())) {
				if (txtNewPassword.getText().length() >= 8) {
					String np = txtNewPassword.getText();
					em.getTransaction().begin();
					u.setPassword(np);
					em.merge(u);
					em.getTransaction().commit();
					em.clear();
					// this.message.add(updates.PASSWORD);
				} else {
					new CustomAlert(AlertType.ERROR, "Erro", "Senha muito curta", "A senha deve conter no minimo 8 caracteres");
					return;
				}
			}
		}

		if (!check.isTextAreaEmpty(txtBio) || check.isTextFieldEmpty(txtName)) {
			if (!check.isTextFieldEmpty(txtName)) {
				String nn = txtName.getText();
				em.getTransaction().begin();
				p.setName(nn);
				em.merge(p);
				em.getTransaction().commit();
				em.clear();
			}
			if (!check.isTextAreaEmpty(txtBio)) {
				em.getTransaction().begin();
				p.setBio(txtBio.getText());
				em.merge(p);
				em.getTransaction().commit();
				em.clear();
			}
			SESSION.UPDATE_SESSION();
		}
		try {
			GENERAL_STORE.updateComponentsHOME();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return;
	}

	public void setEventBack(ActionEvent e, ProfileEditPOPOUP screen) {
		screen.close();
	}

	/**
	 * the boolean param stands for if the update most be done in the question or in
	 * the answare, if true = question
	 * 
	 * @param e
	 * @param content
	 * @param txtNewSecutiry
	 * @param btnChange
	 * @param btnChangeQuestion 
	 * @param question
	 */
	public void setEventChangeSecurity(ActionEvent e,ProfileEditPOPOUP screen, HBox content, TextField txtNewSecutiry,
																					Button btnChange,
																					Button btnBack, boolean question) {
		Button btnCancel = new Button("Cancelar");
		content.getChildren().clear();
		content.getChildren().addAll(txtNewSecutiry, btnChange);
		btnChange.setText("Finalizar");
		
		// btnChange.setOnAction(); /* update the database where */
	
		content.getChildren().add(btnCancel);
		
		if(question)txtNewSecutiry.setPromptText(SESSION.getUserLogged().getSecurityQuestion().toString());
		else txtNewSecutiry.setPromptText(SESSION.getUserLogged().getSecurityQuestionAnswer().toString());
		
		btnCancel.setOnAction(e1 ->{
			content.getChildren().clear();
			if(question)content.getChildren().addAll(new Label(SESSION.getUserLogged().getSecurityQuestion().toString(), btnChange));
			else content.getChildren().addAll(new Label(SESSION.getUserLogged().getSecurityQuestionAnswer().toString(), btnChange));
			btnChange.setText("Mudar");
		});
		backToNormalOptions(e,btnBack,screen);
	}
	private void backToNormalOptions(ActionEvent e ,Button btnBack, ProfileEditPOPOUP screen) { 
		btnBack.setOnAction(e1 -> {
			screen.init();
			/*
			 * GAMBIARRA A GENTE ACEITA
			 */
			btnBack.setOnAction(e2-> { this.setEventBack(e, screen);});
		});
	}
}

































































