package widgets.alertMessage;

import javafx.scene.control.DialogPane;

public class MessageDialog  extends DialogPane{
	String dialogTitle;	
	String dialogHeader;
	String dialogContent;
	
	
	public MessageDialog() {
		this.computeMinHeight(200);
		this.computeMinWidth(200);
		this.getStylesheets().add(this.getClass().getResource("/css/DIALOG_PANE2.css").toExternalForm());
	}
}