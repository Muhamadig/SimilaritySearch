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


		//Step 1: get all words test:
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

		//Step2:get synonyms for each word:
		t= System.currentTimeMillis();
		SynSetMap asymmetric=generator.asymmetricSyns("AllWords.xml");

		System.err.println("number of words from WordNet= "+asymmetric.size());
		XML synSetXML=XMLFactory.getXML(XMLFactory.SynSetMap);
		synSetXML.export(asymmetric, "Asymmetric.xml");
		SynSetMap importAsymmetric= (SynSetMap) synSetXML.Import("Asymmetric.xml");
		System.err.println("number of words from XML File= " +importAsymmetric.size());
		System.err.println("is equal: "+asymmetric.equals(importAsymmetric));
		System.err.println("time of getting all synonyms: "+(float)((System.currentTimeMillis()-t)/1000)+" secs");
		System.out.println("----------------------------------------------------");
		//=======================================================================================================

		//Step3:make symmetric:
		t= System.currentTimeMillis();
		SynSetMap symmetric=generator.symmetricSyns("Asymmetric.xml");

		System.err.println("number of words from Asymmetric= "+symmetric.size());
		synSetXML.export(symmetric, "Symmetric.xml");
		SynSetMap importSymmetric= (SynSetMap) synSetXML.Import("Symmetric.xml");
		System.err.println("number of words from XML File= " +importSymmetric.size());
		System.err.println("is equal: "+symmetric.equals(importSymmetric));
		System.err.println("time of making it symeetric: "+(float)((System.currentTimeMillis()-t)/1000)+" secs");
		System.out.println("----------------------------------------------------");
		//=======================================================================================================


		//Step4:Rep Words Deic:
		t= System.currentTimeMillis();
		RepWordMap repWord=generator.createSynMap("Symmetric.xml");

		System.err.println("number of words from function of rep= "+repWord.size());
		XML repWordXML=XMLFactory.getXML(XMLFactory.RepWordMap);
		repWordXML.export(repWord, "RepWords.xml");
		RepWordMap importRep=  (RepWordMap) repWordXML.Import("RepWords.xml");
		System.err.println("number of words from XML File= " +importRep.size());
		System.err.println("is equal: "+repWord.equals(importRep));
		System.err.println("time of making it symeetric: "+(float)((System.currentTimeMillis()-t)/1000)+" secs");
		System.out.println("----------------------------------------------------");
		//=======================================================================================================

		
		HashSet<String> kind=importSymmetric.get("kind");
		System.out.println(kind.toString());
		
		System.out.println(importRep.get(kind));
		//RepWordMap map=generator.createSynMap();
		//System.out.println(map.size());
	}

}
