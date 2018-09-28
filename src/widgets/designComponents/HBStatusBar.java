package widgets.designComponents;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import statics.ENUMS;
import statics.SESSION;

public class HBStatusBar extends HBox {

	ToggleButton tbBusy;
	ToggleButton tbAvailable;
	ToggleGroup toggleGroup;

	public HBStatusBar(String leftButtonText, String rightButtonText) {
		init();
		this.tbBusy.setText(leftButtonText);
		this.tbAvailable.setText(rightButtonText);

		if (SESSION.getProfileLogged().getStatus().equals(ENUMS.DISPONIBILITY_FOR_PROJECT.AVAILABLE.getValue()))
			tbAvailable.setSelected(true);
		if (SESSION.getProfileLogged().getStatus().equals(ENUMS.DISPONIBILITY_FOR_PROJECT.BUSY.getValue()))
			tbBusy.setSelected(true);

		this.getChildren().addAll(this.tbBusy, this.tbAvailable);

	}
	
	

	public HBStatusBar() {
		init();
	}

	private void init() {
		this.getStylesheets().add(this.getClass().getResource("/css/TOGGLE.css").toExternalForm());

		this.tbBusy = new ToggleButton();
		this.tbAvailable = new ToggleButton();

		this.toggleGroup = new ToggleGroup();

		this.tbBusy.setToggleGroup(toggleGroup);
		this.tbAvailable.setToggleGroup(toggleGroup);
		
		this.setAlignment(Pos.CENTER);
	
	}

	public void setGroupEvent(ChangeListener<Toggle> a) {
		this.toggleGroup.selectedToggleProperty().addListener(a);
	}

}
