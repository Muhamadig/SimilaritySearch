package controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
	
	public static String docxRead(String fileDest){
		return null;
	}
	
	public static HashMap<String, Integer> textToFrequency (String text){
		
		HashMap<String, Integer> freq=new HashMap<>();
		
		String [] textWords=text.trim().split(" ");
		String [] words=filter(textWords);
		System.out.println("words in string array=" + words.length);
		for(String str:words){
			
			if(freq.containsKey(str)) freq.replace(str, freq.get(str),freq.get(str)+1 );
			else freq.put(str, 1);
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
