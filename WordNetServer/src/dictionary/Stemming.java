package dictionary;
import java.util.ArrayList;
import java.util.List;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;

public class Stemming {
	
	private IDictionary dict;
	
	public Stemming(IDictionary dict){
		this.dict=dict;
	}
	public String stem(String word){
		WordnetStemmer wnstem=new WordnetStemmer(dict);
		List<String> res= new ArrayList<String>();
		res.addAll(wnstem.findStems(word, POS.NOUN));
		res.addAll(wnstem.findStems(word, POS.ADJECTIVE));
		res.addAll(wnstem.findStems(word, POS.ADVERB));
		res.addAll(wnstem.findStems(word, POS.VERB));

		//get the shortest word
		if(res.isEmpty()) return word;
		while(res.contains("")) res.remove(res.indexOf(""));
		String min_word=res.get(0);	
		for(String str:res){
			if(str.length()<min_word.length())min_word=str; 
		}

		return (min_word.replaceAll("[-_]", " ")).trim().toLowerCase();

	}
	


}
