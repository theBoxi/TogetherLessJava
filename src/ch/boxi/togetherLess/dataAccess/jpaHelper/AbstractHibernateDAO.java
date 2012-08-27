package ch.boxi.togetherLess.dataAccess.jpaHelper;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractHibernateDAO {
	protected Session takeTransaction() {
		SessionFactory session = HibernateUtil.getSessionFactory();
		Session sess = session.getCurrentSession();
		Transaction tx = sess.getTransaction();
		if (!tx.isActive()) {
			tx.begin();
		}
		return sess;
	}

	protected Session createNewTransaction() {
		SessionFactory session = HibernateUtil.getSessionFactory();
		Session sess = session.getCurrentSession();
		sess.beginTransaction();
		return sess;
	}
}
