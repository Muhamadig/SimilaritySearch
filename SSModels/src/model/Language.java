package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Language {

	Langs lang;
	ArrayList<String> stopWords;
	public  enum Langs{
		ENGLISH,
		SPANISH;
	}
	
	public Language(Langs l)
	{
		this.lang = l;
		stopWords=getSW(l.toString());
	}
	
	public ArrayList<String> getSW(){
		return stopWords;
	}
	/**
	 * 
	 * @param language the language of the stop words.
	 * @return array list of the stop words
	 */
	private  ArrayList <String> getSW(String language)
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

}
