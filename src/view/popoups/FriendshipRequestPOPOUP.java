package view.popoups;

import java.io.FileNotFoundException;

import application.controllers.FriendshipRequestsController;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class FriendshipRequestPOPOUP extends StandartLayoutPOPOUP {

	private final ScrollBar sc;
	private FriendshipRequestsController controller;
	/**
	 * This scene will contain the friendship requests of the user the scene have
	 * he's own stage, this because are a popoup window
	 * 
	 * @author jefter66
	 * @throws FileNotFoundException
	 */
	public FriendshipRequestPOPOUP(Window owner) throws FileNotFoundException {
		super(owner);
		this.controller = new FriendshipRequestsController();
		this.scene.getStylesheets().add(this.getClass().getResource("/css/FRIEND_REQUEST2.css").toExternalForm());
		

		this.initStyle(StageStyle.DECORATED);
		
		this.setScene(scene);

		controller.init(this.layout, this);
		/*
		 * make this work
		 */
		this.sc = new ScrollBar();
		this.sc.setLayoutX(scene.getWidth() - sc.getWidth());
		this.sc.setMin(0);
		this.sc.setOrientation(Orientation.VERTICAL);
		this.sc.prefHeight(this.getHeight());
	}
}

/**
 * 
 * @author ensismoebius
 *
 */
/*
	class Componente extends VBox {

	private Button btn;

	public Componente() {
		this.btn = new Button("ewewe");
		this.getChildren().add(btn);
	}
	public void setEventHandler(EventHandler<ActionEvent> e) {
		this.btn.setOnAction(e);
		}
*/
