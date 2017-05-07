package model;

import java.util.ArrayList;
import java.util.Map.Entry;

public class FVValueSorted extends ArrayList<Entry<String,Integer>> {
	private int Sum;
	
	public FVValueSorted(){
		super();
		Sum=0;
	}
	
	public FVValueSorted(FVKeySortedMap fv){
		Sum=0;
		for(Entry<String, Integer> entry: fv.entrySet()){
			this.add(entry);
			Sum+=entry.getValue();
		}
	}
	
	@Override
	public boolean add(Entry<String,Integer> e){
		boolean res=super.add(e);
		Sum+=e.getValue();
		return res;
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
	

}
