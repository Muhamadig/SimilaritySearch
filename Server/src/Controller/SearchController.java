package Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import Controller.KMeans.Cluster;
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
	
	private static FVHashMap AllClusterWords(DBCluster c) throws IOException, SQLException{
		List<DBText> res = TextsDao.getByCluster(c.getId());
		ArrayList<FVHashMap> texts = ToHash(res);
		FVHashMap All = new FVHashMap();
		for(FVHashMap text : texts)
			All.merge(text);
		return All;
	 }
	
	public static int getCluster(FVKeySortedMap finalvec, List<DBCluster> clusters){
		int cluster =-1;
		double min =Double.MAX_VALUE;
		double dist=min;
		for(DBCluster c: clusters){
				dist = CommonDistnce(finalvec , c);
			if(dist<min){
				min=dist;
				cluster=c.getId();
			}
		}
		return cluster;
	}
	
	private static ArrayList<FVHashMap> ToHash(List<DBText> texts){
		ArrayList<FVHashMap> candidates = new ArrayList<FVHashMap>();
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
			xml = XMLFactory.getXML(XMLFactory.FV);
			FVHashMap candidate = (FVHashMap) xml.Import("TextFV"+File.separator+text.getFinalFV_name());
			candidates.add(candidate);
		}
		return candidates;
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

	public static double CommonDistnce(FVKeySortedMap text, DBCluster c){
		
		FVValueSorted CommonWords=null;
		FVHashMap AllWords=null;
		try{
		if(!c.isCommonWords_upToDate()){
			byte [] CW = c.getCommonWords();
			FileOutputStream f=new FileOutputStream("CW"+ File.separator+ c.getCommonWords_name()+".xml");
			f.write(CW);
			f.close();
			
			c.setCommonWords_upToDate(true);
			db.clusters.update(c);
		}

		CommonWords = (FVValueSorted) xml.Import("CW"+File.separator+c.getCommonWords_name()+".xml");
		 AllWords = AllClusterWords(c);
		 xml = XMLFactory.getXML(XMLFactory.FV_ValueSorted);
		}catch( IOException | SQLException e){
			e.printStackTrace();
		}
		double dist=0.0;
		int thresholdindex = Cluster.thresholds[c.getId()];
		for(int i=0;i<thresholdindex;i++){
			String CW = CommonWords.get(i).getKey();
			if(CommonWords.get(i).getValue() !=0){
			//	System.out.println("Word: " + CW +" Values: ["+text.get(CW) +","+CommonWords.get(i).getValue()+"]");
				if(CommonWords.get(i).getValue()!=0)
					dist += Math.pow((text.get(CW) - CommonWords.get(i).getValue()), 2);
			}
		}
		dist= Math.sqrt(dist);
		
//		FVKeySortedMap SigWord = new FVKeySortedMap();
//		for(String key : AllWords.keySet())
//			if(!CommonWords.contains(AllWords.get(key)) || (CommonWords.contains(AllWords.get(key)) && CommonWords.getByKey(key)!=0))
//				SigWord.put(key, AllWords.get(key));
//
//		for(String word : SigWord.keySet())
//			if(SigWord.get(word)!=0)
//				dist += Math.sqrt(Math.pow((text.get(word) - SigWord.get(word)), 2));
//		
//
//		
//		long text_num=0;
//		try {
//			text_num = TextsDao.numOfTexts(c.getId());
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		for(int i=0;i<thresholdindex;i++){
//			String CW = CommonWords.get(i).getKey();
//			if(text.containsKey(CW) && CommonWords.getByKey(CW)!=0)
//				dist +=Math.sqrt( Math.pow((text.get(CW) - (CommonWords.get(i).getValue()/text_num)), 2));
//		}

	
		

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
