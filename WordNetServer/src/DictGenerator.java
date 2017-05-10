import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;

import XML.SynSetMapXML;
import XML.WordSetXML;
import XML.XML;
import XML.XMLFactory;
import edu.mit.jwi.IDictionary;
import model.RepWordMap;
import model.SynSetMap;

public class DictGenerator {
	private Stemming stemer;
	private Synonyms synonyms;
	private XML synSetMapXML;
	private XML wordSetMapXML;

	public DictGenerator(IDictionary dic){
		stemer=new Stemming(dic);
		synonyms=new Synonyms(dic);
		synSetMapXML=XMLFactory.getXML(XMLFactory.SynSetMap);
		wordSetMapXML=XMLFactory.getXML(XMLFactory.WordSetMap);
	}

	public SynSetMap asymmetricSyns(String allWordsFileName){
		HashSet<String> allWords=(HashSet<String>) wordSetMapXML.Import(allWordsFileName);
		HashSet<String> synRes;
		SynSetMap map=new SynSetMap();
		for(String key:allWords){
			map.put(key,synonyms.getAllSynonyms(key));
		}
		System.out.println("in the func: "+map.get("tamu communis").isEmpty());
		return map;
	}

	public SynSetMap symmetricSyns(String asymmetricFileName){
		SynSetMap asymmetric=(SynSetMap) synSetMapXML.Import(asymmetricFileName);
		System.out.println("in the reading: "+asymmetric.get("tamu communis").isEmpty());

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
