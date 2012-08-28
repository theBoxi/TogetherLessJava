package ch.boxi.togetherLess.dataAccess.jpaHelper;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class HibernateUtil {
  private static final SessionFactory sessionFactory;
  static {
    try {
      // Create the SessionFactory from hibernate.cfg.xml
      AnnotationConfiguration config = new AnnotationConfiguration().configure();
      //print schema creation sql commands
      new SchemaExport(config).create(true, false); //(showHql, run)
      sessionFactory = config.buildSessionFactory();
    } catch (Throwable ex) {
      // Make sure you log the exception, as it might be swallowed
      System.err.println("Initial SessionFactory creation failed." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }
}
