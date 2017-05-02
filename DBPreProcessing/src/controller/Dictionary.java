package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

import model.Language;
import model.Language.Langs;

public class Dictionary {

	private ArrayList<String> words;
	private TreeMap<String, HashSet<String>> dictionary;
	public Dictionary(){
		words = new ArrayList<String>();
		dictionary = new TreeMap<>();
	}
	
	public void initialize() throws IOException{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now)+ " Initialize....");
		BufferedReader in = new BufferedReader(new FileReader("EnglishWordsList/wordlist.txt"));
		String word;
		while((word = in.readLine())!=null)
			words.add(word);
		ArrayList<String> filtered = StopWordsFiltering.RemoveSW(words, new Language(Langs.ENGLISH));
		words = (ArrayList<String>) filtered.clone();
		in.close();
	}
	
	public void buildDic(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now)+ " Starts build the Dictionary....");
		ArrayList <String> copywords = (ArrayList<String>) words.clone();
		String stem;
		StanfordStemmer stemmer = new StanfordStemmer();
		for(Object word : copywords.toArray()){
			stem = stemmer.Stemming(word.toString());
			dictionary.put(stem, Synonyms.getSynSet(stem));
		}
		
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now2 = LocalDateTime.now();
		System.out.println(dtf2.format(now2)+ " Finish building the dictionary....");
	}

	public TreeMap<String, HashSet<String>> getDictionary(){return this.dictionary;}
}
