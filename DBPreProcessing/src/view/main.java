package view;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

import controller.ReadFile;
import controller.StanfordStemmer;
import controller.StopWordsFiltering;
import controller.Synonyms;
import model.FVHashMap;
import model.Language;
import model.Language.Langs;

public class main {

	public static void main(String[] args) {
	
		String text=ReadFile.ReadFile("PDFs/file1_en.pdf", "pdf");
		StanfordStemmer stem=new StanfordStemmer();
		FVHashMap stemText= stem.lemmatize(text);
		FVHashMap stemAndSWText=StopWordsFiltering.RemoveSW(stemText, new Language(Langs.ENGLISH));
		
		HashMap<String, HashSet<String>> synTable=new HashMap<>();
	//	System.out.println(LocalDateTime.now());
		for(String key: stemAndSWText.keySet()){
			synTable.put(key, Synonyms.getSynSet(key));
		}
		
		for(String key:synTable.keySet()){
			System.out.println(key +"--> "+ synTable.get(key));
		}
		System.out.println(LocalDateTime.now());
	}
	
}

