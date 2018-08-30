package friendship;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import POJOs.Profile;
import POJOs.UserRegistration;
import db.hibernate.factory.Database;
import db.user.util.SESSION;
import widgets.HBProfileContent;

public class SearchFriend {
	private EntityManager em;
	private ArrayList<HBProfileContent> returnFromSearch;
	private ArrayList<Profile> result;

	public SearchFriend() {
		this.returnFromSearch = new ArrayList<HBProfileContent>();
		this.result = new ArrayList<Profile>();
	}

	public void loadOptions(String str) {
		if (em == null) {
			em = Database.createEntityManager();
		}

		Query q = em.createQuery("from Profile where name like :pName");
		q.setParameter("pName", "%" + str + "%");

		this.result.clear();
		this.returnFromSearch.clear();

//		if (!q.getResultList().isEmpty()) {
			for (int i = 0; i < q.getResultList().size(); i++) {
				Profile p = SESSION.getProfileLogged();
				if (!(p.getCod() == i)) {
					this.result.add((Profile) q.getResultList().get(i));
				}
			}
//		}
		for (int i = 0; i < this.result.size(); i++) {
			this.returnFromSearch.add(new HBProfileContent(result.get(i)));
		}
		
		em.clear();
		em.close();
		em = null;
	}

	public ArrayList<HBProfileContent> getResults() {
		return this.returnFromSearch;
	}
}




















