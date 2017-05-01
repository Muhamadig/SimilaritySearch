package view;


import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import org.apache.lucene.util.packed.PackedLongValues.Iterator;
import java.time.format.DateTimeFormatter;
import controller.Dictionary;
import controller.DictionaryToMap;
import controller.ReadFile;
import controller.StanfordStemmer;
import controller.StopWordsFiltering;
import controller.Synonyms;
import model.FVHashMap;
import model.FVKeySortedMap;
import model.FVValueSorted;
import model.Language;
import model.Language.Langs;
import net.sf.extjwnl.JWNLException;
public class main {

	public static void main(String[] args) throws IOException, JWNLException {

		
		/*DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now)); //2016/11/16 12:08:43
		
		
		String text=ReadFile.ReadFile("PDFs/file1_en.pdf", "pdf");
		StanfordStemmer stem=new StanfordStemmer();
		FVHashMap stemText= stem.lemmatize(text);
		FVHashMap stemAndSWText=StopWordsFiltering.RemoveSW(stemText, new Language(Langs.ENGLISH));

		HashMap<String, HashSet<String>> global=new HashMap<String, HashSet<String>>(); // synonyms vector for the stemms

		for(String key: stemAndSWText.keySet()){
			global.put(key, Synonyms.getSynSet(key));
		}
		
		HashMap<String, HashSet<String>> local=Synonyms.setsVector(global);
		FVHashMap reduced=Synonyms.repWord(local, stemAndSWText);
		

		FVKeySortedMap keysorted=new FVKeySortedMap(reduced);
		FVValueSorted sorted=utils.Util.sortByValues(keysorted) ;
		
		System.out.println(sorted);
		
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now2 = LocalDateTime.now();
		System.out.println(dtf2.format(now2));*/
		
//		DatamuseQuery d=new DatamuseQuery();
//		String res=d.findSimilar("play");
//		JSONParse j=new JSONParse();
//		String[] words=j.parseWords(res);
//		for(String str:words) System.out.print(str+" , ");
//		System.out.println();
//		System.out.println();
//		System.out.println(Synonyms.getSynSet("play"));
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
		
		Dictionary dic = new Dictionary();
		dic.initialize();
		dic.buildDic();
		TreeMap<String,HashSet<String>> synonyms = dic.getDictionary();
		 FileWriter writer = new FileWriter("EnglishWordsList/synonyms.txt", true);    
		for(String word : synonyms.keySet())
			writer.write(word + "["+synonyms.get(word)+"]\n");
		 writer.close();
		
		//   new DictionaryToMap("EnglishWordsList/file_properties.xml","EnglishWordsList/result.txt").convert();
		   
			DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now2 = LocalDateTime.now();
			System.out.println(dtf2.format(now2));
	}
}

