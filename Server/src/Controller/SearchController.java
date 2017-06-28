package Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import Controller.KMeans.Cluster;
import DBModels.DBCluster;
import DBModels.DBText;
import Database.DbHandler;
import Server.Config;
import Views.Clusters;
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
	private static XML keySxml = XMLFactory.getXML(XMLFactory.FVSortedMap);
	private static XML fvxml = XMLFactory.getXML(XMLFactory.FV);
	private static XML hashlist = XMLFactory.getXML(XMLFactory.HashList);
	
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
		ArrayList<String> GlobalCW = CommonGlobal();
		for(int i =0;i<6;i++){
				dist = CommonDistance(finalvec , GlobalCW , i);
			if(dist<min){
				min=dist;
				cluster=i;
			}
		}
		return cluster;
	}
	
	public static int getCluster(FVKeySortedMap finalvec){
		int cluster =-1;
		double min =Double.MAX_VALUE;
		double dist=min;
		
		TreeMap <Integer ,ArrayList<String>> centorids = (TreeMap <Integer ,ArrayList<String>>) hashlist.Import("Centroids.xml");
		for(Integer key : centorids.keySet()){
			ArrayList<String> copy = (ArrayList<String>) centorids.get(key).clone();
			Collections.sort(copy);
			dist = CentroidDistance(copy, finalvec);
			if(dist<min){
				min= dist;
				cluster = key;
			}
		}
		
//		TreeMap <Integer ,ArrayList<String>> clusters = (TreeMap <Integer ,ArrayList<String>>) hashlist.Import("Clusters.xml");
//		for(Integer key : clusters.keySet()){
//			dist = ClusterAvgDistance(clusters.get(key),finalvec);
//			if(dist < min){
//				min = dist;
//				cluster = key;
//			}
//		}
		return cluster;
	}
	
	@SuppressWarnings("unchecked")
	private static int NOTexts(int cluster){
		TreeMap<Integer, ArrayList<String>> res = (TreeMap<Integer, ArrayList<String>>) hashlist.Import("clusters.xml");
		return res.get(cluster).size();
	}
	
