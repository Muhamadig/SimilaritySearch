package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import controller.ReadFile;
import controller.StanfordStemmer;
import controller.StopWordsFiltering;
import controller.Synonyms;
import edu.stanford.nlp.pipeline.POSTaggerAnnotator;
import edu.stanford.nlp.process.WordSegmenter;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import model.FVHashMap;
import model.FVKeySortedMap;
import model.Language;
import model.Language.Langs;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.IndexWordSet;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.dictionary.Dictionary;
import utils.Util;
public class main {

	public static void main(String[] args) {
	
		String text=ReadFile.ReadFile("PDFs/file1_en.pdf", "pdf");
		StanfordStemmer stem=new StanfordStemmer();
		FVHashMap stemText= stem.lemmatize(text);
		FVHashMap stemAndSWText=StopWordsFiltering.RemoveSW(stemText, new Language(Langs.ENGLISH));
		
		HashMap<String, HashSet<String>> synTable=new HashMap<>();
		
//		for(String key: stemAndSWText.keySet()){
//			synTable.put(key, Synonyms.getSynSet(key));
//		}
//		
//		for(String key:synTable.keySet()){
//			System.out.println(key +"--> "+ synTable.get(key));
//		}
		
		System.out.println(Synonyms.getSynSet("play"));
		System.out.println(Synonyms.getSynSet("playing"));
	}
	
}

