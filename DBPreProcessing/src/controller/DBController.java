package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

import com.sun.tools.xjc.gen.Array;

import Client.Application;
import DBModels.DBCluster;
import DBModels.DBGlobal;
import DBModels.DBText;
import XML.XML;
import XML.XMLFactory;
import Utils.*;
public class DBController {

	public static DBController instance=new DBController();
	XML hashListXML=XMLFactory.getXML(XMLFactory.HashList);
	TreeMap<Integer, ArrayList<String>> clusters=(TreeMap<Integer, ArrayList<String>>) hashListXML.Import("Clusters.xml");

	XML globalXML=XMLFactory.getXML(XMLFactory.FV_ValueSorted);

	ArrayList<String> all_texts;
	private DBController() {
		all_texts=new ArrayList<>();
	}


	public static DBController getInstance(){
		return instance;
	}

	public boolean createClusters(String clusterFileDir){
		boolean res=true;
		DBCluster cluster;
		byte[] cluster_CW;

		for(Integer key:clusters.keySet()){
			cluster_CW=Serialization.toByteArray(new File("CW"+File.separator+key+"_CW.xml"));
			cluster=new DBCluster(key, key+"_CW", false, cluster_CW);


			Request r=new Request("clusters/create");
			r.addParam("cluster", cluster);
			if((int)Application.client.sendRequest(r)>0) res=res && true;
			else res=false;
		}
		return res;
	}

	public boolean createTexts(){
		boolean res=true;
		ArrayList<String> clusterTexts;
		DBText dbText;
		byte[] textFile;
		byte[] finalFV;
		int textCount;
		for(Integer key:clusters.keySet()){
			clusterTexts=clusters.get(key);

			for(String text:clusterTexts){
				textFile=Serialization.toByteArray(new File("HTMLs"+File.separator+Util.get_XmlFile_Name(text)));
				finalFV=Serialization.toByteArray(new File("FinalFVs"+File.separator+text));

				text=filter(text);
				all_texts.add(text);
				textCount=contain(text);
				if(textCount>1){
					text=text.substring(0,text.indexOf(".html.xml"))+"_"+textCount+text.substring(text.indexOf(".html.xml"), text.length());
					dbText=new DBText(Util.get_XmlFile_Name(text), textFile,text, false, finalFV, null);
				}
				else{
					dbText=new DBText(Util.get_XmlFile_Name(text), textFile,text, false, finalFV, null);
				}
				Request r=new Request("texts/create");
				r.addParam("text", dbText);
				r.addParam("clusterID", key);

				if((int)Application.client.sendRequest(r)>0) res=res && true;
				else res=false;
			}
		}

		return res;
	}

	
	public boolean createGlobals(String path){
		boolean res=true;
		byte[] globalFV;
		byte[] commonFV;
		globalFV=Serialization.toByteArray(new File(path+"global.xml"));
		commonFV=Serialization.toByteArray(new File(path+"common.xml"));
		
		DBGlobal globalRow=new DBGlobal("globalFV", globalFV,false);
		DBGlobal commonRow=new DBGlobal("commonFV", commonFV,false);
		
		Request r=new Request("global/create");
		r.addParam("globalRow", globalRow);

		if((int)Application.client.sendRequest(r)>0) res=res && true;
		else res=false;
		
		r.addParam("globalRow", commonRow);

		if((int)Application.client.sendRequest(r)>0) res=res && true;
		else res=false;

		return res;
		
	}
	private String filter(String name){
		return name.replaceAll("\\'", "");
	}

	private int contain(String name){
		int count =0;
		for(String str:all_texts){
			if(str.equals(name)) count ++;
		}

		return count;
	}

}



