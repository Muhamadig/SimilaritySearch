package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import DBModels.DBCluster;
import DBModels.DBText;
import Database.DbHandler;
import Server.Config;
import Views.Texts;
import XML.XML;
import XML.XMLFactory;
import model.FVHashMap;
import model.FVKeySortedMap;
import model.FVValueSorted;

public class SearchController {
	private static XML xml = XMLFactory.getXML(XMLFactory.FV_ValueSorted);
	private static DbHandler db = Config.getConfig().getHandler();
	private static Texts TextsDao = new Texts();
	
	private static FVHashMap reduceFV(FVHashMap fv, FVHashMap common) {
		FVHashMap reducedFV=(FVHashMap) fv.clone();
		for(String key:fv.keySet()){
			if(common.containsKey(key)) reducedFV.remove(key);
		}
		return reducedFV;
	}
	
	public static FVHashMap expandFV(FVHashMap initial,FVValueSorted global,FVValueSorted common){
		
		FVHashMap globalFV=new FVHashMap();
		FVHashMap commonFV=new FVHashMap();
		
		for(Entry<String ,Integer> entry:global){
			globalFV.put(entry.getKey(), entry.getValue());
		}
		
		for(Entry<String ,Integer> entry:common){
			commonFV.put(entry.getKey(), entry.getValue());
		}
		
		//reducedGlobal=GLOBAL-COMMON;
		FVHashMap reducedGlobal=reduceFV(globalFV,commonFV);
		
		//currFV=INITIAL-COMMON
		FVHashMap currFV = reduceFV(initial,commonFV);
		
		for(String key:reducedGlobal.keySet()){
			if(!currFV.containsKey(key)) currFV.put(key, 0);
		}

		return currFV;
		
	}

	private static FVKeySortedMap AllClusterWords(DBCluster c) throws IOException, SQLException{
		List<DBText> res = TextsDao.getByCluster(c.getId());
		ArrayList<FVKeySortedMap> texts = ToKeySorted(res);
		FVKeySortedMap All = new FVKeySortedMap();
		for(FVKeySortedMap text : texts)
			All.merge(text);
		return All;
	 }
	
	public static int getCluster(FVKeySortedMap finalvec, List<DBCluster> clusters){
		int cluster =-1;
		double min =Double.MAX_VALUE;
		double dist=min;
		for(DBCluster c: clusters){
			try {
				dist = CommonDistnce(finalvec , c);
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(dist<min){
				min=dist;
				cluster=c.getId();
			}
		}
		return cluster;
	}
	
	private static ArrayList<FVKeySortedMap> ToKeySorted(List<DBText> texts) {
		ArrayList<FVKeySortedMap> candidates = new ArrayList<FVKeySortedMap>();
		for(DBText text : texts){
			if(!text.isFV_upToDate()){
				byte [] fv = text.getFinalFV();
				FileOutputStream f;
				try {
					f = new FileOutputStream("TextFV"+ File.separator+ text.getFinalFV_name());
					f.write(fv);
					f.close();
					text.setFV_upToDate(true);
					db.texts.update(text);
				} catch (IOException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			xml = XMLFactory.getXML(XMLFactory.FVSortedMap);
			FVKeySortedMap candidate = (FVKeySortedMap) xml.Import("TextFV"+File.separator+text.getFinalFV_name());
			candidates.add(candidate);
		}
		return candidates;
	}

	public static double CommonDistnce(FVKeySortedMap text, DBCluster c) throws IOException, SQLException{
		
		if(!c.isCommonWords_upToDate()){
			byte [] CW = c.getCommonWords();
			FileOutputStream f=new FileOutputStream("CW"+ File.separator+ c.getCommonWords_name()+".xml");
			f.write(CW);
			f.close();
			
			c.setCommonWords_upToDate(true);
			db.clusters.update(c);
		}
		FVValueSorted CommonWords = (FVValueSorted) xml.Import("CW"+File.separator+c.getCommonWords_name()+".xml");
		FVKeySortedMap AllWords = AllClusterWords(c);
		double dist=0.0;
//		for(int i=0;i<CommonWords.size();i++){
//			String CW = CommonWords.get(i).getKey();
//			if(text.containsKey(CW)){
//			//	System.out.println("Word: " + CW +" Values: ["+text.get(CW) +","+CommonWords.get(i).getValue()+"]");
//				if(text.get(CW)!=0 && CommonWords.get(i).getValue()!=0)
//					dist += Math.pow((text.get(CW) - CommonWords.get(i).getValue()), 2);
//			}
//		}
//		dist= Math.sqrt(dist);
		
		FVKeySortedMap SigWord = new FVKeySortedMap();
		for(String key : AllWords.keySet())
			if(!CommonWords.contains(AllWords.get(key)) || (CommonWords.contains(AllWords.get(key)) && CommonWords.getByKey(key)!=0))
				SigWord.put(key, AllWords.get(key));
		
		System.out.println("Done...");
		for(String word : SigWord.keySet())
			if(text.containsKey(word))
				dist += Math.pow((text.get(word) - SigWord.get(word)), 2);
		
		dist= Math.sqrt(dist);
//		for(String key : AllWords.keySet())
//			if(!CommonWords.contains(key))
//				if(text.containsKey(key))
//					dist += Math.pow((text.get(key) - AllWords.get(key)), 2);
//			dist= Math.sqrt(dist);
		System.out.println("Cluster number: "+c.getId() + " CW Distance is: " + dist);
	return dist;	
	}
	
	public static ArrayList<DBText> Pareto(FVKeySortedMap finalfv , List<DBText> texts) throws IOException, SQLException{
		ArrayList<FVKeySortedMap> candidates = ToKeySorted(texts);
		ArrayList<FVKeySortedMap> results = new Pareto(candidates,finalfv).ParetoCalculate();
		ArrayList<DBText> ResultsToReturn = new ArrayList<DBText>();
		for(FVKeySortedMap result : results){
			int index = candidates.indexOf(result);
			ResultsToReturn.add(texts.get(index));
		}
		return ResultsToReturn;		
	}
}
