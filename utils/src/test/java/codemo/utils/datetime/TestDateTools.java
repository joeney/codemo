package codemo.utils.datetime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDateTools {
	String DTFormat, DFormat, TFormat;
	String DTString, DString, TString;
	Date date;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		DFormat = "yyyy-MM-dd";
		TString = "HH:mm:ss";
		DTFormat = DFormat + " " + TString;
		DString = "2009-11-22";
		TString = "22:33:44";
		DTString = DString + " " + TString;
		date = new SimpleDateFormat(DTFormat).parse(DTString);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link ext.cjt.DateTools#getDateString(java.util.Date)}.
	 */
	@Test
	public void testGetDateStringDate() {
		assertEquals(DateTools.getDateString(date), DString);
	}

	/**
	 * Test method for
	 * {@link ext.cjt.DateTools#getDatetimeString(java.util.Date)}.
	 */
	@Test
	public void testGetDatetimeStringDate() {
		assertEquals(DateTools.getDatetimeString(date), DTString);
	}

	/**
	 * Test method for {@link ext.cjt.DateTools#getDate(java.lang.String)}.
	 */
	@Test
	public void testGetDateString() {
		try {
			assertEquals(new SimpleDateFormat(DString).format(DateTools.getDate(DTString)), DString);
		} catch (ParseException e) {
			e.printStackTrace();
			fail("ParseException");
		}
	}

	/**
	 * Test method for {@link ext.cjt.DateTools#getDatetime(java.lang.String)}.
	 */
	@Test
	public void testGetDatetimeString() {
		try {
			assertEquals(new SimpleDateFormat(DTString).format(DateTools.getDatetime(DTString)), DTString);
		} catch (ParseException e) {
			e.printStackTrace();
			fail("ParseException");
		}
	}

	/**
	 * Test method for
	 * {@link ext.cjt.DateTools#getDateString(java.util.Date, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetDateStringDateString() {
		assertEquals(DateTools.getDateString(date, "yyyy/MM/dd"), "2009/11/22");
	}

	/**
	 * Test method for
	 * {@link ext.cjt.DateTools#getDatetimeString(java.util.Date, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetDatetimeStringDateString() {
		assertEquals(DateTools.getDatetimeString(date, "yyyy/MM/dd HH:mm:ss"), "2009/11/22 22:33:44");
	}

	/**
	 * Test method for
	 * {@link ext.cjt.DateTools#getDate(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetDateStringString() {
		try {
			assertEquals(new SimpleDateFormat("yyyy/MM/dd").format(DateTools.getDate("2009/11/22", "yyyy/MM/dd")), "2009/11/22");
		} catch (ParseException e) {
			e.printStackTrace();
			fail("ParseException");
		}
	}

	/**
	 * Test method for
	 * {@link ext.cjt.DateTools#getDatetime(java.lang.String, java.lang.String)}
	 * .
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testGetDatetimeStringString() throws ParseException {
		try {
			assertEquals(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(DateTools.getDatetime("2009/11/22 22:33:44", "yyyy/MM/dd HH:mm:ss")), "2009/11/22 22:33:44");
		} catch (ParseException e) {
			e.printStackTrace();
			fail("ParseException");
		}
	}

	@Test
	public void testLocalDateTools() throws ParseException {
		System.out.println(DateTools.getLocalDateString("GMT+8"));
		System.out.println(DateTools.getLocalTimeString("GMT+8"));
		System.out.println(DateTools.getLocalDatetimeString("Asia/Shanghai"));
		System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(DateTools.getLocalDatetime("GMT+9")));
	}

}
