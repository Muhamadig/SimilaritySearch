package controller;

import Client.Application;
import Utils.Request;
import model.FVHashMap;
import model.Language;

public class SuperSteps {
	
	/**
	 * 
	 * @param pathAndName 	:The file path and name ,for example (PDFs/file_en.pdf).
	 * @param fileType		:The File Type for example (pdf).
	 * @param lang			:Language of the text ,use:(new Language(Langs.ENGLISH))
	 * @return				:Final Frequency Vector of the text.
	 */
	public static FVHashMap buildFrequencyVector(String pathAndName,String fileType,Language lang,Integer[] stopWords_num){

		//step 1:read the file and clean it with (^[a-zA-Z0-9])
		String text=ReadFile.ReadFile(pathAndName, fileType);
		
		//step 2:convert text to frequency Vector:
		FVHashMap initialFV=new FVHashMap(text);
		
		//step 3:remove the stop words from the initial Frequency Victor
		FVHashMap removedSWFV=StopWordsFiltering.RemoveSW(initialFV, lang,stopWords_num);

		//step 4: reduce the frequency vector by Stemming and Synonyms (Request from WordNet Server)
		Request request=new Request("FV/fv");
		request.addParam("fv", removedSWFV);
		return (FVHashMap) Application.client.sendRequest(request);
	}
}
