package model;

import java.util.HashMap;

public class FVHashMap extends HashMap<String, Integer>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int Sum;
	
	
	public FVHashMap(){
		super();
		Sum=0;
	}
	
	public FVHashMap(String text){
		super();
		Sum=0;
		textToFrequency(text);
	}
	
	public int sum(){
		int sum=0;
		for(Integer value: this.values()) sum+=value;
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
	
	
	public void textToFrequency (String text){


		String [] words=text.trim().split("\\s");
		
		for(String str:words) put(str,1);
		if(this.containsKey(""))this.remove("");
	}
	
	public void merge(FVHashMap FV){
		
		for (String str: FV.keySet()) put(str,FV.get(str));
	}
	
//	public String toString(){
//		String str="";
//		for(String key : this.keySet()) str+=key+"="+this.get(key)+"\n";
//		return str;
//		
//	}

}
