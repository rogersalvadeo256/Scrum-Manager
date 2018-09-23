package view.scenes;
//package scenes.scenes;
//import java.util.Random;
//
//import db.pojos.FriendTable;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.shape.Rectangle;
//import javafx.stage.Stage;
//
//public class AddFriendScene extends Stage {
//
//	private ObservableList<FriendTable> listaDeProduto;
//	private TableView<FriendTable> tabela;
//	private Button btnAddFriend;
//	private ImageView imgProfile;
//	private Label lblNome, lblStatus;
//	private HBox hbxMyProfile, hbxAddFriend;
//	private TextField txtFiltro;
//	private Rectangle imgPerfil;
//	private VBox layout;
//	static int nmr=0;
//
//	public AddFriendScene() {
//		this.listaDeProduto = FXCollections.observableArrayList();
//		this.tabela = new TableView<FriendTable>();
//
//		this.btnAddFriend = new Button("adicionar um amigo");
//
//		this.layout = new VBox();
//
//		this.imgPerfil = new Rectangle();
//		this.imgPerfil.setWidth(60);
//		this.imgPerfil.setHeight(60);
//
//		this.lblNome = new Label("Nome Da Pessoa");
//		this.lblStatus = new Label("Online");
//		this.txtFiltro = new TextField();
//		this.txtFiltro.setPromptText("Filtro");
//		this.hbxMyProfile = new HBox();
//		this.hbxAddFriend = new HBox();
//
//		this.hbxMyProfile.getChildren().addAll(imgPerfil, lblNome, lblStatus);
//		this.hbxMyProfile.setSpacing(15);
//		this.hbxAddFriend.getChildren().addAll(btnAddFriend, txtFiltro);
//
//		this.layout.setAlignment(Pos.CENTER);
//
//		TableColumn<FriendTable, Integer> foto = new TableColumn<FriendTable, Integer>();
//		TableColumn<FriendTable, String> user = new TableColumn<FriendTable, String>();
//		TableColumn<FriendTable, String> status = new TableColumn<FriendTable, String>();
//
//		tabela.getColumns().addAll(foto, user, status);
//		this.tabela.setItems(listaDeProduto);
//
//		this.btnAddFriend.setOnAction(evento -> {
//			FriendTable p = new FriendTable();
//			p.setCod(nmr++);
//			p.setUser("Carlos Magno");
//			p.setStatus("Online");
//			p.setFoto(new ImageView("image.jpg"));
//
//			this.listaDeProduto.add(p);
//		});
//
//		foto.setCellValueFactory(new PropertyValueFactory("foto"));
//		user.setCellValueFactory(new PropertyValueFactory("user"));
//		status.setCellValueFactory(new PropertyValueFactory("status"));
//
//		this.layout.getChildren().addAll(hbxMyProfile, hbxAddFriend, tabela);
//		Scene cena = new Scene(layout);
//		cena.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
//		this.layout.setSpacing(15);
//		this.layout.setAlignment(Pos.CENTER);
//		this.setScene(cena);
//		this.show();
//	}
//
//}
