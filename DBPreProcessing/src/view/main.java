package view;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import Client.Application;
import Controller.KMeans.Cluster;
import DBModels.DBText;
import Utils.Request;
import Utils.Serialization;
import Utils.Util;
import XML.XML;
import XML.XMLFactory;
import model.FVHashMap;
import model.FVKeySortedMap;

public class main {

	public static void main(String[] args) throws IOException{

		//		File pre=new File("C:/Users/MASTER/Desktop/New Folder (3)/Frequency Vectors/final FVs");
		//		File server=new File("D:/SimilaritySearch/Server/Texts Final Fvs");
		//		
		//		File[] pre_files=pre.listFiles();
		//		File[] server_Files=server.listFiles();
		//		ArrayList<String> server_names=new ArrayList<>();
		//		for(File file:server_Files){
		//			server_names.add(file.getName());
		//		}
		//		for(File file:pre_files){
		//			if(!server_names.contains(file.getName().replaceAll("\\'", ""))) System.out.println(file.getName());
		//		}
		//		File clusters=new File("C:/Users/MASTER/Desktop/fromClusters.txt");
		//		File gui=new File("C:/Users/MASTER/Desktop/fromGui.txt");
		//
		//		System.out.println(clusters.getName());
		//		System.out.println(clusters.getParent());
		//		FileReader cluster_f=new FileReader(clusters);
		//		FileReader gui_f=new FileReader(gui);
		//		
		//		BufferedReader cluster_bf=new BufferedReader(cluster_f);
		//		BufferedReader gui_bf=new BufferedReader(gui_f);
		//		
		//		ArrayList<String> cluster_arr=new ArrayList<>();
		//		ArrayList<String> gui_arr=new ArrayList<>();
		//		String line;
		//		while((line=cluster_bf.readLine())!=null) cluster_arr.add(line);
		//		
		//		while((line=gui_bf.readLine())!=null) gui_arr.add(line+".html.xml");
		//
		//		
		//		for(String curr:cluster_arr){
		//			if(!gui_arr.contains(curr)) System.out.println(curr);
		//		}
		//		


		File clusters_file=new File("Clusters.xml");
		XML hashlist=XMLFactory.getXML(XMLFactory.HashList);
		TreeMap<Integer,ArrayList<String>> clusters=(TreeMap<Integer, ArrayList<String>>) hashlist.Import("Clusters.xml");
		HashMap<String,Integer> c_map=new HashMap<>();
		ArrayList<String> curr;
		ArrayList<String> all=new ArrayList<>();
		for(Integer key:clusters.keySet()){
			curr=clusters.get(key);
			all.addAll(curr);
			for(String str:curr){
				c_map.put(str, key);
			}
		}

		System.out.println(c_map.size());
		System.out.println(all.size());

		ArrayList<String> all_texts=new ArrayList<>();
		for(String text:all){

			if(!text.equals(text=filter(text))) System.out.println("Filter: "+text);
			all_texts.add(text);
			int textCount = contain(text,all_texts);
			if(textCount>1){
				System.out.println("textCount>1\n"+text );
				text=text.substring(0,text.indexOf(".html.xml"))+"_"+textCount+text.substring(text.indexOf(".html.xml"), text.length());
				System.out.println(text);
			}

			
		}
	}

	private static int contain(String name, ArrayList<String> all_texts) {
		int count =0;
		for(String str:all_texts){
			if(str.equals(name)) count ++;
		}

		return count;
	}

	private static String filter(String name){
		return name.replaceAll("\\'", "");
	}

}

