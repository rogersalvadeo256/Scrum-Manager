package widgets;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import POJOs.Profile;
import POJOs.UserRegistration;
import db.hibernate.factory.Database;

public class AddFriend {
	private EntityManager em;
	private ArrayList<HBProfileContent> returnFromSearch;
	
	public AddFriend() {
		em = Database.createEntityManager();
		this.returnFromSearch=new ArrayList<HBProfileContent>();
	}
	public void search(String strSearch) { 
		Query q = em.createQuery("from UserRegistration where userName like :userName");
		q.setParameter("userName", "%" + strSearch.toString() + "%");

		Query q1 = em.createQuery("from Profile where name like :name");
		q1.setParameter("name", "%" + strSearch.toString() + "%");
		
		this.returnFromSearch.clear();
		
		for(int i=0;i<q.getResultList().size(); i++) {
			UserRegistration u = (UserRegistration) q.getResultList().get(i);
			this.returnFromSearch.add(new HBProfileContent(u));
		}
		
		for(int i=0;i<q1.getResultList().size();i++) { 
			Profile p = (Profile) q1.getResultList().get(i);
			this.returnFromSearch.add(new HBProfileContent(p));
		}
		return ;
	}
	public ArrayList<HBProfileContent> searchResults() {
		return returnFromSearch;
	}

}
