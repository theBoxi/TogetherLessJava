package ch.boxi.togetherLess.test;

import java.io.File;

import org.junit.BeforeClass;

import ch.boxi.togetherLess.dataAccess.jpaHelper.HibernateUtilConstants;

public class AbstractUnitTest {
	public static final String hibernateConfigLocation = "D:\\GitHome\\git\\TogetherLess\\config\\hibernate.cfg.xml";
	
	@BeforeClass
	public static void initHibernate(){
		File f = new File("./");
		System.out.println("location: " + f.getAbsolutePath());
		System.setProperty(HibernateUtilConstants.hibernateConfigLocation, hibernateConfigLocation);
	}
}
