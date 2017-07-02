package Controllers;

import java.util.ArrayList;

import Client.Application;
import Controller.ReadFile;
import Controller.StopWordsFiltering;
import DBModels.Result;
import Utils.Request;
import XML.XML;
import XML.XMLFactory;
import model.FVHashMap;
import model.Language;

public class SearchController {

	public static ArrayList<Result> search(String pathAndName,String fileType,Language lang){
		FVHashMap initialFV=toInitialFV(pathAndName,fileType,lang);
		Request request=new Request("search/search");
		request.addParam("FV", initialFV);
		return (ArrayList<Result>) Application.client.sendRequest(request);
	}
	public static FVHashMap toInitialFV(String pathAndName,String fileType,Language lang){

		Integer[] stopWords_num = {0};
		//step 1:read the file and clean it with (^[a-zA-Z0-9])
		String text=ReadFile.ReadFile(pathAndName, fileType);

		//step 2:convert text to frequency Vector:
		FVHashMap initialFV=new FVHashMap(text);

		//step 3:remove the stop words from the initial Frequency Victor
		FVHashMap removedSWFV=StopWordsFiltering.RemoveSW(initialFV, lang,stopWords_num);


		//step 4: reduce the frequency vector by Stemming and Synonyms (Request from WordNet Server)
		Request request=new Request("FV/fv");
		request.addParam("fv", removedSWFV);
		return (FVHashMap) Application.WN_Client.sendRequest(request);
	}
	
	public static int getCLuster(String name){
		Request request=new Request("search/find");
		request.addParam("text", name);
		return  (int) Application.client.sendRequest(request);
	}
}
