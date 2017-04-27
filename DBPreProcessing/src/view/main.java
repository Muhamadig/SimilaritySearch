package view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import controller.ReadFile;
import controller.StanfordStemmer;
import controller.StopWordsFiltering;
import edu.stanford.nlp.pipeline.POSTaggerAnnotator;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import model.FVHashMap;
import model.FVKeySortedMap;
import model.Language;
import model.Language.Langs;
import net.sf.extjwnl.data.POS;
import utils.Util;
public class main {

	public static void main(String[] args) {
		String str="conclusion";
		String str2="counter";
		
//		System.out.println(StanfordStemmer.getSynset(str,POS.ADJECTIVE).toString());
//		System.out.println(StanfordStemmer.getSynset(str2,POS.ADJECTIVE).toString());
//
//		System.out.println(StanfordStemmer.getSynset(str,POS.ADVERB).toString());
//		System.out.println(StanfordStemmer.getSynset(str2,POS.ADVERB).toString());
//		System.out.println(StanfordStemmer.getSynset(str,POS.NOUN).toString());
//		System.out.println(StanfordStemmer.getSynset(str2,POS.NOUN).toString());
//		System.out.println(StanfordStemmer.getSynset(str,POS.VERB).toString());
//		System.out.println(StanfordStemmer.getSynset(str2,POS.VERB).toString());
		String en_pdf=ReadFile.ReadFile("PDFs/file1_EN.pdf", "pdf");

		MaxentTagger a=new MaxentTagger("english-left3words-distsim.tagger");
//		System.out.println(a.tagString(en_pdf));
		ArrayList<String> all=new ArrayList<>();
		ArrayList<String> tags=new  ArrayList<>();
		String tagingStr=a.tagString(en_pdf);
		String[] words= tagingStr.trim().split("\\s");
//		boolean ab=Pattern.matches("(.*)(_)([A-Z]*)", "ab_AA");
		HashSet<String> tag= new HashSet<>();
		for(String str1:words){
			str1=str1.replaceAll("(.*)(_)(.*)", "$3");
			tags.add(str1);
			tag.add(str1);
		}
		System.out.println(tag.toString());
		System.out.println(tags.size()==words.length);
		
//		System.out.println(ab);
//		//new language
//		Language eng= new Language(Langs.ENGLISH);
//		
//		//Step1: read files
//		String en_pdf=ReadFile.ReadFile("PDFs/file1_EN.pdf", "pdf");
//		
//		
//		//without stemming:
//
//		
//		FVHashMap original=new FVHashMap(en_pdf);
//		System.out.println("Original:\n" +original.toString());
//		System.out.println("#of Words:"+original.size()+" #of appears:"+ original.getSum());
//		
//		//step2:stemming
//		StanfordStemmer stm = new StanfordStemmer();
//		FVHashMap fv=stm.lemmatize(en_pdf);
//		
//		//step3: remove stop words
//		FVHashMap fv_SW=StopWordsFiltering.RemoveSW(fv, eng);
//		
//		System.out.println("after Stemming:\n" +fv.toString());
//		System.out.println("#of Words:"+fv.size()+" #of appears:"+ fv.getSum());
//		
//		System.out.println("after SW filtering:\n" +fv_SW.toString());
//		System.out.println("#of Words:"+fv_SW.size()+" #of appears:"+ fv_SW.getSum());
//		
//		
//		for(String key:original.keySet()){
//			System.out.println(key+"-->"+stm.Stemming(key));
//		}
//		
	}
}

