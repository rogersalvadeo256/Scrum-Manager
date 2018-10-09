package view.popoups;

import java.io.IOException;

import application.controllers.ProjectInviteController;
import javafx.stage.Stage;
public class ProjectInvite extends StandartLayoutPOPOUP { 


      private ProjectInviteController controller;
      /**
       * 
       * components with the informations 
       * 
       * button to go back
       * 
       */
      public ProjectInvite(Stage owner) throws IOException{ 
            super(owner);

            this.controller = new ProjectInviteController(this, this.layout);

            this.controller.drawInvites(this, layout);
            
            
      }



}