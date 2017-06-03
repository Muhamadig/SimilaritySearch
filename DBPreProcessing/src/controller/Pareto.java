package controller;

import java.util.ArrayList;

import model.FVKeySortedMap;

public class Pareto {

	private static int NO_Results =5;
	private ArrayList<FVKeySortedMap> candidates;
	private FVKeySortedMap inputText;
	private ArrayList<ArrayList<Integer>> ObjectiveVectors;
	private ArrayList<ArrayList<Integer>> Dominates;
	
	public Pareto(ArrayList<FVKeySortedMap> vectors , FVKeySortedMap text){
		candidates = vectors;
		inputText = text;
		ObjectiveVectors = new ArrayList<ArrayList<Integer>>();
	}
	
	private void CalculateObjectiveVectors(){
		ArrayList<Integer> ObjectiveVector;
		for(FVKeySortedMap candidate : candidates){
			ObjectiveVector = new ArrayList<Integer>();
			for(String key : inputText.keySet())
				ObjectiveVector.add(Math.abs(inputText.get(key) - candidate.get(key)));
			ObjectiveVectors.add(ObjectiveVector);
		}	
	}
	
	private int CheckDominates(ArrayList<Integer> ObjVec1 , ArrayList<Integer> ObjVec2){
		int Dominates =0;
		int len = ObjVec1.size();
		for(int i=0;i<len;i++){
			int value1 = ObjVec1.get(i);
			int value2 = ObjVec2.get(i);
			if(value1 <= value2){
				if(Dominates == 2)
					return 0;
				Dominates =1;
			}
			else
				if(Dominates==1)
					return 0;
				Dominates =2;
		}
		return Dominates;
	}
	
	private void InitArrayLists (){
		int len = ObjectiveVectors.size();
		Dominates = new ArrayList<ArrayList<Integer>>(len);
		ArrayList<Integer> init;
		for(int i=0;i<len;i++){
			init = new ArrayList<Integer>();
			Dominates.add(init);
		}
			
	}
	
	private void CalculateDominates(){
		int len = ObjectiveVectors.size();
		for(int i=0;i<len-1;i++){
			for(int j=i+1; j<len;j++){
				int dom = CheckDominates(ObjectiveVectors.get(i),ObjectiveVectors.get(j));
				switch (dom){
				case 1: Dominates.get(i).add(j); break;
				case 2: Dominates.get(j).add(i); break;
				}
			}
	}
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<Integer> RemoveDominates(){
		int i;
		ArrayList<Integer> NotOptimals = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> CopyDominates = (ArrayList<ArrayList<Integer>>) Dominates.clone();
		int size = CopyDominates.size();
		for( i=0;i<size;i++){
			ArrayList<Integer> Doms = Dominates.get(i);
			for(int j=0;j<Doms.size();j++){
				CopyDominates.remove(Doms.get(j));
				if(!(NotOptimals.contains(j)))
					NotOptimals.add(j);
			}
		}
		ArrayList<Integer> Optimal = new ArrayList<Integer>();
		for(i=0;i<size;i++)
			if(!(NotOptimals.contains(i)))
				Optimal.add(i);
		return Optimal;
	}
	
	private ArrayList<Integer> CalculateNResults(ArrayList<Integer> optimal){
		ArrayList<Integer> Closest = new ArrayList<Integer>();
		if(optimal.size() <= NO_Results)
			return optimal;
		
		for(int i=0;i<NO_Results;i++){
			int min = MinIndex(optimal);
			if(min!=Integer.MAX_VALUE){
				optimal.remove(min);
				Closest.add(min);
			}
			else
				return null;
		}
		return Closest;
	}
	
	private int MinIndex(ArrayList<Integer> optimal){
		int min = Integer.MAX_VALUE;
		double minAvg = Double.MAX_VALUE;
		for(int i=0;i<optimal.size();i++){
			Double avg = Average(i);
			if(avg < minAvg){
				min =i;
				minAvg = avg;
			}
		}
		return min;
	}
	
	private Double Average(int index){
		int sum=0;
		ArrayList<Integer> calc = ObjectiveVectors.get(index);
		for(Integer value : calc)
			sum+=value;
		return (double) (sum/calc.size());
	}
	
	public ArrayList<FVKeySortedMap> ParetoCalculate(){
		
		ArrayList<FVKeySortedMap> results = new ArrayList<FVKeySortedMap>();
		
		CalculateObjectiveVectors();
		
		InitArrayLists();
		
		CalculateDominates();
		
		ArrayList<Integer> optimal = RemoveDominates();
		
		ArrayList<Integer> ClosestResults = CalculateNResults(optimal);
		
		for(Integer index : ClosestResults)
			results.add(candidates.get(ClosestResults.get(index)));
		
		return results;
	}
}
