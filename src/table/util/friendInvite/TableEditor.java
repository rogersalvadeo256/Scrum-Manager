package table.util.friendInvite;

import db.pojos.Profile;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class TableEditor implements Callback<TableColumn<Profile, ImageView>, TableCell<Profile, ImageView>> {

	@Override
	public TableCell<Profile, ImageView> call(TableColumn<Profile, ImageView> param) {
		return new InviteFriend();
	}
	
	
	class InviteFriend extends TableCell<Profile, ImageView>{ 
		
		
		public InviteFriend() {
			
			if(this.isSelected()) { 
				
				
			}
			
			
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	

}





















