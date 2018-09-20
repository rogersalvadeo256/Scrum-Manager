package widgets.designComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class SearchBar extends HBox{

	private TextField txtSearch;
	private Button btnSearch;
	
	 public SearchBar() throws FileNotFoundException {
		 	ImageView icon_search = new ImageView();
			icon_search.setImage(new Image(new FileInputStream(new File("resources/images/icons/add.png"))));
			icon_search.setFitHeight(100);
			icon_search.setFitWidth(50);	
			this.btnSearch = new Button();
			this.btnSearch.setGraphic(icon_search);
	 
			this.txtSearch=new TextField();
			this.getChildren().addAll(txtSearch,btnSearch);
			this.setAlignment(Pos.CENTER);
	 }
	/*
	 * finish later
	 * 
	 */
	
	
	
	
	
}












