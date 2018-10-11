package view.popoups;

import java.io.IOException;

import application.controllers.ProjectInviteController;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class ProjectInvitePOPOUP extends StandartLayoutPOPOUP {
	
	
	private ProjectInviteController controller;
	/**
	 * 
	 * components with the informations
	 * 
	 * button to go back
	 * 
	 */
	public ProjectInvitePOPOUP(Stage owner) throws IOException {
		super(owner);

		this.controller = new ProjectInviteController(this, this.layout);
		
		this.setTitle("Convites para projeto");
		
		this.controller.drawInvites(this, layout);
		
		
		this.initStyle(StageStyle.DECORATED);
		
	}
	
	
}