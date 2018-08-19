package widgets;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VBAddFriend extends VBox {

	
	
	public VBAddFriend(String searchField) {
		
		new UserFound(searchField);
		return;
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private class UserFound {
		
		private HBox hbUsr;
		private ImageView usrImage;
		private Label usrName, usrBio;
		public UserFound(String userName) {
			EntityManager em = Database.createEntityManager();
			
			Query q = em.createQuery("from UserRegistration where userName=:userName"); 
			q.setParameter("userName", userName.toString());
			
			Query q1 = em.createQuery("from Profile where name=:name");
			q1.setParameter("name", userName.toString());
			
			
			
			
			
		
		}
		
		
	}

}









































