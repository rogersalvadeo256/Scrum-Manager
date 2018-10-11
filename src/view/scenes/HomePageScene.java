package view.scenes;

/**
 * @author jefter66
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import application.main.Window;
import db.pojos.USER_PROFILE;
import friendship.QUERYs_FRIENDSHIP;
import friendship.SearchFriend;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import listeners.Close;
import statics.DB_OPERATION;
import statics.ENUMS;
import statics.GENERAL_STORE;
import statics.PROFILE_IMG;
import statics.SERIALIZATION;
import statics.SERIALIZATION.FileType;
import statics.SESSION;
import view.popoups.FriendListPOPOUP;
import view.popoups.FriendshipRequestPOPOUP;
import view.popoups.NewProjectPOPOUP;
import view.popoups.ProfileEditPOPOUP;
import view.popoups.ProjectInvitePOPOUP;
import widgets.designComponents.photoContent.ShowImage;
import widgets.designComponents.profileContents.HBStatusBar;
import widgets.designComponents.projectContents.ProjectContentHomePage;
import widgets.toaster.Toast;

public class HomePageScene extends Scene {
	
	private AnchorPane layout;
	private Button btnExit, btnLogOut;
	private Button btnEditProfile;
	private ImageView profileImg;
	private Label lblName, lblUsername, lblCurrentProject, lblProjectsDone, lblEmail, lblProjects;
	private HBox hbHeader, hbNav;
	private TextField txtSearch;
	private Button btnProjectInvitation, btnHome;
	private VBox vbProfileInfo;
	private Button btnFriendRequest, btnFriends;
	private VBox vbLeftColumn, vbRightColumn;
	private VBox vbSearchResult;
	private HBox hbIcon;
	private SearchFriend searchUser;
	private Toast toast, toast2;
	private HBStatusBar statusBar;
	private Label lblOverview;
	private VBox vbNav;
	
	public HomePageScene() throws ClassNotFoundException, SQLException, IOException {
		super(new HBox());
		
		
		this.getStylesheets().add(this.getClass().getResource("/css/HOME_PAGE_SCENE.css").toExternalForm());
		
		this.layout = new AnchorPane();
		Window.mainStage.setTitle("Home");
		Window.mainStage.setWidth(1000);
		Window.mainStage.setHeight(800);
		
//		Window.mainStage.setResizable(true);
		
		
		
		this.lblCurrentProject = new Label("Projetos Atuais");
		this.lblProjectsDone = new Label("Projetos Concluidos");
		this.lblEmail = new Label();
		
		this.lblName = new Label();
		this.lblUsername = new Label();
		this.profileImg = new ImageView();
		this.btnFriendRequest = new Button();
		this.btnLogOut = new Button();
		this.btnFriends = new Button();
		this.btnProjectInvitation = new Button();
		
		Window.mainStage.setResizable(false);
		
		this.statusBar = new HBStatusBar(true, "Ocupado", "Disponivel");
		
		this.statusBar.setGroupEvent(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				
				newValue = newValue == null ? oldValue : newValue;
				newValue.setSelected(true);
				
				if (newValue.isSelected()) {
					
					if (SESSION.getProfileLogged().getStatus().equals(ENUMS.DISPONIBILITY_FOR_PROJECT.AVAILABLE.getValue())) {
						USER_PROFILE up = SESSION.getProfileLogged();
						DB_OPERATION.MERGE(up);
					}
					if (SESSION.getProfileLogged().getStatus().equals(ENUMS.DISPONIBILITY_FOR_PROJECT.BUSY.getValue())) {
						USER_PROFILE up = SESSION.getProfileLogged();
						up.setAvailability(ENUMS.DISPONIBILITY_FOR_PROJECT.AVAILABLE.getValue());
						DB_OPERATION.MERGE(up);
					}
				}
			}
		});
		/*
		 * in this class the components are treated
		 */
		GENERAL_STORE.setComponentsHOME(lblName, lblUsername, profileImg, btnFriendRequest, btnFriends, btnProjectInvitation, vbLeftColumn, vbRightColumn);
		GENERAL_STORE.loadComponentsHOME();
		
		this.profileImg.setOnMouseClicked(e -> {
			try {
				ShowImage show = new ShowImage(PROFILE_IMG.loadImage(), Window.mainStage);
				show.showAndWait();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		});
		
		this.lblName.setId("lblName");
		this.lblName.setId("userName");
		
		this.lblCurrentProject = new Label("Projetos em andamento");
		this.lblName.setId("lblProject");
		this.lblProjectsDone = new Label("Projetos finalizados");
		this.lblName.setId("lblProject");
		
		this.vbSearchResult = new VBox();
		// this.vbSearchResult.setLayoutX(5);
		this.vbSearchResult.getStyleClass().add("vbox");
		this.vbSearchResult.setId("sugestions");
		
		this.txtSearch = new TextField();
		
		this.txtSearch.setFocusTraversable(false);
		this.txtSearch.getStyleClass().add("text-field");
		this.txtSearch.setId("search");
		
		this.searchUser = new SearchFriend();
		
		txtSearch.setPromptText("Encontre outros usuarios");
		this.txtSearch.setOnKeyTyped(event -> {
			HomePageScene.this.vbSearchResult.getChildren().clear();
			if (!HomePageScene.this.txtSearch.getText().trim().isEmpty()) {
				try {
					HomePageScene.this.searchUser.loadOptions(txtSearch.getText());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if (!searchUser.getResults().isEmpty()) {
					for (int i = 0; i < searchUser.getResults().size(); i++) {
						searchUser.getResults().get(i).getStyleClass().add("hbox");
						searchUser.getResults().get(i).setId("hSugestions");
						vbSearchResult.getChildren().add(searchUser.getResults().get(i));
					}
					return;
				}
			}
			vbSearchResult.getChildren().clear();
		});
		
		this.btnEditProfile = new Button();
		this.btnEditProfile.setOnAction(e -> {
			try {
				new ProfileEditPOPOUP(Window.mainStage).show();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		this.btnExit = new Button();
		this.btnExit.setOnAction(new Close(Window.mainStage));
		
		this.btnFriends.setOnAction(e -> {
			if (!QUERYs_FRIENDSHIP.friendsList().isEmpty()) {
				try {
					new FriendListPOPOUP(Window.mainStage).show();
					return;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			this.toast2 = new Toast(Window.mainStage, "Voce não tem amigos");
			this.setOnMouseMoved(e1 -> {
				this.toast2.close();
			});
		});
		this.btnFriendRequest.setOnAction(event -> {
			if (!QUERYs_FRIENDSHIP.friendshipRequestsList().isEmpty()) {
				try {
					new FriendshipRequestPOPOUP(Window.mainStage).showAndWait();
					return;
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
			this.toast = new Toast(Window.mainStage, "Voce não tem solicitações de amizade");
			this.setOnMouseMoved(e2 -> {
				this.toast.close();
			});
		});
		
		this.btnProjectInvitation.setOnAction(e -> {
			try {
				new ProjectInvitePOPOUP(Window.mainStage).show();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		this.btnFriendRequest.setId("friend-request");
		this.btnHome = new Button();
		
		final int SIZE = 15;
		ImageView icon_p = new ImageView();
		icon_p.setImage(new Image(new FileInputStream(new File("resources/images/icons/friends.png"))));
		icon_p.setFitHeight(SIZE);
		icon_p.setFitWidth(SIZE);
		this.btnFriends.setGraphic(icon_p);
		this.btnFriends.setText("Amigos");
		
		
		ImageView icon_f = new ImageView();
		icon_f.setImage(new Image(new FileInputStream(new File("resources/images/icons/friend_request.png"))));
		icon_f.setFitHeight(SIZE);
		icon_f.setFitWidth(SIZE);
		this.btnFriendRequest.setGraphic(icon_f);
		this.btnFriendRequest.setText("Pedidos de \n amizade");
		
		ImageView icon_invitation = new ImageView();
		icon_invitation.setFitHeight(SIZE);
		icon_invitation.setFitWidth(SIZE);
		icon_invitation.setImage(new Image(new FileInputStream(new File("resources/images/icons/project_invitation.png"))));
		this.btnProjectInvitation.setGraphic(icon_invitation);
		this.btnProjectInvitation.setText("Convites\npara projetos");
		
		ImageView icon_u = new ImageView();
		icon_u.setFitHeight(SIZE);
		icon_u.setFitWidth(SIZE);
		
		ImageView icon_edit_profile = new ImageView();
		icon_edit_profile.setImage(new Image(new FileInputStream(new File("resources/images/icons/edit_profile.png"))));
		icon_edit_profile.setFitHeight(SIZE);
		icon_edit_profile.setFitWidth(SIZE);
		this.btnEditProfile.setGraphic(icon_edit_profile);
		this.btnEditProfile.setText("Editar perfil");
		
		
		ImageView icon_exit = new ImageView();
		icon_exit.setImage(new Image(new FileInputStream(new File("resources/images/icons/exit.png"))));
		icon_exit.setFitHeight(SIZE);
		icon_exit.setFitWidth(SIZE);
		this.btnExit.setGraphic(icon_exit);
		this.btnExit.setText("Sair");
		
		ImageView icon_scrum = new ImageView();
		icon_scrum.setImage(new Image(new FileInputStream(new File("resources/images/icons/scrum_icon.png"))));
		icon_scrum.setFitHeight(SIZE);
		icon_scrum.setFitWidth(SIZE);
		this.btnHome.setGraphic(icon_scrum);
		this.btnHome.setMaxSize(20, 50);
		
		
		ImageView icon_logout = new ImageView();
		icon_logout.setImage(new Image(new FileInputStream(new File("resources/images/icons/logout.png"))));
		icon_logout.setFitHeight(SIZE);
		icon_logout.setFitWidth(SIZE);
		this.btnLogOut.setGraphic(icon_logout);
		this.btnLogOut.setText("Logout");
		
		
		this.btnHome.getStyleClass().add("header-buttons");
		this.btnProjectInvitation.getStyleClass().add("header-buttons");
		this.btnFriendRequest.getStyleClass().add("header-buttons");
		this.btnFriends.getStyleClass().add("header-buttons");
		this.btnEditProfile.getStyleClass().add("header-buttons");
		this.btnExit.getStyleClass().add("header-buttons");
		this.btnLogOut.getStyleClass().add("header-buttons");
		
		this.btnExit.setId("exit");
		this.btnLogOut.setId("avarage");
		this.btnFriends.setId("avarage");
		
		
		this.btnLogOut.setOnAction(e -> {
			SESSION.RESET();
			try {
				SERIALIZATION.deleteFileSerialization(FileType.SESSION);
				Window.mainStage.setScene(new LoginScene());
			} catch (ClassNotFoundException | FileNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		});

		this.profileImg.setFitHeight(200);
		this.profileImg.setFitWidth(200);
		
		this.hbHeader = new HBox();
		this.hbHeader.getStyleClass().add("hbox");
		this.hbHeader.setId("header");
		this.hbHeader.setPrefWidth(Window.mainStage.getMaxWidth());
		this.hbHeader.setSpacing(5);
		this.hbHeader.setAlignment(Pos.CENTER);
		this.hbHeader.getChildren().addAll(btnHome,txtSearch, btnProjectInvitation, btnFriendRequest, btnFriends, btnEditProfile, btnLogOut, btnExit);
		
		AnchorPane.setTopAnchor(hbHeader, 0.0);
		AnchorPane.setBottomAnchor(hbHeader, Window.mainStage.getHeight() - 100);
		AnchorPane.setLeftAnchor(hbHeader, 0.0);
		AnchorPane.setRightAnchor(hbHeader, 0.0);
		this.layout.getChildren().add(hbHeader);
		
		this.vbProfileInfo = new VBox();
		this.vbProfileInfo.getStyleClass().add("vbox");
		this.vbProfileInfo.setId("profile-info");
		
		this.hbIcon = new HBox();
//		this.hbIcon.getChildren().add(new Label("Começar projeto"));
		this.hbIcon.setAlignment(Pos.CENTER);
		this.hbIcon.getStyleClass().add("hbox");
		this.hbIcon.setId("project");
		
		this.hbIcon.setOnMouseClicked(e -> {
			try {
				new NewProjectPOPOUP(Window.mainStage).showAndWait();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
		});
		
		this.vbProfileInfo.setPadding(new Insets(0, 0, 0, 10));
		this.vbProfileInfo.setSpacing(25);
		this.vbProfileInfo.setAlignment(Pos.CENTER);
		this.vbProfileInfo.getChildren().addAll(profileImg, lblName, lblUsername, lblEmail, statusBar);
		AnchorPane.setTopAnchor(vbProfileInfo, 70.0);
		AnchorPane.setBottomAnchor(vbProfileInfo, 5.0);
		AnchorPane.setLeftAnchor(vbProfileInfo, 0.0);
		AnchorPane.setRightAnchor(vbProfileInfo, 750.0);
		this.layout.getChildren().add(vbProfileInfo);
		
		this.vbLeftColumn = new VBox();
		this.vbLeftColumn.getStyleClass().add("vbox");
		this.vbLeftColumn.getChildren().add(this.lblProjectsDone);
		
		this.vbLeftColumn.setPrefWidth(400);
		this.vbLeftColumn.setAlignment(Pos.TOP_CENTER);
		
		this.vbRightColumn = new VBox();
		this.vbRightColumn.getStyleClass().add("vbox");
		this.vbRightColumn.setId("rightColumn");
		this.vbRightColumn.getChildren().add(this.lblCurrentProject);
		
		this.vbRightColumn.setPrefWidth(400);
		this.vbRightColumn.setAlignment(Pos.TOP_CENTER);
		
		this.vbRightColumn.setSpacing(50);
		
//		AnchorPane.setTopAnchor(vbRightColumn, 70.0);
//		AnchorPane.setBottomAnchor(vbRightColumn, 5.0);
//		AnchorPane.setLeftAnchor(vbRightColumn, 250.0);
//		AnchorPane.setRightAnchor(vbRightColumn, 350.0);
////		this.layout.getChildren().add(vbRightColumn);
//		
//		AnchorPane.setTopAnchor(vbLeftColumn, 70.0);
//		AnchorPane.setBottomAnchor(vbLeftColumn, 5.0);
//		AnchorPane.setLeftAnchor(vbLeftColumn, 620.0);
//		AnchorPane.setRightAnchor(vbLeftColumn, 0.0);
////		this.layout.getChildren().add(vbLeftColumn);
//		
//		AnchorPane.setTopAnchor(vbSearchResult, 65.0);
//		AnchorPane.setBottomAnchor(vbSearchResult, Window.mainStage.getHeight());
//		AnchorPane.setLeftAnchor(vbSearchResult, 30.0);
//		AnchorPane.setRightAnchor(vbSearchResult, 600.0);
////		this.layout.getChildren().add(vbSearchResult);
		
		
		this.hbNav = new HBox();
		
		this.lblProjects= new Label("Meus Projetos");
		this.lblOverview = new Label("Visão geral");
		this.lblProjects.setId("lProject");
		this.lblOverview.setId("lOverview");
		
		this.hbNav.getChildren().addAll(lblOverview,lblProjects);
		this.hbNav.setId("hbNav");	
		this.hbNav.setSpacing(30);
		this.hbNav.setAlignment(Pos.CENTER);
		
		this.vbNav = new VBox();
		
		this.vbNav.setId("vbNav");
		
		vbNav.getChildren().add(hbNav);
		
		AnchorPane.setTopAnchor(vbNav, 70.0);
		AnchorPane.setBottomAnchor(vbNav, 5.0);
		AnchorPane.setLeftAnchor(vbNav, 250.0);
		AnchorPane.setRightAnchor(vbNav, 0.0);
		this.layout.getChildren().add(vbNav);
		
		ProjectContentHomePage pch = new ProjectContentHomePage(this.getHeight());
		
		this.vbNav.getChildren().add(pch);
		
		
		AnchorPane.setTopAnchor(hbIcon, 600.0);
		AnchorPane.setBottomAnchor(hbIcon, 5.0);
		AnchorPane.setLeftAnchor(hbIcon, 240.0);
		AnchorPane.setRightAnchor(hbIcon, 0.0);
		this.layout.getChildren().add(hbIcon);
		
		ImageView icon = new ImageView();
		icon.setImage(new Image(new FileInputStream(new File("resources/images/icons/scrum_icon.png"))));
		icon.setFitHeight(180);
		icon.setFitWidth(200);
		this.hbIcon.getChildren().add(icon);
		this.hbIcon.setAlignment(Pos.CENTER_RIGHT);
		
		
		
		
		/*
		 * space reserved for the calendar
		HBox teste = new HBox();
		teste.setId("test");
		teste.setAlignment(Pos.CENTER);
		teste.getChildren().add(new Label("teste"));
		
		AnchorPane.setTopAnchor(teste, 200.0);
		AnchorPane.setBottomAnchor(teste, 200.0);
		AnchorPane.setLeftAnchor(teste, 240.0);
		AnchorPane.setRightAnchor(teste, 0.0);
		this.layout.getChildren().add(teste);
		
		*/
		
		
		
		
		
		
		
		
		
		
		this.setRoot(layout);
		
	}
}
