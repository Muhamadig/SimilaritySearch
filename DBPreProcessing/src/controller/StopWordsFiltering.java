package controller;



import java.util.ArrayList;

import model.FVHashMap;
import model.Language;
public class StopWordsFiltering {

	
	/**
	 * 
	 * @param words Frequency vector 
	 * @param language The language of the text(english,spanish...)
	 * @return Frequency vector of words without stop wods.
	 */
	public static FVHashMap RemoveSW(FVHashMap words , Language language)
	{
		int sum=0;
		FVHashMap temp = (FVHashMap) words.clone();
		ArrayList<String> SW=language.getSW();
		for(String str:SW)
		{
			if(temp.containsKey(str))
			{
				sum++;
				temp.remove(str);
			}
		}
		System.out.println(sum);
		return temp;
	}
	
	
}
