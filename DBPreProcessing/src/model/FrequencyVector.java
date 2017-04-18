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
	
	public FrequencyVector(String text){
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
	
	
	public void textToFrequency (String text){


		String [] words=text.trim().split("\\s");
		
		for(String str:words){

			if(this.containsKey(str)) this.replace(str, this.get(str),this.get(str)+1 );
			else this.put(str, 1);
		}
		if(this.containsKey(""))this.remove("");
	}
	
	public void merge(FrequencyVector FV){
		
		for (String str: FV.keySet()) {
			if(this.containsKey(str)) this.replace(str, this.get(str),this.get(str)+FV.get(str) );
			else this.put(str,FV.get(str));
		}
	}
	
	public String toString(){
		String str="";
		for(String key : this.keySet()) str+=key+"="+this.get(key)+"\n";
		return str;
		
	}

}
