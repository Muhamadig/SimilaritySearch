package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.IRAMDictionary;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Word;
import edu.mit.jwi.item.WordID;
import edu.mit.jwi.morph.WordnetStemmer;
import model.FVHashMap;

public class JWIFramework {


	public JWIFramework(){

	}

	public static IDictionary loadToRam() throws IOException, InterruptedException{
		String wnhome = System.getenv("WNHOME");
		String path = File.separator + "dict";
		URL url = new URL("file", null , path );
		File dir=new File("dict");
		IRAMDictionary dict = new RAMDictionary(dir,  ILoadPolicy . NO_LOAD );

		dict . open ();

		System .out . print ("\n Loading Wordnet into memory ... ");
		long t = System . currentTimeMillis ();
		dict . load ( true );
		System.out.println("done "+(System.currentTimeMillis()-t));
		return dict;

	}

	public static String stemmer(String word,IDictionary dict){
		WordnetStemmer stem=new WordnetStemmer(dict);
		HashSet<String> set=new HashSet<>();
		List<String> res= new ArrayList<String>();
		res.addAll(stem.findStems(word, POS.NOUN));
		res.addAll(stem.findStems(word, POS.ADJECTIVE));
		res.addAll(stem.findStems(word, POS.ADVERB));
		res.addAll(stem.findStems(word, POS.VERB));


		//get the shortest word
		if(res.isEmpty()) return word;
		String min_word=res.get(0);	
		for(String str:res){
			if(str.length()<min_word.length())min_word=str; 
		}


		return min_word;

	}

	public static HashSet<String> getSynonyms ( IDictionary dict ,String key,POS pos,FVHashMap fv){

		HashSet<String> set=new HashSet<>();
		// look up first sense of the word "dog "
		IIndexWord idxWord = dict.getIndexWord(key, pos);
		if(idxWord==null) return null;
//		List<IWordID> wordids=idxWord . getWordIDs ();
//		for(IWordID wordID:wordids){
//			IWord word = dict . getWord ( wordID );
//			ISynset synset = word . getSynset ();
//
//			// iterate over words associated with the synset
//			String tmp_lema;
//			for(IWord w : synset . getWords ())
//				if(fv.containsKey(tmp_lema=w.getLemma())) set.add(tmp_lema);
//		}
				IWordID wordID = idxWord . getWordIDs ().get (0) ; // 1st meaning
				IWord word = dict . getWord ( wordID );
				ISynset synset = word . getSynset ();
		
				// iterate over words associated with the synset
				String tmp_lema;
				for( IWord w : synset . getWords ())
					if(fv.containsKey(tmp_lema=w.getLemma())) set.add(tmp_lema);

		return set;
	}

	public static HashSet<String> getAllSynonyms(IDictionary dict ,String key,FVHashMap fv){

		HashSet<String> set=new HashSet<>();
		HashSet<String> temp;
		if((temp = getSynonyms(dict, key, POS.ADJECTIVE,fv))!=null) set.addAll(temp);
		if((temp = getSynonyms(dict, key, POS.ADVERB,fv))!=null) set.addAll(temp);
		if((temp = getSynonyms(dict, key, POS.NOUN,fv))!=null) set.addAll(temp);
		if((temp = getSynonyms(dict, key, POS.VERB,fv))!=null) set.addAll(temp);
		return set;


	}
}
