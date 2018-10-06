package widgets.designComponents.photoContent;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ShowImage extends Stage{

	private Scene scene;
	private VBox container;
	private ImageView image;
	public ShowImage(Image image, Window owner) {
		this.container = new VBox();
		
		this.image = new ImageView(image);
		this.image.prefHeight(image.getHeight());
		this.image.prefWidth(image.getWidth());
		
		this.container.getChildren().add(this.image);
		this.scene = new Scene(container);
		
		this.setScene(scene);
		
		this.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				
				if(e.getCode() == KeyCode.ESCAPE) ShowImage.this.close();
			}
		});
		
		
		this.initOwner(owner);
		this.initModality(Modality.WINDOW_MODAL);
		this.setResizable(false);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
