package controller;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ReadFile {

	
	public static String pdfRead(String fileDest){
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
		return text;
	}
}
