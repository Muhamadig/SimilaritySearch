package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
public class Filtering {


	public static ArrayList <String> getSW(String language)
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

	public static HashMap <String,Integer> RemoveSW(HashMap <String,Integer> words , ArrayList<String> SW)
	{
		int sum=0;
		HashMap<String,Integer> temp = (HashMap <String,Integer>) words.clone();
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
