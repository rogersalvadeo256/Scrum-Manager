package scenes.scenes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.List;

import application.main.Window;
import db.pojos.Integrantes;
import db.pojos.Quadro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProjectScene extends Scene {

	private AnchorPane layout = new AnchorPane();
	private ObservableList<Integrantes> listaInteg;
	private TableView<Integrantes> integrantes;
	private TableView<Quadro> quadro;
	private ObservableList<Quadro> listaQuadro;
	private Button btnAddMember, btnCommit, btnSla;
	private Label lblPjtName;
	private Text lblBio;
	private VBox vbxLbl, vbxParty;
	private HBox hbxBtns;

	public ProjectScene() {
		super(new AnchorPane());
		integrantes = new TableView<Integrantes>();
		quadro = new TableView<Quadro>();
		this.listaInteg = FXCollections.observableArrayList();
		this.listaQuadro = FXCollections.observableArrayList();
		btnAddMember = new Button("addMember");
		lblPjtName = new Label();
		lblBio = new Text();
		btnCommit = new Button("#");
		btnCommit.setPrefWidth(120);
		btnCommit.setPrefHeight(60);
		btnSla = new Button("sla");
		btnSla.setPrefWidth(120);
		btnSla.setPrefHeight(60);
		vbxLbl = new VBox();
		vbxParty = new VBox();
		hbxBtns = new HBox();
		
		Window.mainStage.setWidth(1200);
		Window.mainStage.setHeight(700);
		
		

		hbxBtns.getChildren().addAll(btnSla, btnCommit);
		hbxBtns.setSpacing(20);

		vbxParty.getChildren().addAll(integrantes, btnAddMember);
		vbxParty.setSpacing(15);

		String bio;
		bio = "Projeto destinado a ajudar outras pessoas \ncom seus TCCs";

		lblPjtName.setText("TCC");
		lblBio.setText(bio);

		// Tabela integrantes

		TableColumn<Integrantes, Integer> foto = new TableColumn<Integrantes, Integer>();
		TableColumn<Integrantes, String> namee = new TableColumn<Integrantes, String>("Nome");
		TableColumn<Integrantes, String> funcao = new TableColumn<Integrantes, String>("Fun��o");

		this.integrantes.getColumns().addAll(foto, namee, funcao);

		vbxLbl.getChildren().addAll(lblPjtName, lblBio);
		vbxLbl.setSpacing(20d);

		this.integrantes.setItems(listaInteg);

		foto.setCellValueFactory(new PropertyValueFactory("foto"));
		namee.setCellValueFactory(new PropertyValueFactory("name"));
		funcao.setCellValueFactory(new PropertyValueFactory("funcao"));

		// Kill me

		// Tabela do quadro

		TableColumn<Quadro, String> toDo = new TableColumn<Quadro, String>("To Do");
		TableColumn<Quadro, String> inProg = new TableColumn<Quadro, String>("In Progress");
		TableColumn<Quadro, String> done = new TableColumn<Quadro, String>("Done!");

		this.quadro.getColumns().addAll(toDo, inProg, done);

		quadro.resizeColumn(toDo, 150d);
		quadro.resizeColumn(inProg, 150d);
		quadro.resizeColumn(done, 150d);

		this.quadro.setItems(listaQuadro);
		this.btnAddMember.setOnAction(evento -> {
			Integrantes i = new Integrantes();
			i.setFoto(new ImageView("image.jpg"));
			i.setName("sla");
			i.setFuncao("programador");

			this.listaInteg.add(i);
		});
		
		this.btnCommit.setOnAction(evento -> {
			Quadro q = new Quadro();
			q.setToDo("tem que fazer isso");
			q.setInProg("to fazendo isso");
			q.setDone("fizemos isso");
			
			this.listaQuadro.add(q);
		});
		
		
		toDo.setCellValueFactory(new PropertyValueFactory("toDo"));
		inProg.setCellValueFactory(new PropertyValueFactory("inProg"));
		done.setCellValueFactory(new PropertyValueFactory("done"));

		quadro.setMaxWidth(15d);

		// foi
		

		layout.getChildren().addAll(vbxLbl, vbxParty, quadro, hbxBtns);
		AnchorPane.setTopAnchor(vbxLbl, 15d);
		AnchorPane.setLeftAnchor(vbxLbl, 15d);

		AnchorPane.setTopAnchor(vbxParty, 15d);
		AnchorPane.setRightAnchor(vbxParty, 15d);

		AnchorPane.setTopAnchor(quadro, 220d);
		AnchorPane.setLeftAnchor(quadro, 15d);
		AnchorPane.setRightAnchor(quadro, 300d);

		AnchorPane.setBottomAnchor(hbxBtns, 20d);
		AnchorPane.setRightAnchor(hbxBtns, 300d);
		
		
		this.setRoot(layout);
		
	}

}