/*	public static void test(List<DBCluster> clusters) throws IOException, SQLException{
		ArrayList<List<DBText>> clusters_texts=new ArrayList<>();
		ArrayList<FVValueSorted> commons=new ArrayList<>();
		System.out.println("1");
		for(DBCluster cluster:clusters){
			List<DBText> c_texts=TextsDao.getByCluster(cluster.getId());
			clusters_texts.add(c_texts);
			FVValueSorted CommonWords = (FVValueSorted) xml.Import("CW"+File.separator+cluster.getCommonWords_name()+".xml");
			commons.add(CommonWords);
		}
		ArrayList<ArrayList<FVKeySortedMap>> fvs=new ArrayList<>();
		System.out.println("2");

		for(List<DBText> cluster_list:clusters_texts){
			fvs.add(ToKeySorted(cluster_list));
		}
		ArrayList<FVKeySortedMap> globals=new ArrayList<>();
		
		System.out.println("3");

		for(ArrayList<FVKeySortedMap> c_fvs:fvs){
			FVKeySortedMap curr=new FVKeySortedMap();
			for(FVKeySortedMap fv:c_fvs){
				curr.merge(fv);
			}
			globals.add(curr);
		}
		
		//globals fsor each cluster the global vec
		//common for each cluster the common
		//fvs for each cluster the texts fvs
		
		ArrayList<FVKeySortedMap> sig=new ArrayList<>();
		if((globals.size()==commons.size())&& globals.size()==fvs.size()){
			System.out.println("4");
			for(int i=0;i<globals.size();i++){
				FVKeySortedMap currSig=new FVKeySortedMap();
				for(String key:globals.get(i).keySet()){
					if(!commons.get(i).contains(key)) currSig.put(key, globals.get(i).get(key));
				}
				keySxml.export(currSig, "sig"+i);
//				sig.add(currSig);
				currSig=null;
			}
		}
		
		

		
	}
	*/
	
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
//		try{
//		if(!c.isCommonWords_upToDate()){
//			byte [] CW = c.getCommonWords();
//			FileOutputStream f=new FileOutputStream("CW"+ File.separator+ c.getCommonWords_name()+".xml");
//			f.write(CW);
//			f.close();
//			
//			c.setCommonWords_upToDate(true);
//			db.clusters.update(c);
//		}
//
//		CommonWords = (FVValueSorted) xml.Import("CW"+File.separator+c.getCommonWords_name()+".xml");
//		 AllWords = AllClusterWords(c);
//		 xml = XMLFactory.getXML(XMLFactory.FV_ValueSorted);
//		}catch( IOException | SQLException e){
//			e.printStackTrace();
//		}
		xml = XMLFactory.getXML(XMLFactory.FV_ValueSorted);
		CommonWords = (FVValueSorted) xml.Import("CW"+File.separator+c.getId()+"_common.xml");
		double dist=0.0;
	//	int thresholdindex = Cluster.thresholds[c.getId()];
		for(int i=0;i<CommonWords.size();i++){
			String CW = CommonWords.get(i).getKey();
			dist += Math.pow((text.get(CW) - CommonWords.get(i).getValue()), 2);
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

	public static double CommonDistance(FVKeySortedMap finalvec , ArrayList<String> Global , int cluster){
		double dist = 0.0;
		FVHashMap CommonWords = (FVHashMap) fvxml.Import("clusters"+File.separator+cluster+"_common.xml");
		CommonWords = ExpandedCW(CommonWords, Global);
		int numtexts = NOTexts(cluster);
		for(String cw : CommonWords.keySet())
			if(finalvec.get(cw)!=0)
			dist+=Math.pow((finalvec.get(cw) - (CommonWords.get(cw)/numtexts)), 2);
		dist = Math.sqrt(dist);
		System.out.println("Cluster "+ cluster +" = "+ dist);
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
	
	private static ArrayList<String> CommonGlobal(){
		
		ArrayList<String> Global = new ArrayList<String>();
		FVHashMap cw;
		for(int i=0;i<6;i++){
			cw = (FVHashMap) fvxml.Import("clusters"+File.separator+i+"_common.xml");
			for(String key: cw.keySet())
				if(!Global.contains(key))
					Global.add(key);
		}
		return Global;
	}

	private static FVHashMap ExpandedCW(FVHashMap OriginalCW , ArrayList<String> global){
		FVHashMap copy = (FVHashMap) OriginalCW.clone();
		for(String key : global)
			if(!OriginalCW.containsKey(key))
				copy.put(key, 0);
		return copy;
	}
	
	private static double CentroidDistance(ArrayList<String> ClusterCentorid , FVKeySortedMap finalvec){
		double dist = 0.0;
		
		ArrayList<Double> vector = new ArrayList<Double>();
		
		for(String key : finalvec.keySet())
			vector.add(finalvec.get(key)*1.0);
		Collections.sort(vector);
		
		for(int i=0;i<ClusterCentorid.size();i++)
			if(vector.get(i)!=0)
				dist+= Math.sqrt(Math.pow(Double.parseDouble(ClusterCentorid.get(i)) - vector.get(i), 2));
	
		return dist;
	}
	
	private static double ClusterAvgDistance(ArrayList<String> Ctexts , FVKeySortedMap finalvec){
		double dist=0.0;
		FVKeySortedMap vec;
		for(String text : Ctexts){
			vec = (FVKeySortedMap) keySxml.Import("FinalFVs"+File.separator+text);
			dist+=TwoTextsDistance(finalvec , vec);
		}
		
		return dist/Ctexts.size();
	}
	
	private static double TwoTextsDistance(FVKeySortedMap newtext , FVKeySortedMap clustertext){
		double dist =0.0;
		for(String key : newtext.keySet())
				dist+=Math.pow(newtext.get(key) - clustertext.get(key), 2);

		dist = Math.sqrt(dist);
		return dist;
	}
	
	public static void main(String[] args) {
		FVKeySortedMap finalvec = (FVKeySortedMap) keySxml.Import("FinalFVs"+File.separator+"Muwema -v- Facebook Ireland Limited.html.xml`x");
		int res = getCluster(finalvec);
		System.out.println("Result = "+ res);
	}
}
