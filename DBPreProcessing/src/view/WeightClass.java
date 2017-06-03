package view;

import model.FVKeySortedMap;

public class WeightClass{
	
	private FVKeySortedMap words;
	private int weight;
	private int freq;
	public WeightClass() {
		// TODO Auto-generated constructor stub
		words = new FVKeySortedMap();
	}
	
	public void setFreq(int freq){
		this.freq = freq;
	}
	
	public int getFreq(){
		return this.freq;
	}
	
	public void setWeight(int weight){
		this.weight = weight;
	}
	
	public int getWeight(){
		return this.weight;
	}
	public FVKeySortedMap getWords(){
		return this.words;}
	
	public void add(String key , Integer Value){
		words.put(key, Value);
	}
	
}
