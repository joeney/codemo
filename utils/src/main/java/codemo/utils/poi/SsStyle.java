package sf.codemo.utils.poi;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public class SsStyle {
	private Workbook workbook;
	private CellStyle cellstyle;
	private Font font;

	public SsStyle(Workbook workbook) {
		super();
		this.setWorkbook(workbook);
		this.setCellstyle(this.getWorkbook().createCellStyle());
		this.setFont(this.getWorkbook().createFont());
	}

	/**
	 * @param workbook
	 * @param format
	 * @return
	 */
	public SsStyle setDataFormat(Workbook workbook, String format) {
		this.cellstyle.setDataFormat(workbook.createDataFormat().getFormat(format));
		return this;
	}

	/**
	 * @param pattern
	 *            e.g.: CellStyle.SOLID_FOREGROUND
	 * @return
	 */
	public SsStyle setFillPattern(short pattern) {
		this.cellstyle.setFillPattern(pattern);
		return this;
	}

	/**
	 * @param color
	 *            e.g.: HSSFColor.RED.index
	 * @note !IMPORTANT. You MUST call setFillPattern before setBackgroundColor
	 *       to take effect.
	 */
	public SsStyle setBackgroundColor(short color) {
		this.cellstyle.setFillBackgroundColor(color);
		return this;
	}

	/**
	 * @param color
	 *            e.g.: HSSFColor.RED.index
	 * @return
	 */
	public SsStyle setForegroundColor(short color) {
		this.cellstyle.setFillForegroundColor(color);
		return this;
	}

	/**
	 * @param color
	 *            e.g.: HSSFColor.RED.index
	 * @return
	 */
	public SsStyle setBorderColor(short color) {
		this.cellstyle.setTopBorderColor(color);
		this.cellstyle.setLeftBorderColor(color);
		this.cellstyle.setBottomBorderColor(color);
		this.cellstyle.setRightBorderColor(color);
		return this;
	}

	/**
	 * @param color
	 *            e.g.: HSSFColor.RED.index
	 * @return
	 */
	public SsStyle setBorderTopColor(short color) {
		this.cellstyle.setTopBorderColor(color);
		return this;
	}

	/**
	 * @param color
	 *            e.g.: HSSFColor.RED.index
	 * @return
	 */
	public SsStyle setBorderLeftColor(short color) {
		this.cellstyle.setLeftBorderColor(color);
		return this;
	}

	/**
	 * @param color
	 *            e.g.: HSSFColor.RED.index
	 * @return
	 */
	public SsStyle setBorderBottomColor(short color) {
		this.cellstyle.setBottomBorderColor(color);
		return this;
	}

	/**
	 * @param color
	 *            e.g.: HSSFColor.RED.index
	 * @return
	 */
	public SsStyle setBorderRightColor(short color) {
		this.cellstyle.setRightBorderColor(color);
		return this;
	}

	/**
	 * @param boldweight
	 *            e.g.: Font.BOLDWEIGHT_BOLD
	 * @note Do not take effect. Maybe something wrong.
	 */
	public SsStyle setBorderWeight(short boldweight) {
		font.setBoldweight(boldweight);
		this.cellstyle.setFont(font);
		return this;
	}

	/**
	 * @param top
	 *            e.g.: CellStyle.BORDER_NONE ...
	 * @param left
	 *            e.g.: ditto
	 * @param bottom
	 *            e.g.: ditto
	 * @param right
	 *            e.g.: ditto
	 * @return
	 */
	public SsStyle setBorder(short top, short left, short bottom, short right) {
		this.cellstyle.setBorderTop(top);
		this.cellstyle.setBorderBottom(bottom);
		this.cellstyle.setBorderLeft(left);
		this.cellstyle.setBorderRight(right);
		return this;
	}

	/**
	 * @param border
	 *            e.g.: CellStyle.BORDER_THIN
	 * @return
	 */
	public SsStyle setBorder(short border) {
		this.cellstyle.setBorderTop(border);
		this.cellstyle.setBorderBottom(border);
		this.cellstyle.setBorderLeft(border);
		this.cellstyle.setBorderRight(border);
		return this;
	}

	/**
	 * @param border
	 *            e.g.: CellStyle.BORDER_THIN
	 * @return
	 */
	public SsStyle setBorderTop(short border) {
		this.cellstyle.setBorderTop(border);
		return this;
	}

	/**
	 * @param border
	 *            e.g.: CellStyle.BORDER_THIN
	 * @return
	 */
	public SsStyle setBorderBottom(short border) {
		this.cellstyle.setBorderBottom(border);
		return this;
	}

	/**
	 * @param border
	 *            e.g.: CellStyle.BORDER_THIN
	 * @return
	 */
	public SsStyle setBorderLeft(short border) {
		this.cellstyle.setBorderLeft(border);
		return this;
	}

	/**
	 * @param border
	 *            e.g.: CellStyle.BORDER_THIN
	 * @return
	 */
	public SsStyle setBorderRight(short border) {
		this.cellstyle.setBorderRight(border);
		return this;
	}

	public SsStyle setFontFamily(String fontFamily) {
		font.setFontName(fontFamily);
		this.cellstyle.setFont(font);
		return this;
	}

	public SsStyle setFontSize(int fontSize) {
		font.setFontHeight((short) fontSize);
		this.cellstyle.setFont(font);
		return this;
	}

	/**
	 * @param fontWeight
	 *            e.g.: Font.BOLDWEIGHT_BOLD
	 * @return
	 */
	public SsStyle setFontWeight(short fontWeight) {
		font.setBoldweight(fontWeight);
		this.cellstyle.setFont(font);
		return this;
	}

	public SsStyle setFontItalic(boolean b) {
		font.setItalic(b);
		this.cellstyle.setFont(font);
		return this;
	}

	/**
	 * @param color
	 *            e.g.: HSSFColor.RED.index
	 * @return
	 */
	public SsStyle setFontColor(short color) {
		font.setColor(color);
		this.cellstyle.setFont(font);
		return this;
	}

	/**
	 * @param align
	 *            e.g.: CellStyle.ALIGN_LEFT
	 * @return
	 */
	public SsStyle setAlignmentHorizontal(short align) {
		this.cellstyle.setAlignment(align);
		return this;
	}

	/**
	 * @param align
	 *            e.g.: CellStyle.VERTICAL_TOP
	 * @return
	 */
	public SsStyle setAlignmentVertical(short align) {
		this.cellstyle.setVerticalAlignment(align);
		return this;
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public CellStyle getCellstyle() {
		return cellstyle;
	}

	public void setCellstyle(CellStyle cellstyle) {
		this.cellstyle = cellstyle;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}
}
