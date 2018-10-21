package widgets.designComponents.projectContents;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import statics.ENUMS;

public class ScrollColumn extends ScrollPane{
	private HBox hColumn;
	private VBox vbLeft,vbRight;
	private VBox column;
	
	public ScrollColumn () { 
		this.getStylesheets().add(this.getClass().getResource("/css/SCROLL.css").toExternalForm());
		this.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.setVbarPolicy(ScrollBarPolicy.ALWAYS);
			
		this.column = new VBox();
		this.hColumn = new HBox();
		this.vbLeft = new VBox();
		this.vbRight = new VBox();
		
		column.setId("teste");
		
		column.setAlignment(Pos.CENTER);
		column.getChildren().add(new Label(ENUMS.PROJECT_FRAMEWORK.TO_DO.getValue()));
		column.getChildren().addAll(hColumn);
		
		for ( int i = 0 ; i < 100 ; i ++) { 
			vbLeft.getChildren().add(new Label (" ALKFSJLFJAFL"));
			vbRight.getChildren().add(new Label (" ALKFSJLFJAFL"));
		}
		
		hColumn.getChildren().addAll(vbLeft,vbRight);
		
		this.setContent(column);
		

		
		
	}





















}
