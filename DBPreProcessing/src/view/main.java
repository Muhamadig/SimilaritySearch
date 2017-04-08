package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import controller.Filtering;
import controller.ReadFile;

public class main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String text=ReadFile.pdfRead("PDFs/file1_SP.pdf");
		HashMap<String, Integer> vector=ReadFile.textToFrequency(text);
		
		System.out.println(vector.toString());
		//System.out.println(text.trim().length());
		System.out.println(vector.size());
		
		Set <String> keys = vector.keySet();
		Iterator<String> it = keys.iterator();
		String str = null;
		int sum=0;
		while (it.hasNext())
		{
			str=it.next();
			sum=sum+vector.get(str);
		}
		
		//System.out.println(sum);
		
		ArrayList<String> StopWords = Filtering.getSW("Spanish");
		//System.out.println(StopWords.size());
		vector = Filtering.RemoveSW(vector, StopWords);
		System.out.println(vector.size());
		//System.out.println(vector.toString());
	}

}
