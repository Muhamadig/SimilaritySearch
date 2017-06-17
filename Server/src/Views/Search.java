package Views;

import java.util.ArrayList;

import Controller.SearchController;
import Utils.Request;
import model.FVHashMap;
import model.FVKeySortedMap;
import model.FVValueSorted;

public class Search extends View{
	
	
	Global globalDao=new Global();
	
	public Object search(Request request){
		
		ArrayList<byte[]> texts=new ArrayList<>();
		
		//Step 1: get the text initial frequency vector from the user 
		//the initial frequency vector is filtered from stop words , synonyms and stemming.
		FVHashMap textFV=(FVHashMap) request.getParam("fv");
		
		//Step 2: get the database common words frequency vector.
		FVValueSorted cWVector=globalDao.getVector("common");
		
		//Step 3: get the database global frequency vector.
		FVValueSorted globalVector=globalDao.getVector("global");

		//Step 4: remove the common words from the initial FV and then expand the vector.
		FVHashMap expandedFV=SearchController.expandFV(textFV, globalVector, cWVector);
		
		
		//Step 5: Sort the expanded Vector By Key.
		FVKeySortedMap finalFV=new FVKeySortedMap(expandedFV);
		
		//Step 6: Find the cluster that the Final FV belongs to.
		
		
		
		//Step 7: Run Pareto Algorithm To Find best 5 Texts.
		
		
		//Step 8: return pareto results.
		
		return texts;

	}
}
