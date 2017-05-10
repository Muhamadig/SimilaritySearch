package dictionary;
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



}
