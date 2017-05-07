import java.util.HashSet;

import XML.WordSetXML;
import edu.mit.jwi.IDictionary;

public class Demo {

	public static void main(String[] args) {
		//139964
		WordNet wn=new WordNet();
		IDictionary dict=wn.getDictionary();
		DictGenerator generator=new DictGenerator(dict);
		generator.symmetricSyns();
//		
//		System.out.println("Done: " + ((System.currentTimeMillis()-t)/1000) +"secs");
		
	}

}
