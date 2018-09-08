package widgets.alertMessage;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class CustomAlert extends Alert{


	private MessageDialog dialogPane;

	public CustomAlert(AlertType alert, String title, String header, String contentText) {
		super(alert);
		dialogPane=new MessageDialog();
		this.setDialogPane(dialogPane);
		this.setTitle(title);
		this.setHeaderText(header);
		this.setContentText(contentText);
		this.getButtonTypes().add(ButtonType.OK);
		this.show();
	}
}
