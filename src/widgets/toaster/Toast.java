package widgets.toaster;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Toast extends Stage {
	Scene scene; 
	HBox layout;
	Label text;
	public Toast(Window owner,String text) {
		this.text = new Label(text);
		this.layout = new HBox();
		this.scene  = new Scene(layout);
		this.setScene(scene);

		Runnable show = new Runnable() {
			@Override
			public void run() {
				while (true) {
					Toast.this.show();
					try {
						Thread.sleep(0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		
	}
	
	
}


































