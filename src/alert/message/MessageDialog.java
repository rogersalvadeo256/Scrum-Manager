package alert.message;

import java.util.ArrayList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import referring.css.ReferringCss;

public class MessageDialog  extends DialogPane{
	String dialogTitle;	
	String dialogHeader;
	String dialogContent;
	ArrayList<ButtonType> buttons;
	ReferringCss cssReference;
	public MessageDialog() {
		this.buttons = new ArrayList<ButtonType>();
		this.buttons.clear();
		this.computeMinHeight(200);
		this.computeMinWidth(200);
		this.cssReference=new ReferringCss();
		this.cssReference.referringDialogPane(this);
	}
	public void setButtons(ButtonType button){
		this.buttons.add(button);	
	}

	private void drawnButtons(int amount) {
		for(int i=0; i<amount;i++){
			this.createButton(this.buttons.get(i));
		}
	}
}