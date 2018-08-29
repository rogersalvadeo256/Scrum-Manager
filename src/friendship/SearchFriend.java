package friendship;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import POJOs.Profile;
import POJOs.UserRegistration;
import db.hibernate.factory.Database;
import db.user.util.UserOnline;
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

		Query q = em.createQuery("from Profile where name like :name");
		q.setParameter("name", "%" + str + "%");

		Query q1 = em.createQuery("from UserRegistration where userName like :userName");
		q1.setParameter("userName", "%" + str + "%");

		
		Query q2 = em.createQuery("from UserRegistration as u  inner join Profile as p  on u.codUser = p.codProfile where u.userName=:uName and p.name=:pName");
		q2.setParameter("uName", "%" + str + "%");
		q2.setParameter("pName", "%" + str + "%");
	
		q.getResultList();
		
		this.result.clear();
		this.returnFromSearch.clear();

		if (!q.getResultList().isEmpty()) {
			for (int i = 0; i < q.getResultList().size(); i++) {
				this.result.add((Profile) q.getResultList().get(i));
			}
		}
		if (!q1.getResultList().isEmpty()) {
			for (int i = 0; i < q1.getResultList().size(); i++) {
				UserRegistration u = new UserRegistration();
				u = (UserRegistration) q1.getResultList().get(i);
				Profile p = new Profile();
				p = u.getProfile();
				for (int j = 0; j < q1.getResultList().size(); j++) {
//					if(!(this.result.get(j).getCod() ==  UserOnline.getUserLogged().getCodUser())) ;
					if (!(this.result.get(j).getCod() == UserOnline.getProfile().getCod())) this.result.add(p);
				}
			}
		}
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
