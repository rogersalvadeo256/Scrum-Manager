package scenes;

/**
 * 	@author jefter66: jefter66
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import POJOs.Profile;
import application.main.Window;
import css.indicator.object.IndicatorOfCss;
import db.hibernate.factory.Database;
import db.user.util.UserOnline;
import friendship.SearchFriend;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import listeners.Close;
import popoups.scenes.FriendshipRequestPopOup;
import widgets.HBProfileContent;

public class HomePageScene extends Scene {

	private AnchorPane layout;
	private Button btnExit;
	private Button btnEditProfile;
	private ImageView profileImg;

	private Label lblName, lblUsername, lblCurrentProject, lblProjectsDone, lblBiography, lblEmail;
	private Button btnEditBio;

	private HBox hbHeader;
	private TextField txtSearch;
	private Button btnSearch;
	private ImageView scrumIcon; // , messageIcon, friendRequestIcon, settingsIcon;
	private ArrayList<File> iconPath;
	private ArrayList<FileInputStream> fis;
	private VBox vbProfileInfo;

	private HBox hbMenu;
	private Button btnStartProject, btnfriends;

	private Button btnFriendRequest, btnProjectInviteRequest;

	private AnchorPane apSearch;
	private VBox vbLeftColumn, vbRightColumn;
	private VBox vbSearchResult;
	private ScrollBar sc;

	private HBox hbBntInteractWithBio;
	private Button btnOk, btnCancel;
	private TextArea txtBio;

	private SearchFriend searchFriend;

	public HomePageScene() throws ClassNotFoundException, SQLException, FileNotFoundException {
		super(new HBox());
		IndicatorOfCss.referringScene(this, IndicatorOfCss.cssFile.HOME_PAGE_SCENE);

		this.layout = new AnchorPane();
		Window.mainStage.setTitle("Home");
		Window.mainStage.setWidth(1000);
		Window.mainStage.setHeight(800);

		this.iconPath = new ArrayList<File>();
		this.fis = new ArrayList<FileInputStream>();

		this.lblCurrentProject = new Label("Projetos Atuais");
		this.lblProjectsDone = new Label("Projetos Concluidos");
		this.lblEmail = new Label();

		this.lblName = new Label("nome"); // UserOnline.getProfile().getName());
		this.lblName.setId("lblName");

		this.lblUsername = new Label("usernome"); // UserOnline.getUserLogged().getUserName());
		this.lblName.setId("userName");

		this.lblCurrentProject = new Label("Projetos em andamento");
		this.lblName.setId("lblProject");
		this.lblProjectsDone = new Label("Projetos finalizados");
		this.lblName.setId("lblProject");

		/*
		 * the user can edit this
		 */

		/*
		 * images for the scene
		 */
		this.iconPath.add(new File("resources/images/icons/scrum_icon.png"));
		this.iconPath.add(new File("resources/images/icons/envelope.png"));
		this.iconPath.add(new File("resources/images/icons/friend_request.png"));
		/*
		 * 
		 */

		this.iconPath.add(new File("resources/images/icons/search.png"));

		/*
		 * 
		 */
		this.fis.add(new FileInputStream(iconPath.get(0)));
		this.fis.add(new FileInputStream(iconPath.get(1)));
		this.fis.add(new FileInputStream(iconPath.get(2)));
		this.fis.add(new FileInputStream(iconPath.get(3)));

		this.scrumIcon = new ImageView();
		this.setImage(scrumIcon, 0);

		this.vbSearchResult = new VBox();
		this.vbSearchResult.setLayoutX(5);
		this.sc = new ScrollBar();

		this.vbSearchResult.getChildren().add(sc);

		sc.setLayoutX(vbSearchResult.getWidth() - sc.getWidth());
		sc.setMin(0);
		sc.setOrientation(Orientation.VERTICAL);
		sc.setPrefHeight(360);
		sc.setMax(180 * 2);

		sc.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				vbSearchResult.setLayoutY(-new_val.doubleValue());
			}
		});

		this.txtSearch = new TextField();
		/*
		 * tirar foco
		 */
		this.txtSearch.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			}
		});

		this.btnSearch = new Button();
		this.txtSearch.setFocusTraversable(false);
		this.txtSearch.getStyleClass().add("text-field");

		this.searchFriend = new SearchFriend();

		this.txtSearch.setOnKeyTyped(event -> {

			if (!txtSearch.getText().isEmpty()) {

				HomePageScene.this.searchFriend.loadOptions(txtSearch.getText());
				HomePageScene.this.vbSearchResult.getChildren().clear();

				if (!searchFriend.getResults().isEmpty()) {
					for (int i = 0; i < searchFriend.getResults().size(); i++) {
						
						HomePageScene.this.vbSearchResult.getChildren().add(searchFriend.getResults().get(i));
					
					
					
					}
				}

			}

		});
		
		
		
		
		
		
		

		this.btnEditProfile = new Button("Editar Perfil");
		this.btnEditProfile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/*
				 * open new window with the profile informations and options to edit
				 */

			}
		});
		this.btnExit = new Button("Sair");
		this.btnExit.setOnAction(new Close(Window.mainStage));
			
		
		/*
		 * load the name with the qtd of requests
		 */
		this.btnFriendRequest = new Button();
		this.btnProjectInviteRequest = new Button();

		this.btnFriendRequest.setOnAction(event -> {
			try {
				new FriendshipRequestPopOup(Window.mainStage.getScene().getWindow()).showAndWait();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		});

		this.btnFriendRequest.setId("friend-request");
		this.btnProjectInviteRequest.setId("project-invite");

		ImageView icon_p = new ImageView();
		icon_p.setImage(new Image(this.fis.get(1)));
		icon_p.setFitHeight(40);
		icon_p.setFitWidth(40);
		this.btnProjectInviteRequest.setGraphic(icon_p);

		ImageView icon_f = new ImageView();
		icon_f.setImage(new Image(this.fis.get(2)));
		icon_f.setFitHeight(40);
		icon_f.setFitWidth(40);
		this.btnFriendRequest.setGraphic(icon_f);

		ImageView icon_s = new ImageView();
		icon_s.setFitHeight(40);
		icon_s.setFitWidth(40);
		icon_s.setImage(new Image(this.fis.get(3)));
		this.btnSearch.setGraphic(icon_s);

		ImageView icon_u = new ImageView();
		icon_u.setFitHeight(40);
		icon_u.setFitWidth(40);
		// icon_u.setImage(new Image(this.fis.get(4)));

		this.btnSearch.getStyleClass().add("header-buttons");
		this.btnFriendRequest.getStyleClass().add("header-buttons");
		this.btnProjectInviteRequest.getStyleClass().add("header-buttons");
		this.btnEditProfile.getStyleClass().add("header-buttons");
		this.btnExit.getStyleClass().add("header-buttons");
		this.btnEditProfile.setOnAction(e -> {
			// teste

			for (int i = 0; i < 100; i++) {
				HBox a = new HBox();
				a.getChildren().add(new Button("SSSSSSSSSS"));
				vbSearchResult.setVisible(true);
				vbSearchResult.getChildren().add(a);
				apSearch.setId("sugestionsOn");
			}

		});

		this.lblBiography = new Label();// UserOnline.getProfile().getBiography();
		this.lblBiography.getStyleClass().add("label");

		this.profileImg = new ImageView();
		this.profileImg.setFitHeight(200);
		this.profileImg.setFitWidth(200);
		this.profileImg.setTranslateX(15);

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
		this.txtBio.setPrefRowCount(5);
		this.txtBio.setWrapText(true);
		this.txtBio.setTranslateX(-5);
		this.btnEditBio.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				HomePageScene.this.txtBio.setVisible(true);
				HomePageScene.this.txtBio.setText(HomePageScene.this.lblBiography.getText());
				HomePageScene.this.txtBio.setTranslateY(-150);
				HomePageScene.this.hbBntInteractWithBio.setVisible(true);
				HomePageScene.this.lblBiography.setVisible(false);
				HomePageScene.this.btnEditBio.setVisible(false);
				HomePageScene.this.lblUsername.setTranslateY(30);
				HomePageScene.this.lblName.setTranslateY(40);
				HomePageScene.this.vbProfileInfo.getChildren().add(txtBio);
				HomePageScene.this.hbBntInteractWithBio.setTranslateY(60);

				HomePageScene.this.profileImg.setTranslateY(60);
			}
		});
		this.btnCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				HomePageScene.this.vbProfileInfo.getChildren().remove(txtBio);
				HomePageScene.this.txtBio.setVisible(false);
				HomePageScene.this.hbBntInteractWithBio.setVisible(false);
				HomePageScene.this.lblBiography.setVisible(true);
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
				Query q = em.createQuery("from Profile where codProfile=:codProfile");
				q.setParameter("codProfile", UserOnline.getProfile().getCod());
				if (q.getResultList().size() > 0) {

					Profile p = (Profile) q.getResultList().get(0);
					em.getTransaction().begin();
					p.setBiography(txtBio.getText());
					em.merge(p);
					em.getTransaction().commit();
					em.clear();
					em.close();
				}
				HomePageScene.this.lblBiography.setText(HomePageScene.this.txtBio.getText());
				HomePageScene.this.hbBntInteractWithBio.setVisible(false);
				HomePageScene.this.txtBio.setVisible(false);
				HomePageScene.this.lblBiography.setVisible(true);
				HomePageScene.this.btnEditBio.setVisible(true);
			}
		});
		/*
		 * database
		 */
		this.profileImg.setImage(new Image(new FileInputStream("resources/images/icons/profile_picture.png")));

		this.hbMenu = new HBox();
		this.hbMenu.getStyleClass().add("hbox");
		this.hbMenu.setId("menu");
		this.hbMenu.setSpacing(20);

		this.btnStartProject = new Button("Começar projeto");
		this.btnStartProject.getStyleClass().add("button");
		this.btnStartProject.setId("menu");
		this.btnfriends = new Button("Amigos");
		this.btnfriends.getStyleClass().add("button");
		this.btnfriends.setId("menu");
		this.hbMenu.setAlignment(Pos.CENTER);
		this.hbMenu.setSpacing(20);
		this.hbMenu.getChildren().addAll(btnStartProject, btnfriends);

		this.hbHeader = new HBox();
		this.hbHeader.getStyleClass().add("hbox");
		this.hbHeader.setId("header");
		this.hbHeader.setPrefWidth(Window.mainStage.getMaxWidth());
		this.hbHeader.setSpacing(5);
		this.hbHeader.setAlignment(Pos.CENTER);
		this.hbHeader.getChildren().addAll(scrumIcon, txtSearch, btnSearch, btnFriendRequest, btnProjectInviteRequest,
				btnEditProfile, btnExit);

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
		this.vbProfileInfo.getChildren().addAll(profileImg, lblName, lblUsername, lblBiography, btnEditBio,
				hbBntInteractWithBio, lblEmail);
		AnchorPane.setTopAnchor(vbProfileInfo, 70.0);
		AnchorPane.setBottomAnchor(vbProfileInfo, 5.0);
		AnchorPane.setLeftAnchor(vbProfileInfo, 0.0);
		AnchorPane.setRightAnchor(vbProfileInfo, 750.0);
		this.layout.getChildren().add(vbProfileInfo);

		this.vbLeftColumn = new VBox();
		this.vbLeftColumn.getStyleClass().add("vbox");
		this.vbLeftColumn.setId("vbLeft");
		this.vbLeftColumn.getChildren().add(this.lblCurrentProject);

		this.vbLeftColumn.setAlignment(Pos.TOP_CENTER);
		this.vbLeftColumn.setPrefWidth(400);

		this.vbRightColumn = new VBox();
		this.vbRightColumn.getStyleClass().add("vbox");
		this.vbRightColumn.setId("vbRight");
		this.vbRightColumn.getChildren().add(this.lblProjectsDone);

		this.vbRightColumn.setPrefWidth(400);
		this.vbRightColumn.setAlignment(Pos.TOP_CENTER);

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

		this.apSearch = new AnchorPane();
		AnchorPane.setTopAnchor(vbSearchResult, 40.0);
		AnchorPane.setBottomAnchor(vbSearchResult, Window.mainStage.getHeight());
		AnchorPane.setLeftAnchor(vbSearchResult, 20.0);
		AnchorPane.setRightAnchor(vbSearchResult, 20.0);
		apSearch.getChildren().addAll(vbSearchResult, sc);

		apSearch.getStyleClass().add("anchor-pane");
		apSearch.setId("sugestionsOff");

		AnchorPane.setTopAnchor(apSearch, 45.0);
		AnchorPane.setBottomAnchor(apSearch, 5.0);
		AnchorPane.setLeftAnchor(apSearch, 170.0);
		AnchorPane.setRightAnchor(apSearch, 610.0);
		this.layout.getChildren().add(apSearch);

		this.setRoot(layout);

	}

	private void setImage(ImageView image, int fis) {
		image.setFitHeight(80);
		image.setFitWidth(80);
		image.setImage(new Image(this.fis.get(fis)));
	}

}
