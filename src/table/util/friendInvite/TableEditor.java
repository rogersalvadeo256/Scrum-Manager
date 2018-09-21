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

	class InviteFriend extends TableCell<Profile, ImageView> {

		private ImageView image;

		public InviteFriend() {
			this.image = new ImageView();
		}

		@Override
		public void startEdit() {
			super.startEdit();
			setGraphic(this.image);

			this.image.setImage(this.getItem().getImage());

			setText(null);
		}

		@Override
		protected void updateItem(ImageView item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				this.setText(null);
				this.setGraphic(null);
			} else {
				this.setText(null);
				this.setGraphic(this.image);
			}
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();
			this.setGraphic(this.image);
		}

	}

}
