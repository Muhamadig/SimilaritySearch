package view;

import java.io.IOException;
import java.nio.file.DirectoryStream.Filter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import controller.Filtering;
import controller.ReadFile;
import controller.Util;

public class main {

	public static void main(String[] args) {
		HashMap<String, Integer> init_words= ReadFile.ReadFile("PDFs/test.pdf", "pdf");
		ArrayList<String>sw_eng= Filtering.getSW("english");
		HashMap<String, Integer> swf_words=Filtering.RemoveSW(init_words, sw_eng);
		
		System.out.println("Test :");
		System.out.println("initial:\n"+init_words.toString()+"\n");
		System.out.println("init size:"+Util.freqSum(init_words));
		
		System.out.println("after filtering:\n"+swf_words.toString()+"\n");
		System.out.println(swf_words.size());
		System.out.println("after filtering size:"+ Util.freqSum(swf_words));
		
		
		
	}

}
