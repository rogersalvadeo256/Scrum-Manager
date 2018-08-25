package widgets;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;

public class VBSearchFieldsSugestions extends VBox {

	private ScrollBar sb;
	private Hyperlink hl;
	
	
	public VBSearchFieldsSugestions() {
		this.sb=new ScrollBar();
		this.hl=new Hyperlink("Ver todos os resultados de..." );
	
		this.maxWidth(Double.MAX_VALUE);
		this.maxHeight(Double.MAX_VALUE);

		   sb.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov,
	                Number old_val, Number new_val) {
	            	VBSearchFieldsSugestions.this.setLayoutY(-new_val.doubleValue());
	            }
	        });
		this.getChildren().add(hl);
	}
	
	
	public void drawSugestionBox(HBProfileContent hbp) { 
		if(this.getChildren().size() > 3) {
			this.sb.setLayoutX(this.getWidth() -sb.getWidth());
			this.sb.setMin(0);
			this.sb.setOrientation(Orientation.VERTICAL);
			this.sb.setPrefHeight(180);
			this.sb.setMax(360);
		}
		this.getChildren().add(hbp);
	}
	
}

