package application.main;

import db.hibernate.factory.Database;
/*
 * SCRUM MANAGER TCC PROJECT
 * 
 * 	CREATED ON: 18 JUN  2018
 * 
 * AUTHORS: ANDRÉ LOPES, 
 * 			JEFTER SANTIAGO, ROGER FERRARO 
 * 
 */
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		Database.createEntityManager();
		new Window();
	}
}
