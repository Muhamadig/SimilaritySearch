package controller;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
}
