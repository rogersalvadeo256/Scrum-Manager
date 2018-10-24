package widgets.designComponents.projectContents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import db.pojos.PROJECT;
import db.pojos.USER_PROFILE;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import widgets.designComponents.profileContents.HBProfileInvitedBy;

/**
 * button to accept , to refuse button to see more information *.*
 */

public class HBProjectInvitationComponent extends HBox {
	
	private VBox vbContent, vbProjectContent, vbLabelsContent;
	private HBox hbButtons, hbProjectName, hbProject;
	private HBox hbAboutProject;
	private Button btnAccept, btnRefuse, btnAbout;
	private Label lblProjectName;
	private Text lblAbout;
	private HBProfileInvitedBy invitedByContent;
	public HBProjectInvitationComponent(USER_PROFILE invitedBy, PROJECT project) throws IOException {
		
		this.btnAccept = new Button();
		this.btnRefuse = new Button();
	
		
		final int SIZE = 30;
		ImageView icon_a = new ImageView();
		icon_a.setImage(new Image(new FileInputStream(new File("resources/images/icons/accept.png"))));
		icon_a.setFitHeight(SIZE);
		icon_a.setFitWidth(SIZE);
		this.btnAccept.setGraphic(icon_a);
		
		ImageView icon_r = new ImageView();
		icon_r.setImage(new Image(new FileInputStream(new File("resources/images/icons/refuse.png"))));
		icon_r.setFitHeight(SIZE);
		icon_r.setFitWidth(SIZE);
		this.btnRefuse.setGraphic(icon_r);

		this.getStylesheets().add(this.getClass().getResource("/css/PROJECT_INVITE_COMPONENT.css").toExternalForm());
		this.applyCss();
		
		this.lblProjectName = new Label(project.getProjName());
		
		this.lblAbout = new Text(project.getProjDescription());
		
		this.invitedByContent = new HBProfileInvitedBy(invitedBy);
		
		
		
		
		initialLayout();
	}
	private void showAboutProject() {

		boolean isNull = this.hbAboutProject == null ? true : false;
		
		if (isNull) {
			
			this.hbAboutProject = new HBox();
			this.hbAboutProject.getChildren().add(lblAbout);
			this.vbLabelsContent.getChildren().remove(btnAbout);
			this.vbLabelsContent.getChildren().add(hbAboutProject);			
			this.vbLabelsContent.getChildren().add(btnAbout);
			
			
			
			this.lblAbout.setWrappingWidth(vbLabelsContent.getWidth());
			
			this.btnAbout.setText("Mostrar menos");
			
			
			this.btnAbout.setOnAction(e ->{
				this.getChildren().clear();
				this.vbContent = null;
				this.hbProjectName = null;
				this.vbProjectContent = null;
				this.vbLabelsContent = null;
				this.hbButtons=null;
				this.hbAboutProject = null;
				
				this.initialLayout();
			});
			return;
		}
		initialLayout();
	}
	private void initialLayout() {
		
		this.getChildren().clear();
		
		this.btnAbout = new Button("Sobre o projeto");
		this.hbProjectName = new HBox();
		this.hbButtons = new HBox();
		this.vbProjectContent =new VBox();
		this.vbLabelsContent=new VBox();
		this.hbProject =new HBox();
		this.vbContent = new VBox();
		
		this.hbButtons.setSpacing(20);
		this.hbButtons.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		this.setAlignment(Pos.CENTER);
		this.vbLabelsContent.setSpacing(5);
		this.vbLabelsContent.setAlignment(Pos.CENTER);
	
		
		this.hbProjectName.getChildren().addAll(new Label ("Projeto : ") ,lblProjectName);
		this.vbLabelsContent.getChildren().add(hbProjectName);
		this.vbLabelsContent.getChildren().add(btnAbout);

		this.hbProject.getChildren().addAll(this.invitedByContent,vbLabelsContent);
		this.vbProjectContent.getChildren().add(hbProject);
		
		this.vbContent.getChildren().addAll(this.vbProjectContent ,this.hbButtons);
		this.hbButtons.getChildren().addAll(this.btnAccept, this.btnRefuse);
		this.getChildren().add(this.vbContent);

		
		
		this.btnAbout.setOnAction(e -> {
			showAboutProject();
		});
		
		
		
		
	}
	
	
	public void setAcceptEvent(EventHandler<ActionEvent> e) {
		this.btnAccept.setOnAction(e);
	}
	public void setRefuseEvent(EventHandler<ActionEvent> e) {
		this.btnRefuse.setOnAction(e);
	}
}



































