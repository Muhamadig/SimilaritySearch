package controller;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import Utils.Util;
import XML.XML;
import XML.XMLFactory;
import model.FVHashMap;
import model.FVKeySortedMap;
import model.FVValueSorted;
import model.Language;
import model.Text;

public class Proccessing {
	private XML fvXml;
	private XML treeMapXML;
	private XML fv_ValueSortedXml;
	
	public Proccessing(){
		fvXml=XMLFactory.getXML(XMLFactory.FV);
		treeMapXML=XMLFactory.getXML(XMLFactory.FVSortedMap);
		fv_ValueSortedXml=XMLFactory.getXML(XMLFactory.FV_ValueSorted);
	}

	public Text process(File file,String exportDirectory,Language lang){
		Integer[] stopWords_num = {0}; 
		String type=Utils.Util.getFileExtension(file.getName());
		FVHashMap fv=SuperSteps.buildFrequencyVector(file.getAbsolutePath().toString(), type, lang,stopWords_num);
		fvXml.export(fv, exportDirectory+File.separator+file.getName()+".xml");
		
		Text text=new Text(file.getName(), fv, stopWords_num[0], fv.size(), fv.getSum(), exportDirectory+""+file.getName()+".xml",type);
		return text;
	}
	
	
	
	public FVValueSorted createGlobal(ArrayList<String> fv_paths,String exportPath){
		FVHashMap globalFV=new FVHashMap();
		for (String path : fv_paths) {
			globalFV.merge((FVHashMap) fvXml.Import(path));	
		}
		FVValueSorted sorted_Global=sortFV_By_Value(globalFV);
		File dir=new File(exportPath+File.separator+"results");
		dir.mkdir();
		fv_ValueSortedXml.export(sorted_Global, exportPath+File.separator+"results"+File.separator+"global.xml");
		return sorted_Global;
	}
	
	public FVValueSorted importGlobal(String path){
		return 	(FVValueSorted) fv_ValueSortedXml.Import(path);

	}
<<<<<<< HEAD

	public FVValueSorted getCommonVector(FVValueSorted global,int threshold_index,String export_path){
		
		FVValueSorted common=new FVValueSorted();
		for(int index=0;index<=threshold_index;index++){
			common.add(global.get(index));
=======
	
	public ArrayList<Map.Entry<String,Integer>> sortFVHashMap(FVHashMap _fv){
		ArrayList<Map.Entry<String,Integer>> res=new ArrayList<>();
		FVHashMap fv=_fv;
		for(Map.Entry<String, Integer> map:fv.entrySet()){
			 res.add(map);
>>>>>>> 867acacb83cc245d62e9fb2d18a8580035a303b4
		}
		File dir=new File(export_path+File.separator+"results");
		dir.mkdir();
		fv_ValueSortedXml.export(common, export_path+File.separator+"results"+File.separator+"common.xml");		
		return common;
	}
	
	public FVValueSorted sortFV_By_Value(FVHashMap _fv){
		return new FVValueSorted(_fv);
	}
	
	public void expandAll(ArrayList<String> fv_paths, FVValueSorted global_sorted, FVValueSorted common_sorted,String export_dir, ArrayList<String> names) {
		FVHashMap currFV;
		FVHashMap global=new FVHashMap();
		FVHashMap common=new FVHashMap();
		for(Entry<String ,Integer> entry:global_sorted){
			global.put(entry.getKey(), entry.getValue());
		}
		
		for(Entry<String ,Integer> entry:common_sorted){
			common.put(entry.getKey(), entry.getValue());
		}
		System.out.println(global.size() +" "+ global.getSum());
		System.out.println(common.size() +" "+ common.getSum());

		FVHashMap reducedGlobal=reduceFV(global,common);
		for (String path : fv_paths) {
			currFV=reduceFV((FVHashMap) fvXml.Import(path),common);
			for(String key:reducedGlobal.keySet()){
				if(!currFV.containsKey(key)) currFV.put(key, 0);
			}
			fvXml.export(currFV,export_dir+File.separator+names.get(fv_paths.indexOf(path)));
		}
		
		
	}

	public FVHashMap reduceFV(FVHashMap fv, FVHashMap common) {
		FVHashMap reducedFV=(FVHashMap) fv.clone();
		for(String key:fv.keySet()){
			if(common.containsKey(key)) reducedFV.remove(key);
		}
		return reducedFV;
	}

	public void sortFV_BY_Key_Export(ArrayList<String> fv_paths,ArrayList<String> fv_names, String sortedDirectory) {
		
		FVHashMap currFV;
		FVKeySortedMap currSorted;
		for(String path:fv_paths){
			currFV=(FVHashMap) fvXml.Import(path);
			currSorted=new FVKeySortedMap(currFV);
			treeMapXML.export(currSorted,sortedDirectory+File.separator+fv_names.get(fv_paths.indexOf(path)));
			currFV=null;
			currSorted=null;
		}
		
	}

	public HashMap<String, Integer> checkThresholdWord(String threshold, String commonWordsPath) {
		HashMap<String, Integer> res=new HashMap<>();
		
		FVValueSorted global=importGlobal(commonWordsPath);
		
		if(global.containsKey(threshold)){
			int index=global.getByKey(threshold);
			res.put("word place", index);
			res.put("word FR", global.get(index).getValue());
			
			res.put("num of common words", index+1);
			res.put("num of sig words", (global.size()-1)-index);
			int sum=0;
			for(int i=0;i<=index;i++){
				sum+=global.get(i).getValue();
			}
			res.put("commonFR", sum);
			sum=0;
			for(int i=index+1;i<global.size();i++){
				sum+=global.get(i).getValue();
			}
			res.put("sigFR", sum);

			return res;
		}
		
		return null;
	}
}
