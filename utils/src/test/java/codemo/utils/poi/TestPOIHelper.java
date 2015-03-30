package codemo.utils.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class TestPOIHelper {

	@Test
	public void testCreate() throws Exception {
		String f = "D:/tmp/tmp_" + new Date().getTime() + ".xls";
		//		String f = "tmp_" + new Date().getTime() + ".xlsx";
		Workbook wb = POIHelper.getWorkbook(f, true);
		wb.createSheet();
		Sheet sheet = wb.getSheetAt(0);
		POIHelper.setValue(sheet, 2, 1, "时间（Time）:", null);
		POIHelper.setValue(sheet, 2, 2, new Date(), new SsStyle(wb).setDataFormat(wb, "yyyy-MM-dd HH:mm:ss"));

		FileOutputStream fileOut = new FileOutputStream(f);
		wb.write(fileOut);
		fileOut.close();
	}

	//@Test
	public void testUpdate() throws Exception {
		// String f = "any.xlsx";
		String f = "D:/tmp/any.xls";
		Workbook wb = POIHelper.getWorkbook(f, false);
		Sheet sheet = wb.getSheetAt(0);
		POIHelper.setValue(sheet, 1, 1, new Date(), new SsStyle(wb).setDataFormat(wb, "yyyy-MM-dd HH:mm:ss"));

		FileOutputStream fileOut = new FileOutputStream(f);
		wb.write(fileOut);
		fileOut.close();
	}

	//@Test
	public void testRefreshFormulas() throws Exception {
		// File f = new File("formulas.xls");
		// FileInputStream fi = new FileInputStream(f);
		Workbook wb = POIHelper.getWorkbook("D:/tmp/formulas.xlsx", false);
		// XSSFWorkbook wb = new XSSFWorkbook(fi);
		Sheet sheet = wb.getSheetAt(0);
		System.out.println(POIHelper.getString(sheet, 1, 1));
		POIHelper.setValue(sheet, 1, 1, "234", null);
		sheet = wb.getSheetAt(1);
		POIHelper.refreshFormulas(sheet);
		FileOutputStream foStream = new FileOutputStream(new File("formulas.xlsx"));
		wb.write(foStream);
	}

	public void testGetValue() throws IOException {
		File f = new File("D:/tmp/demo.xls");
		FileInputStream fi = new FileInputStream(f);
		HSSFWorkbook wb = new HSSFWorkbook(fi);
		// XSSFWorkbook wb = new XSSFWorkbook(fi);
		HSSFSheet sheet = wb.getSheetAt(0);
		System.out.println(POIHelper.getString(sheet, 1, 1));
		POIHelper.setValue(sheet, 1, 1, "234", null);
		sheet = wb.getSheetAt(1);
		POIHelper.setFormula(sheet, 3, 2, "Sheet1!b2", null);
		FileOutputStream foStream = new FileOutputStream(f);
		wb.write(foStream);
	}

	@Test
	public void testAll() throws IOException {
		String f = "D:/tmp/TEMP-" + new Date().getTime() + ".xlsx";
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet();
		POIHelper.setValue(sheet, 8, 0, 1.23D, new SsStyle(wb).setBorder(HSSFCellStyle.BORDER_THIN));
		POIHelper.setValue(sheet, 8, 2, new Date(), new SsStyle(wb).setBorder(HSSFCellStyle.BORDER_THIN));
		POIHelper.setValue(sheet, 8, 4, 123456789, new SsStyle(wb).setBorder(HSSFCellStyle.BORDER_THIN));
		POIHelper.setValue(sheet, 10, 0, 1.23D, new SsStyle(wb).setBorder(HSSFCellStyle.BORDER_THIN).setDataFormat(wb, "000.000"));
		POIHelper.setValue(sheet, 10, 2, new Date(), new SsStyle(wb).setBorder(HSSFCellStyle.BORDER_THIN).setDataFormat(wb, "yy-M"));
		POIHelper.setValue(sheet, 10, 4, 123456789, new SsStyle(wb).setBorder(HSSFCellStyle.BORDER_THIN).setDataFormat(wb, "#,###"));

		POIHelper.setValue(sheet, 0, 0, "OK", new SsStyle(wb).setAlignmentHorizontal(HSSFCellStyle.ALIGN_LEFT).setFontFamily("Courier New").setFontSize(400).setFontWeight(HSSFFont.BOLDWEIGHT_BOLD));
		POIHelper.setValue(sheet, 1, 1, "color", new SsStyle(wb).setFontColor(HSSFColor.LIGHT_BLUE.index));
		POIHelper.setValue(sheet, 1, 2, "OK", new SsStyle(wb).setBorderTop(HSSFCellStyle.BORDER_THIN));
		POIHelper.setValue(sheet, 2, 2, "OK", new SsStyle(wb).setBorder(HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_DASHED, HSSFCellStyle.BORDER_THICK, HSSFCellStyle.BORDER_DASH_DOT_DOT));
		POIHelper.setValue(sheet, 2, 0, "OK", new SsStyle(wb).setBorderTop(HSSFCellStyle.BORDER_THIN).setBorderWeight(HSSFFont.BOLDWEIGHT_BOLD));
		POIHelper.setValue(sheet, 2, 1, "OK", new SsStyle(wb).setBorderLeft(HSSFCellStyle.BORDER_THIN).setBorderWeight(HSSFFont.BOLDWEIGHT_NORMAL));
		POIHelper.setValue(sheet, 4, 0, "OK", new SsStyle(wb).setBorderTop(HSSFCellStyle.BORDER_THIN).setBorderTopColor(HSSFColor.RED.index));
		POIHelper.setValue(sheet, 4, 2, "OK", new SsStyle(wb).setBorder(HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_DASHED, HSSFCellStyle.BORDER_THICK, HSSFCellStyle.BORDER_DASH_DOT_DOT)
				.setBorderColor(HSSFColor.RED.index));
		POIHelper.setValue(sheet, 4, 4, "OK", new SsStyle(wb).setFillPattern(HSSFCellStyle.BIG_SPOTS).setBackgroundColor(HSSFColor.RED.index));
		POIHelper.setValue(sheet, 4, 6, "OK", new SsStyle(wb).setFillPattern(HSSFCellStyle.BIG_SPOTS).setForegroundColor(HSSFColor.LIGHT_BLUE.index));
		POIHelper.setValue(sheet, 4, 8, "OK", new SsStyle(wb).setFillPattern(HSSFCellStyle.BIG_SPOTS).setForegroundColor(HSSFColor.RED.index).setBackgroundColor(HSSFColor.LIGHT_BLUE.index));
		POIHelper.setValue(sheet, 6, 0, "OK", new SsStyle(wb).setFillPattern(HSSFCellStyle.SOLID_FOREGROUND).setForegroundColor(new HSSFColor.RED().getIndex()));

		POIHelper.merge(sheet, 12, 13, 3, 7);
		POIHelper.merge(sheet, 15, 16, 3, 8, "Table Header");
		POIHelper.merge(sheet, 18, 19, 3, 8, "Table Header",
				new SsStyle(wb).setAlignmentHorizontal(HSSFCellStyle.ALIGN_CENTER).setBorder(HSSFCellStyle.BORDER_THIN).setFontSize(300).setFontWeight(HSSFFont.BOLDWEIGHT_BOLD));

		POIHelper.setValue(sheet, 21, 1, "OK", new SsStyle(wb).setFillPattern(HSSFCellStyle.SOLID_FOREGROUND).setForegroundColor(HSSFColor.RED.index).setBackgroundColor(HSSFColor.LIGHT_BLUE.index));
		POIHelper
				.setFormula(sheet, 23, 1, "3*4", new SsStyle(wb).setFillPattern(HSSFCellStyle.SOLID_FOREGROUND).setForegroundColor(HSSFColor.RED.index).setBackgroundColor(HSSFColor.LIGHT_BLUE.index));

		POIHelper.setValue(sheet, 25, 3, "some height", new SsStyle(wb).setBorder(HSSFCellStyle.BORDER_THIN));
		POIHelper.setRowHeight(sheet, 25, 500);
		POIHelper.setRowHeight(sheet, new int[] { 27, 29 }, 500);
		POIHelper.setRowHeight(sheet, 35, 45, 500);

		POIHelper.setStyle(sheet, 47, 48, 2, 3, new SsStyle(wb).setBorder(HSSFCellStyle.BORDER_THIN));

		File file = new File(f);
		System.out.println(file.getAbsoluteFile().getAbsolutePath());
		FileOutputStream foStream = new FileOutputStream(file);
		wb.write(foStream);
	}
}
