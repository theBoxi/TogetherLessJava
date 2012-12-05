package ch.boxi.togetherLess.dataAccess.jpaHelper;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractHibernateDAO {
	protected Session takeTransaction() {
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();
		Transaction tx = sess.getTransaction();
		sess.setFlushMode(FlushMode.AUTO);
		if (!tx.isActive()) {
			tx.begin();
		}
		return sess;
	}

	protected Session createNewTransaction() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();
		sess.setFlushMode(FlushMode.AUTO);
		sess.beginTransaction();
		return sess;
	}
	
	protected void closeTransaction(){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sess = sessionFactory.getCurrentSession();
		sess.close();
	}
}
