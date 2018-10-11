package widgets.designComponents.projectContents;

import java.util.List;

import db.pojos.PROJECT;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProjectContentHomePage extends VBox {
	
	private List<HBProjectComponent> components;
	private HBox firstRow, secondRow;
	
	
	public ProjectContentHomePage(double d) {
		this.setPrefHeight(d);
		PROJECT teste = new PROJECT();
		
		teste.setProjName("TESTES");
		teste.setProjDescription("NADA NÂO bla bla  bla bla  bla bla  bla bla  bla bla  bla bla  bla bla  bla bla ");
		teste.setProjType("Logistica");


		
		
		this.getChildren().add(new HBProjectComponent(teste));
		
	
	}
}
























