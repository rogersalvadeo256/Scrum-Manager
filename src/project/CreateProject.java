package project;

import java.util.List;

import db.pojos.PROJECT;
import db.pojos.PROJECT_MEMBER;
import statics.DB_OPERATION;

public class CreateProject {

	public void createProject(PROJECT project, PROJECT_MEMBER member) {
		insertProject(project, member);
		
		
	}

	
	

	private void insertProject(PROJECT p, PROJECT_MEMBER member) {
		DB_OPERATION.PERSIST(p);
		DB_OPERATION.PERSIST(member);

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public void createProject(PROJECT project, List<PROJECT_MEMBER> listMember) {
		insertProject(project, listMember);
	
	}
	
	private void insertProject(PROJECT p, List<PROJECT_MEMBER> listMember) {
		DB_OPERATION.PERSIST(p);
		DB_OPERATION.PERSIST(listMember);
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
