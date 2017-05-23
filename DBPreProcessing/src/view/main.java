package view;

import XML.XML;
import XML.XMLFactory;
import controller.SuperSteps;
import model.FVHashMap;
import model.Language;
import model.Language.Langs;

public class main {

	public static void main(String[] args){
		String str="file1 .html.docx.";
		str=str.replaceAll(".*\\.(html|pdf|docx|doc)","$1");
		System.out.println(str);

	}
}

