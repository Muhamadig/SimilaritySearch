package controller;

import dictionary.Stemming;
import edu.mit.jwi.IDictionary;
import model.FVHashMap;
import model.RepWordMap;
import model.SynSetMap;

public class FrequencyVector {
	private SynSetMap synonymsMap;
	private RepWordMap repWordMap;
	private Stemming stemming;
	
	
	public FrequencyVector(IDictionary dict,SynSetMap synonymsMap,RepWordMap repWordMap){
		this.synonymsMap=synonymsMap;
		this.repWordMap=repWordMap;
		this.stemming=new Stemming(dict);
	}
	
	public FVHashMap stemmingAndSynonyms(FVHashMap fv){
		FVHashMap stemmingMap=new FVHashMap();
		FVHashMap res=new FVHashMap();
		for(String key:fv.keySet()){
			stemmingMap.put(stemming.stem(key),fv.get(key));
		}
		for(String key:fv.keySet()){
			if(synonymsMap.containsKey(key)){
				res.put(repWordMap.get(synonymsMap.get(key)), fv.get(key));
			}
			else{
				res.put(key, fv.get(key));
			}
		}
		return res;
	}
}
