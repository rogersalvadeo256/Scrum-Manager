package widgets.designComponents.search;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CustomScroll extends ScrollPane{
	
	
	private Node content;
	public CustomScroll () {  
		this.getStylesheets().add(this.getClass().getResource("/css/SCROLL.css").toExternalForm());
		this.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		this.content = new VBox();
	}
	public void setComponent( VBox content)  { 
		this.setContent(content);
		this.content = content;
	}
	
	public void setComponent (HBox hbContent) { 
		this.setContent(hbContent);
	}
	public Node getComponent() { 
		return this.content;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
