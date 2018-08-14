package scenes;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NewProject extends VBox {

	private Label lblProjectName, lblDescProject, lblMember;
	private TextField txtProjectName,txtMember;
	private Hyperlink lblAddMember;
	private TextArea txtDescProject;
	private Button btnSalvar,btnCancelar,btnInvite;
	private HBox hbxPN,hbxBTN,hbxMember;
	
	
	public  NewProject(){	
		this.lblProjectName = new Label("Nome do Projeto");
		this.lblDescProject = new Label("Descri��o do Projeto");
		this.lblMember = new Label("Membros:");
		this.txtProjectName = new TextField();
		this.txtDescProject = new TextArea();
		this.txtMember = new TextField();
		this.lblAddMember = new Hyperlink("Adicione mais um membro");
		this.hbxBTN = new HBox();
		this.hbxPN = new HBox();
		this.hbxMember = new HBox();
		
		this.txtProjectName.setMaxWidth(300);
		this.txtDescProject.setMaxWidth(300);
		this.txtMember.setMaxWidth(300);
		this.txtProjectName.setAlignment(Pos.CENTER);
		this.txtMember.setAlignment(Pos.CENTER);
		this.btnSalvar = new Button("Salvar");
		
		this.btnInvite = new Button("Convidar");
		this.btnCancelar = new Button("Cancelar");

		hbxPN.getChildren().addAll(lblProjectName,txtProjectName);
		
		hbxBTN.getChildren().addAll(btnSalvar,btnCancelar);
		hbxMember.getChildren().addAll(lblMember,txtMember,btnInvite);
		
		
		this.getChildren().addAll(hbxPN,lblDescProject,txtDescProject,hbxMember,lblAddMember);
		this.getChildren().addAll(hbxBTN);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(5);
		
		
		
		
		
	}
}
