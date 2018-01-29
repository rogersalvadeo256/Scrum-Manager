package application.controllers;

import java.util.Optional;

import db.pojos.PROJECT;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import statics.DB_OPERATION;
import statics.SESSION;
import view.popoups.NewProjectPOPOUP;
import widgets.alertMessage.CustomAlert;

public class NewProjectSceneController {

	public void actionBack(NewProjectPOPOUP screen) {
		screen.close();
	}

	public void actionFinish(String projectName, String projectDescription) {
		
		if (DB_OPERATION.QUERY("FROM PROJECT WHERE PROJ_NAME = :P_NAME", "P_NAME", projectName).isEmpty() ? true : false){
			PROJECT newProject = new PROJECT();

			newProject.setProjName(projectName);
			newProject.setProjDescription(projectDescription);
			newProject.setProjCreator(SESSION.getProfileLogged().getCod());
			DB_OPERATION.PERSIST(newProject);

			Optional<ButtonType> value = new CustomAlert(AlertType.INFORMATION, "Projeto criado com sucesso", "Projeto iniciado", "Clique em OK para abri a pagina de administração do projeto " + projectName )
					.showAndWait();
			
			
			
			
			return;
		}
		new CustomAlert(AlertType.ERROR, "Erro", "Já existem projetos com esse nome", "O nome escolhido já existe").show();
		return;
	}


	public void actionInviteFriends() {
		/*
		 * open a popoup window with a table image of the friends selected images get the
		 * friend and send a invite
		 */
	}

}

