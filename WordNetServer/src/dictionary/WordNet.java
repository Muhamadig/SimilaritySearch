package dictionary;
import java.io.File;
import java.io.IOException;
import XML.XML;
import XML.XMLFactory;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.IRAMDictionary;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;
import model.RepWordMap;
import model.SynSetMap;

public class WordNet {
	private IDictionary _dict;
	private SynSetMap synonymsMap;
	private RepWordMap repWordMap;
	private Stemming stemming;

	public WordNet(){
		this._dict=load();
		synonymsMap=loadSynMap();
		repWordMap=loadRepWordDic();
		stemming=new Stemming(_dict);
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
	
	private RepWordMap loadRepWordDic(){
		System.out.println("Loading Represintative Word Dictionary...");
		XML repWordXML=XMLFactory.getXML(XMLFactory.RepWordMap);
		RepWordMap importRep=  (RepWordMap) repWordXML.Import("SynonymsDictionary/RepWords.xml");
		System.out.println("Loaded ,Number of represintative words: "+importRep.size());
		return importRep;
	}
	
	private SynSetMap loadSynMap(){
		System.out.println("Loading Synonyms Map Dictionary...");
		XML synSetXML=XMLFactory.getXML(XMLFactory.SynSetMap);
		SynSetMap importSymmetric= (SynSetMap) synSetXML.Import("SynonymsDictionary/Symmetric.xml");
		System.out.println("Loaded ,Number of words: "+importSymmetric.size());
		return importSymmetric;
	}

	public SynSetMap getSynonymsMap() {
		return synonymsMap;
	}


	public RepWordMap getRepWordMap() {
		return repWordMap;
	}

	

	public Stemming getStemming() {
		return stemming;
	}




}
