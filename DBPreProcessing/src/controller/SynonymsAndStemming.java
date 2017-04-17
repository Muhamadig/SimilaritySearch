package controller;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import model.FrequencyVector;


public class SynonymsAndStemming {

	private List<String> AllWords;	
	
	public SynonymsAndStemming() {
		AllWords = new LinkedList<String>();
	}
	
	public void CollectWords (FrequencyVector [] Vectors)
	{
		for(FrequencyVector vec : Vectors)
		{
			for(String word : vec.keySet())
				if(!AllWords.contains(word))
					AllWords.add(word);
		}
		Collections.sort(AllWords);
	}
	
	public void ExpandedVectors (FrequencyVector [] Vectors)
	{
		Iterator<String> it = AllWords.iterator();
		while(it.hasNext())
		{
			for(FrequencyVector vec : Vectors)
			{
				String word =(String) it.next();
				if(!vec.containsKey(word))
					vec.put(word, (Integer) 0);
			}
		}
		
		for (FrequencyVector vec :Vectors)
			vec = Util.sortByValues(vec);
	}
	
	public FrequencyVector AllWordsFrequencies(FrequencyVector [] vectors)
	{
		int sum;
		FrequencyVector AllFrequinces = new FrequencyVector();
		Iterator <String> words = AllWords.iterator();
		while(words.hasNext())
		{
			sum=0;
			String word = words.next();
			for(FrequencyVector vec : vectors)
				sum+=vec.get(word);
			AllFrequinces.put(word, sum);
		}
		AllFrequinces = Util.sortByValues(AllFrequinces);
		return AllFrequinces;
	}
	
	public FrequencyVector GetCommonWords (FrequencyVector words)
	{
		int diff = -1;
		String threshold = null,tmpthresh=null;
		FrequencyVector Cwords = new FrequencyVector();
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
			Cwords.put(str, words.get(str));
		}
		return Cwords;
	}
}
