import java.util.HashSet;
import java.util.Iterator;

import XML.WordSetXML;
import XML.XML;
import XML.XMLFactory;
import edu.mit.jwi.IDictionary;
import model.FVHashMap;
import model.SynSetMap;
import model.SynsMap;

public class Demo {

	public static void main(String[] args) {
		WordNet wn=new WordNet();
		IDictionary dict=wn.getDictionary();
		DictGenerator generator=new DictGenerator(dict);
		SynsMap map=generator.createSynMap();
		System.out.println(map.size());
	}

}
