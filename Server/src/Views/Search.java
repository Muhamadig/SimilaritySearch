package Views;

import java.util.ArrayList;

import Controller.SearchController;
import Utils.Request;
import model.FVHashMap;
import model.FVValueSorted;

public class Search extends View{
	
	
	Global globalDao=new Global();
	
	public Object search(Request request){
		
		ArrayList<byte[]> texts=new ArrayList<>();
		
		//frequency vector after remove stop words and synonyms and stemming.
		FVHashMap textFV=(FVHashMap) request.getParam("fv");
		
		//get common words vector
		FVValueSorted cWVector=globalDao.getVector("common");
		//get global words vector
		FVValueSorted globalVector=globalDao.getVector("global");

		//expand initial FV -->with reduce 
		
		//sort the expanded vector by key ==final Text vector
		
		//find the text cluster
		
		//pareto
		
		//return the best 5 results
		
		return texts;

	}
}
