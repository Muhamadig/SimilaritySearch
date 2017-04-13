package view;
import controller.ReadFile;
import opennlp.tools.*;
import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.stemmer.snowball.SnowballStemmer;
import opennlp.tools.stemmer.snowball.SnowballStemmer.ALGORITHM;
import controller.StopWordsFiltering;

import model.FrequencyVector;
import org.apache.lucene.analysis.en.*;
public class main {

	public static void main(String[] args) {
		
		String str= ReadFile.ReadFile("PDFs/file1_EN.pdf","pdf");
		System.out.println(str);
		FrequencyVector str_freq=ReadFile.textToFrequency(str);
		System.out.println(str_freq);
		FrequencyVector SWF=StopWordsFiltering.RemoveSW(str_freq, "english");
		System.out.println(SWF);
		
		

	}

}
