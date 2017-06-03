package controller;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import XML.XML;
import XML.XMLFactory;
import model.FVHashMap;
import model.FVKeySortedMap;
import model.Language;
import model.Text;

public class Proccessing {
	private XML fvXml;
	private XML treeMapXML;
	
	public Proccessing(){
		fvXml=XMLFactory.getXML(XMLFactory.FV);
		treeMapXML=XMLFactory.getXML(XMLFactory.FVSortedMap);
	}

	public Text process(File file,String exportDirectory,Language lang){
		Integer[] stopWords_num = {0}; 
		String type=utils.Util.getFileExtension(file.getName());
		FVHashMap fv=SuperSteps.buildFrequencyVector(file.getAbsolutePath().toString(), type, lang,stopWords_num);
		fvXml.export(fv, exportDirectory+File.separator+file.getName()+".xml");
		
		Text text=new Text(file.getName(), fv, stopWords_num[0], fv.size(), fv.getSum(), exportDirectory+""+file.getName()+".xml",type);
		return text;
	}
	
	
	
	public FVHashMap createGlobal(ArrayList<String> fv_paths,String exportPath){
		FVHashMap globalFV=new FVHashMap();
		for (String path : fv_paths) {
			globalFV.merge((FVHashMap) fvXml.Import(path));	
		}
		fvXml.export(globalFV, exportPath+File.separator+"global.xml");
		return globalFV;
	}
	
	public FVHashMap getGlobal(String path){
		return (FVHashMap) fvXml.Import(path);
	}
	
	public FVHashMap getCommonVector(ArrayList<String> fv_paths,String exportPath,Integer numOfTexts, String globalPath){
		FVHashMap commmonFV=new FVHashMap();
		FVHashMap global=getGlobal(globalPath);
		FVHashMap currFV;
		FVHashMap isCommon=new FVHashMap();
		for (String path : fv_paths) {
			currFV=(FVHashMap) fvXml.Import(path);
			for(String key:currFV.keySet()){
				if((currFV.get(key)).compareTo(0)>0)isCommon.put(key, 1);
			}
		}
		for(String key:isCommon.keySet()){
			if(isCommon.get(key).equals(numOfTexts)) commmonFV.put(key, global.get(key));
		}
		fvXml.export(commmonFV, exportPath+File.separator+"common.xml");
		return commmonFV;		
	}
	
	public ArrayList<Map.Entry<String,Integer>> sortFVHashMap(FVHashMap _fv){
		ArrayList<Map.Entry<String,Integer>> res=new ArrayList<>();
		FVHashMap fv=_fv;
		for(Map.Entry<String, Integer> map:fv.entrySet()){
			 res.add(map);
		}
		Collections.sort(res, new utils.FVComparatorByValue());
		return res;
	}

	
	
	public void expandAll(ArrayList<String> fv_paths, FVHashMap global, FVHashMap common) {
		FVHashMap currFV;
		FVHashMap reducedGlobal=reduceFV(global,common);
		for (String path : fv_paths) {
			currFV=reduceFV((FVHashMap) fvXml.Import(path),common);
			for(String key:reducedGlobal.keySet()){
				if(!currFV.containsKey(key)) currFV.put(key, 0);
			}
			fvXml.export(currFV,path);
		}
		
		
	}

	public FVHashMap reduceFV(FVHashMap fv, FVHashMap common) {
		FVHashMap reducedFV=(FVHashMap) fv.clone();
		for(String key:fv.keySet()){
			if(common.containsKey(key)) reducedFV.remove(key);
		}
		return reducedFV;
	}

	public void sortFV(ArrayList<String> fv_paths,ArrayList<String> fv_names, String sortedDirectory) {
		
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
}
