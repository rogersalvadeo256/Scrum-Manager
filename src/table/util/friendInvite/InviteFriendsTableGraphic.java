package table.util.friendInvite;

import db.pojos.Profile;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Window;
import scenes.popoups.StandartLayoutPOPOUP;
import statics.SESSION;

public class InviteFriendsTableGraphic extends StandartLayoutPOPOUP {

	private ObservableList<Profile> friendsList;
	private TableView<Profile> table;
	
	public InviteFriendsTableGraphic(Window owner) {
		super(owner);
		
		TableColumn<Profile, ImageView> image = new TableColumn<Profile, ImageView>("image");
		TableColumn<Profile, String> name = new TableColumn<Profile, String>("Nome");
//		TableColumn<Profile, String> status = new TableColumn<Profile, String>("Descrição");

		this.table.getColumns().addAll(image,name);
		this.table.setItems(this.friendsList);
		
		image.setCellValueFactory(new PropertyValueFactory("image"));
		name.setCellValueFactory(new PropertyValueFactory("name"));
//		status.setCellValueFactory(new PropertyValueFactory("descricao"));

		image.setCellFactory(new TableEditor());

		for(int i=0;i<SESSION.getProfileLogged().getFriendsList().size();i++) {
			this.friendsList.add(SESSION.getProfileLogged().getFriendsList().get(i));
	
		
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}	

