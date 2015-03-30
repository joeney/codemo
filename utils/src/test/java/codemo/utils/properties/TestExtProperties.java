package codemo.utils.properties;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TestExtProperties {
	ExtProperties p;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		p = new ExtProperties("./target/classes/config.ini", "utf-8");
		//		p.load();
	}

	/**
	 * Test method for {@link ext.cjt.ExtProperties#load()}.
	 */
	@Test
	public void testLoad() {
		assertEquals(p.get("name"), "Andy");
	}

	/**
	 * Test method for {@link ext.cjt.ExtProperties#get(java.lang.String)}.
	 */
	@Test
	public void testGetString() {
		assertEquals(p.get("contact.email"), "andy@mail.com");
	}

	/**
	 * Test method for
	 * {@link ext.cjt.ExtProperties#get(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetStringString() {
		assertEquals(p.get("color", "hat"), "red");
	}

	/**
	 * Test method for {@link ext.cjt.ExtProperties#getGroup(java.lang.String)}.
	 */
	@Test
	public void testGetGroup() {
		Map<String, String> map = p.getGroup("color");
		assertEquals(map.get("hat"), "red");
	}

	/**
	 * Test method for {@link ext.cjt.ExtProperties#getLine(java.lang.String)}.
	 */
	@Test
	public void testGetLineString() {
		assertEquals(p.getLine("age"), 4);
	}

	/**
	 * Test method for
	 * {@link ext.cjt.ExtProperties#getLine(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetLineStringString() {
		assertEquals(p.getLine("hobbies", "sports"), 21);
	}

	/**
	 * Test method for {@link ext.cjt.ExtProperties#update(String,String)}.
	 */
	@Test
	public void testUpdate() {
		p.update("age", "9");
		assertEquals(p.get("age"), "9");
	}
}
