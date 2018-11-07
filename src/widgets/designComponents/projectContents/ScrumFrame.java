package widgets.designComponents.projectContents;

import db.pojos.PROJECT_TASK;
import javafx.scene.layout.HBox;
import statics.ENUMS;

public class ScrumFrame extends HBox {
	
		private ScrollColumn toDo;
		private ScrollColumn doing;
		private ScrollColumn done;
		
		
		public ScrumFrame () { 
			
			this.getStylesheets().add(this.getClass().getResource("/css/SCRUM_FRAME.css").toExternalForm());
			
			this.setId("hbox");
			
			this.toDo  = new ScrollColumn(ENUMS.PROJECT_FRAMEWORK.TO_DO);
			this.doing  = new ScrollColumn(ENUMS.PROJECT_FRAMEWORK.DOING);
			this.done= new ScrollColumn(ENUMS.PROJECT_FRAMEWORK.DONE);
			this.getChildren().addAll(toDo,doing,done);
			
		}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
