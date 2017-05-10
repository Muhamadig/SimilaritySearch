import java.util.HashSet;
import java.util.Iterator;

import XML.WordSetXML;
import XML.XML;
import XML.XMLFactory;
import edu.mit.jwi.IDictionary;
import model.FVHashMap;
import model.RepWordMap;
import model.SynSetMap;

public class Demo {

	public static void main(String[] args) {
		WordNet wn=new WordNet();
		IDictionary dict=wn.getDictionary();
		DictGenerator generator=new DictGenerator(dict);
		
		
		//get all words test:
		long t= System.currentTimeMillis();
		HashSet<String> allWords=wn.getAllWords();
		System.err.println("number of words from WordNet= "+allWords.size());
		XML wordsXML=XMLFactory.getXML(XMLFactory.WordSetMap);
		wordsXML.export(allWords, "AllWords.xml");
		HashSet<String> importAllWords=(HashSet<String>) wordsXML.Import("AllWords.xml");
		System.err.println("number of words from XML File= " +importAllWords.size());
		System.err.println("is equal: "+allWords.equals(importAllWords));
		System.err.println("time of getting all words: "+(float)((System.currentTimeMillis()-t)/1000)+" secs");
		System.out.println("----------------------------------------------------");
		//=======================================================================================================
		//RepWordMap map=generator.createSynMap();
		//System.out.println(map.size());
	}

}
