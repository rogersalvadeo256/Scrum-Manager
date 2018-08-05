package css.indicator.object;

import alert.message.MessageDialog;
import javafx.scene.Scene;

public class IndicatorOfCss {
	private static String path = "/cssStyles/";
	private static String extension = ".css";

	public IndicatorOfCss() {
	}

	public static enum cssFile {
		LOGIN_SCENE, HOME_PAGE_SCENE, DIALOG_PANE;
	};

	/**
	 * the first parameter is the scene that want to referrer in a css file the
	 * second the css file name
	 * 
	 * @param Scene
	 *            scene
	 * @param cssFile
	 *            file
	 */
	public static void referringScene(Scene scene, cssFile file) {
		if (file.equals(cssFile.LOGIN_SCENE)) {
			String realPath = path + cssFile.LOGIN_SCENE.toString() + extension;
			scene.getStylesheets().add(	scene.getClass().getResource(realPath.trim()).toExternalForm());
		}
		if (file.equals(cssFile.HOME_PAGE_SCENE)) {
			String realPath = IndicatorOfCss.path + cssFile.HOME_PAGE_SCENE.toString() + extension;
			scene.getStylesheets().add(scene.getClass().getResource(realPath.trim()).toExternalForm());
		}
	}

	public static void referringDialogPane(MessageDialog dialog) {
		String realPath = path + cssFile.DIALOG_PANE + extension;
		dialog.getStylesheets()	.add(dialog.getClass().getResource(realPath.trim()).toExternalForm());
	}

}
