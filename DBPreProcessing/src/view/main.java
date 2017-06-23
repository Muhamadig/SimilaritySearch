package view;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import XML.XML;
import XML.XMLFactory;
import model.FVHashMap;
import model.FVKeySortedMap;

public class main {

	public static void main(String[] args) throws IOException{
		XML keySortedXml=XMLFactory.getXML(XMLFactory.FVSortedMap);
		FVKeySortedMap currFV;
		File folder=new File("FinalFVs");
		File output=new File("output.txt");
		BufferedWriter writer = null;
		
		File first=folder.listFiles()[0];
		currFV=(FVKeySortedMap) keySortedXml.Import(first.getAbsolutePath());
		String toWrite="";
		for(String key:currFV.keySet()){
			toWrite+="@ATTRIBUTEDEF="+key+"\n";
			
		}
		writer = new BufferedWriter(new FileWriter(output));
		writer.write(toWrite);
		int counter=0;
		for (final File fileEntry : folder.listFiles()) {
			counter++;
			currFV=(FVKeySortedMap) keySortedXml.Import(fileEntry.getAbsolutePath());
			toWrite="@NAME="+fileEntry.getName()+"\n";
			for(String key:currFV.keySet()){
				toWrite+=currFV.get(key).doubleValue()+" ";
			}
			writer.append(toWrite+"\n");
			System.out.println(counter);
		}
		writer.close();
		System.out.println("done");

	}

}

