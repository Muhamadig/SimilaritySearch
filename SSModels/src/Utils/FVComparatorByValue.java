package Utils;

import java.util.Comparator;
import java.util.Map.Entry;
public class FVComparatorByValue implements Comparator<Entry<String, Integer>> {

	
	public int compare(Entry<String, Integer> entry0, Entry<String, Integer> entry1) {
		// TODO Auto-generated method stub
		return entry1.getValue()- entry0.getValue();
	}

}
