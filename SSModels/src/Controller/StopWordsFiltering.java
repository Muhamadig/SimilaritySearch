package Controller;



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
	public static FVHashMap RemoveSW(FVHashMap words , Language language,Integer[] stopWords_num)
	{
		FVHashMap temp = (FVHashMap) words.clone();
		ArrayList<String> SW=language.getSW();
		for(String str:SW)
		{
			if(temp.containsKey(str))
			{
				stopWords_num[0]+=temp.get(str);
				temp.remove(str);
			}
		}
		return temp;
	}
	
	public static ArrayList<String> RemoveSW(ArrayList <String> words, Language language)
	{
		@SuppressWarnings("unchecked")
		ArrayList<String> temp = (ArrayList<String>) words.clone();
		int sum =0;
		ArrayList<String> SW = language.getSW();
		for(String str:SW)
		{
			if(temp.contains(str))
			{
				sum++;
				temp.remove(str);
			}
		}
		System.out.println(sum);
		return temp;
	}
}
