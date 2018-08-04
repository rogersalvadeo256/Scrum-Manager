package referring.css;

import alert.message.MessageDialog;
import javafx.scene.Scene;

public class ReferringCss {
	private static final String path = "/cssStyles/";
	private static final String extension = ".css";

	public  static enum cssFiles {
		LOGIN_SCENE, HOME_PAGE_SCENE,DIALOG_PANE;
	};

	public static void referringScene(Scene scene, cssFiles file) {
		if (file.equals(cssFiles.LOGIN_SCENE)) {
			scene.getStylesheets().add(scene.getClass().getResource(path + cssFiles.LOGIN_SCENE + extension ).toExternalForm());
		}
		if(file.equals(cssFiles.HOME_PAGE_SCENE)) {
			scene.getStylesheets().add(scene.getClass().getResource(path + cssFiles.HOME_PAGE_SCENE + extension ).toExternalForm());
		}
	}
	public static void referringDialogPane(MessageDialog dialog) {
		dialog.getStylesheets().add(dialog.getClass().getResource(path + cssFiles.DIALOG_PANE + extension).toExternalForm());
	}
		
}






















