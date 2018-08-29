package validation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;

public class ForgotPassword implements 	 EventHandler<ActionEvent> {
	
//	private boolean valide
	public ForgotPassword(PasswordField p1, PasswordField p2) {
		
		if(p1.getText().equals(p2.getText())) ;
		
	}
	
	
	
	
	
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}


	
	
	
}
