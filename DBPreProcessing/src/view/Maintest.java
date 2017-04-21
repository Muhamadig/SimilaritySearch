package view;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.FVHashMap;
import controller.ReadFile;
import controller.StanfordStemmer;
import controller.StopWordsFiltering;
public class Maintest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StanfordStemmer stm = new StanfordStemmer();
		String pdf=ReadFile.ReadFile("PDFs/file1_sp.pdf", "pdf");
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Date dateobj = new Date();
		System.out.println(df.format(dateobj));
		
		FVHashMap vector = new FVHashMap();
		vector.textToFrequency(pdf);
		vector = StopWordsFiltering.RemoveSW(vector, "spanish");
		pdf = StopWordsFiltering.toString(vector);
		List <String> stemms =stm.lemmatize(pdf);
		for (int i =0;i<stemms.size();i++)
			System.out.println(stemms.get(i));
		DateFormat df1 = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Date dateobj1 = new Date();
		System.out.println(df1.format(dateobj1));
		
		
	}

}
