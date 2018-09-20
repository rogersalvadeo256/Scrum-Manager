package widgets.alertMessage;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class CustomAlert extends Alert {

	private MessageDialog dialogPane;

	public CustomAlert(AlertType alert, String title, String header, String contentText) {
		super(alert);
		init(alert, title, header, contentText);
	}

	public CustomAlert(AlertType alert, String title, String header, String contentText, boolean btnCancel) {
		super(alert);
		init(alert, title, header, contentText);
		this.getButtonTypes().add(ButtonType.CANCEL);

	}

	public void init(AlertType alert, String title, String header, String contentText) {

		dialogPane = new MessageDialog();
		this.setDialogPane(dialogPane);
		this.setTitle(title);
		this.setHeaderText(header);
		this.setContentText(contentText);
		this.getButtonTypes().add(ButtonType.OK);
		this.show();
	}

}
