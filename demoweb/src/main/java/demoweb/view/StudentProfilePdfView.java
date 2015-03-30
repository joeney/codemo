package demoweb.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import demoweb.domain.Student;

@Component
public class StudentProfilePdfView extends AbstractITextPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Student s = (Student) model.get("student");

		doc.add(new Paragraph("Student Profile"));
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 5.0f, 5.0f });
		table.setSpacingBefore(10);
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.BLACK);
		PdfPCell cell = null;

		cell = new PdfPCell(new Phrase("Id:", font));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(s.getId() + "", font));
		table.addCell(cell);
		table.completeRow();

		cell = new PdfPCell(new Phrase("Name:", font));
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(s.getName(), font));
		table.addCell(cell);
		table.completeRow();

		doc.add(table);

	}

}