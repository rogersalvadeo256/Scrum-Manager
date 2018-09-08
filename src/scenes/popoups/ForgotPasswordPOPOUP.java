package scenes.popoups;

import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ForgotPasswordPOPOUP extends Stage {
	
	private Label lblUserName;
	
	
	public ForgotPasswordPOPOUP(Window owner) {
		
		
		
		
		
		this.initOwner(owner);
		this.initModality(Modality.WINDOW_MODAL);
		this.setWidth(600);
		this.setHeight(500);
		this.setResizable(false);
	}
	
	
	
	
	
	
	
	
	
	
	
}
