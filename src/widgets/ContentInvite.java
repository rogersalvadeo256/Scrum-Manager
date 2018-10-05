package widgets;

import db.pojos.USER_PROFILE;
import friendship.QUERYs_FRIENDSHIP;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ContentInvite extends HBox {
	
//	private  
	private VBox leftColumn, rightColumn;
	
	
	public ContentInvite() {

		this.leftColumn = new VBox();
		this.rightColumn = new VBox();
		
	}
	
	private void  loadComponents () { 

		
		
		
	}

	
	private  void populateList() { 
		
		 
		 for (USER_PROFILE p : QUERYs_FRIENDSHIP.friendsList()) {
			 
			 
			 
			
		 }

		 
		 
		 
		 
		 
		 
		 
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
