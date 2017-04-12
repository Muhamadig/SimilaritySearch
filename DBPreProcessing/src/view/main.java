package view;
import controller.ReadFile;
import controller.StopWordsFiltering;
import controller.stemmer.PorterStemmer;
import controller.stemmer.snowball.SnowballStemmer;
import controller.stemmer.snowball.SnowballStemmer.ALGORITHM;
import model.FrequencyVector;
import org.apache.lucene.analysis.en.*;
public class main {

	public static void main(String[] args) {
		
		
		FrequencyVector init_words= ReadFile.ReadFile("PDFs/file1_EN.pdf", "pdf");
		FrequencyVector swf_words=StopWordsFiltering.RemoveSW(init_words, "english");
		PorterStemmer stm= new PorterStemmer();
		System.out.println(stm.stem("funniest"));
		SnowballStemmer sns=new SnowballStemmer(ALGORITHM.ENGLISH);
		System.out.println(sns.stem("funniest"));
		System.err.println();
		EnglishAnalyzer en=new EnglishAnalyzer();
		
		
		

	}

}
