package POJOs;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Integrantes {

//	Rectangle imgProfile;
	String namee,funcao;
	ImageView foto;

	public String getName() {
		return namee;
	}

	public void setName(String namee) {
		this.namee = namee;
	}

	public ImageView getFoto() {
		return foto;
	}

	public void setFoto(ImageView foto) {
		this.foto = foto;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
	
}
