package view.popoups;

import java.io.IOException;

import application.controllers.TeamComponentController;
import db.pojos.PROJECT_MEMBER;
import db.pojos.PROJECT_TASK;
import friendship.QUERYs_FRIENDSHIP;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import widgets.designComponents.projectContents.HBColumnsInvite;
import widgets.designComponents.projectContents.ScrumFrame;
import widgets.toaster.Toast;

public class TeamPOPOup extends StandartLayoutPOPOUP {

	VBox layout = new VBox();
	private Label lblProjectName, lblAboutTheProject, lblScrumMaster;
	private HBox hButtons;
	private Button btnCancel, btnInvite;
	
	HBox hbxOrg1 = new HBox(), hbxOrg2 = new HBox(), hbxOrg3 = new HBox(),hbxOrg4 = new HBox();
	
	private VBox content;
	private HBox layoutForInviteComponents;
	private Toast toast;
	private TeamComponentController controller;

	
	public TeamPOPOup(Stage mainStage) throws IOException {
		super(mainStage);
		this.controller = new TeamComponentController(this);

		this.btnCancel = new Button("Cancelar");
		this.btnCancel.setId("back");
		this.btnInvite = new Button("Convidar amigos");
		
		this.lblProjectName = new Label();
		this.lblAboutTheProject = new Label();
		this.lblScrumMaster = new Label();
		
		this.layout.getStylesheets().add(this.getClass().getResource("/css/NEW_PROJECT.css").toExternalForm());
		

		hbxOrg1.getChildren().addAll(lblProjectName, lblAboutTheProject);
		hbxOrg2.getChildren().add(lblScrumMaster);
		hbxOrg4.getChildren().addAll(btnCancel,btnInvite);
		layout.getChildren().addAll(hbxOrg1,hbxOrg2,hbxOrg3,hbxOrg4);
		
		this.layout.setAlignment(Pos.CENTER);
		this.layout.setSpacing(10);
		
		this.btnCancel.setOnAction(e -> {
			controller.actionBack();
		});	
	btnInvite.setOnAction(e3->{
			if (QUERYs_FRIENDSHIP.friendsList().isEmpty()) {
				this.toast = new Toast(this, "Voce não tem amigos");
				this.scene.setOnMouseMoved(e1 -> {
					toast.close();
					return;
				});
				return;
			}
			try {
				inviteFriendLayoutState();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		initialLayout();
	}

	private void inviteFriendLayoutState() throws IOException {
		boolean t = layoutForInviteComponents == null ? true : false;

		if (t)
			layoutForInviteComponents = new HBox();

		boolean isTheVBOX = this.scene.getRoot() == layout ? true : false;

		if (isTheVBOX) {
			layoutForInviteComponents.getChildren().add(layout);
			this.scene.setRoot(layoutForInviteComponents);
			this.btnInvite.setText("Finalizar");

			HBColumnsInvite component = new HBColumnsInvite();

			this.layoutForInviteComponents.getChildren().add(component);

			this.sizeToScene();

			return;
		}
		boolean isTheHBOX = this.scene.getRoot() == layoutForInviteComponents ? true : false;
		if (isTheHBOX) {
			layoutForInviteComponents.getChildren().clear();
			this.scene.setRoot(layout);
			this.btnInvite.setText("Convidar amigos");
			return;
		}
	}
	private void initialLayout() {

		this.layout.getChildren().clear();

		this.layout.getChildren().addAll(this.lblProjectName, this.lblScrumMaster);
		this.layout.getChildren().add(this.lblAboutTheProject);
		this.layout.getChildren().addAll(this.btnInvite);
		this.layout.getChildren().add(this.btnCancel);

		
		this.initStyle(StageStyle.DECORATED);
		
		this.setWidth(500);
		this.setHeight(500);

		this.layout.setAlignment(Pos.CENTER);
		this.layout.setSpacing(10);

		Scene scene = new Scene(layout);


		this.setScene(scene);
		this.show();
	}
}
	
	
	
	
	
	