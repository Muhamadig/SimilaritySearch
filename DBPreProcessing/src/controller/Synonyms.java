package controller;

import java.util.HashSet;
import java.util.List;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.IndexWordSet;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.dictionary.Dictionary;

public class Synonyms {
	public static HashSet<String> getSynSet(String word){
		HashSet<String> synsSet=new HashSet<>();

		Dictionary dic=null;
		try {
			dic=Dictionary.getDefaultResourceInstance();
			IndexWordSet words= dic.lookupAllIndexWords(word);

			for(IndexWord iw:words.getIndexWordArray()){
				List <Synset> senses=iw.getSenses();
				for(Synset sense:senses) {
					
					synsSet.add(sense.getSynset().getWords().get(0).getLemma());
				}
			}

		} catch (JWNLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return synsSet;

	}
}
