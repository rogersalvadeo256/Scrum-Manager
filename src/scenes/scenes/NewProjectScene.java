package scenes.scenes;


import application.main.Window;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NewProjectScene extends Scene {

	private Label lblProjectName, lblDescProject;
	private TextField txtProjectName;
	private TextArea txtDescProject;
	private Button btnSalvar,btnCancelar,btnInvite;
	private HBox hbxBTN,hbxMember;
	private VBox hbxPN,vbxTela;
	
	public NewProjectScene(){	
		super(new VBox());
		this.lblProjectName = new Label("Nome do Projeto");
		this.lblDescProject = new Label("Descrição do Projeto");
		this.txtProjectName = new TextField();
		this.txtDescProject = new TextArea();
		this.txtDescProject.setId("descProject");
		this.txtDescProject.setId("txtDescProject");
		this.hbxBTN = new HBox();
		this.hbxPN = new VBox();
		this.hbxMember = new HBox();
		this.vbxTela = new VBox();
		
		this.getStylesheets().add(this.getClass().getResource("/css/NEW_PROJECT.css").toExternalForm());
		
		Window.mainStage.setWidth(800);
		Window.mainStage.setHeight(600);
		
		this.txtProjectName.setMaxWidth(300);
		this.txtDescProject.setMaxWidth(300);
		this.txtDescProject.setPrefRowCount(10);
		this.txtDescProject.setWrapText(true);
		
		this.txtProjectName.setAlignment(Pos.CENTER);
		this.btnSalvar = new Button("Salvar");
		this.btnSalvar.setId("btnSalve");
		
		this.btnInvite = new Button("Convidar amigos");
		this.btnInvite.setId("btnInvite");
		this.btnCancelar = new Button("Cancelar");
		this.btnCancelar.setId("btnCancel");

		hbxPN.getChildren().addAll(lblProjectName,txtProjectName);
		hbxPN.setSpacing(10);
		hbxPN.setAlignment(Pos.CENTER);

		
		hbxBTN.getChildren().addAll(btnSalvar,btnCancelar);
		hbxBTN.setSpacing(10);
		hbxBTN.setAlignment(Pos.CENTER);
				
		hbxMember.getChildren().addAll(btnInvite);
		
		hbxMember.setSpacing(15);
		hbxMember.setAlignment(Pos.CENTER);
		
		
		vbxTela.getChildren().addAll(hbxPN,lblDescProject,txtDescProject,hbxMember);
		vbxTela.getChildren().addAll(hbxBTN);
		vbxTela.setAlignment(Pos.CENTER);
		
		vbxTela.setSpacing(15);
		
		
		setRoot(vbxTela);
	}
	
	public void setEventInvite(EventHandler<ActionEvent> e) { 
		this.btnInvite.setOnAction(e);
	}
	
	
}
