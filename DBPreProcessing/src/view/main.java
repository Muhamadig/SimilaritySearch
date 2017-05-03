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
import controller.JWIFramework;
import controller.ReadFile;
import controller.StanfordStemmer;
import controller.StopWordsFiltering;
import controller.Synonyms;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;
import model.FVHashMap;
import model.FVKeySortedMap;
import model.FVValueSorted;
import model.Language;
import model.Language.Langs;
import net.sf.extjwnl.JWNLException;
public class main {

	public static void main(String[] args) throws IOException, JWNLException {



		IDictionary dict = null;
		try {
			dict=JWIFramework.loadToRam();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System .out . print ("\n loading text file ... ");
		long t = System . currentTimeMillis ();



		//============================================================
		String text=ReadFile.ReadFile("PDFs/file1_en.pdf", "pdf");
		System.out.println("done "+(System.currentTimeMillis()-t));

		FVHashMap fv=new FVHashMap(text);

		System.out.println();
		System.out.println(fv.toString());
		System.err.println("number of words in the initial fv:"+fv.size()+"	number of all frequencies:"+ fv.getSum());


		System .out . print ("\n removing stop words ... ");
		t = System . currentTimeMillis ();
		fv=StopWordsFiltering.RemoveSW(fv, new Language(Langs.ENGLISH));
		System.out.println("done "+(System.currentTimeMillis()-t));
		System.out.println();
		System.out.println(fv.toString());
		System.err.println("number of words in the removed sw fv:"+fv.size()+"	number of all frequencies:"+ fv.getSum());


		System .out . print ("\nfinding stemmings ... ");
		t = System . currentTimeMillis ();
		FVHashMap fv_stem=new FVHashMap();
		for(String key:fv.keySet()){
			fv_stem.put(JWIFramework.stemmer(key, dict),fv.get(key));
		}
		System.out.println("done "+(System.currentTimeMillis()-t));
		System.out.println();
		System.out.println(fv_stem.toString());
		System.err.println("number of words in the stemmed fv:"+fv_stem.size()+"	number of all frequencies:"+ fv_stem.getSum());

		Long synTime=System.currentTimeMillis();
		System .out . print ("\n find synonyms ... ");
		t = System . currentTimeMillis ();
		HashMap<String,HashSet<String>> synonymsMap=new HashMap<>();
		for(String key:fv_stem.keySet()){
			synonymsMap.put(key, JWIFramework.getAllSynonyms(dict, key,fv_stem));
		}
		HashSet<String> tmpSet=new HashSet<>(); 
		for(String key:synonymsMap.keySet()){
			tmpSet=synonymsMap.get(key);
			for(String syn:tmpSet){
				(synonymsMap.get(syn)).add(key);
			}
		}

		for(String key:synonymsMap.keySet()){
			System.out.println(key+"-->"+ (synonymsMap.get(key)).toString());
		}

		System.err.println("number of words in synonyms vector:"+synonymsMap.size());

		HashSet<HashSet<String>> synonymsSets=new HashSet<>();
		for(String key:synonymsMap.keySet()){
				synonymsSets.add(synonymsMap.get(key));
		}
		System.out.println(synonymsSets.toString());
		System.err.println("number of words in synonyms sets vector:"+synonymsSets.size());

		System.out.println("done "+(System.currentTimeMillis()-t));
		
		//i have synonymsMap which for every key there is an hashset<string> of synonyms.
		
		FVHashMap FinalFV=new FVHashMap();
		HashSet<String> synSetTemp;
		HashMap<String, Boolean> checkList=new HashMap<>();
		for(String key:fv_stem.keySet()){
			checkList.put(key, false);
		}
		for(String key:synonymsMap.keySet()){
			if(!checkList.get(key)){
				synSetTemp=synonymsMap.get(key);
				if(synSetTemp.isEmpty()){
					FinalFV.put(key, fv_stem.get(key));
					checkList.replace(key, false, true);
				}
				else{
					for(String entireKey:synonymsMap.keySet()){
						if(synSetTemp.equals(synonymsMap.get(entireKey))) {
							FinalFV.put(key,fv_stem.get(entireKey));
							checkList.replace(entireKey, false, true);
						}
					}
				}
				
			}
			
		}
		System.out.println("The Final Frequency Vector:");
		System.out.println(FinalFV.toString());
		System.out.println("Number of words in final FV:"+ FinalFV.size()+" All frequencies of final FV:"+FinalFV.getSum());
		System.out.println("synonyms Done:\n The time of synonyms processing : " +(System.currentTimeMillis()-synTime)+"milisecs" );
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now2 = LocalDateTime.now();
		System.out.println(dtf2.format(now2));
		/*FVKeySortedMap KeySorted = new FVKeySortedMap();
		KeySorted.putAll(FinalFV);
		FVValueSorted SortedFinalFV = new FVValueSorted(KeySorted);
		//SortedFinalFV.putAll(FinalFV);
		System.out.println(SortedFinalFV.toString());*/
	}
}

