package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controller.Filtering;
import controller.ReadFile;
import controller.Util;
import model.FrequencyVector;

public class main {

	public static void main(String[] args) {
		FrequencyVector init_words= ReadFile.ReadFile("PDFs/file1_SP.pdf", "pdf");
		ArrayList<String>sw_eng= Filtering.getSW("Spanish");
		FrequencyVector swf_words=Filtering.RemoveSW(init_words, sw_eng);
		
		System.out.println("Test :");
		System.out.println("initial:\n"+init_words.toString()+"\n");
		System.out.println("init size:"+init_words.size());
		
		System.out.println("after filtering:\n"+swf_words.toString()+"\n");
		System.out.println("after filtering size:"+ swf_words.size());
		FrequencyVector sortedWords = Util.sortByKeys(swf_words);
		System.out.println(sortedWords.toString());
		
		System.out.println("sum by func for init words"+ init_words.sum());
		System.out.println("sum by  variable for init words"+ init_words.getSum());
		
		System.out.println("sum by func for swf_words"+ swf_words.sum());
		System.out.println("sum by  variable for swf_words"+ swf_words.getSum());


	}

}
