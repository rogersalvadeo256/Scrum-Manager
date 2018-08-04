package validation;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CheckEmptyFields { 
	
	/**
	 * If the return are false, the field isn't empty
	 * @param TextField tfield
	 * @return boolean
	 * @author jefter66
	 */
	public boolean isTextFieldEmpty(TextField tfield) { 
		if(tfield.getText().trim().isEmpty()) return true;
		return false;
	}
	/**
	 * If the return are false, the field isn't empty
	 * @param PasswordField pfield
	 * @return boolean
	 * @author jefter66
	 */
	public boolean isPasswordFieldEmpty(PasswordField pfield){
		if(pfield.getText().isEmpty()) return true;
		return false;
	}
}













































