package ch.boxi.togetherLess.build;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PropertyReplaycerTest {
	private Properties properties;
	private PropertyReplaycer replaycer;
	
	private static final String HOST = "localhost";
	private static final String PORT = "8080";
	
	@Before
	public void init(){
		properties = new Properties();
		properties.put("tgl.host", HOST);
		properties.put("tgl.port", PORT);
		replaycer = new PropertyReplaycer(properties, true);
	}
	
	@Test
	public void testSingleProperty(){
		String original = "<tgl:property name=\"tgl.host\"/>";
		String replayced = replaycer.replayceAllProperties(original);
		Assert.assertEquals(HOST, replayced);
	}
	
	@Test
	public void testMultipleProperty(){
		String original = "<tgl:property name=\"tgl.host\"/>:<tgl:property name=\"tgl.port\"/>";
		String replayced = replaycer.replayceAllProperties(original);
		Assert.assertEquals(HOST + ":" + PORT, replayced);
	}
	
	@Test
	public void testMultiplePropertyInCustomLine(){
		String original = "123<tgl:property name=\"tgl.host\"/>:<tgl:property name=\"tgl.port\"/>456";
		String replayced = replaycer.replayceAllProperties(original);
		Assert.assertEquals("123" + HOST + ":" + PORT + "456", replayced);
	}
	
	@Test
	public void testNonPropertyLine(){
		String original = "<tgl:include name=\"test.html\"/>";
		String replayced = replaycer.replayceAllProperties(original);
		Assert.assertEquals(original, replayced);
	}
}
