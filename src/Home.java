
import com.mysql.jdbc.BufferRow;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Home extends Scene {

	private GridPane layout;

	private HBox top;
	private HBox content;
	private VBox columnLeft;
	private VBox columnMiddle;
	private VBox columnRight;
	private Button btnExit;
	private Button btnEditProfile;
	private VBox opcoes;
	private VBox nameUserName;
	private Label name;
	private Label userName;
	private Label lblNewProject;
	private Button btnNewProject;

	private Label lblProjects;
	private Hyperlink myProjects;
	private Label lblMyCompleteProjects;
	private Hyperlink myCompleteProjects;

	public Home() {
		super(new HBox());

		String css=this.getClass().getResource("/cssStyles/style.css").toExternalForm();
		this.getStylesheets().add(css);
//		
//		String css2=this.getClass().getResource("/cssStyles/bgground2.css").toExternalForm();
//		this.getStylesheets().add(css2);
		
		
		this.layout = new GridPane();
		this.content = new HBox();
		this.layout.add(content, 0, 2, 1, 1);

		Window.janela.setWidth(850);
		Window.janela.setHeight(600);
		Window.janela.setTitle("Principal");

		this.top = new HBox();
		this.layout.add(top, 0, 0, 1, 1);
		this.top.setAlignment(Pos.CENTER);
		this.top.setSpacing(10);
		this.top.setPadding(new Insets(10));
		this.top.setTranslateX(10);
		this.top.setTranslateY(20);
		this.opcoes = new VBox();
//		this.opcoes.setSpacing(50);

		this.name = new Label("nome");
		this.name.setFont(new Font(25));
		this.userName = new Label("userName");
		this.userName.setFont(new Font(15));
		this.nameUserName = new VBox();
		this.nameUserName.getChildren().addAll(name, userName);
		this.btnExit = new Button("Sair");
		this.btnExit.setOnAction(actionEvent -> Platform.exit());
		this.btnEditProfile = new Button("Editar Perfil");

//		this.nameUserName.setTranslateX(10);
		this.opcoes.setTranslateX(150);

		this.opcoes.getChildren().addAll(btnEditProfile, btnExit);
		this.opcoes.setSpacing(20);

		this.top.setSpacing(60);
		this.top.getChildren().addAll(new Label("aqui vai a foto"), nameUserName, opcoes);

		this.columnMiddle = new VBox();
		this.columnLeft = new VBox();
		this.columnRight = new VBox();
		
		columnRight.setId("vbox");
		columnMiddle.setId("vbox");
		columnLeft.setId("vbox");

		this.content.getChildren().addAll(columnLeft, columnMiddle, columnRight);
		this.content.setPadding(new Insets(10));
		this.content.setSpacing(5);

		this.btnExit.setId("exitbtn");
		
		
		this.columnLeft.setTranslateX(20);
		this.columnLeft.setTranslateY(40);
		this.columnLeft.setPadding(new Insets(10));
		this.btnNewProject = new Button("Novo Projeto");
		this.lblNewProject = new Label("Começar novo projeto");
		this.lblNewProject.setFont(new Font(20));
		this.columnLeft.getChildren().addAll(lblNewProject, btnNewProject);

		this.lblProjects = new Label("Projetos em andamento");
		this.lblProjects.setFont(new Font(20));
		this.myProjects = new Hyperlink("(buscar nome dos projetos no banco de \n dados e colocar aqui");
		this.myProjects.setFont(new Font(15));
		this.columnMiddle.setTranslateX(20);
		this.columnMiddle.setTranslateY(40);
		this.columnMiddle.setPadding(new Insets(10));
		this.columnMiddle.getChildren().addAll(lblProjects, myProjects);

		this.lblMyCompleteProjects = new Label("Projetos Concluidos");
		this.lblMyCompleteProjects.setFont(new Font(20));

		this.columnRight.setTranslateX(20);
		this.columnRight.setTranslateY(40);
		this.columnRight.setPadding(new Insets(10));
		this.columnRight.getChildren().addAll(lblMyCompleteProjects);

		this.setRoot(layout);

	}

}
