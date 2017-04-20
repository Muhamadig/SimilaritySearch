package controller;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import model.FVHashMap;


public class SynonymsAndStemming {

	private List<String> AllWords;	
	private List<String> Cwords;
	
	public SynonymsAndStemming() {
		AllWords = new LinkedList<String>();
		Cwords = new LinkedList<String>();
	}
	
	public void CollectWords (FVHashMap [] Vectors)
	{
		for(FVHashMap vec : Vectors)
		{
			for(String word : vec.keySet())
				if(!AllWords.contains(word))
					AllWords.add(word);
		}
		Collections.sort(AllWords);
	}
	
	public void ExpandedVectors (FVHashMap [] Vectors)
	{
		Iterator<String> it = AllWords.iterator();
		while(it.hasNext())
		{
			for(FVHashMap vec : Vectors)
			{
				String word =(String) it.next();
				if(!vec.containsKey(word))
					vec.put(word, (Integer) 0);
			}
		}
		
//		for (FrequencyVector vec :Vectors)
			//vec = Util.sortByValues(vec);
	}
	
	public FVHashMap AllWordsFrequencies(FVHashMap [] vectors)
	{
		int sum;
		FVHashMap AllFrequinces = new FVHashMap();
		Iterator <String> words = AllWords.iterator();
		while(words.hasNext())
		{
			sum=0;
			String word = words.next();
			for(FVHashMap vec : vectors)
				sum+=vec.get(word);
			AllFrequinces.put(word, sum);
		}
		//AllFrequinces = Util.sortByValues(AllFrequinces);
		return AllFrequinces;
	}
	
	public List<String> getCommonWords (FVHashMap words)
	{
		int diff = -1;
		String threshold = null,tmpthresh=null;
		Set <String> keys = words.keySet();
		Iterator<String> keyit = keys.iterator();
		if(keyit.hasNext())
			tmpthresh = keyit.next();
		while(keyit.hasNext())
		{
			String temp = keyit.next();
			int difference = words.get(tmpthresh) - words.get(temp);
			if(difference > diff)
			{
				diff = difference;
				threshold = temp;
			}
		}
		
		keyit = keys.iterator();
		while (keyit.hasNext())
		{
			String str = keyit.next();
			if(str.equals(threshold))
				break;
			Cwords.add(str);
		}
		return Cwords;
	}
	
	public List <String> getCommonWords(){return this.Cwords;}

	public List<String> getAllWords(){return this.AllWords;}
	
	public FVHashMap RemoveCWords(FVHashMap vector)
	{
		Iterator<String> it = Cwords.iterator();
		while(it.hasNext())
		{
			String cword = it.next();
			if(vector.containsKey(cword))
				vector.remove(cword);
		}
		return vector;
	}
	
}
