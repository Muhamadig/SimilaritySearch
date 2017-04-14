package view;

import org.tartarus.snowball.ext.PorterStemmer;

import controller.Stemmer;



public class main {

	public static void main(String[] args) {
	
		PorterStemmer stemmer = new PorterStemmer();
		stemmer.setCurrent("apples"); //set string you need to stem
		stemmer.stem();  //stem the word
		System.out.println(stemmer.getCurrent());//get the stemmed word
		Stemmer stm = new Stemmer();
		String str = "apples";
		stm.add(str.toCharArray(), str.length());
		stm.stem();
		System.out.println(stm.toString());
	}
}

