package Views;


import java.util.List;
import Controller.SearchController;
import Controller.SearchController.Method;
import DBModels.DBText;
import Utils.Request;
import XML.XML;
import XML.XMLFactory;
import model.FVHashMap;
import model.FVKeySortedMap;
import model.FVValueSorted;

public class Search extends View{

	private Texts TextsDao = new Texts();
	Global DBGlobals=new Global();

	XML fvXML=XMLFactory.getXML(XMLFactory.FV);

	public Object search(Request request){


		//Step 1: get the text initial frequency vector from the user 
		//the initial frequency vector is filtered from stop words , synonyms and stemming.
		FVHashMap textFV=(FVHashMap) request.getParam("FV");

		//Step 2: get the database common words frequency vector.
		FVValueSorted cWVector=DBGlobals.getCommonVector();

		//Step 3: get the database global frequency vector.
		FVValueSorted globalVector=DBGlobals.getGlobalVector();


		//Step 4: remove the common words from the initial FV and then expand the vector.
		FVHashMap expandedFV=SearchController.expandFV(textFV, globalVector, cWVector);


		//Step 5: Sort the expanded Vector By Key.
		FVKeySortedMap finalFV=new FVKeySortedMap(expandedFV);


		//Step 6: Find the cluster that the Final FV belongs to.
		int cluster=SearchController.classify(finalFV, Method.EUCLIDEAN);


		//Step 7: Run Pareto Algorithm To Find best 5 Texts.
		List<DBText> results = SearchController.Pareto(finalFV, cluster);

		//Step 8: return pareto results.
		return SearchController.getResults(results);

	}
	public Object find(Request request){

		String name=(String) request.getParam("text");
		Request r=new Request();
		r.addParam("id", name);
		DBText t=(DBText) TextsDao.read(r);
		return t.getClusterId().getId();
	}

}
