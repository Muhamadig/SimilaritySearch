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

import model.FrequencyVector;

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

	/**
	 * 
	 * @param text : full text
	 * @return hashmap<word,frequency> frequency vector 
	 */
	public static FrequencyVector textToFrequency (String text){

		FrequencyVector freq=new FrequencyVector();

		String [] words=text.trim().split(" ");
		
		//		String [] words=filter(textWords);
		for (int i=0;i<words.length;i++)
		{
			words[i] = words[i].replace("\r", "");
			words[i] = words[i].replace("\n", "");
		}
		System.out.println("words in string array=" + words.length);
		for(String str:words){

			if(freq.containsKey(str)) freq.replace(str.toLowerCase(), freq.get(str),freq.get(str)+1 );
			else freq.put(str.toLowerCase(), 1);
		}
		if(freq.containsKey(""))freq.remove("");
		System.out.println("words in string array=" + freq.size());
		return freq;
	}

	/**
	 * 
	 * @param dest The file destination and name for example (file1_EN.pdf).
	 * @param type the file type (pdf,doc,docx).
	 * @return the text as String in lower case.
	 */
	public static String ReadFile(String dest,String type){
		String text = null;
		if(type.equals("pdf")) text=pdfRead(dest);
		if(type.equals("doc")) text=docRead(dest);
		if(type.equals("docx")) text=docxRead(dest);
		return text;
	}


	
}
