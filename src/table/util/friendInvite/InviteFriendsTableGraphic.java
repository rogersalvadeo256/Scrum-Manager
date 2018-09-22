package table.util.friendInvite;

import db.pojos.USER_PROFILE;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Window;
import scenes.popoups.StandartLayoutPOPOUP;
import statics.SESSION;

public class InviteFriendsTableGraphic extends StandartLayoutPOPOUP {

	private ObservableList<USER_PROFILE> friendsList;
	private TableView<USER_PROFILE> table;

	public InviteFriendsTableGraphic(Window owner) {
		super(owner);

		TableColumn<USER_PROFILE, ImageView> image = new TableColumn<USER_PROFILE, ImageView>("image");
		TableColumn<USER_PROFILE, String> name = new TableColumn<USER_PROFILE, String>("Nome");
//		TableColumn<Profile, String> status = new TableColumn<Profile, String>("Descrição");

		this.table.getColumns().addAll(image, name);
		this.table.setItems(this.friendsList);

		image.setCellValueFactory(new PropertyValueFactory("image"));
		name.setCellValueFactory(new PropertyValueFactory("name"));
//		status.setCellValueFactory(new PropertyValueFactory("descricao"));

		image.setCellFactory(new TableEditor());

//		for(int i=0;i<SESSION.getProfileLogged().getFriendsList().size();i++) {
//			this.friendsList.add(SESSION.getProfileLogged().getFriendsList().get(i));

	}

}
