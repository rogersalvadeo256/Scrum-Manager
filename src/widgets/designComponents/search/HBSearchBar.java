package widgets.designComponents.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import application.main.Window;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.popoups.NewProjectPOPOUP;

public class HBSearchBar extends HBox{

	
	private TextField txtSearch;
	private Label lblInfo;
	private Button btnDoIt;
	private ImageView image ;
	private VBox layout;
	
	public HBSearchBar() throws FileNotFoundException {
		
		this.getStylesheets().add(this.getClass().getResource("/css/SEARCH_BAR.css").toExternalForm());
		this.applyCss();
		this.setId("hbox");
		
		this.txtSearch = new TextField();
		this.txtSearch.setPromptText("Encontre seus projetos");
		
		int size  = 30 ;
		this.image = new ImageView();
		this.image.setImage(new Image(new FileInputStream(new File("resources/images/icons/search.png"))));
		this.image.setFitHeight(size);
		this.image.setFitWidth(size);
		
		this.btnDoIt = new Button();
		this.btnDoIt.setGraphic(image);
		this.btnDoIt.setMaxSize(30, 30);
		
		this.lblInfo= new Label("Começar projeto");
		HBox hbLabel = new HBox();
		
		hbLabel.getChildren().add(lblInfo);
		hbLabel.setAlignment(Pos.CENTER_LEFT);
		hbLabel.setTranslateX(-120);
		
		hbLabel.setOnMouseClicked(e ->{
			try {
				new NewProjectPOPOUP(Window.mainStage).showAndWait();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		});
		
		
		this.layout=new VBox();
		
		this.setAlignment(Pos.CENTER_RIGHT);
		
		this.getChildren().addAll(hbLabel, txtSearch, btnDoIt);
		
		this.layout.getChildren().add(this);
		
		this.layout.setId("vbox");
		
	}
	
	
	public TextField getTxtSearch() {
		return txtSearch;
	}
	public void  setTextSearchEvent(EventHandler<KeyEvent> e ) { 
		this.txtSearch.setOnKeyTyped(e);
	}
	public void setButtonSearchEvent(EventHandler<ActionEvent> e) { 
		this.btnDoIt.setOnAction(e);
	}
}















