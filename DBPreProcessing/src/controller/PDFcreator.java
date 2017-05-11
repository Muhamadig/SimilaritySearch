package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFcreator {

	private static String FILE = "PDFs/FirstPdf.pdf";
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
	public PDFcreator(){
		
	}
	
	public void pdfcreate(String text,String title) throws IOException, DocumentException{
		try {
	            Document document = new Document();
	            PdfWriter.getInstance(document, new FileOutputStream(title));
	            document.open();
	            Paragraph content = new Paragraph(text, subFont);
	        	document.add(content);
	            document.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
	}
}

