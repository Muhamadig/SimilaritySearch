package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

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
	
	public static String docxRead(String fileDest) throws IOException{
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
		return text;
	}
	
	public static String docRead (String fileDest) throws IOException
	{
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
		return text;
	}
	
	public static HashMap<String, Integer> textToFrequency (String text){
		
		HashMap<String, Integer> freq=new HashMap<>();
		
		String [] textWords=text.trim().split(" ");
		String [] words=filter(textWords);
		System.out.println("words in string array=" + words.length);
		for(String str:words){
			
			if(freq.containsKey(str)) freq.replace(str.toLowerCase(), freq.get(str),freq.get(str)+1 );
			else freq.put(str.toLowerCase(), 1);
		}
		freq.remove("");
		return freq;
	}
	
	public static String[] filter (String [] words)
	{
		int len= words.length;
		for(int i=0;i<len;i++)
			words[i]=words[i].replaceAll("[^a-zA-Z0-9']","");
		return words;
	}
}
