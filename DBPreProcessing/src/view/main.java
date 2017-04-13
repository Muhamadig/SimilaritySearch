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
		
		
		FrequencyVector init_words= ReadFile.ReadFile("PDFs/file1_EN.pdf", "pdf");
		FrequencyVector swf_words=StopWordsFiltering.RemoveSW(init_words, "english");
PorterStemmer ss= new PorterStemmer();
		System.out.println(ss.stem("funniest"));
		SnowballStemmer sns=new SnowballStemmer(ALGORITHM.ENGLISH);
		System.out.println(sns.stem("funniest"));
		System.err.println();
		
		
		

	}

}
