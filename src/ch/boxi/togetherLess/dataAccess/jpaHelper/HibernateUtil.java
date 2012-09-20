package ch.boxi.togetherLess.dataAccess.jpaHelper;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {
	private static final SessionFactory sessionFactory;
	private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
	
	static {
		String hibernateConfigLocation = System.getProperty(HibernateUtilConstants.hibernateConfigLocation);
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			AnnotationConfiguration config = null;
			if(StringUtils.isEmpty(hibernateConfigLocation)){
				//Do default
				config = new AnnotationConfiguration().configure();
			} else{
				File configFile = new File(hibernateConfigLocation);
				Boolean isReadable = configFile.canRead();
				logger.info("use non-default hibernateConfig from \"" + configFile.getAbsolutePath() + "\" is readable:" + isReadable);
				config = new AnnotationConfiguration().configure(hibernateConfigLocation);
			}
			
			// print schema creation sql commands
			new SchemaExport(config).create(true, false); // (showHql, run)
			
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
