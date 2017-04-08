package controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Util {
	
	
	public static Integer freqSum(HashMap<String, Integer> vector){
		Integer sum=0;
		Iterator it = vector.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        sum=sum.sum(sum, (int) pair.getValue());
	        //it.remove(); // avoids a ConcurrentModificationException
	    }
	    
	    return sum; 
	}
}
