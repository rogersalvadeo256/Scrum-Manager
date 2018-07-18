package alert.messages;

import java.util.ArrayList;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class MessageDialog  extends DialogPane{
	String dialogTitle;	
	String dialogHeader;
	String dialogContent;
	ArrayList<ButtonType> buttons;

	public MessageDialog() {
		this.buttons = new ArrayList<ButtonType>();
		this.buttons.clear();

		this.computeMinHeight(200);
		this.computeMinWidth(200);

		this.getStylesheets().add(getClass().getResource("/cssStyles/dialogPane.css").toExternalForm());
				
		
	}
	
	public void setButtons(ButtonType button){
		this.buttons.add(button);	
	}

	private void drawnButtons(int amount) {
		for(int i=0; i<amount;i++){
			this.createButton(this.buttons.get(i));
		}
	}
		
	

	public void setDialogHeader(String dialogHeader){
		this.setDialogHeader(dialogHeader);
	}
	public void setDialogTitle(String dialogTitle) {
		this.setDialogTitle(dialogTitle);
	}
	public void setDialogContent(String dialogContent) {
		this.setDialogContent(dialogContent);
	}
	
	public String getDialogHeader(){
		return this.dialogHeader;
	}
	public String getDialogTitle(){
		return this.dialogTitle;
	}
	public String getDialogContent(){
		return this.dialogContent;
	}
}
