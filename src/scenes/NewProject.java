package scenes;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Window;

public class NewProject extends Scene {

	private Label lblProjectName, lblDescProject, lblMember;
	private TextField txtProjectName,txtMember;
	private Hyperlink lblAddMember;
	private TextArea txtDescProject;
	private Button btnSalvar,btnCancelar,btnInvite;
	private HBox hbxPN,hbxBTN,hbxMember;
	private VBox vbxTela;
	
	public NewProject(){	
		super(new VBox());
		this.lblProjectName = new Label("Nome do Projeto");
		this.lblDescProject = new Label("Descrição do Projeto");
		this.lblMember = new Label("Membros:");
		this.txtProjectName = new TextField();
		this.txtDescProject = new TextArea();
		this.txtMember = new TextField();
		this.lblAddMember = new Hyperlink("Adicione mais um membro");
		this.hbxBTN = new HBox();
		this.hbxPN = new HBox();
		this.hbxMember = new HBox();
		this.vbxTela = new VBox();
		
		Window.mainStage.setWidth(800);
		Window.mainStage.setHeight(600);
		
		this.txtProjectName.setMaxWidth(300);
		this.txtDescProject.setMaxWidth(300);
		this.txtDescProject.setPrefRowCount(10);
		this.txtDescProject.setWrapText(true);
		
		this.txtMember.setMaxWidth(300);
		this.txtProjectName.setAlignment(Pos.CENTER);
		this.txtMember.setAlignment(Pos.CENTER);
		this.btnSalvar = new Button("Salvar");
		
		this.btnInvite = new Button("Convidar");
		this.btnCancelar = new Button("Cancelar");

		hbxPN.getChildren().addAll(lblProjectName,txtProjectName);
		hbxPN.setSpacing(10);
		hbxPN.setAlignment(Pos.CENTER);

		
		hbxBTN.getChildren().addAll(btnSalvar,btnCancelar);
		hbxBTN.setSpacing(10);
		hbxBTN.setAlignment(Pos.CENTER);
				
		hbxMember.getChildren().addAll(lblMember,txtMember,btnInvite);
		
		hbxMember.setSpacing(15);
		hbxMember.setAlignment(Pos.CENTER);
		
		
		vbxTela.getChildren().addAll(hbxPN,lblDescProject,txtDescProject,hbxMember,lblAddMember);
		vbxTela.getChildren().addAll(hbxBTN);
		vbxTela.setAlignment(Pos.CENTER);
		
		vbxTela.setSpacing(15);
		
		
		setRoot(vbxTela);
	}
}
