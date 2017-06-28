package Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.sun.org.apache.bcel.internal.generic.NEW;

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
	private static XML fvXML = XMLFactory.getXML(XMLFactory.FV);

	
	private static XML hashlist = XMLFactory.getXML(XMLFactory.HashList);
	
	private static TreeMap<Integer, ArrayList<String>> clusters=(TreeMap<Integer, ArrayList<String>>) hashlist.Import("clusters.xml");
	
	
			
			
	
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

	//	private static FVHashMap AllClusterWords(DBCluster c) throws IOException, SQLException{
	//		List<DBText> res = TextsDao.getByCluster(c.getId());
	//		ArrayList<FVHashMap> texts = ToHash(res);
	//		FVHashMap All = new FVHashMap();
	//		for(FVHashMap text : texts)
	//			All.merge(text);
	//		return All;
	//	 }

	public static int getCluster(FVKeySortedMap finalvec, List<DBCluster> clusters){
		//		int cluster =-1;
		//		double min =Double.MAX_VALUE;
		//		double dist=min;
		//		for(DBCluster c: clusters){
		//				dist = CommonDistnce(finalvec , c);
		//			if(dist<min){
		//				min=dist;
		//				cluster=c.getId();
		//			}
		//		}
		//		return cluster;

		int cluster =-1;
		double min =Double.MAX_VALUE;
		double dist=min;
		for(int i=0;i<6;i++){
			dist = CommonDistnce(finalvec , i);
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
	private static double CommonDistnce(FVKeySortedMap finalvec, int i) {
		double dist=0.0;
		FVHashMap CommonWords = (FVHashMap) fvXML.Import("FinalFVs/Director of Public Prosecutions -v- O'Dea.html.xml");
		double dist=min;
		
		int num_of_texts=clusters.get(i).size();
		for(String key : CommonWords.keySet()){
			
				dist += Math.pow((finalvec.get(key) - (CommonWords.get(key))), 2);
//				if(CommonWords.get(key).compareTo(0)!=0)
//				dist +=Math.abs(finalvec.get(key) - (CommonWords.get(key)/num_of_texts));

			}
		
		dist= Math.sqrt(dist);
		
		System.out.println("cluster: "+i+"="+dist);
		return dist;
	}


	/*public static void test(List<DBCluster> clusters) throws IOException, SQLException{
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

	/*private static ArrayList<FVHashMap> ToHash(List<DBText> texts){
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
			if(!c.isCommonWords_upToDate()){
				byte [] CW = c.getCommonWords();
				FileOutputStream f=new FileOutputStream("CW"+ File.separator+ c.getCommonWords_name()+".xml");
				f.write(CW);
				f.close();

				c.setCommonWords_upToDate(true);
				db.clusters.update(c);
			}
//
			CommonWords = (FVValueSorted) xml.Import("CW"+File.separator+c.getCommonWords_name()+".xml");
			AllWords = AllClusterWords(c);
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
				//	System.out.println("Word: " + CW +" Values: ["+text.get(CW) +","+CommonWords.get(i).getValue()+"]");
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
	}*/

	
	//global=fvvaluesorted
	//common fvhashmap
	public static int findCluster(FVKeySortedMap finalvec, List<DBCluster> clusters){
		
		HashMap<Integer, FVValueSorted> globals=new HashMap<>();
		HashMap<Integer, FVHashMap> commons=new HashMap<>();
		HashMap<Integer, FVValueSorted> expandedCommons=new HashMap<>();

		double res=Double.MAX_VALUE;
		Integer cluster = null;
		FVValueSorted currGlobal;
		FVHashMap currCommon;
		for(int i=0;i<6;i++){
			currGlobal=(FVValueSorted) xml.Import("clusters"+File.separator+i+"_global.xml");
			currCommon=(FVHashMap) fvXML.Import("clusters"+File.separator+i+"_common.xml");
			
			globals.put(i, currGlobal);
			commons.put(i, currCommon);
		}
		
		int maxCWV=findMaxCV(commons);
		
		commons=null;
		
		for(Integer key:globals.keySet()){
			expandedCommons.put(key, expandCommon(globals.get(key),maxCWV));
		}
		ArrayList<String> currCommonWords;
		for(int i=0;i<5;i++){
			for(int j=i+1;j<6;j++){
				
				currCommonWords=findCommonWords(expandedCommons.get(i),expandedCommons.get(j));
				HashMap<String, Number> currRes=findDistance(expandedCommons.get(i),i,expandedCommons.get(j),j,currCommonWords,finalvec);
				res=Math.min(res, (double) currRes.get("dist"));
				if(res==(double) currRes.get("dist")) cluster=(Integer) currRes.get("cluster");
				
				System.out.println("between "+i +"and "+j +" :Cluster="+cluster+" Dist="+res );
			}
		}
		
		System.out.println(cluster);
		return cluster;
		
	}
	
	private static HashMap<String, Number> findDistance(FVValueSorted exCommon1, int i, FVValueSorted exCommon2,
			int j, ArrayList<String> commonWords, FVKeySortedMap finalvec) {
		
		HashMap<String, Number> result = new HashMap<>();
		double dist1=0;
		double dist2=0;
		for(Map.Entry<String, Integer> entry:exCommon1){
			if(finalvec.containsKey(entry.getKey()) && (!commonWords.contains(entry.getKey()))){
				dist1+=Math.pow(((entry.getValue()/clusters.get(i).size())-finalvec.get(entry.getKey())), 2);
			}
		}
		
		dist1=Math.sqrt(dist1);
		System.out.println(dist1 +":" +i);
		for(Map.Entry<String, Integer> entry:exCommon2){
			if(finalvec.containsKey(entry.getKey()) && (!commonWords.contains(entry.getKey()))){
				dist2+=Math.pow(((entry.getValue()/clusters.get(j).size())-finalvec.get(entry.getKey())), 2);
			}
		}
		dist2=Math.sqrt(dist2);
		System.out.println(dist2 +":" +j);

		result.put("dist", Math.min(dist1, dist2));
		
		int clusterId = ((double)result.get("dist")==dist1)?i:j;
		result.put("cluster", clusterId);
		return result;
	}

	private static ArrayList<String> findCommonWords(FVValueSorted exCommon1, FVValueSorted exCommon2) {
		ArrayList<String> common=new ArrayList<String>();
		for(Map.Entry<String, Integer> entry:exCommon1){
			if(exCommon2.containsKey(entry.getKey())) common.add(entry.getKey());
		}
		return common;
	}

	private static FVValueSorted expandCommon(FVValueSorted fvValueSorted, int maxCWV) {
		
		FVValueSorted fv=new FVValueSorted();
		for(int i=0;i<maxCWV;i++){
			fv.add(fvValueSorted.get(i));
		}
		return fv;
	}

	private static int findMaxCV(HashMap<Integer, FVHashMap> commons) {
		int max=0;
		for(Integer key:commons.keySet()){
			if(commons.get(key).size()>max) max=commons.get(key).size();
		}
		return max;
	}

	public static void main(String [] args){
		int res=findCluster((FVKeySortedMap) keySxml.Import("FinalFVs/Director of Public Prosecutions -v- O'Dea.html.xml"), null);
		System.out.println("res="+res);
	}

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
