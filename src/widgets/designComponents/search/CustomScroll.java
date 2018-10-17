package widgets.designComponents.search;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CustomScroll extends ScrollPane{
	
	public CustomScroll () {  
		this.getStylesheets().add(this.getClass().getResource("/css/SCROLL.css").toExternalForm());
		this.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.setVbarPolicy(ScrollBarPolicy.ALWAYS);
	}
	
	public void setComponent( VBox content)  { 
		this.setContent(content);
	}
	
	public void setComponent (HBox hbContent) { 
		this.setContent(hbContent);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
