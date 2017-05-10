package dictionary;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


import XML.XML;
import XML.XMLFactory;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import model.RepWordMap;
import model.SynSetMap;

public class DictGenerator {
	private IDictionary _dict;
	private Stemming stemmer;
	private Synonyms synonyms;
	private XML synSetMapXML;
	private XML wordSetMapXML;

	public DictGenerator(IDictionary dic){
		this._dict=dic;
		stemmer=new Stemming(dic);
		synonyms=new Synonyms(dic);
		synSetMapXML=XMLFactory.getXML(XMLFactory.SynSetMap);
		wordSetMapXML=XMLFactory.getXML(XMLFactory.WordSetMap);
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

	public SynSetMap asymmetricSyns(String allWordsFileName){
		HashSet<String> allWords=(HashSet<String>) wordSetMapXML.Import(allWordsFileName);
		SynSetMap map=new SynSetMap();
		for(String key:allWords){
			map.put(key,synonyms.getAllSynonyms(key));
		}
		return map;
	}

	public SynSetMap symmetricSyns(String asymmetricFileName){
		SynSetMap asymmetric=(SynSetMap) synSetMapXML.Import(asymmetricFileName);

		HashSet<String> currentSet;

		for(String word : asymmetric.keySet()){
			currentSet=asymmetric.get(word);

			if(currentSet.isEmpty())
				currentSet.add(word);
			else{
				for(String synonym:currentSet){
					if((asymmetric.get(synonym)!=null)&&(!asymmetric.get(synonym).contains(word))) (asymmetric.get(synonym)).add(word);

				}

			}
		}
		
		return asymmetric;

	}
	
	public RepWordMap createSynMap(String symmetricFileName){
		SynSetMap synset=(SynSetMap) synSetMapXML.Import(symmetricFileName);
		RepWordMap map=new RepWordMap();
		HashSet<String> current;
		for(String key:synset.keySet()){
			current=synset.get(key);
			if(!map.containsKey(current)){
				Iterator<String> it=current.iterator();
				map.put(current, it.next());
			}
		}
		
		return map;
		
	}
}
