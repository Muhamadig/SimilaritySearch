package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import model.FVHashMap;
public class StopWordsFiltering {


	/**
	 * 
	 * @param language the language of the stop words.
	 * @return array list of the stop words
	 */
	private static ArrayList <String> getSW(String language)
	{
		ArrayList<String> SW =new ArrayList<String>(); 
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader("StopWords/"+language +".txt"));
			String line;
			while ((line = reader.readLine()) != null)
				SW.add(line);
			reader.close();
			return SW;
		}
		catch (Exception e)
		{
			System.err.format("Exception occurred trying to read '%s'.", "StopWord/"+language +".txt");
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * 
	 * @param words Frequency vector 
	 * @param language The language of the text(english,spanish...)
	 * @return Frequency vector of words without stop wods.
	 */
	public static FVHashMap RemoveSW(FVHashMap words , String language)
	{
		int sum=0;
		FVHashMap temp = (FVHashMap) words.clone();
		ArrayList<String> SW=getSW(language);
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
