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
      public ProjectInvitePOPOUP(Stage owner) throws IOException{ 
            super(owner);


       	
//  		this.layout.getStylesheets().add(this.getClass().getResource("/css/INVITATION_COMPONENT.css").toExternalForm());
//  		this.layout.applyCss();
  		            
            this.controller = new ProjectInviteController(this, this.layout);

            this.controller.drawInvites(this, layout);
            
            
            this.initStyle(StageStyle.DECORATED);
            
      }



}