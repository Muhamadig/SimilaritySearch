package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ReadFile {

	
	public static String pdfRead(String fileDest){
		File file = new File(fileDest); 
		String text=null;
		int flag=1;
		try {
			PDDocument document = PDDocument.load(file);
			PDFTextStripper pdfStripper = new PDFTextStripper();
			text = pdfStripper.getText(document);
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(text);
		System.out.println();
		return text;
	}
	
		public static String docConverter(File input) throws IOException
	{
			String text = null;
	        FileInputStream fis;
	     		try {
				fis = new FileInputStream(input.getAbsolutePath());
				 HWPFDocument document = new HWPFDocument(fis);
				 text = new WordExtractor(document).getText();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return text;
	}
		public static String docxConverter (File input) throws IOException
		{
			String text =null;
			  FileInputStream fis;
			   XWPFWordExtractor ex = null;
			   XWPFDocument DOC;
			  try{
				  fis = new FileInputStream(input.getAbsolutePath());
				  DOC = new XWPFDocument(fis);
				  ex = new XWPFWordExtractor(DOC);
				  text = ex.getText();
			  }catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return text;
		}
		public static String pdfConverter (File input)
		{
			String text =null;
			 try {
		            PDDocument doc = PDDocument.load(input);
		        text = new PDFTextStripper().getText(doc);
		           
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        } 
			 return text;
		}
		
}
