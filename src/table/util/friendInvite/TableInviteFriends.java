package table.util.friendInvite;
import java.util.List;

import db.pojos.USER_PROFILE;
import friendship.QUERYs_FRIENDSHIP;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import widgets.designComponents.HBProfileContentForTable;

public class TableInviteFriends extends VBox {

	private ObservableList<USER_PROFILE> friendsList;
	private TableView<USER_PROFILE> table;

	@SuppressWarnings("unchecked")
	public TableInviteFriends() {

		this.table = new TableView<USER_PROFILE>();

			
		TableColumn<USER_PROFILE, String> name = new  TableColumn<USER_PROFILE, String>("Nome");
		
		name.setCellValueFactory(new PropertyValueFactory<>("PROF_NAME"));
		
		this.table.setItems(getList());
		
		this.table.getColumns().add(name);	
		
		
		
		this.getChildren().add(table);

	}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	
	
		
		
		
	private ObservableList<USER_PROFILE> getList() {

		boolean t = friendsList == null ? true : false;
		if (t)
			this.friendsList = FXCollections.observableArrayList();

		List<USER_PROFILE> list = QUERYs_FRIENDSHIP.friendsList();

		for (USER_PROFILE profile : list) {
			this.friendsList.add(profile);
		}
		return this.friendsList;
	}

	
	private ObservableList<HBProfileContentForTable> loadComponents () { 
		return null;
	}
	
	
	
	

}
