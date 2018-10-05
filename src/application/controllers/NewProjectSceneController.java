package application.controllers;

import db.pojos.PROJECT;
import db.pojos.PROJECT_MEMBER;
import project.CreateProject;
import statics.SESSION;
import view.popoups.NewProjectPOPOUP;

public class NewProjectSceneController {

	private CreateProject crateProject;

	public NewProjectSceneController() {
		this.crateProject = new CreateProject();
	}

	public void actionBack(NewProjectPOPOUP screen) {
		screen.close();
	}

	public void actionFinish(String projectName, String projectDescription, String memberFunction) {

		PROJECT project = new PROJECT();
		project.setProjName(projectName);
		project.setProjDescription(projectDescription);
		project.setProjCreator(SESSION.getProfileLogged().getCod());

		PROJECT_MEMBER member = new PROJECT_MEMBER();

		member.setMbrDtAdd();
		member.setMbrProfileCod(SESSION.getProfileLogged().getCod());
		member.setMbrProject(project.getProjectCod());

		if (!memberFunction.isEmpty())
			member.setMbrFunction(memberFunction); 


			

		// if (DB_OPERATION.QUERY("FROM PROJECT WHERE PROJ_NAME = :P_NAME", "P_NAME",
		// projectName).isEmpty() ? true : false){
		// PROJECT newProject = new PROJECT();
		//
		// newProject.setProjName(projectName);
		// newProject.setProjDescription(projectDescription);
		// newProject.setProjCreator(SESSION.getProfileLogged().getCod());
		// DB_OPERATION.PERSIST(newProject);
		//
		// Optional<ButtonType> value = new CustomAlert(AlertType.INFORMATION, "Projeto
		// criado com sucesso", "Projeto iniciado", "Clique em OK para abri a pagina de
		// administração do projeto " + projectName )
		// .showAndWait();
		// return;
		// }
		// new CustomAlert(AlertType.ERROR, "Erro", "Já existem projetos com esse nome",
		// "O nome escolhido já existe").show();
		// return;
	}

	public void actionInviteFriends() {
		/*
		 * open a popoup window with a table image of the friends selected images get
		 * the friend and send a invite
		 */
	}

}
