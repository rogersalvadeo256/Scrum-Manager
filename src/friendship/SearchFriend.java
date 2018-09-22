package friendship;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.USER_PROFILE;
import statics.SESSION;
import widgets.designComponents.HBProfileContent;

public class SearchFriend {
	private EntityManager em;
	private ArrayList<HBProfileContent> returnFromSearch;
	private ArrayList<USER_PROFILE> result;

	public SearchFriend() {
		this.returnFromSearch = new ArrayList<HBProfileContent>();
		this.result = new ArrayList<USER_PROFILE>();
	}

	@SuppressWarnings("unchecked")
	public void loadOptions(String str) throws IOException {
		if (em == null) {em = Database.createEntityManager();
		}
		this.result.clear();
		this.returnFromSearch.clear();

		Query q = em.createQuery("FROM USER_PROFILE WHERE PROF_NAME LIKE :PROF_NAME AND COD_PROFILE <> : LOGGED_USER_PROF_COD");
		q.setParameter("PROF_NAME", str + "%" );
		q.setParameter("LOGGED_USER_PROF_COD", SESSION.getProfileLogged().getCod());

		this.result = (ArrayList<USER_PROFILE>) q.getResultList();
		
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






























