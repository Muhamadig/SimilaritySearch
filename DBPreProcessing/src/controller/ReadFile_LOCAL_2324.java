package controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ReadFile {

	/**
	 * 
	 * @param fileDest destination pdf file url 
	 * @return pdf file text
	 */
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
	/**
	 * 
	 * @param fileDest: destination word(doc,docx) file url 
	 * @return word file text
	 */
	public static String docxRead(String fileDest){
		return null;
	}
	
	/**
	 * 
	 * @param text : full text
	 * @return hashmap<word,frequency> frequency vector 
	 */
	public static HashMap<String, Integer> textToFrequency (String text){
		
		HashMap<String, Integer> freq=new HashMap<>();
		
		String [] words=text.trim().split(" ");
//		String [] words=filter(textWords);
		System.out.println("words in string array=" + words.length);
		for(String str:words){
			
			if(freq.containsKey(str)) freq.replace(str, freq.get(str),freq.get(str)+1 );
			else freq.put(str, 1);
		}
		if(freq.containsKey(""))freq.remove("");
		return freq;
	}
	
	/**
	 * 
	 * @param dest: the file name and url
	 * @param type: file type (pdf,doc,docx...)
	 * @return file words frequency vector.
	 */
	public static HashMap<String, Integer> ReadFile(String dest,String type){
		String text = null;
		if(type.equals("pdf")) text=pdfRead(dest);
		if(type.equals("dox")||type.equals("docx")) text=docxRead(dest);
		return textToFrequency(text);
	}
	
	
	public static String[] filter (String [] words)
	{
		int len= words.length;
		for(int i=0;i<len;i++)
			words[i]=words[i].replaceAll("[^a-zA-Z0-9']","");
		return words;
	}
}
