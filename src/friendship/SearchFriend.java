package friendship;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.Profile;
import statics.SESSION;
import widgets.designComponents.HBProfileContent;

public class SearchFriend {
	private EntityManager em;
	private ArrayList<HBProfileContent> returnFromSearch;
	private ArrayList<Profile> result;

	public SearchFriend() {
		this.returnFromSearch = new ArrayList<HBProfileContent>();
		this.result = new ArrayList<Profile>();
	}

	@SuppressWarnings("unchecked")
	public void loadOptions(String str) throws IOException {
		if (em == null) {em = Database.createEntityManager();
		}
		this.result.clear();
		this.returnFromSearch.clear();

		Query q = em.createQuery("from Profile where name like :pName and codProfile <> :codOnline");
		q.setParameter("pName", str + "%" );
		q.setParameter("codOnline", SESSION.getProfileLogged().getCod());

		this.result = (ArrayList<Profile>) q.getResultList();
		
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






























