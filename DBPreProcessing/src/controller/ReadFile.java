package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ReadFile {

	/**
	 * 
	 * @param fileDest destination pdf file url 
	 * @return pdf file text
	 */
	private static String pdfRead(String fileDest){
		File file = new File(fileDest); 
		String text=null;
		try {
			PDDocument document = PDDocument.load(file);
			PDFTextStripper pdfStripper = new PDFTextStripper();
			text = pdfStripper.getText(document);
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text.toLowerCase();
	}
	/**
	 * 
	 * @param fileDest: destination word(doc,docx) file url 
	 * @return word file text
	 */

	private static String docxRead(String fileDest) {
		String text =null;
		File file = new File(fileDest); 
		FileInputStream fis;
		XWPFWordExtractor ex = null;
		XWPFDocument DOC;
		try{
			fis = new FileInputStream(file.getAbsolutePath());
			DOC = new XWPFDocument(fis);
			 ex = new XWPFWordExtractor(DOC);
			text = ex.getText();
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text.toLowerCase();
	}

	private static String docRead (String fileDest) {
		String text = null;
		FileInputStream fis;
		File file = new File(fileDest); 
		try {
			fis = new FileInputStream(file.getAbsolutePath());
			HWPFDocument document = new HWPFDocument(fis);
			text = new WordExtractor(document).getText();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text.toLowerCase();
		
	}

	private static String htmlRead(String fileDest){
		File input = new File(fileDest);
		Document doc = null;
		try {
			doc = Jsoup.parse(input,"UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element table = doc.select("table").first();
		Element div = doc.getElementById("itabs");
		String text = table.text() + "\n" + div.text();
		return text;
	}
	/**
	 * 
	 * @param dest The file destination and name for example (file1_EN.pdf).
	 * @param type the file type (pdf,doc,docx).
	 * @return the text as String in lower case.
	 * @throws IOException 
	 */
	public static String ReadFile(String dest,String type){
		String text = null;
		if(type.equals("pdf")) text=pdfRead(dest);
		if(type.equals("doc")) text=docRead(dest);
		if(type.equals("docx")) text=docxRead(dest);
		if(type.equals("html")) text =htmlRead(dest);
		System.out.println(Thread.activeCount());
		return clean(text);
//		return text;

	}

	
	static String clean(String str){
		return str.replaceAll("[^0-9a-zA-Z\\s]", "");
	}

	
}
