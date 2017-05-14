package controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.InputStream;
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
	/*
	public void converttopdf(String html){	
		try{
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("PDFs/convert.pdf"));
		document.open();
		
		XMLWorkerHelper.getInstance().parseXHtml(writer,document,new StringReader(html)); 
		document.close();
		System.out.println( "PDF Created!" );
		}catch(Exception e){
			 e.printStackTrace();
		}
	}*/
	
	public void pdfconvertor(String html) throws IOException, DocumentException{
		Document document = new Document();
	    PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream("PDFs/convert.pdf"));
	    writer.setInitialLeading(12.5f);
	    document.open();
	    XMLWorkerHelper.getInstance().parseXHtml(writer, document,new FileInputStream("HTMLs/test.html"));
	    document.close();
	}
	
	
  }
	



