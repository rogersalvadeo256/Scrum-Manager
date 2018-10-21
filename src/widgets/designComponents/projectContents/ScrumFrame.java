package widgets.designComponents.projectContents;

import javafx.scene.layout.HBox;

public class ScrumFrame extends HBox {
	
		private ScrollColumn toDo;
		private ScrollColumn doing;
		private ScrollColumn done;
		
		
		public ScrumFrame () { 
			
			this.toDo  = new ScrollColumn();
			this.doing  = new ScrollColumn();
			this.done= new ScrollColumn();
			
			this.getChildren().addAll(toDo,doing,done);
			
		}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
