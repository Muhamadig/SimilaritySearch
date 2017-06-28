package Views;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.bind.v2.util.XmlFactory;

import Controller.SearchController;
import DBModels.DBText;
import Utils.Request;
import XML.XMLFactory;
import model.FVHashMap;
import model.FVKeySortedMap;
import model.FVValueSorted;

public class Search extends View{
	
	private Clusters clusterDao = new Clusters();
	private Texts TextsDao = new Texts();
	Global DBGlobals=new Global();
	
	public Object search(Request request){
		
		ArrayList<byte[]> texts=new ArrayList<>();
		
		//Step 1: get the text initial frequency vector from the user 
		//the initial frequency vector is filtered from stop words , synonyms and stemming.
		FVHashMap textFV=(FVHashMap) request.getParam("fv");
		
		//Step 2: get the database common words frequency vector.
		FVValueSorted cWVector=DBGlobals.getCommonVector();
		
		//Step 3: get the database global frequency vector.
		FVValueSorted globalVector=DBGlobals.getGlobalVector();

		//Step 4: remove the common words from the initial FV and then expand the vector.
		FVHashMap expandedFV=SearchController.expandFV(textFV, globalVector, cWVector);
		
		
		//Step 5: Sort the expanded Vector By Key.
		FVKeySortedMap finalFV=new FVKeySortedMap(expandedFV);
		
		
		//Step 6: Find the cluster that the Final FV belongs to.
		int clusterID =0;
			 clusterID = SearchController.getCluster(finalFV, clusterDao.getAll());
			System.out.println(clusterID);
		System.out.println(clusterID);
		
		List<DBText> CTexts = TextsDao.getByCluster(clusterID);
		
		//Step 7: Run Pareto Algorithm To Find best 5 Texts.
		List<DBText> results = null;
		try {
			 results = SearchController.Pareto(finalFV, CTexts);
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(DBText res : results){
			texts.add(res.getTextFile());
			System.out.println(res.getName());
		}
		//Step 8: return pareto results.
		return texts;

	}
	
	
}
