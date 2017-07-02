package Controller;

import java.util.ArrayList;

import model.FVKeySortedMap;

public class Pareto {

	private  int NO_Results =5;
	private ArrayList<FVKeySortedMap> candidates;
	private FVKeySortedMap inputText;
	private ArrayList<ArrayList<Integer>> ObjectiveVectors;
	private ArrayList<Integer> Dominated;
	
	public Pareto(ArrayList<FVKeySortedMap> vectors , FVKeySortedMap text){
		candidates = vectors;
		inputText = text;
		ObjectiveVectors = new ArrayList<ArrayList<Integer>>();
		Dominated = new ArrayList<Integer>();
	}
	
	private void CalculateObjectiveVectors(){
		ArrayList<Integer> ObjectiveVector;
		for(FVKeySortedMap candidate : candidates){
			ObjectiveVector = new ArrayList<Integer>();
			for(String key : inputText.keySet())
				if(candidate.containsKey(key))
				 ObjectiveVector.add(Math.abs(inputText.get(key) - candidate.get(key)));
				 else
					 ObjectiveVector.add(inputText.get(key));
			ObjectiveVectors.add(ObjectiveVector);
		}
	}
	
	private int CheckDominates(ArrayList<Integer> ObjVec1 , ArrayList<Integer> ObjVec2){
		
		return (Dominates(ObjVec1 , ObjVec2) ? 1 : (Dominates(ObjVec2 , ObjVec1) ? 2 : 0));

	}
	
	private boolean Dominates(ArrayList<Integer> ObjVec1 , ArrayList<Integer> ObjVec2){
		
		for (int i=0;i<ObjVec1.size();i++)
				if(ObjVec1.get(i) > ObjVec2.get(i))
					return false;
		return true;
	}
	
	private void CalculateDominates(){
		int len = ObjectiveVectors.size();
		for(int i=0;i<len-1;i++){
			for(int j=i+1; j<len;j++){
				int dom = CheckDominates(ObjectiveVectors.get(i),ObjectiveVectors.get(j));
				switch (dom){
				case 1: if(!Dominated.contains(j)) Dominated.add(j); break;
					
				case 2: if(!Dominated.contains(i)) Dominated.add(i);break;
				}
			}
	}
	}
	
	private int IsExist(){
		int index = -1;
		int sum=0;
		//FVKeySortedMap candidate : candidates
		for(int i=0;i<candidates.size();i++){
			for(String key : inputText.keySet()){
				FVKeySortedMap candidate = candidates.get(i);
				if(candidate.containsKey(key))
				 sum+=(Math.abs(inputText.get(key) - candidate.get(key)));
			}
			if(sum==0)
				index=i;
			sum=0;	
		}
		
		return index;
	}
	
	private ArrayList<Integer> RemoveDominates(){
		ArrayList<Integer> Optimal = new ArrayList<Integer>();
		for(int i=0;i<ObjectiveVectors.size();i++)
			if(!Dominated.contains(i))
				Optimal.add(i);
		return Optimal;
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<Integer> CalculateNResults(ArrayList<Integer> optimal){
		
		if(optimal.size() <= NO_Results)
			return optimal;
		ArrayList<Integer> Closest = new ArrayList<Integer>();
		
		ArrayList<ArrayList<Integer>> CopyObjVec = (ArrayList<ArrayList<Integer>>) ObjectiveVectors.clone();
		for(int i=0;i<NO_Results;i++){
			int min = MinIndex(optimal,CopyObjVec);
			if(min!=Integer.MAX_VALUE){
				CopyObjVec.remove(min);
				optimal.remove(min);
				Closest.add(min);
			}
			else
				return null;
		}
		return Closest;
	}
	
	private int MinIndex(ArrayList<Integer> optimal,ArrayList<ArrayList<Integer>> CopyObjVec){
		int min = Integer.MAX_VALUE;
		double minAvg = Double.MAX_VALUE;
		ArrayList<Integer> IndextoRet = new ArrayList<Integer>();
		for(int i=0;i<optimal.size();i++){
			IndextoRet = CopyObjVec.get(i);
			Double avg = Average(IndextoRet);
			if(avg < minAvg){
				min =i;
				minAvg = avg;
			}
		}
		min = ObjectiveVectors.indexOf(IndextoRet);
		return min;
	}
	
	private Double Average(ArrayList<Integer> calc){
		int sum=0;
		for(Integer value : calc)
				sum+=value;
		return (double) (sum/calc.size());
	}
	
	public ArrayList<FVKeySortedMap> ParetoCalculate(){
		
		ArrayList<FVKeySortedMap> results = new ArrayList<FVKeySortedMap>();
		
		CalculateObjectiveVectors();
		
		int res = IsExist();
		
		if(res!=-1){
			results.add(candidates.get(res));
			ObjectiveVectors.remove(res);
			NO_Results-=1;
			
		}
		
		CalculateDominates();

		ArrayList<Integer> optimal = RemoveDominates();
		
		ArrayList<Integer> ClosestResults = CalculateNResults(optimal);
		
		for(Integer index : ClosestResults)
			results.add(candidates.get(index));
		NO_Results =5;
		return results;
	}
}
