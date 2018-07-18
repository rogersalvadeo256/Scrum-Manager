package fields;

public class FieldValidation { 

	public FieldsValidation(){  

	}

	public boolean isTextFieldEmpty(TextField tfield) { 

		if(tfield.getText.trim().isEmpty()) return true;
		return false;
	}
	public boolean isPasswordFieldEmpty(PasswordField pfield){

		if(pfield.getText().isEmpty()) return true;
		return false;
	}
}













































