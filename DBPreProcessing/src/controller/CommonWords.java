package controller;

import java.util.ArrayList;

import model.FVKeySortedMap;
import model.FVValueSorted;
import utils.Util;

public class CommonWords {

	
	public static FVKeySortedMap mergeAll(ArrayList<FVKeySortedMap> exapandedFVList){
		
		FVKeySortedMap globalFV= new FVKeySortedMap();
		for(FVKeySortedMap map:exapandedFVList){
			globalFV.merge(map);
		}
		
		return globalFV;
	}
	
	public static FVValueSorted sortGlobalFV(FVKeySortedMap globalFV){
		
		return Util.sortByValues(globalFV);
	}
}
