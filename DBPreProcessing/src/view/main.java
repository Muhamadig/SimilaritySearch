package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controller.StopWordsFiltering;
import controller.ReadFile;
import controller.Util;
import model.FrequencyVector;

public class main {

	public static void main(String[] args) {
		
		
		FrequencyVector init_words= ReadFile.ReadFile("PDFs/file1_EN.pdf", "pdf");
		FrequencyVector swf_words=StopWordsFiltering.RemoveSW(init_words, "english");
		
		


	}

}
