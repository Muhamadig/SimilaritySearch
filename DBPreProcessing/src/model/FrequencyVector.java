package model;

import java.util.HashMap;

public class FrequencyVector extends HashMap<String, Integer>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int Sum;
	
	
	public FrequencyVector(){
		super();
		Sum=0;
	}
	
	public int sum(){
		int sum=0;
		for(Integer value: this.values()) sum+=value;
		return sum;
	}
	
	public  Integer put(String object,Integer value){
		Integer val=super.put(object, value);
		if(val != null) Sum+=(value-val);
		else
			Sum+=value;
		return val;
	}
	
	public boolean replace(String key, Integer oldValue,Integer newValue){
		boolean res= super.replace(key, oldValue, newValue);
		Sum= Sum +(newValue - oldValue);
		return res;
	}
	
	public Integer remove(Object key){
		Sum-=this.get(key);
		return super.remove(key);
	}
	
	public int getSum(){
		return this.Sum;
	}
	
	
	

}
