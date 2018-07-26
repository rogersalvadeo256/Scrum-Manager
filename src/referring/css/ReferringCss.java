package referring.css;

import java.util.ArrayList;

import alert.message.MessageDialog;
import design.objects.MyLabel;
import javafx.scene.Scene;

public class ReferringCss {
	private final ArrayList<String> cssPATHS;
	
	public ReferringCss() {
		this.cssPATHS = new ArrayList<>();
		cssPATHS.add("css/Styles/loginScene.css");
	}

	public void referringScene(Scene scene, cssFile.cssFiles file) {
		if (file.equals(cssFile.cssFiles.LOGIN)) {
			scene.getStylesheets().add(scene.getClass().getResource("/cssStyles/loginScene.css").toExternalForm());
		}
		if(file.equals(cssFile.cssFiles.HOME_PAGE_SCENE)) {
			scene.getStylesheets().add(scene.getClass().getResource("/cssStyles/homeScene.css").toExternalForm());
		}
	}
	public void referringLabel(MyLabel label) { 
		label.getStylesheets().add(label.getClass().getResource("/cssStyles/label.css").toExternalForm());
	}
	public void referringDialogPane(MessageDialog dialog) {
		dialog.getStylesheets().add(dialog.getClass().getResource("/cssStyles/dialogPane.css").toExternalForm());
	}
	public static class cssFile {
		public static enum cssFiles {
			LOGIN, HOME_PAGE_SCENE;
		};
	}
}



























