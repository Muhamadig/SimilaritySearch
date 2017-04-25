package view;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import controller.ReadFile;
import controller.StanfordStemmer;
import controller.StopWordsFiltering;
import model.FVHashMap;
import model.FVKeySortedMap;
import model.Language;
import model.Language.Langs;
import utils.Util;
public class main {

	public static void main(String[] args) {
		
		
		//new language
		Language eng= new Language(Langs.ENGLISH);
		
		//Step1: read files
		String en_pdf=ReadFile.ReadFile("PDFs/file1_EN.pdf", "pdf");
		
		
		//without stemming:

		
		FVHashMap original=new FVHashMap(en_pdf);
		System.out.println("Original:\n" +original.toString());
		System.out.println("#of Words:"+original.size()+" #of appears:"+ original.getSum());
		
		//step2:stemming
		StanfordStemmer stm = new StanfordStemmer();
		FVHashMap fv=stm.lemmatize(en_pdf);
		
		//step3: remove stop words
		FVHashMap fv_SW=StopWordsFiltering.RemoveSW(fv, eng);
		
		System.out.println("after Stemming:\n" +fv.toString());
		System.out.println("#of Words:"+fv.size()+" #of appears:"+ fv.getSum());
		
		System.out.println("after SW filtering:\n" +fv_SW.toString());
		System.out.println("#of Words:"+fv_SW.size()+" #of appears:"+ fv_SW.getSum());
		
		
		for(String key:original.keySet()){
			System.out.println(key+"-->"+stm.Stemming(key));
		}
		
	}
}

