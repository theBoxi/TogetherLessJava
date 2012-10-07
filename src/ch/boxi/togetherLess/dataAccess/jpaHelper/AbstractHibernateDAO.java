package ch.boxi.togetherLess.dataAccess.jpaHelper;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractHibernateDAO {
	protected Session takeTransaction() {
		SessionFactory session = HibernateUtil.getSessionFactory();
		Session sess = session.getCurrentSession();
		Transaction tx = sess.getTransaction();
		sess.setFlushMode(FlushMode.AUTO);
		if (!tx.isActive()) {
			tx.begin();
		}
		return sess;
	}

	protected Session createNewTransaction() {
		SessionFactory session = HibernateUtil.getSessionFactory();
		Session sess = session.getCurrentSession();
		sess.setFlushMode(FlushMode.AUTO);
		sess.beginTransaction();
		return sess;
	}
}
