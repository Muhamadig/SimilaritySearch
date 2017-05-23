package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.itextpdf.text.pdf.events.IndexEvents.Entry;

import XML.XML;
import XML.XMLFactory;
import model.FVHashMap;
import model.Language;
import model.Language.Langs;
import model.Text;

public class Proccessing {
	private int numOfFiles;
	private int numOfWords;
	private int numOfFQ;
	private long time;
	private static int counter=0;
	private XML fvXml;



	public Proccessing(){
		resetParams();
		fvXml=XMLFactory.getXML(XMLFactory.FV);

	}

	public ArrayList<String> readFilesNames(String directory){
		ArrayList<String> names=new ArrayList<>();
		File dir = new File(directory);
		File[] directoryListing = dir.listFiles();
		if (directoryListing == null) return null;
		for (File file : directoryListing) {
			names.add(file.getName());
		}

		return names; 
	}
	public ArrayList<Long> getFilesSize(String directory){
		ArrayList<Long> size=new ArrayList<>();
		File dir = new File(directory);
		File[] directoryListing = dir.listFiles();
		if (directoryListing == null) return null;
		for (File file : directoryListing) {
			size.add(file.length());
		}

		return size; 
	}

	public void resetParams(){
		numOfFiles=0;
		numOfWords=0;
		numOfFQ=0;
		time=0;
	}

	public Text process(File file,String exportDirectory,Language lang){
		Integer[] stopWords_num = {0}; 
		String type=utils.Util.getFileExtension(file.getName());
		FVHashMap fv=SuperSteps.buildFrequencyVector(file.getAbsolutePath().toString(), type, lang,stopWords_num);
		fvXml.export(fv, exportDirectory+""+file.getName()+".xml");
		
		Text text=new Text(file.getName(), fv, stopWords_num[0], fv.size(), fv.getSum(), exportDirectory+""+file.getName()+".xml",type);
		return text;
	}
	public static int getCounter(){
		return counter;
	}
	public void createGlobal(){
		FVHashMap globalFV=new FVHashMap();
		File dir = new File("FVs/");
		File[] directoryListing = dir.listFiles();
		if (directoryListing == null){
			System.err.println("no xml files founded");
			return;
		}
		for (File file : directoryListing) {
			globalFV.merge((FVHashMap) fvXml.Import("FVs/"+file.getName()));	
		}
		fvXml.export(globalFV, "Global.xml");
		System.out.println("Done");
	}
	
	public FVHashMap getGlobal(){
		return (FVHashMap) fvXml.Import("Global.xml");
	}
	public ArrayList<Integer> getNumbers(){
		int words=0,f=0;
		File dir = new File("FVs/");
		File[] directoryListing = dir.listFiles();
		if (directoryListing == null){
			System.err.println("no xml files founded");
			return null;
		}
		for (File file : directoryListing) {
			FVHashMap curr=(FVHashMap) fvXml.Import("FVs/"+file.getName());	
			words+=curr.size();
			f+=curr.getSum();
		}
		ArrayList<Integer> res=new ArrayList<>();
		res.add(words);
		res.add(f);
		return res;
	}
	
	public ArrayList<Map.Entry<String,Integer>> sortGlobal(){
		ArrayList<Map.Entry<String,Integer>> res=new ArrayList<>();
		FVHashMap fv=getGlobal();
		for(Map.Entry<String, Integer> map:fv.entrySet()){
			 res.add(map);
			 System.out.println(res.get(res.size()-1));
		}
		Collections.sort(res, new utils.FVComparatorByValue());
		System.out.println(res.toString());
		return res;
	}
}
