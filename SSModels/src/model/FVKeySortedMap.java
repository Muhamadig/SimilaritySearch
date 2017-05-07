package model;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class FVKeySortedMap extends TreeMap<String, Integer> {
	private int Sum;



	public FVKeySortedMap(){
		super();
		Sum=0;
	}

	public FVKeySortedMap(Comparator<? super String> comparator){
		super(comparator);
		Sum=0;
	}
	
	
	public FVKeySortedMap(Map<?extends String,? extends Integer > m){
		super(m);
		Sum=sum(m);
	}

	public FVKeySortedMap(SortedMap<String, ? extends Integer> m){
		super(m);
		Sum=sum(m);
	}



	private int sum(Map<?extends String,? extends Integer > m){
		int sum=0;
		for(Integer value: m.values()) sum+=value;
		return sum;
	}

	public  Integer put(String object,Integer value){
		Integer val=get(object);
		if(val != null) {
			Sum-=val;
			super.put(object, val+value);
			Sum+=(value+val);
		}
		else{
			super.put(object, value);
			Sum+=value;
		}
		return val;
	}



	public Integer remove(Object key){
		Sum-=this.get(key);
		return super.remove(key);
	}

	public int getSum(){
		return this.Sum;
	}



	public void merge(FVKeySortedMap FV){

		for (String str: FV.keySet()) put(str,FV.get(str));
	}

	public String toString(){
		String str="";
		for(String key : this.keySet()) str+=key+"="+this.get(key)+"\n";
		return str;

	}

}
