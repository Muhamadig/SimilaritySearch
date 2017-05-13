package controller;

import java.io.ByteArrayInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import java.nio.charset.Charset;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.tool.xml.XMLWorkerHelper;


public class PDFcreator {

	//private static String FILE = "PDFs/FirstPdf.pdf";
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
	
	public void converttopdf(String html){	
		try{
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("PDFs/convert.pdf"));
		document.open();
		
		XMLWorkerHelper.getInstance().parseXHtml(writer,document,new StringReader(html.replaceAll(" ", ""))); 
		document.close();
		System.out.println( "PDF Created!" );
		}catch(Exception e){
			 e.printStackTrace();
		}
	}
	public void pdfconvertor(String html) throws IOException, DocumentException{
		Document document = new Document(PageSize.LETTER);
		PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("PDFs/convert.pdf"));
		document.open();


		XMLWorkerHelper worker = XMLWorkerHelper.getInstance(); 

		Reader htmlReader = new StringReader(html);

		worker.parseXHtml(pdfWriter, document, htmlReader);
		document.close();

	}
	public void convert(String html) throws DocumentException, IOException{
		// step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("PDFs/Convert.pdf"));
        // step 3
        document.open();
        // step 4
   
 
        XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
        ByteArrayInputStream is = new ByteArrayInputStream(html.getBytes("UTF-8"));
        worker.parseXHtml(writer, document, is, Charset.forName("UTF-8"));
        // step 5
        document.close();
    }
}


