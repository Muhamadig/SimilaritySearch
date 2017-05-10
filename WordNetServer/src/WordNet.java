import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.IRAMDictionary;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

public class WordNet {
	private IDictionary _dict;
	public WordNet(){
		this._dict=load();
	}

	public IDictionary getDictionary(){
		return _dict;
	}

	private IDictionary load(){

		File dir=new File("dict");
		IRAMDictionary dict = new RAMDictionary(dir,  ILoadPolicy . NO_LOAD );

		try {
			dict . open ();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System .out . print ("\n Loading Wordnet into memory ... ");
		long t = System . currentTimeMillis ();
		try {
			dict . load ( true );
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("done "+(System.currentTimeMillis()-t));
		return dict;

	}

	public HashSet<String> getAllWords(){
		HashSet<String> allWords=new HashSet<>();
		allWords.addAll(getAllWordsByPOS(POS.NOUN));
		allWords.addAll(getAllWordsByPOS(POS.ADJECTIVE));
		allWords.addAll(getAllWordsByPOS(POS.VERB));
		allWords.addAll(getAllWordsByPOS(POS.ADVERB));
		return allWords;

	}

	private HashSet<String> getAllWordsByPOS(POS pos){
		HashSet<String> res=new HashSet<>();
		Stemming stemmer=new Stemming(_dict);
		Iterator<IIndexWord> iterator= _dict.getIndexWordIterator(pos);
		IIndexWord idxWord;
		
		while(iterator.hasNext()){
			idxWord=iterator.next();

			List<IWordID> wordIDList=idxWord.getWordIDs();
			IWord word;
			String str;
			for(IWordID wordID:wordIDList){
				word = _dict . getWord ( wordID );
				str=(stemmer.stem(word.getLemma())).toLowerCase();
				str=str.replaceAll("[-_]", " ");
				str=str.trim();
				if(!str.equals("")) res.add(str);
			}

		}
		return res;

	}
}
