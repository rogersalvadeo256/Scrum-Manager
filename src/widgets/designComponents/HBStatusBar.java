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

	/*
	 * positive for available or active
	 */
	ToggleButton tbPositive;
	/*
	 * negative for busy or inactive
	 */
	ToggleButton tbNegative;
	ToggleGroup toggleGroup;

	public HBStatusBar(boolean availability, String strNegative, String strPositive) {

		this.tbNegative = new ToggleButton(strNegative);
		this.tbPositive = new ToggleButton(strPositive);
		
		init();

		if (availability) {
			if (SESSION.getProfileLogged().getStatus().equals(ENUMS.DISPONIBILITY_FOR_PROJECT.AVAILABLE.getValue()))
				tbPositive.setSelected(true);
			if (SESSION.getProfileLogged().getStatus().equals(ENUMS.DISPONIBILITY_FOR_PROJECT.BUSY.getValue()))
				tbNegative.setSelected(true);
			return;
		}

		if (SESSION.getUserLogged().getStatus().equals(ENUMS.ACCOUNT_STATUS.ACTIVE.getValue()))
			tbPositive.setSelected(true);
		return;
	}

	private void init() {
		this.getStylesheets().add(this.getClass().getResource("/css/TOGGLE.css").toExternalForm());

		this.getStyleClass().add("hbox");
		this.toggleGroup = new ToggleGroup();

		this.tbNegative.setToggleGroup(toggleGroup);
		this.tbPositive.setToggleGroup(toggleGroup);

		this.tbPositive.setId("btnAvailable");
		this.tbNegative.setId("btnBusy");


		this.getChildren().addAll(this.tbNegative, this.tbPositive);
		this.setSpacing(20);
		this.setAlignment(Pos.CENTER);
	}
	public void setGroupEvent(ChangeListener<Toggle> a) {
		this.toggleGroup.selectedToggleProperty().addListener(a);
	}
}
