package ch.boxi.togetherLess.dataAccess.jpaHelper;

public class JpaTransactionControllerDAO extends AbstractHibernateDAO {
	@Override
	public void closeTransaction(){
		super.closeTransaction();
	}
}
