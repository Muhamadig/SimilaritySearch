
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
//
import XML.XML;
import XML.XMLFactory;
import dictionary.DictGenerator;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.IRAMDictionary;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;
import model.RepWordMap;
import model.SynSetMap;

public class BuildDic {

	public static void main(String[] args) {


		IDictionary dict=load();
		DictGenerator generator=new DictGenerator(dict);


		//Step 1: get all words from Wordnet:
		System.out.println("loading all words from WordNet...");
		long t= System.currentTimeMillis();
		HashSet<String> allWords=generator.getAllWords();
		System.err.println("number of words from WordNet= "+allWords.size());
		XML wordsXML=XMLFactory.getXML(XMLFactory.WordSetMap);
		wordsXML.export(allWords, "SynonymsDictionary/AllWords.xml");
		@SuppressWarnings("unchecked")
		HashSet<String> importAllWords=(HashSet<String>) wordsXML.Import("SynonymsDictionary/AllWords.xml");
		System.err.println("number of words from XML File= " +importAllWords.size());
		System.err.println("is equal: "+allWords.equals(importAllWords));
		System.err.println("time of loading all words: "+(float)((System.currentTimeMillis()-t)/1000)+" secs");
		System.out.println("----------------------------------------------------");
		//=======================================================================================================

		//Step2:get synonyms for each word:
		System.out.println("loading all words synonyms from WordNet...");

		t= System.currentTimeMillis();
		SynSetMap asymmetric=generator.asymmetricSyns("SynonymsDictionary/AllWords.xml");

		System.err.println("number of words from WordNet= "+asymmetric.size());
		XML synSetXML=XMLFactory.getXML(XMLFactory.SynSetMap);
		synSetXML.export(asymmetric, "SynonymsDictionary/Asymmetric.xml");
		SynSetMap importAsymmetric= (SynSetMap) synSetXML.Import("SynonymsDictionary/Asymmetric.xml");
		System.err.println("number of words from XML File= " +importAsymmetric.size());
		System.err.println("is equal: "+asymmetric.equals(importAsymmetric));
		System.err.println("time of loading all synonyms: "+(float)((System.currentTimeMillis()-t)/1000)+" secs");
		System.out.println("----------------------------------------------------");
		//=======================================================================================================

		//Step3:make symmetric:
		System.out.println("making symmetrical dictionary...");
		t= System.currentTimeMillis();
		SynSetMap symmetric=generator.symmetricSyns("SynonymsDictionary/Asymmetric.xml");

		System.err.println("number of words from Asymmetric= "+symmetric.size());
		synSetXML.export(symmetric, "SynonymsDictionary/Symmetric.xml");
		SynSetMap importSymmetric= (SynSetMap) synSetXML.Import("SynonymsDictionary/Symmetric.xml");
		System.err.println("number of words from XML File= " +importSymmetric.size());
		System.err.println("is equal: "+symmetric.equals(importSymmetric));
		System.err.println("time of making it symeetric: "+(float)((System.currentTimeMillis()-t)/1000)+" secs");
		System.out.println("----------------------------------------------------");
		//=======================================================================================================


		//Step4:Rep Words Dec:
		System.out.println("building represintative word dictionary...");

		t= System.currentTimeMillis();
		RepWordMap repWord=generator.createSynMap("SynonymsDictionary/Symmetric.xml");

		System.err.println("number of words from function of rep= "+repWord.size());
		XML repWordXML=XMLFactory.getXML(XMLFactory.RepWordMap);
		repWordXML.export(repWord, "SynonymsDictionary/RepWords.xml");
		RepWordMap importRep=  (RepWordMap) repWordXML.Import("SynonymsDictionary/RepWords.xml");
		System.err.println("number of words from XML File= " +importRep.size());
		System.err.println("is equal: "+repWord.equals(importRep));
		System.err.println("time of building represintative word dictionary: "+(float)((System.currentTimeMillis()-t)/1000)+" secs");
		System.out.println("----------------------------------------------------");
		//=======================================================================================================

//		new WordNet();



	}
	private static IDictionary load(){

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

}


