package controller;

import Server.View;
import Server.WordNetHandler;
import Utils.Request;
import dictionary.Stemming;
import model.FVHashMap;
import model.RepWordMap;
import model.SynSetMap;

public class FrequencyVector extends View {
	private SynSetMap synonymsMap;
	private RepWordMap repWordMap;
	private Stemming stemming;
	
	
	public FrequencyVector(){
		this.synonymsMap=WordNetHandler.getSynonymsMap();
		this.repWordMap=WordNetHandler.getRepWordMap();
		this.stemming=WordNetHandler.getStemming();
	}
	
	private FVHashMap stemmingAndSynonyms(FVHashMap fv){
		FVHashMap stemmingMap=new FVHashMap();
		FVHashMap res=new FVHashMap();
		for(String key:fv.keySet()){
			stemmingMap.put(stemming.stem(key),fv.get(key));
		}
		for(String key:stemmingMap.keySet()){
			if(synonymsMap.containsKey(key)){
				res.put(repWordMap.get(synonymsMap.get(key)), stemmingMap.get(key));
			}
			else{
				res.put(key, stemmingMap.get(key));
			}
		}
		return res;
	}
	
	public Object fv(Request request){
		FVHashMap fv=(FVHashMap) request.getParam("fv");
		return stemmingAndSynonyms(fv);

		
	}
}
