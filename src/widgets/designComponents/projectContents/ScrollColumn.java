package widgets.designComponents.projectContents;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import statics.ENUMS.PROJECT_FRAMEWORK;

public class ScrollColumn extends ScrollPane{
	private VBox column;
	
	public ScrollColumn (PROJECT_FRAMEWORK title) { 
		this.getStylesheets().add(this.getClass().getResource("/css/SCROLL.css").toExternalForm());
		this.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
	
		this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		this.column = new VBox();
		this.column.getStyleClass().add(this.getClass().getResource("/css/SCRUM_FRAME.css").toExternalForm());
		
		column.setId("column");
		
		column.setPrefWidth(220);
		
		
		column.setAlignment(Pos.CENTER);
		column.getChildren().add(new Label(title.getValue()));
		this.setContent(column);
		

		
		
	}





















}
