import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class RegistrationForm {
	
	private Label lblName, lblUserName, lblEmail, lblPassword,lblConfirmPassword;
	private TextField txtName, txtUserName, txtEmail;
	private PasswordField passwordField, confirmPasswordField;
	private Button btnRegister, btnCancel;
	private GridPane layout;
	private final Label message;
	private Insets borders;
	private Button btnExit;

	
	public RegistrationForm() {
		this.layout = new GridPane();
		
		this.lblName = new Label("Nome: ");
		this.lblUserName = new Label("Nome de usuario: ");
		this.lblEmail = new Label("Email: ");
		this.lblPassword = new Label("Digite sua senha: ");
		this.lblConfirmPassword = new Label("Confirmar Senha: ");
		
		this.message = new Label("");
		this.layout.add(message, 0, 5);
		
		this.txtName = new TextField();
		this.txtEmail = new TextField();
		this.txtUserName = new TextField();
		
		this.passwordField = new PasswordField();
		this.confirmPasswordField = new PasswordField();
		
		this.btnExit = new Button("Sair");
		this.btnRegister = new Button("Cadastrar");
		this.btnCancel = new Button("Cancelar");

		this.btnRegister.setMaxWidth(600);
		this.btnCancel.setMaxWidth(600);
		this.btnCancel.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {

				
				
			}
		});

		this.btnExit.setMaxWidth(600);
		this.btnExit.setOnAction(actionEvent -> Platform.exit());
		

		
		this.layout.add(lblName, 0, 0, 1, 1);
		this.layout.add(txtName, 1, 0, 2, 1);
		
		this.layout.add(lblUserName, 0, 1, 1, 1);
		this.layout.add(txtUserName, 1, 1, 2, 1);
		
		this.layout.add(lblEmail, 0, 2, 1, 1);
		this.layout.add(txtEmail, 1, 2, 2, 1);
		
		this.layout.add(lblPassword, 0, 3, 1, 1);
		this.layout.add(passwordField, 1, 3, 2, 1);
		
		this.layout.add(lblConfirmPassword, 0, 4, 1, 1);    // confirmar senha automaticamente, antes de clicar no botao
		this.layout.add(confirmPasswordField, 1, 4, 2, 1);
		
		this.layout.add(btnRegister, 0, 5, 2, 1);
		this.layout.add(btnCancel, 0, 6, 2, 1);
		
		this.layout.add(btnExit, 1, 5, 2, 1);
		
		
		this.borders = new Insets(50);
		this.layout.setHgap(10); // espaço colocado horizontalmente
		this.layout.setVgap(10); // espaço colocado verticalmente
		this.layout.setAlignment(Pos.CENTER);
		this.layout.setPadding(borders);
		this.layout.setMinSize(600, 400);
	
		
	} 
	
	
	
	
	public Scene registerScene() { 
		Scene register = new Scene(layout);
		return register;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
