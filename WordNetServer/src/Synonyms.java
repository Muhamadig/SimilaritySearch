import java.util.HashSet;
import java.util.Iterator;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import model.SynSetMap;

public class Synonyms {
	private IDictionary dict;

	public Synonyms(IDictionary dict){
		this.dict=dict;
	}


	public HashSet<String> getSynonymsByPOS (String key,POS pos){

		HashSet<String> set=new HashSet<>();
		IIndexWord idxWord = dict.getIndexWord(key, pos);
		if(idxWord==null) return null;

		IWordID wordID = idxWord . getWordIDs ().get (0) ; // 1st meaning
		IWord word = dict . getWord ( wordID );
		ISynset synset = word . getSynset ();

		for( IWord w : synset . getWords ()){
			set.add((w.getLemma().replaceAll("[-_]", " ")).toLowerCase());
		}
			

		return set;
	}

	public HashSet<String> getAllSynonyms(String key){

		HashSet<String> set=new HashSet<>();
		HashSet<String> temp;
		if((temp = getSynonymsByPOS(key, POS.ADJECTIVE))!=null) set.addAll(temp);
		if((temp = getSynonymsByPOS(key, POS.ADVERB))!=null) set.addAll(temp);
		if((temp = getSynonymsByPOS(key, POS.NOUN))!=null) set.addAll(temp);
		if((temp = getSynonymsByPOS(key, POS.VERB))!=null) set.addAll(temp);
		return set;


	}
}