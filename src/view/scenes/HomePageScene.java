package view.scenes;

/**
 * @author jefter66
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import application.main.Window;
import db.pojos.USER_PROFILE;
import friendship.QUERYs_FRIENDSHIP;
import friendship.SearchFriend;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import listeners.Close;
import project.QUERY_PROJECT;
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
import widgets.designComponents.projectContents.HBProjectComponent;
import widgets.designComponents.search.CustomScroll;
import widgets.designComponents.search.HBSearchBar;
import widgets.toaster.Toast;

public class HomePageScene extends Scene {

	private Button btnExit, btnLogOut;
	private Button btnEditProfile;
	private Button btnProjectInvitation, btnHome;
	private Button btnFriendRequest, btnFriends;

	private ImageView profileImg;
	private Label lblName, lblUsername;

	private TextField txtSearch;
	private HBStatusBar statusBar;

	private AnchorPane layout;

	private VBox vbProfileInfo;
	private HBox hbHeader, hbNav;
	private VBox vbSearchResult;
	private HBox hbIcon;
	private VBox vbNav;

	private SearchFriend searchUser;
	private CustomScroll scroll;
	private Label lblStart;
	private Toast toast;
	private ToggleButton tbOverview;
	private ToggleButton tbProject;
	private HBox hbStartProject;

	private boolean x;
	private CustomScroll scrollProjects;
	protected HBSearchBar searchBar;
	protected HBox hbHaveNoProject;
	protected VBox vbProjectComponents;

	public HomePageScene() throws ClassNotFoundException, SQLException, IOException {
		super(new HBox());

		this.getStylesheets().add(this.getClass().getResource("/css/HOME_PAGE_SCENE.css").toExternalForm());

		this.layout = new AnchorPane();
		Window.mainStage.setTitle("Home");

		Window.mainStage.setWidth(1024);
		Window.mainStage.setHeight(768);

		this.lblName = new Label();
		this.lblUsername = new Label();
		this.profileImg = new ImageView();
		this.btnFriendRequest = new Button();
		this.btnLogOut = new Button();
		this.btnFriends = new Button();
		this.btnProjectInvitation = new Button();
		this.vbProjectComponents = new VBox();

		Window.mainStage.setResizable(false);

		this.statusBar = new HBStatusBar(true, "Ocupado", "Disponivel");

		this.statusBar.setGroupEvent(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

				newValue = newValue == null ? oldValue : newValue;
				newValue.setSelected(true);

				if (newValue.isSelected()) {
					if (SESSION.getProfileLogged().getStatus()
							.equals(ENUMS.DISPONIBILITY_FOR_PROJECT.AVAILABLE.getValue())) {
						USER_PROFILE up = SESSION.getProfileLogged();

						up.setAvailability(ENUMS.DISPONIBILITY_FOR_PROJECT.BUSY.getValue());

						DB_OPERATION.MERGE(up);
						return;
					}
					if (SESSION.getProfileLogged().getStatus()
							.equals(ENUMS.DISPONIBILITY_FOR_PROJECT.BUSY.getValue())) {
						USER_PROFILE up = SESSION.getProfileLogged();

						up.setAvailability(ENUMS.DISPONIBILITY_FOR_PROJECT.AVAILABLE.getValue());

						DB_OPERATION.MERGE(up);
						return;
					}
				}
			}
		});
		/*
		 * in this class the components are treated
		 */
		GENERAL_STORE.setComponentsHOME(lblName, lblUsername, profileImg, btnFriendRequest, btnFriends,
				btnProjectInvitation, vbProjectComponents);

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

		this.lblName.setId("lblProject");
		this.lblName.setId("lblProject");

		this.vbSearchResult = new VBox();
		this.vbSearchResult.getStyleClass().add("vbox");
		this.vbSearchResult.setId("sugestions");

		this.txtSearch = new TextField();

		this.txtSearch.setFocusTraversable(false);
		this.txtSearch.getStyleClass().add("text-field");
		this.txtSearch.setId("search");

		this.searchUser = new SearchFriend();
		this.scroll = new CustomScroll();

		txtSearch.setPromptText("Localize usuarios");
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

						test();
					}

					return;
				}
			}
			vbSearchResult.getChildren().clear();
		});

		this.setOnMouseClicked(e -> {

			test();

//			if (!this.vbSearchResult.getChildren().isEmpty()) {
//				this.vbSearchResult.getChildren().clear();
//				this.layout.getChildren().remove(scroll);
//				/*
//				 * WTF
//				 */
//				this.layout.getChildren().remove(vbSearchResult);
//				this.layout.getChildren().add(vbSearchResult);
//
//				this.scroll.setVisible(false);
			this.txtSearch.setText(new String());
//			}
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
			this.toast = new Toast(Window.mainStage, "Voce não tem amigos");
			this.setOnMouseMoved(e1 -> {
				this.toast.close();
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
			this.toast = new Toast(Window.mainStage, "Nenhuma solicitação de amizade");
			this.setOnMouseMoved(e2 -> {
				this.toast.close();
			});
		});
		this.btnProjectInvitation.setOnAction(e -> {
			if (!QUERY_PROJECT.INVITES_PROJECT().isEmpty()) {
				try {
					new ProjectInvitePOPOUP(Window.mainStage).show();
					return;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			this.toast = new Toast(Window.mainStage, "Nenhum convite para projetos");
			this.setOnMouseMoved(e2 -> {
				this.toast.close();
			});
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
		icon_invitation
				.setImage(new Image(new FileInputStream(new File("resources/images/icons/project_invitation.png"))));
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
		this.hbHeader.getChildren().addAll(btnHome, txtSearch, btnProjectInvitation, btnFriendRequest, btnFriends,
				btnEditProfile, btnLogOut, btnExit);

		AnchorPane.setTopAnchor(hbHeader, 0.0);
		AnchorPane.setBottomAnchor(hbHeader, Window.mainStage.getHeight() - 100);
		AnchorPane.setLeftAnchor(hbHeader, 0.0);
		AnchorPane.setRightAnchor(hbHeader, 0.0);
		this.layout.getChildren().add(hbHeader);

		this.vbProfileInfo = new VBox();
		this.vbProfileInfo.getStyleClass().add("vbox");
		this.vbProfileInfo.setId("profile-info");

		this.hbIcon = new HBox();
		this.hbIcon.setAlignment(Pos.CENTER);

		this.vbProfileInfo.setPadding(new Insets(0, 0, 0, 10));
		this.vbProfileInfo.setSpacing(25);
		this.vbProfileInfo.setAlignment(Pos.CENTER);
		this.vbProfileInfo.getChildren().addAll(profileImg, lblName, lblUsername, statusBar);
		AnchorPane.setTopAnchor(vbProfileInfo, 70.0);
		AnchorPane.setBottomAnchor(vbProfileInfo, 5.0);
		AnchorPane.setLeftAnchor(vbProfileInfo, 0.0);
		AnchorPane.setRightAnchor(vbProfileInfo, 780.0);
		this.layout.getChildren().add(vbProfileInfo);

		AnchorPane.setTopAnchor(scroll, 65.0);
		AnchorPane.setLeftAnchor(scroll, 0d);

		AnchorPane.setTopAnchor(vbSearchResult, 65.0);
		AnchorPane.setLeftAnchor(vbSearchResult, 0d);

		this.hbNav = new HBox();

		this.tbOverview = new ToggleButton("Visão Geral");
		this.tbOverview.setId("lOverview");

		this.tbProject = new ToggleButton("Meus Projetos");
		this.tbProject.setId("lProject");

		ToggleGroup group = new ToggleGroup();

		tbOverview.setToggleGroup(group);
		tbProject.setToggleGroup(group);

		this.tbOverview.setSelected(true);
		this.x = tbOverview.isSelected();

		this.vbNav = new VBox();
		this.vbNav.setId("vbNav");

		this.scrollProjects = new CustomScroll();
		this.scrollProjects.setFitToWidth(true);
		if (x) {

			vbNav.getChildren().add(hbNav);

			AnchorPane.setTopAnchor(vbNav, 70.0);
			AnchorPane.setBottomAnchor(vbNav, 5.0);
			AnchorPane.setLeftAnchor(vbNav, 250.0);
			AnchorPane.setRightAnchor(vbNav, 0.0);
			this.layout.getChildren().add(vbNav);

			this.vbNav.setSpacing(20);
			if (!GENERAL_STORE.projectComponent().isEmpty()) {
				for (int i = 0; i < GENERAL_STORE.projectComponent().size(); i++) {
					vbNav.getChildren().add(GENERAL_STORE.projectComponent().get(i));
					if (vbNav.getChildren().size() >= 2)
						break;
				}
			}
			this.hbNav.getChildren().addAll(tbOverview, tbProject);
			this.hbNav.setId("hbNav");
			this.hbNav.setSpacing(30);
			this.hbNav.setAlignment(Pos.CENTER);

			ImageView icon = new ImageView();
			icon.setImage(new Image(new FileInputStream(new File("resources/images/icons/scrum_icon.png"))));
			icon.setFitHeight(180);
			icon.setFitWidth(200);
			this.hbIcon.getChildren().add(icon);
			this.hbIcon.setAlignment(Pos.CENTER_RIGHT);

			AnchorPane.setTopAnchor(hbIcon, 450.0);
			AnchorPane.setBottomAnchor(hbIcon, 150.0);
			AnchorPane.setLeftAnchor(hbIcon, 240.0);
			AnchorPane.setRightAnchor(hbIcon, 0.0);
			this.layout.getChildren().add(hbIcon);

			this.hbStartProject = new HBox();

			this.lblStart = new Label("COMEÇAR PROJETO");
			lblStart.setId("labelProject");
			hbStartProject.setId("project");
			hbStartProject.getChildren().add(lblStart);
			hbStartProject.setAlignment(Pos.CENTER);

		}
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

				newValue = newValue == null ? oldValue : newValue;
				newValue.setSelected(true);
				changeScreenComponents();
			}
		});

		AnchorPane.setTopAnchor(scrollProjects, 220d);
		AnchorPane.setLeftAnchor(scrollProjects, 250d);
		AnchorPane.setRightAnchor(scrollProjects, 0d);
		AnchorPane.setBottomAnchor(scrollProjects, 50d);

		hbStartProject.setOnMouseClicked(e -> {
			try {
				new NewProjectPOPOUP(Window.mainStage).showAndWait();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		});
		AnchorPane.setTopAnchor(hbStartProject, 600.0);
		AnchorPane.setBottomAnchor(hbStartProject, 5.0);
		AnchorPane.setLeftAnchor(hbStartProject, 240.0);
		AnchorPane.setRightAnchor(hbStartProject, 0.0);
		this.layout.getChildren().add(hbStartProject);

		this.setRoot(layout);
	}

	private void changeScreenComponents() {
		if (x) {
			vbNav.getChildren().clear();
			vbNav.getChildren().add(hbNav);
			layout.getChildren().removeAll(hbIcon, hbStartProject);

			try {
				searchBar = new HBSearchBar();

				searchBar.setTextSearchEvent(new EventHandler<KeyEvent>() {

					ArrayList<HBProjectComponent> projectComponent = GENERAL_STORE.projectComponent();

					@Override
					public void handle(KeyEvent e) {
						vbProjectComponents.getChildren().clear();
						for (int i = 0; i < projectComponent.size(); i++) {
							boolean begin = projectComponent.get(i).getProject().getProjName()
									.startsWith(searchBar.getTxtSearch().getText());
							boolean end = projectComponent.get(i).getProject().getProjName()
									.endsWith(searchBar.getTxtSearch().getText());

							if (begin || end) {
								vbProjectComponents.getChildren().add(projectComponent.get(i));
							}
						}
					}
				});
				vbNav.getChildren().add(searchBar);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			ArrayList<HBProjectComponent> components = GENERAL_STORE.projectComponent();

			if (!components.isEmpty()) {

				vbProjectComponents.getChildren().clear();

				vbProjectComponents.setId("project-list");

				vbProjectComponents.getChildren().addAll(components);

				boolean y = vbProjectComponents.getChildren().size() > 3;

				if (y) {
					
					
					layout.getChildren().add(scrollProjects);
					scrollProjects.setComponent(vbProjectComponents);
					scrollProjects.setVisible(true);
					x = false;
					return;
				}
				vbNav.getChildren().add(vbProjectComponents);
			}
			if (components.isEmpty()) {
				hbHaveNoProject = new HBox();
				hbHaveNoProject.setId("haveNoProject");

				hbHaveNoProject.getChildren().add(new Label(" Voce não possui nenhum projeto, inicie um agora mesmo "));

				vbNav.getChildren().add(hbHaveNoProject);
			}
			x = false;
			return;
		}
		vbNav.getChildren().clear();
		vbNav.getChildren().addAll(hbNav);
		vbNav.getChildren().add(GENERAL_STORE.projectComponent().get(0));
		layout.getChildren().remove(scrollProjects);
		layout.getChildren().addAll(hbIcon, hbStartProject);
		x = true;
	}

	private void test() {
		boolean a = this.vbSearchResult.getChildren().isEmpty();

		if (!a) {

			if (this.vbSearchResult.getChildren().size() < 4) {
				this.scroll.setVisible(false);
				boolean x = vbSearchResult.getParent() != layout;
				if (x) {
					layout.getChildren().remove(scroll);
					layout.getChildren().add(vbSearchResult);
				}
				return;
			}

			boolean y = scroll.getContent() != vbSearchResult;

			if (y) {
				layout.getChildren().remove(vbSearchResult);
				layout.getChildren().add(scroll);
				this.scroll.setComponent(this.vbSearchResult);
				this.scroll.setVisible(true);
				return;
			}

			layout.getChildren().remove(scroll);
			return;
		}
	}
	
	
	
	
	
	
	
	
}
