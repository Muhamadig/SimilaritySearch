package view;

import java.util.ArrayList;
import java.util.HashMap;


import controller.Filtering;
import controller.ReadFile;
import controller.Util;

public class main {

	public static void main(String[] args) {
		HashMap<String, Integer> init_words= ReadFile.ReadFile("PDFs/test.pdf", "pdf");
		ArrayList<String>sw_eng= Filtering.getSW("English");
		HashMap<String, Integer> swf_words=Filtering.RemoveSW(init_words, sw_eng);
		
		System.out.println("Test :");
		System.out.println("initial:\n"+init_words.toString()+"\n");
		System.out.println("init size:"+init_words.size());
		
		System.out.println("after filtering:\n"+swf_words.toString()+"\n");
		System.out.println("after filtering size:"+ swf_words.size());
		System.out.println(swf_words.size());
		System.out.println("after filtering size:"+ Util.freqSum(swf_words));
		System.out.println("after filtering size:"+ swf_words.size());

		System.out.println(swf_words.size());
		System.out.println("after filtering size:"+ Util.freqSum(swf_words));
		
		
		
	}

}
