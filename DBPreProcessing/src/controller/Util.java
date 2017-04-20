package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


import model.FVHashMap;
import model.FVSortedMap;
import utils.FVComparatorByValue;

public class Util {

	public static  Map<String, Integer> sortByKeys(Map fv){
		FVHashMap res= new FVHashMap();
		Map<String, Integer> map= new TreeMap<String,Integer>(fv);
		for(String key:map.keySet()){
			res.put(key, map.get(key));
		}
		
		return map;
		
	}
	public static ArrayList<Entry<String, Integer>> sortByValues(FVSortedMap fv){  
		ArrayList<Entry<String, Integer>> sorted=new ArrayList<>();
		FVComparatorByValue comparator =new FVComparatorByValue();
		for(Entry<String, Integer> entry: fv.entrySet()) sorted.add(entry);
		
		Collections.sort(sorted, comparator);
		return sorted;
		
    }
	
	
	}

