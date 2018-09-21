package scenes.scenes;

/**
 * @author jefter66: jefter66
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import application.main.Window;
import db.hibernate.factory.Database;
import db.pojos.Profile;
import friendship.SearchFriend;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import listeners.Close;
import scenes.popoups.FriendListPOPOUP;
import scenes.popoups.FriendshipRequestPOPOUP;
import scenes.popoups.NewProjectPOPOUP;
import scenes.popoups.ProfileEditPOPOUP;
import statics.GENERAL_STORE;
import statics.ProfileImg;
import statics.SESSION;
import widgets.designComponents.HBoxPhotoDecoration;
import widgets.designComponents.SearchBar;
import widgets.designComponents.ShowImage;
import widgets.toaster.Toast;

public class HomePageScene extends Scene {

	private AnchorPane layout;
	private Button btnExit, btnLogOut;
	private Button btnEditProfile;
	private ImageView profileImg;

	private Label lblName, lblUsername, lblCurrentProject, lblProjectsDone, lblBio, lblEmail;
	private Button btnEditBio;

	private HBox hbHeader;
	private TextField txtSearch;
	private Button btnSearch, btnHome;
	private VBox vbProfileInfo;
	private Button btnFriendRequest, btnFriends;
	private VBox vbLeftColumn, vbRightColumn;
	private VBox vbSearchResult;
	private HBox hbBntInteractWithBio;
	private HBox hbStartProject;
	private Button btnOk, btnCancel;
	private TextArea txtBio;
	private SearchFriend searchFriend;
//	private HBoxPhotoDecoration imageContent;
	private Toast toast,toast2;
	
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
		this.lblBio = new Label();
		this.profileImg = new ImageView();
		this.btnFriendRequest = new Button();
		this.btnLogOut = new Button();

//		this.imageContent = new HBoxPhotoDecoration(this.profileImg);

		/*
		 * in this class the components are treated
		 */
		GENERAL_STORE.setComponentsHOME(lblName, lblUsername, lblBio, profileImg, btnFriendRequest);
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
		this.lblBio.getStyleClass().add("label");
		this.lblBio.setId("bio");

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
						HomePageScene.this.vbSearchResult.getChildren().add(searchFriend.getResults().get(i));
					}
//					apSearch.setId("sugestionsOn");
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

		this.btnFriends = new Button();
		this.btnFriends.setOnAction(e -> {
		if(SESSION.getProfileLogged().getFriendsList().size() > 0) {
			try {
				new FriendListPOPOUP(Window.mainStage).showAndWait();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else { 
			this.toast2 = new Toast(Window.mainStage, "Voce não tem amigos");
			this.setOnMouseMoved(e1 -> { 
				this.toast2.close();
			});
		}
		});

		this.btnFriendRequest.setOnAction(event -> {

			if (SESSION.getProfileLogged().getFriendshipRequests().size() > 0) {
				try {
					new FriendshipRequestPOPOUP(Window.mainStage).showAndWait();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else { 
				this.toast = new Toast(Window.mainStage, "Não há solicitações de amizade");
				this.setOnMouseMoved(e ->{
					this.toast.close();
				});
				
			}
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
				Window.mainStage.setScene(new LoginScene());
			} catch (ClassNotFoundException | FileNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		});

		this.profileImg.setFitHeight(200);
		this.profileImg.setFitWidth(200);

		this.btnEditBio = new Button("Editar Bio");
		this.btnEditBio.getStyleClass().add("button");
		this.btnEditBio.setId("editBio");
		this.btnEditBio.setTranslateX(-5);

		this.hbBntInteractWithBio = new HBox();
		this.btnOk = new Button("Salvar");
		this.btnCancel = new Button("Cancelar");
		this.btnCancel.setId("cancel");
		this.btnOk.setId("save");

		this.hbBntInteractWithBio.getChildren().addAll(btnOk, btnCancel);

		this.hbBntInteractWithBio.setVisible(false);
		this.hbBntInteractWithBio.setSpacing(5);
		this.txtBio = new TextArea();
		this.txtBio.setPrefRowCount(15);
		this.txtBio.setWrapText(true);
		this.txtBio.setTranslateX(-5);
		this.btnEditBio.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				HomePageScene.this.txtBio.setVisible(true);
				HomePageScene.this.txtBio.setText(HomePageScene.this.lblBio.getText());
				HomePageScene.this.txtBio.setTranslateY(-150);
				HomePageScene.this.hbBntInteractWithBio.setVisible(true);
				HomePageScene.this.lblBio.setVisible(false);
				HomePageScene.this.btnEditBio.setVisible(false);
				HomePageScene.this.lblUsername.setTranslateY(30);
				HomePageScene.this.lblName.setTranslateY(40);
				vbProfileInfo.getChildren().remove(txtBio);
				HomePageScene.this.vbProfileInfo.getChildren().add(txtBio);
				HomePageScene.this.hbBntInteractWithBio.setTranslateY(60);

				HomePageScene.this.profileImg.setTranslateY(50);
			}
		});
		this.btnCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				HomePageScene.this.vbProfileInfo.getChildren().remove(txtBio);
				HomePageScene.this.txtBio.setVisible(false);
				HomePageScene.this.hbBntInteractWithBio.setVisible(false);
				HomePageScene.this.lblBio.setVisible(true);
				HomePageScene.this.lblName.setTranslateY(0);
				HomePageScene.this.lblUsername.setTranslateY(0);
				HomePageScene.this.btnEditBio.setVisible(true);
				HomePageScene.this.profileImg.setTranslateY(0);
			}
		});

		this.btnOk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				EntityManager em = Database.createEntityManager();
				Profile p = (Profile) SESSION.getProfileLogged();
				em.getTransaction().begin();
				p.setBio(txtBio.getText());
				em.merge(p);
				em.getTransaction().commit();
				em.clear();
				em.close();

				HomePageScene.this.lblBio.setText(HomePageScene.this.txtBio.getText());
				HomePageScene.this.hbBntInteractWithBio.setVisible(false);
				HomePageScene.this.txtBio.setVisible(false);
				HomePageScene.this.lblBio.setVisible(true);
				HomePageScene.this.btnEditBio.setVisible(true);
			}
		});

		this.hbHeader = new HBox();
		this.hbHeader.getStyleClass().add("hbox");
		this.hbHeader.setId("header");
		this.hbHeader.setPrefWidth(Window.mainStage.getMaxWidth());
		this.hbHeader.setSpacing(5);
		this.hbHeader.setAlignment(Pos.CENTER);
		this.hbHeader.getChildren().addAll(btnHome, txtSearch , btnSearch, btnFriendRequest, btnFriends, btnEditProfile, btnLogOut, btnExit);

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
		this.vbProfileInfo.getChildren().addAll(profileImg, lblName, lblUsername, lblBio, btnEditBio, hbBntInteractWithBio, lblEmail);
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



































