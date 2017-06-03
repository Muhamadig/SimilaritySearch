package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;

import Utils.FVComparatorByValue;

public class FVValueSorted extends ArrayList<Entry<String,Integer>> {
	private int Sum;
	
	public FVValueSorted(){
		super();
		Sum=0;
	}
	
	public FVValueSorted(FVHashMap fv){
		Sum=0;
		for(Entry<String, Integer> entry: fv.entrySet()){
			this.add(entry);
		}
		Collections.sort(this,new FVComparatorByValue());
	}
	
	@Override
	public boolean add(Entry<String,Integer> e){
		
		int index=0;
		while(index<this.size() && (this.get(index).getValue().compareTo(e.getValue())>=0)) index ++;
		this.add(index, e);
		Sum+=e.getValue();
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o){
		boolean res =super.remove(o);
		Sum-=((Entry<String,Integer>)o).getValue();
		return res;
		
	}
	
	public int getSum(){
		return Sum;
	}
	
	public boolean containsKey(String key) {
		
		int index=0;
		while(index<this.size() && (!(this.get(index).getKey()).equals(key))) index++;
		if(index<this.size() && this.get(index).getKey().equals(key)) return true;
		return false;
	}
	
	public int getByKey(String key){
		int index=0;
		while(index<this.size() && (!this.get(index).getKey().equals(key))) index++;
		if(index<this.size() && this.get(index).getKey().equals(key)) return index;
		return -1;
	}
}
