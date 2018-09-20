package scenes.popoups;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import application.controllers.FriendshipRequestsController;
import db.pojos.Profile;
import db.util.GENERAL_STORE;
import db.util.SESSION;
import friendship.FriendshipRequest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import widgets.designComponents.HBFriendRequest;

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
		this.scene.getStylesheets().add(this.getClass().getResource("/css/FRIEND_REQUEST.css").toExternalForm());

		this.setScene(scene);

		controller.init(this, this.layout);
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
class Componente extends VBox {

	private Button btn;

	public Componente() {
		this.btn = new Button("ewewe");
		this.getChildren().add(btn);
	}

	public void setEventHandler(EventHandler<ActionEvent> e) {
		this.btn.setOnAction(e);
	}
}
