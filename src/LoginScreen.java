
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class LoginScreen extends Scene {

	private final Label message;
	private Label lblEmail, lblPassword;

	private Button btnLogin, btnExit, btnSingIn;
	private TextField txtEmail;
	private PasswordField txtPassword;
	private GridPane layout;
	private Insets borders;
	private DatabaseConnection connect;
	private Hyperlink esqueciSenha;

	public LoginScreen() throws ClassNotFoundException, SQLException {

		super(new HBox());

		this.layout = new GridPane();

		this.message = new Label("");

		this.esqueciSenha = new Hyperlink("Esqueci minha senha");

		this.connect = new DatabaseConnection();

		this.lblEmail = new Label("UserName");
		this.txtEmail = new TextField();

		this.lblPassword = new Label("Password");
		this.txtPassword = new PasswordField();

		this.btnLogin = new Button("Login");
		this.btnExit = new Button("Exit");
		this.btnSingIn = new Button("SingIn");

		this.layout.add(lblEmail, 0, 0);
		this.layout.add(txtEmail, 1, 0);

		this.layout.add(lblPassword, 0, 1);
		this.layout.add(txtPassword, 1, 1);

		this.layout.add(message, 1, 3);
		this.layout.add(esqueciSenha, 1, 2);
		this.layout.add(btnLogin, 0, 5, 2, 1);
		this.layout.add(btnExit, 0, 7, 2, 1);
		this.layout.add(btnSingIn, 0, 6, 2, 1);

		this.btnSingIn.setMaxWidth(500);
		this.btnLogin.setMaxWidth(500);
		this.btnExit.setMaxWidth(500);
		// this.getStylesheets().add(LoginScreen.class.getResource("/styles/style.css").toExternalForm());
		String css = this.getClass().getResource("/cssStyles/style.css").toExternalForm();
		this.getStylesheets().add(css);

		this.btnLogin.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Window.janela.setScene(new Home());

				 String name = LoginScreen.this.txtEmail.getText();
				 String pass = LoginScreen.this.txtPassword.getText();
				
				 try {
				 if (connect.validateLogin(name, pass)) {
				 message.setText("rigth, you can pass");
				 message.setTextFill(Color.rgb(21, 117, 84));
				 } else {
				 message.setText("you shall not pass ");
				 message.setTextFill(Color.rgb(210, 39, 30));
				 }
				 } catch (SQLException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
				 }

			}
		});

		this.btnExit.setOnAction(actionEvent -> Platform.exit());

		this.btnSingIn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// try {
				try {
					Window.janela.setScene(new RegistrationForm());
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//

			}
		});

		this.borders = new Insets(50);
		this.layout.setHgap(10); // espaço colocado horizontalmente
		this.layout.setVgap(10); // espaço colocado verticalmente
		this.layout.setAlignment(Pos.CENTER);
		this.layout.setPadding(borders);
		this.layout.setMinSize(400, 300);

		this.setRoot(layout);

	}

}
