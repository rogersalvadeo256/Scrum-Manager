package friendship;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import POJOs.Profile;
import db.hibernate.factory.Database;
import widgets.HBProfileContent;

public class SearchFriend {
	private EntityManager em;
	private ArrayList<HBProfileContent> returnFromSearch;
	private ArrayList<Profile> listOptions;
	
	public SearchFriend() {
		this.returnFromSearch = new ArrayList<HBProfileContent>();
		this.listOptions=new ArrayList<Profile>();
	}
	public void loadOptions(String name) {
		if (em == null) {
			em = Database.createEntityManager();
		}
		
		Query q = em.createQuery("from Profile where name like =:");
		q.setParameter("name", "%" + name + "%");
		
		
		
		for(int i=0;i<q.getResultList().size();i++) {
			this.listOptions.add((Profile) q.getResultList().get(i));
		}
		em.clear();
		em.close();
		em = null;
	}

	public ArrayList<Profile> getListOptions() {
		return listOptions;
	}
	public void setListOptions(ArrayList<Profile> listOptions) {
		this.listOptions = listOptions;
	}
	public ArrayList<HBProfileContent> searchResults() {
		return returnFromSearch;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
