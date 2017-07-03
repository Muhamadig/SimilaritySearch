package Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import DBModels.DBCluster;
import DBModels.DBText;
import DBModels.Result;
import Views.Clusters;
import Views.Texts;
import XML.XML;
import XML.XMLFactory;
import model.FVHashMap;
import model.FVKeySortedMap;
import model.FVValueSorted;


public class SearchController {
	public enum Method{
		EUCLIDEAN,
		ONES;
	}
	private static XML keySxml = XMLFactory.getXML(XMLFactory.FVSortedMap);
	private static XML fvxml = XMLFactory.getXML(XMLFactory.FV);
	private static XML hashlist = XMLFactory.getXML(XMLFactory.HashList);
	

	private static Texts TextsDao = new Texts();
	private static Clusters ClustersDao=new Clusters();
	
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
	
	public static int classify(FVKeySortedMap finalFV,Method method){
		
		FVKeySortedMap fv=new FVKeySortedMap();
		for(String key:finalFV.keySet()){
			if(finalFV.get(key).compareTo(0)!=0) fv.put(key, finalFV.get(key));
		}
		List<DBCluster> clusters = ClustersDao.getAll();
		double final_dist=Integer.MAX_VALUE;
		double curr_dist;
		int res = Integer.MAX_VALUE;
		long texts_num;
		FVHashMap curr_c_CW;
		for(DBCluster cluster:clusters){
			curr_c_CW=(FVHashMap) fvxml.Import("Clustering Data Files"+File.separator+cluster.getCommonWordsFV_name());

			texts_num=TextsDao.numOfTexts(cluster.getId());
			switch(method){
			case EUCLIDEAN:
				if((curr_dist=euclideanDistance(fv,curr_c_CW,texts_num))<final_dist){
					final_dist=curr_dist;
					res=cluster.getId();
				}
				break;
			}
		}
		
		return res;
	}


	private static double euclideanDistance(FVKeySortedMap finalFV, FVHashMap curr_c_CW, long texts_num) {

		double dist=0;
		for(String key:finalFV.keySet()){
			if(curr_c_CW.containsKey(key) )
				dist+=Math.pow(((finalFV.get(key))-(curr_c_CW.get(key)/texts_num)), 2.0);
			else{
				dist+=Math.pow(((finalFV.get(key))-(0)), 2.0);
			}
		}
		return Math.sqrt(dist);
	}

	public static int getCluster(FVKeySortedMap finalvec){
		int cluster =-1;
		double min =Double.MAX_VALUE;
		double dist=min;
		
		@SuppressWarnings("unchecked")
		TreeMap <Integer ,ArrayList<String>> centorids = (TreeMap <Integer ,ArrayList<String>>) hashlist.Import("Centroids.xml");
		for(Integer key : centorids.keySet()){
			@SuppressWarnings("unchecked")
			ArrayList<String> copy = (ArrayList<String>) centorids.get(key).clone();
			dist = CentroidDistance(copy, finalvec);
			if(dist<min){
				min= dist;
				cluster = key;
			}
		}
		return cluster;
	}
	
	public static ArrayList<DBText> Pareto(FVKeySortedMap finalfv ,int cluster) {
		
		ArrayList<FVKeySortedMap> candidates=new ArrayList<>();
		
		ArrayList<DBText> texts = TextsDao.getFVsBYCluster(cluster);
		
		for(DBText curr:texts){
			candidates.add((FVKeySortedMap) keySxml.Import("Texts Final Fvs"+File.separator+curr.getFinalFV_name()));
		}
		ArrayList<FVKeySortedMap> results = new Pareto(candidates,finalfv).ParetoCalculate();
		double dist =0;
		for(FVKeySortedMap res : results){
			for(String key : res.keySet())
				if(finalfv.containsKey(key))
					dist+=Math.pow(finalfv.get(key) - res.get(key), 2);
			dist = Math.sqrt(dist);
			System.out.println("Distance: " + dist);
			dist =0;
		}
		ArrayList<DBText> res=new ArrayList<>();
		for(FVKeySortedMap map:results){
			res.add(texts.get(candidates.indexOf(map)));
		}
		return res;		
	}
	
	private static double CentroidDistance(ArrayList<String> ClusterCentorid , FVKeySortedMap finalvec){
		double dist = 0.0;
		
		ArrayList<Double> vector = new ArrayList<Double>();
		
		for(String key : finalvec.keySet())
			vector.add(finalvec.get(key)*1.0);
		
		for(int i=0;i<ClusterCentorid.size();i++)
			if(vector.get(i)!=0)
				dist+= Math.sqrt(Math.pow(Double.parseDouble(ClusterCentorid.get(i)) - vector.get(i), 2));
	
		return dist;
	}

	public static ArrayList<Result> getResults(List<DBText> results) {
		ArrayList<Result> result=new ArrayList<>();
		for(DBText text:results){
			result.add(new Result(text.getName(), TextsDao.getBlob(text.getName())));
		}
		return result;
	}
}
