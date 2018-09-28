package view.scenes;

/**
 * @author jefter66: jefter66
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import application.main.Window;
import db.hibernate.factory.Database;
import db.pojos.USER_PROFILE;
import db.querys.QUERYs_FRIENDSHIP;
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
import statics.ENUMS;
import statics.GENERAL_STORE;
import statics.ProfileImg;
import statics.SERIALIZATION;
import statics.SERIALIZATION.FileType;
import statics.SESSION;
import view.popoups.FriendListPOPOUP;
import view.popoups.FriendshipRequestPOPOUP;
import view.popoups.NewProjectPOPOUP;
import view.popoups.ProfileEditPOPOUP;
import widgets.designComponents.HBStatusBar;
import widgets.designComponents.ShowImage;
import widgets.toaster.Toast;

public class HomePageScene extends Scene {

	private AnchorPane layout;
	private Button btnExit, btnLogOut;
	private Button btnEditProfile;
	private ImageView profileImg;
	private Label lblName, lblUsername, lblCurrentProject, lblProjectsDone, lblEmail;
	private HBox hbHeader;
	private TextField txtSearch;
	private Button btnSearch, btnHome;
	private VBox vbProfileInfo;
	private Button btnFriendRequest, btnFriends;
	private VBox vbLeftColumn, vbRightColumn;
	private VBox vbSearchResult;
	private HBox hbStartProject;
	private SearchFriend searchFriend;
	private Toast toast, toast2;
	private HBStatusBar statusBar;

	public HomePageScene() throws ClassNotFoundException, SQLException, IOException {
		super(new HBox());

		this.getStylesheets().add(this.getClass().getResource("/css/HOME_PAGE_SCENE.css").toExternalForm());

		this.layout = new AnchorPane();
		Window.mainStage.setTitle("Home");
		Window.mainStage.setWidth(1000);
		Window.mainStage.setHeight(800);

		this.lblCurrentProject = new Label("Projetos Atuais");
		this.lblProjectsDone = new Label("Projetos Concluidos");
		this.lblEmail = new Label();

		this.lblName = new Label();
		this.lblUsername = new Label();
		this.profileImg = new ImageView();
		this.btnFriendRequest = new Button();
		this.btnLogOut = new Button();
		this.btnFriends = new Button();

		this.statusBar = new HBStatusBar("OCUPADO", "DISPONIVEL");

		this.statusBar.setGroupEvent(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

				if (newValue.isSelected()) {
					EntityManager em = null;

					if (SESSION.getProfileLogged().getStatus().equals(ENUMS.DISPONIBILITY_FOR_PROJECT.AVAILABLE.getValue())) {
					
						if (em == null)	em = Database.createEntityManager();

						USER_PROFILE up = SESSION.getProfileLogged();
						em.getTransaction().begin();
						up.setAvailability(ENUMS.DISPONIBILITY_FOR_PROJECT.BUSY.getValue());
						em.merge(up);
						em.getTransaction().commit();
						em.clear();
						em = null;
					}
					if (SESSION.getProfileLogged().getStatus().equals(ENUMS.DISPONIBILITY_FOR_PROJECT.BUSY.getValue())) {
						if (em == null)
							em = Database.createEntityManager();
						USER_PROFILE up = SESSION.getProfileLogged();
						em.getTransaction().begin();
						up.setAvailability(ENUMS.DISPONIBILITY_FOR_PROJECT.AVAILABLE.getValue());
						em.merge(up);
						em.getTransaction().commit();
						em.clear();
						em = null;
					}
				}
			}
		});

		/*
		 * in this class the components are treated
		 */
		GENERAL_STORE.setComponentsHOME(lblName, lblUsername, profileImg, btnFriendRequest, btnFriends);
		GENERAL_STORE.loadComponentsHOME();

		this.profileImg.setOnMouseClicked(e -> {
			try {
				ShowImage show = new ShowImage(ProfileImg.loadImage(), Window.mainStage);
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
//		this.vbSearchResult.setLayoutX(5);
		this.vbSearchResult.getStyleClass().add("vbox");
		this.vbSearchResult.setId("sugestions");

		this.txtSearch = new TextField();

		this.btnSearch = new Button();
		this.txtSearch.setFocusTraversable(false);
		this.txtSearch.getStyleClass().add("text-field");
		this.txtSearch.setId("search");

		this.searchFriend = new SearchFriend();

//		this.test = new SearchBar();

		this.txtSearch.setOnKeyTyped(event -> {
			HomePageScene.this.vbSearchResult.getChildren().clear();
			if (!HomePageScene.this.txtSearch.getText().trim().isEmpty()) {
				try {
					HomePageScene.this.searchFriend.loadOptions(txtSearch.getText());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if (!searchFriend.getResults().isEmpty()) {
					for (int i = 0; i < searchFriend.getResults().size(); i++) {
						searchFriend.getResults().get(i).getStyleClass().add("hbox");
						searchFriend.getResults().get(i).setId("hSugestions");
						vbSearchResult.getChildren().add(searchFriend.getResults().get(i));
					}
					return;
				}
			}
			vbSearchResult.getChildren().clear();
		});

		this.btnEditProfile = new Button();
		this.btnEditProfile.setOnAction(e -> {
			try {
				new ProfileEditPOPOUP(Window.mainStage).showAndWait();
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
					new FriendListPOPOUP(Window.mainStage).showAndWait();
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

		this.btnFriendRequest.setId("friend-request");
		this.btnHome = new Button();

		final int SIZE = 50;
		ImageView icon_p = new ImageView();
		icon_p.setImage(new Image(new FileInputStream(new File("resources/images/icons/friends.png"))));
		icon_p.setFitHeight(SIZE);
		icon_p.setFitWidth(SIZE);
		this.btnFriends.setGraphic(icon_p);

		ImageView icon_f = new ImageView();
		icon_f.setImage(new Image(new FileInputStream(new File("resources/images/icons/friend_request.png"))));
		icon_f.setFitHeight(SIZE);
		icon_f.setFitWidth(SIZE);
		this.btnFriendRequest.setGraphic(icon_f);

		ImageView icon_s = new ImageView();
		icon_s.setFitHeight(SIZE);
		icon_s.setFitWidth(SIZE);
		icon_s.setImage(new Image(new FileInputStream(new File("resources/images/icons/search.png"))));
		this.btnSearch.setGraphic(icon_s);

		ImageView icon_u = new ImageView();
		icon_u.setFitHeight(SIZE);
		icon_u.setFitWidth(SIZE);

		ImageView icon_edit_profile = new ImageView();
		icon_edit_profile.setImage(new Image(new FileInputStream(new File("resources/images/icons/edit_profile.png"))));
		icon_edit_profile.setFitHeight(SIZE);
		icon_edit_profile.setFitWidth(SIZE);
		this.btnEditProfile.setGraphic(icon_edit_profile);

		ImageView icon_exit = new ImageView();
		icon_exit.setImage(new Image(new FileInputStream(new File("resources/images/icons/exit.png"))));
		icon_exit.setFitHeight(SIZE);
		icon_exit.setFitWidth(SIZE);
		this.btnExit.setGraphic(icon_exit);

		ImageView icon_scrum = new ImageView();
		icon_scrum.setImage(new Image(new FileInputStream(new File("resources/images/icons/scrum_icon.png"))));
		icon_scrum.setFitHeight(SIZE);
		icon_scrum.setFitWidth(SIZE);
		this.btnHome.setGraphic(icon_scrum);

		ImageView icon_logout = new ImageView();
		icon_logout.setImage(new Image(new FileInputStream(new File("resources/images/icons/logout.png"))));
		icon_logout.setFitHeight(SIZE);
		icon_logout.setFitWidth(SIZE);
		this.btnLogOut.setGraphic(icon_logout);

		this.btnHome.getStyleClass().add("header-buttons");
		this.btnSearch.getStyleClass().add("header-buttons");
		this.btnFriendRequest.getStyleClass().add("header-buttons");
		this.btnFriends.getStyleClass().add("header-buttons");
		this.btnEditProfile.getStyleClass().add("header-buttons");
		this.btnExit.getStyleClass().add("header-buttons");
		this.btnLogOut.getStyleClass().add("header-buttons");

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
		this.hbHeader.getChildren().addAll(btnHome, txtSearch, btnSearch, btnFriendRequest, btnFriends, btnEditProfile, btnLogOut, btnExit);

		AnchorPane.setTopAnchor(hbHeader, 0.0);
		AnchorPane.setBottomAnchor(hbHeader, Window.mainStage.getHeight() - 100);
		AnchorPane.setLeftAnchor(hbHeader, 0.0);
		AnchorPane.setRightAnchor(hbHeader, 0.0);
		this.layout.getChildren().add(hbHeader);

		this.vbProfileInfo = new VBox();
		this.vbProfileInfo.getStyleClass().add("vbox");
		this.vbProfileInfo.setId("profile-info");

		this.vbProfileInfo.setPadding(new Insets(0, 0, 0, 10));
		this.vbProfileInfo.setSpacing(25);
		this.vbProfileInfo.setAlignment(Pos.CENTER);
		this.vbProfileInfo.getChildren().addAll(profileImg, lblName, lblUsername, statusBar, lblEmail);
		AnchorPane.setTopAnchor(vbProfileInfo, 70.0);
		AnchorPane.setBottomAnchor(vbProfileInfo, 5.0);
		AnchorPane.setLeftAnchor(vbProfileInfo, 0.0);
		AnchorPane.setRightAnchor(vbProfileInfo, 750.0);
		this.layout.getChildren().add(vbProfileInfo);

		this.vbLeftColumn = new VBox();
		this.vbLeftColumn.getStyleClass().add("vbox");
		this.vbLeftColumn.getChildren().add(this.lblCurrentProject);

		this.vbLeftColumn.setPrefWidth(400);
		this.vbLeftColumn.setAlignment(Pos.TOP_CENTER);

		this.vbRightColumn = new VBox();
		this.vbRightColumn.getStyleClass().add("vbox");
		this.vbRightColumn.setId("rightColumn");
		this.vbRightColumn.getChildren().add(this.lblProjectsDone);

		this.hbStartProject = new HBox();
		this.hbStartProject.getChildren().add(new Label("Começar projeto"));
		this.hbStartProject.setAlignment(Pos.TOP_CENTER);
		this.hbStartProject.getStyleClass().add("hbox");
		this.hbStartProject.setId("project");

		this.hbStartProject.setOnMouseClicked(e -> {
			try {
				new NewProjectPOPOUP(Window.mainStage).showAndWait();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		});

		this.vbRightColumn.setPrefWidth(400);
		this.vbRightColumn.setAlignment(Pos.TOP_CENTER);

		this.vbRightColumn.setSpacing(50);
		this.vbRightColumn.getChildren().add(hbStartProject);

		AnchorPane.setTopAnchor(vbRightColumn, 70.0);
		AnchorPane.setBottomAnchor(vbRightColumn, 5.0);
		AnchorPane.setLeftAnchor(vbRightColumn, 250.0);
		AnchorPane.setRightAnchor(vbRightColumn, 350.0);
		this.layout.getChildren().add(vbRightColumn);

		AnchorPane.setTopAnchor(vbLeftColumn, 70.0);
		AnchorPane.setBottomAnchor(vbLeftColumn, 5.0);
		AnchorPane.setLeftAnchor(vbLeftColumn, 620.0);
		AnchorPane.setRightAnchor(vbLeftColumn, 0.0);
		this.layout.getChildren().add(vbLeftColumn);

		AnchorPane.setTopAnchor(vbSearchResult, 65.0);
		AnchorPane.setBottomAnchor(vbSearchResult, Window.mainStage.getHeight());
		AnchorPane.setLeftAnchor(vbSearchResult, 30.0);
		AnchorPane.setRightAnchor(vbSearchResult, 600.0);
		this.layout.getChildren().add(vbSearchResult);

		this.setRoot(layout);

	}
}
