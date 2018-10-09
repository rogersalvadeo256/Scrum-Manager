package widgets.designComponents.projectContents;

import java.io.FileInputStream;
import java.io.IOException;

import db.pojos.PROJECT;
import db.pojos.USER_PROFILE;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import statics.PROFILE_IMG;

/**
 * button to accept , to refuse button to see more information *.*
 */

public class HBProjectInvitationComponent extends HBox {

   private VBox vbContent;
   private HBox hbButtons, hbLabelContent;
   private HBox hbAboutProject;
   private Button btnAccept, btnRefuse, btnAbout;
   private ImageView imgInvitedBy;
   private Label lblInvitedBy, lblProjectName;
   private Text lblAbout;

   public HBProjectInvitationComponent(USER_PROFILE invitedBy, PROJECT project) throws IOException {

      this.vbContent = new VBox();
      this.btnAbout = new Button("Sobre o projeto..");
      this.btnAccept = new Button("Aceitar");
      this.btnRefuse = new Button("Recusar");

      this.lblInvitedBy = new Label(invitedBy.getName() + " está te convidando para fazer parte do projeto");
      this.lblProjectName = new Label(project.getProjName());

      this.lblAbout = new Text(project.getProjDescription());

      boolean profileImage = invitedBy.getPhoto() == null ? true : false;
      if (profileImage)
         this.imgInvitedBy = new ImageView(
               new Image(new FileInputStream("/resources/images/icons/profile_picture.png")));
      else
         this.imgInvitedBy.setImage(PROFILE_IMG.getImage(invitedBy));

      this.hbLabelContent = new HBox();
      this.hbButtons = new HBox();

      initialLayout();

      this.btnAbout.setOnAction(e -> {
         showAboutProject();
      });   


   }
   private void showAboutProject() {

      boolean isNull = this.hbAboutProject == null ? true : false;

      if (isNull) {
         this.hbAboutProject = new HBox();
         this.hbAboutProject.getChildren().add(this.lblAbout);
         this.vbContent.getChildren().clear();
         this.vbContent.getChildren().addAll(this.hbLabelContent, this.hbButtons);
         this.vbContent.getChildren().add(hbAboutProject);
         return;
      }
      initialLayout();

   }
   private void initialLayout() {

      this.getChildren().add(this.imgInvitedBy);
      this.getChildren().add(this.vbContent);

      this.hbLabelContent.getChildren().addAll(this.lblInvitedBy, this.lblProjectName);
      this.vbContent.getChildren().addAll(this.hbLabelContent, this.hbButtons);
      this.hbButtons.getChildren().addAll(this.btnAbout, this.btnAccept, this.btnRefuse);
   }

   public void setAcceptEvent (EventHandler<ActionEvent> e  ){
      this.btnAccept.setOnAction(e);
   }  
   public void setRefuseEvent (EventHandler<ActionEvent> e) { 
      this.btnRefuse.setOnAction(e);
   }
}
