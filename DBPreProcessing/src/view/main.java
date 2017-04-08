package view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import controller.ReadFile;

public class main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String text=ReadFile.docxRead("DOCs/file1_EN.docx");
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
		
		System.out.println(sum);
	}

}
