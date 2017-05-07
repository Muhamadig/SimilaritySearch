import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;

import XML.SynSetMapXML;
import XML.WordSetXML;
import edu.mit.jwi.IDictionary;
import model.SynSetMap;

public class DictGenerator {
	private Stemming stemer;
	private Synonyms synonyms;
	
	public DictGenerator(IDictionary dic){
		stemer=new Stemming(dic);
		synonyms=new Synonyms(dic);
	}
	
	public void generateSyns(){
		long t=System.currentTimeMillis();
		HashSet<String> allWords=WordSetXML.Import("allWords.xml");
		HashSet<String> synRes;
		SynSetMap map=new SynSetMap();
		for(String key:allWords){
			map.put(key,synonyms.getAllSynonyms(key));
		}
		SynSetMapXML.export(map, "AsymetricSyns.xml");
		System.out.println("synonyms Done: " + (float)((System.currentTimeMillis()-t)/1000) +"secs");
		System.out.println("number of words: "+ map.size());
	}
	
	public void symmetricSyns(){
		SynSetMap symmetric=SynSetMapXML.Import("AsymetricSyns.xml");
		System.out.print ("\n Symatric ... \n");
		long t = System . currentTimeMillis ();
		for(String word : symmetric.keySet()){
			HashSet<String> syns = symmetric.get(word);
			if(syns.isEmpty())
				syns.add(word);
			else{
				Iterator<String> it = syns.iterator();
				while(it.hasNext()){
					String syn = it.next();
					if(symmetric.containsKey(syn))
						if(!symmetric.get(syn).contains(word))
							symmetric.get(syn).add(word);
				}
			}
		}
		SynSetMapXML.export(symmetric, "symmetricSyns.xml");
		System.out.println("synonyms Done: " + (float)((System.currentTimeMillis()-t)/1000) +"secs");
		System.out.println("number of words: "+ symmetric.size());
	}
}
