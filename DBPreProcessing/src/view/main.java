package view;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import XML.XML;
import XML.XMLFactory;
import model.FVHashMap;
import model.FVKeySortedMap;

public class main {

	public static void main(String[] args) throws FileNotFoundException{
		String text="Ó Gríanna & ors -v- An Bord Pleanála & ors.html.xml";
		System.out.println(text);
		text=text.substring(0,text.indexOf(".html.xml"))+"_1"+text.substring(text.indexOf(".html.xml"), text.length());

		System.out.println(text);
	}
}

 