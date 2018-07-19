package fields;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FieldValidation { 

	public FieldValidation(){  

	}
	public boolean isTextFieldEmpty(TextField tfield) { 
		if(tfield.getText().trim().isEmpty()) return true;
		return false;
	}
	public boolean isPasswordFieldEmpty(PasswordField pfield){
		if(pfield.getText().isEmpty()) return true;
		return false;
	}
}













































