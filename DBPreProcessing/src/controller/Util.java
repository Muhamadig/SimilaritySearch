package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.stdDSA;

import model.FrequencyVector;

public class Util {

	public static  FrequencyVector sortByKeys(FrequencyVector map){
		ArrayList <String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
      
        //LinkedHashMap will keep the keys in the order they are inserted
        //which is currently sorted on natural ordering
        FrequencyVector sortedMap = new FrequencyVector();
        for(String key: keys){
            sortedMap.put(key, map.get(key));
        }
        return sortedMap;
    }
	
	public static  FrequencyVector sortByValues(FrequencyVector map){  
	       List list = new LinkedList(map.entrySet());
	       // Defined Custom Comparator here
	       Collections.sort(list, new Comparator() {
	            public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o1)).getValue())
	                  .compareTo(((Map.Entry) (o2)).getValue());
	            }
	       });
	       FrequencyVector sortedHashMap = new FrequencyVector();
	       for (Iterator it = list.iterator(); it.hasNext();) {
	              Map.Entry entry = (Map.Entry) it.next();
	              sortedHashMap.put((String)entry.getKey(), (Integer) entry.getValue());
	       } 
	       return sortedHashMap;
    }
	
	
	}

