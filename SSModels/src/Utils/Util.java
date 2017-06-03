package Utils;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


import model.FVHashMap;
import model.FVKeySortedMap;
import model.FVValueSorted;

public class Util {

	
	public static String getFileExtension(String fileName){
		return fileName.replaceAll(".*\\.(html|pdf|docx|doc)","$1");
	}
	public static String cutExtension(String fileName){
		return fileName.replaceAll(".*\\.(html|pdf|docx|doc|xml)","$0");
	}
	public static  Map<String, Integer> sortByKeys(Map fv){
		FVHashMap res= new FVHashMap();
		Map<String, Integer> map= new TreeMap<String,Integer>(fv);
		for(String key:map.keySet()){
			res.put(key, map.get(key));
		}
		
		return map;
		
	}
//	public static FVValueSorted sortByValues(FVKeySortedMap fv){  
//		FVValueSorted sorted=new FVValueSorted(fv);
//		FVComparatorByValue comparator =new FVComparatorByValue();
//	
//		Collections.sort(sorted, comparator);
//		return sorted;
//		
//    }
	
	public static String toString(FVHashMap vec)
	{
		String words="";
		Set<String> keys = vec.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext())
		{
			words+=" " + it.next();
		}
		return words;
	}
	}

