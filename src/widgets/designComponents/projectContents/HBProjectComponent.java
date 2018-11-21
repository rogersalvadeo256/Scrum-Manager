package widgets.designComponents.projectContents;

import java.util.Random;

import db.pojos.PROJECT;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class HBProjectComponent extends HBox {
	Label lblName;
	Text  lblAboutProject;
	VBox layout;
	Circle circle;
	PROJECT project;
	public HBProjectComponent(PROJECT p, Pos position ){
		this.getStylesheets().add(this.getClass().getResource("/css/PROJECT_COMPONENT.css").toExternalForm());
		
		this.project=p;
		this.applyCss();
		this.setId("v");	
		this.layout = new VBox();
		this.lblName = new Label(p.getProjName());
		this.lblAboutProject = new Text(p.getProjDescription());
		
		lblName.setId("name");
		lblAboutProject.setId("about");
		
		lblName.setWrapText(true);
		this.lblAboutProject.setWrappingWidth(250);
		
		this.circle = new Circle();
		circle.setCenterX(30.0f);
		circle.setCenterY(15.0f);
		circle.setRadius(10.0f);
		circle.setFill(randomColor());
		
		HBox hbTypeProject = new HBox();
		
		Label lblTypeProject =  new Label(p.getProjType());
		lblTypeProject.setId("type");

		hbTypeProject.getChildren().addAll(circle,lblTypeProject);
		hbTypeProject.setAlignment(position);
		hbTypeProject.setSpacing(10);
		
		this.setAlignment(position);
		this.layout.setAlignment(position);
		
		layout.getChildren().addAll(lblName, lblAboutProject, hbTypeProject);
		
		this.getChildren().add(layout);
	}

	public void setOnClick(EventHandler<MouseEvent> e) { 
		this.setOnMouseClicked(e);
	}
	
	public PROJECT getProject() {
		return project;
	}

	public void setProject(PROJECT project) {
		this.project = project;
	}
	public Paint randomColor() {
		Random random = new Random();
		int r = random.nextInt(255);
		int g = random.nextInt(255);
		int b = random.nextInt(255);
		return Color.rgb(r, g, b);
	}
}
