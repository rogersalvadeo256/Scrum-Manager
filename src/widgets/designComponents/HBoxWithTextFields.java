package widgets.designComponents;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class HBoxWithTextFields extends HBox {

	private Label lblLabel;
	private TextField txtTextField;

	public HBoxWithTextFields(String labelText) {
		this.lblLabel = new Label(labelText.toString());
		this.txtTextField = new TextField();
		this.getChildren().addAll(lblLabel,txtTextField);
		this.setSpacing(20);
	}
	public String getText() {return txtTextField.getText();}
	public void setText(TextField text) {this.txtTextField = text;}
}
