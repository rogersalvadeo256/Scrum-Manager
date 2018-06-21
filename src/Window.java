import java.sql.SQLException;

import javafx.stage.Stage;

public class Window extends Stage {

	
	public static Stage janela;
	
 	public  Window() throws ClassNotFoundException, SQLException {
 		
 		Window.janela = this;
 		janela.setScene(new LoginScreen());
		this.show();
	}
}