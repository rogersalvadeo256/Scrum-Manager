package project;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import db.hibernate.factory.Database;
import db.pojos.PROJECT;
import db.pojos.PROJECT_TASK;
import statics.ENUMS;
import statics.ENUMS.PROJECT_FRAMEWORK;
import widgets.designComponents.projectContents.ScrumFrame;
import widgets.designComponents.projectContents.TaskComponent;

public class CheckNewTasks {

	public CheckNewTasks(PROJECT p, PROJECT_TASK task, ScrumFrame sf) {

		EntityManager em = Database.createEntityManager();

		Query q = em.createQuery("from PROJECT_TASK where PROJ_COD =:codigo");
		q.setParameter("codigo", p.getProjectCod());
		List<PROJECT_TASK> l = q.getResultList();

		for (PROJECT_TASK t : l) {
			switch (t.getTaskStatus()) {
			case TO_DO:
				sf.addTodo(t);
				break;
			case DOING:
				sf.addDoing(t);
				break;
			case DONE:
				sf.addDone(t);
				break;
			default:
				break;
			}

		}

	}

}
