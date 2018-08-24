package friendship;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import POJOs.Profile;
import POJOs.UserRegistration;
import db.hibernate.factory.Database;
import widgets.HBProfileContent;

public class SearchFriend {
	private EntityManager em;
	private ArrayList<HBProfileContent> returnFromSearch;

	public SearchFriend() {
		this.returnFromSearch = new ArrayList<HBProfileContent>();
	}

	public void search(String strSearch) {
		if (em == null) {
			em = Database.createEntityManager();
		}
		Query q = em.createQuery("from UserRegistration where userName like :userName");
		q.setParameter("userName", "%" + strSearch.toString() + "%");

		Query q1 = em.createQuery("from Profile where name like :name");
		q1.setParameter("name", "%" + strSearch.toString() + "%");

		this.returnFromSearch.clear();

			Profile p = null;
			UserRegistration u = null;
			for (int i = 0; i < q.getResultList().size(); i++) {
				u = (UserRegistration) q.getResultList().get(i);
				this.returnFromSearch.add(new HBProfileContent(u));

			}
			for (int i = 0; i < q1.getResultList().size(); i++) {
				p = (Profile) q1.getResultList().get(i);
				if(u.getProfile().getCod() != p.getCod()) {
					this.returnFromSearch.add(new HBProfileContent(p));
				}
			}

			
			
			
			
			
			
		em.clear();
		em.close();
		em = null;
	}

	public ArrayList<HBProfileContent> searchResults() {
		return returnFromSearch;
	}
}
