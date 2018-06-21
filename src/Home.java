

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class Home extends Scene {

	
	private GridPane layoutUpside;
	private GridPane layoutDown;
	
	
	
	public Home() {
		super(new HBox());
		
		this.layoutUpside = new GridPane();
		this.layoutDown = new GridPane();

		
			
		   layoutDown.setMinHeight(250);
           layoutDown.setMinWidth(500);
           layoutUpside.setMaxHeight(500);
           layoutUpside.setMaxWidth(1000);
		
           
           
          layoutDown.add(oi = new Label("w2222eqsg"), 0, 1);
		
		
		
          
          
          
          
          this.setRoot(layoutDown);
		
		
		
		
		

	}

}
