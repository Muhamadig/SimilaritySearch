package view;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;

import controller.ReadFile;
import controller.StanfordStemmer;
import controller.StopWordsFiltering;
import controller.Synonyms;
import model.FVHashMap;

import model.FVHashMap;
import model.FVKeySortedMap;
import model.FVValueSorted;
import model.Language;
import model.Language.Langs;

public class main {

	public static void main(String[] args) {

		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now)); //2016/11/16 12:08:43
		
		
		String text=ReadFile.ReadFile("PDFs/file1_en.pdf", "pdf");
//		String text="A fractal is a mathematical set that typically displays self-similar patterns"
//				+ " which means they are the same from near as from far Often they have an "
//				+ "irregular and fractured appearance but not always Fractal may be exactly"
//				+ " the same at every scale or as illustrated in Figure 1 they may be nearly the same"
//				+ " at different scales The definition of fractal goes beyond self-similarity per se to"
//				+ " exclude trivial self-similarity and include";
//		System.out.println("The Original text:\n"+text);
//		System.err.println(text.trim().split("\\s").length);
		StanfordStemmer stem=new StanfordStemmer();
		FVHashMap stemText= stem.lemmatize(text);
//		System.out.println("After stemming:\n"+stemText.toString());
//		System.err.println(stemText.getSum() +""+stemText.size());
		FVHashMap stemAndSWText=StopWordsFiltering.RemoveSW(stemText, new Language(Langs.ENGLISH));
//		System.out.println("After SW Remove:\n"+stemAndSWText.toString());

		HashMap<String, HashSet<String>> global=new HashMap<>();

		for(String key: stemAndSWText.keySet()){
			global.put(key, Synonyms.getSynSet(key));
		}
		HashMap<String, HashSet<String>> local=Synonyms.setsVector(global);

		//		
//		System.err.println("the synonyms sets:\n");

//		for(String key:local.keySet()){
//			System.out.println(key +"--> "+ local.get(key));
//		}

//		System.err.println("the reduce begin now ...\n\n\n\n\n----");
		FVHashMap reduced=Synonyms.repWord(local, stemAndSWText);
		
		HashMap<String, HashSet<String>> synTable=new HashMap<>();
	//	System.out.println(LocalDateTime.now());
		for(String key: stemAndSWText.keySet()){
			synTable.put(key, Synonyms.getSynSet(key));
		}
		
		for(String key:synTable.keySet()){
			System.out.println(key +"--> "+ synTable.get(key));
		}
		System.out.println(LocalDateTime.now());

//		System.err.println("reduce:\n");

//		System.out.println(reduced.toString());
		
//		System.out.println("the original fv size :" +stemAndSWText.size()+" all FV:"+stemAndSWText.getSum());
//		System.out.println("the local size :" +local.size());
//		System.out.println("the reduced fv size :" +reduced.size()+" all FV:"+reduced.getSum());
//		System.out.println(dtf.format(now)); //2016/11/16 12:08:43

		FVKeySortedMap keysorted=new FVKeySortedMap(reduced);
		FVValueSorted sorted=utils.Util.sortByValues(keysorted) ;
		
		System.out.println(sorted);
		
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now2 = LocalDateTime.now();
		System.out.println(dtf2.format(now2));
		//2016/11/16 12:08:43
		//		System.out.println(Synonyms.getSynSet("play"));
		//		System.out.println(Synonyms.getSynSet("playing"));
		
//		DatamuseQuery d=new DatamuseQuery();
//		String res=d.findSimilar("play");
//		JSONParse j=new JSONParse();
//		String[] words=j.parseWords(res);
//		for(String str:words) System.out.print(str+" , ");
//		System.out.println();
//		System.out.println();
//		System.out.println(Synonyms.getSynSet("play"));

	}
		


}

