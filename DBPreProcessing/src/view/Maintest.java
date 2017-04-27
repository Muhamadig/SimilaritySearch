package view;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.FVHashMap;
import model.Language;
import model.Language.Langs;
import utils.Util;
import controller.ReadFile;
import controller.StanfordStemmer;
import controller.StopWordsFiltering;
public class Maintest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StanfordStemmer stm = new StanfordStemmer();
		String pdf=ReadFile.ReadFile("PDFs/file1_en.pdf", "pdf");
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Date dateobj = new Date();
		System.out.println(df.format(dateobj));
		FVHashMap vector = new FVHashMap();
		vector.textToFrequency(pdf);
		vector = StopWordsFiltering.RemoveSW(vector, new Language(Langs.ENGLISH));
		pdf = Util.toString(vector);
		
//		for(String str: vector.keySet()){
//			System.out.println(str+"-->"+ stm.lemmatize(str));
//		}
		//vector =stm.lemmatize(vector);
		FVHashMap stemmsHM=stm.lemmatize(vector);
		for(String key: stemmsHM.keySet())
			System.out.println(key + " "+ stemmsHM.get(key));
//		for(String str:stemms){
//			stemmsHM.put(str, 1);
//		}
//		System.out.println(stemmsHM.toString());
//		
		/*for (int i =0;i<stemms.size();i++)
			System.out.println(stemms.get(i));*/
//		DateFormat df1 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
//		Date dateobj1 = new Date();
//		System.out.println(df1.format(dateobj1));
		
		
	}

}
