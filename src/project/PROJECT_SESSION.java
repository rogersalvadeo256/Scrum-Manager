package project;

import java.util.ArrayList;

import db.pojos.PROJECT;
import widgets.designComponents.projectContents.TaskComponent;

public class PROJECT_SESSION {

	
	private static PROJECT project;
	
	public static void initSession (PROJECT p) { 
		PROJECT_SESSION.project = p;
	}
	public static void closeSession () { 
		PROJECT_SESSION.project = null;
	}
	
	public static PROJECT getProject() { 
		return PROJECT_SESSION.project;
	}
	public static ArrayList<TaskComponent> projectTasks( ) { 
		
		return new ArrayList<TaskComponent>();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
