package controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

<<<<<<< HEAD
=======
import com.mysql.cj.core.io.FloatValueFactory;

>>>>>>> ce5e296ba90fa789da9c825dc1f990e71b177708
import model.FVHashMap;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.IndexWordSet;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.dictionary.Dictionary;

public class Synonyms {
	public static HashSet<String> getSynSet(String word){
		HashSet<String> synsSet=new HashSet<>();

		Dictionary dic=null;
		try {
			dic=Dictionary.getDefaultResourceInstance();
			IndexWordSet words= dic.lookupAllIndexWords(word);

			for(IndexWord iw:words.getIndexWordArray()){
				List <Synset> senses=iw.getSenses();
//				for(Synset sense:senses) {
//					
//					synsSet.add(sense.getSynset().getWords().get(0).getLemma());
//				}
				
				for(Synset sense:senses) {
					List<Word> words1= sense.getSynset().getWords();
					for(Word w:words1){
						synsSet.add(w.getLemma());
					}
				}
				
			}

		} catch (JWNLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synsSet.add(word);
		return synsSet;

	}
	
<<<<<<< HEAD
	public static FVHashMap Getreduced(FVHashMap vec , HashMap<String, HashSet<String>> synonyms)
	{
		FVHashMap reducedVec = new FVHashMap();
		int sum=0;
		HashSet<String> syns;
		for(String word : synonyms.keySet()){
			syns = synonyms.get(word);
			for(String synonym : syns){
				if(synonyms.containsKey(synonym))
					sum+=vec.get(synonym);
				}
			reducedVec.put(word, sum);
			sum=0;
		}
		return reducedVec;
=======
	public static HashMap<String, HashSet<String>> setsVector(HashMap<String, HashSet<String>> global){
		HashMap<String, HashSet<String>> local= new HashMap<>();
		
		for(String key:global.keySet()){
			HashSet<String> set=new HashSet<>();
			for(String entireKey:global.keySet()){
				if((global.get(entireKey)).contains(key)) set.add(entireKey);
			}
			local.put(key, set);
		}
		return local;
		
	}
	
	public static FVHashMap repWord(HashMap<String, HashSet<String>> local,FVHashMap fv){
		HashMap<String, String> repWord=new HashMap<>();
		FVHashMap set=new FVHashMap();
		
		HashMap<String, Boolean> toCheck=new HashMap<>();
		int sum=0,sum2=0;
		for(String key:local.keySet()){
			toCheck.put(key, true);
		}
		for(String key:local.keySet()){
			if(toCheck.get(key)) {
				HashSet<String> localSet=local.get(key);
				sum++;

				for(String str: localSet){
					if(toCheck.get(str)) set.put(key, fv.get(str));
					sum2++;
					toCheck.replace(str, true, false);
				}
			}
			
		}
		System.out.println("size:"+sum+ "  all:"+sum2);
		return set;
		
>>>>>>> ce5e296ba90fa789da9c825dc1f990e71b177708
	}
}
