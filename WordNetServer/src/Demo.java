import java.util.HashSet;

import XML.WordSetXML;
import XML.XML;
import XML.XMLFactory;
import edu.mit.jwi.IDictionary;

public class Demo {

	public static void main(String[] args) {
		WordNet wn=new WordNet();
		IDictionary dict=wn.getDictionary();

		//get all words:
		/*
		 
		 
				long t=System.currentTimeMillis();
				
				HashSet<String> allWords=wn.getAllWords();
				XML allWordsMap=XMLFactory.getXML(XMLFactory.WordSetMap);
				allWordsMap.export(allWords, "AllWords.xml");
				
				System.out.println("Done: " + ((System.currentTimeMillis()-t)/1000) +"secs");
		*/
		//read the all words file:
		/*
		long t=System.currentTimeMillis();

		XML allWordsMap=XMLFactory.getXML(XMLFactory.WordSetMap);
		HashSet<String> allWords=(HashSet<String>) allWordsMap.Import("AllWords.xml");

		System.out.println("number of words: " +allWords.size() );
		System.out.println("Done: " + ((System.currentTimeMillis()-t)/1000) +"secs");
		*/
		
		
		//get asymmetric synonyms:
		DictGenerator generator=new DictGenerator(dict);
		generator.asymmetricSyns();
		generator.symmetricSyns();

		//		
		//		DictGenerator generator=new DictGenerator(dict);
		//		generator.symmetricSyns();

	}

}
