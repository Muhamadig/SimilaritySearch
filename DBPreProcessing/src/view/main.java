package view;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import controller.ReadFile;
import controller.StopWordsFiltering;
import model.FVHashMap;
import model.FVKeySortedMap;
import model.Language;
import model.Language.Langs;
import utils.Util;
public class main {

	public static void main(String[] args) {
		
		//Step1: read files
		String en_pdf=ReadFile.ReadFile("PDFs/file1_EN.pdf", "pdf");
		String sp_pdf=ReadFile.ReadFile("PDFs/file1_SP.pdf", "pdf");
		String en_docx=ReadFile.ReadFile("DOCs/file1_EN.docx", "docx");
		//new language
		Language lang= new Language(Langs.ENGLISH);
		
		//string to hash map
		FVHashMap f1= new FVHashMap(en_pdf);
		
		System.out.println(f1.toString());
		System.out.println("number of words:"+ f1.size() + " the sum of all words :"+f1.sum());
		
		//Step 2:remove stop words
		
		f1=StopWordsFiltering.RemoveSW(f1, lang);
		System.out.println("After remove the stop words");
		System.out.println(f1.toString());
		System.out.println("number of words:"+ f1.size() + " the sum of all words :"+f1.sum());
		
		//Step 3: stemming and synonyms
		
		//step 4:merge all frequency vectors
		
	}
}

