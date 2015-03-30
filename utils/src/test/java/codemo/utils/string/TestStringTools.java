package codemo.utils.string;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestStringTools {
	int len = 10;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link ext.cjt.StringTools#eval(StringBugger,CharSequence)}.
	 */
	@Test
	public void testEval() {
		StringBuffer s = new StringBuffer();
		s.append("abc");
		assertEquals(StringTools.eval(s, "z").toString(), "z");
	}

	/**
	 * Test method for {@link ext.cjt.StringTools#isNotEmpty(String)}.
	 */
	@Test
	public void testIsNotEmpty() {
		assertEquals(StringTools.isNotEmpty(null), false);
		assertEquals(StringTools.isNotEmpty(""), false);
		assertEquals(StringTools.isNotEmpty(" "), true);
	}

	/**
	 * Test method for {@link ext.cjt.StringTools#isEmpty(String)}.
	 */
	@Test
	public void testIsEmpty() {
		assertEquals(StringTools.isEmpty(null), true);
		assertEquals(StringTools.isEmpty(""), true);
		assertEquals(StringTools.isEmpty(" "), false);
	}

	/**
	 * Test method for {@link ext.cjt.StringTools#getRandomAlphanumeric(int)}.
	 */
	@Test
	public void testGetRandomAlphanumeric() {
		assertEquals(StringTools.getRandomAlphanumeric(len).length(), len);
	}

	/**
	 * Test method for {@link ext.cjt.StringTools#getRandom(int)}.
	 */
	@Test
	public void testGetRandomInt() {
		assertEquals(StringTools.getRandom(len).length(), len);
	}

	/**
	 * Test method for {@link ext.cjt.StringTools#getRandom(java.lang.String)}.
	 */
	@Test
	public void testGetRandomString() {
		assertEquals(StringTools.getRandom("{???}").length(), 5);
	}

	/**
	 * Test method for
	 * {@link ext.cjt.StringTools#getRandom(java.lang.String, char)}.
	 */
	@Test
	public void testGetRandomStringChar() {
		assertEquals(StringTools.getRandom("{***}", '*').length(), 5);
	}

	/**
	 * Test method for {@link ext.cjt.StringTools#getRandomNum(int)}.
	 */
	@Test
	public void testGetRandomNumInt() {
		assertEquals(StringTools.getRandomNum(len).length(), len);
	}

	/**
	 * Test method for
	 * {@link ext.cjt.StringTools#getRandomNum(java.lang.String)}.
	 */
	@Test
	public void testGetRandomNumString() {
		assertEquals(StringTools.getRandomNum("[????]").length(), 6);
	}

	/**
	 * Test method for
	 * {@link ext.cjt.StringTools#getRandomNum(java.lang.String, char)}.
	 */
	@Test
	public void testGetRandomNumStringChar() {
		assertEquals(StringTools.getRandomNum("[****]", '*').length(), 6);
	}

	/**
	 * Test method for {@link ext.cjt.StringTools#getRandomHex(int)}.
	 */
	@Test
	public void testGetRandomHexInt() {
		assertEquals(StringTools.getRandomHex(len).length(), len);
	}

	/**
	 * Test method for
	 * {@link ext.cjt.StringTools#getRandomHex(java.lang.String)}.
	 */
	@Test
	public void testGetRandomHexString() {
		assertEquals(StringTools.getRandomHex("(?????)").length(), 7);
	}

	/**
	 * Test method for
	 * {@link ext.cjt.StringTools#getRandomHex(java.lang.String, char)}.
	 */
	@Test
	public void testGetRandomHexStringChar() {
		assertEquals(StringTools.getRandomHex("(*****)", '*').length(), 7);
	}

	/**
	 * Test method for {@link ext.cjt.StringTools#cutSpace(java.lang.String)}.
	 */
	@Test
	public void testCutSpace() {
		assertEquals(StringTools.cutSpace("　 a b　c　"), "abc");
	}

	/**
	 * Test method for {@link ext.cjt.StringTools#trim(java.lang.String)}.
	 */
	@Test
	public void testTrim() {
		assertEquals(StringTools.trim("　 a b　c　"), "a b　c");
	}

	/**
	 * Test method for {@link ext.cjt.StringTools#isSpace(char)}.
	 */
	@Test
	public void testIsSpace() {
		assertEquals(StringTools.isSpace('　'), true);
		assertEquals(StringTools.isSpace(' '), true);
	}

}
