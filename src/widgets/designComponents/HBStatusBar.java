package widgets.designComponents;

import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import statics.ENUMS;
import statics.ENUMS.DISPONIBILITY_FOR_PROJECT;
import statics.SESSION;

public class HBStatusBar extends HBox {

	ToggleButton tbBusy;
	ToggleButton tbAvailable;
	ToggleGroup toggleGroup;

	public HBStatusBar() {
		
		this.getStylesheets().add(this.getClass().getResource("/css/TOGGER.css").toExternalForm());

		this.tbBusy = new ToggleButton("Ocupado");
		tbBusy.getStyleClass().add("toggle-button");
		this.tbBusy.setId("btnBusy");
		
		this.tbAvailable = new ToggleButton("Disponivel");
		this.tbAvailable.setId("btnAvalible");

		this.toggleGroup = new ToggleGroup();

		this.tbBusy.setToggleGroup(toggleGroup);
		this.tbAvailable.setToggleGroup(toggleGroup);

		this.toggleGroup.getSelectedToggle();

		
		
		System.out.println(ENUMS.GET_DISPONIBILITY_FOR_PROJECT(DISPONIBILITY_FOR_PROJECT.AVAILABLE).toString());
		System.out.println(SESSION.getProfileLogged().getStatus());

		String a = SESSION.getProfileLogged().getStatus();
		String b = ENUMS.GET_DISPONIBILITY_FOR_PROJECT(DISPONIBILITY_FOR_PROJECT.AVAILABLE).toString();
		
		if(String.valueOf(a) != String.valueOf(b) ) { 
			
			this.tbAvailable.setSelected(true);
			
		}
		this.setAlignment(Pos.CENTER);
		
		
		
		this.getChildren().addAll(this.tbBusy, this.tbAvailable);

	}

}












