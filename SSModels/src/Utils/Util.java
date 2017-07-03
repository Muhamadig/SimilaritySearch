package Utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


import model.FVHashMap;


public class Util {

	
	public static String getFileExtension(String fileName){
		return fileName.replaceAll(".*\\.(html|pdf|docx|doc)","$1");
	}
	public static String cutExtension(String fileName){
		return fileName.replaceAll(".*\\.(html|pdf|docx|doc|xml)","$0");
	}
	
	public static String get_XmlFile_Name(String fullName){
		return fullName.substring(0, fullName.indexOf(".xml"));

	}
	public static  Map<String, Integer> sortByKeys(Map<String, Integer> fv){
		FVHashMap res= new FVHashMap();
		Map<String, Integer> map= new TreeMap<String,Integer>(fv);
		for(String key:map.keySet()){
			res.put(key, map.get(key));
		}
		
		return map;
		
	}
	
	public static String toString(FVHashMap vec)
	{
		String words="";
		Set<String> keys = vec.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext())
		{
			words+=" " + it.next();
		}
		return words;
	}
	}

