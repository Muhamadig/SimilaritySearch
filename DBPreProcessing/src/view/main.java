package view;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.rmi.PortableRemoteObject;

import controller.StopWordsFiltering;
import controller.ReadFile;
import controller.Stemmer;
import controller.stemmer.snowball.*;
import controller.stemmer.snowball.SnowballStemmer.ALGORITHM;
import controller.Util;
import controller.stemmer.PorterStemmer;
import model.FrequencyVector;

public class main {

	public static void main(String[] args) {
		
		
		FrequencyVector init_words= ReadFile.ReadFile("PDFs/file1_EN.pdf", "pdf");
		FrequencyVector swf_words=StopWordsFiltering.RemoveSW(init_words, "english");
		PorterStemmer stm= new PorterStemmer();
		System.out.println(stm.stem("explosion"));
		SnowballStemmer sns=new SnowballStemmer(ALGORITHM.ENGLISH);
		System.out.println(sns.stem("working"));
		

	}

}
