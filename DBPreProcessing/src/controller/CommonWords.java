package controller;

import java.util.ArrayList;
import java.util.Map.Entry;

import model.FVSortedMap;

public class CommonWords {

	
	public static FVSortedMap mergeAll(ArrayList<FVSortedMap> exapandedFVList){
		
		FVSortedMap globalFV= new FVSortedMap();
		for(FVSortedMap map:exapandedFVList){
			globalFV.merge(map);
		}
		
		return globalFV;
	}
	
	public static ArrayList<Entry<String, Integer>> sortGlobalFV(FVSortedMap globalFV){
		
		return Util.sortByValues(globalFV);
	}
}
