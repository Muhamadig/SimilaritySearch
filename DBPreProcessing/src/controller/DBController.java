package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;
import Client.Application;
import DBModels.DBCluster;
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

	public boolean createClusters(String path){
		boolean res=true;
		DBCluster cluster;
		byte[] cluster_CW;
		byte[] cluster_global;
		for(Integer key:clusters.keySet()){
			cluster_CW=Serialization.toByteArray(new File(path+File.separator+"Clusters CW_Sig FVs"+File.separator+key+"_common.xml"));
			cluster_global=Serialization.toByteArray(new File(path+File.separator+"Clusters Global FVs"+File.separator+key+"_global.xml"));
			cluster=new DBCluster(key, key+"_CW.xml",key+"_global.xml");


			Request r=new Request("clusters/create");
			r.addParam("cluster", cluster);
			r.addParam("c_CW", cluster_CW);
			r.addParam("c_global", cluster_global);

			if((int)Application.client.sendRequest(r)>0) res=res && true;
			else res=false;
		}
		return res;
	}

	public boolean createTexts(String path,String texts_path){
		boolean res=true;
		ArrayList<String> clusterTexts;
		DBText dbText;
		byte[] textFile;
		byte[] finalFV;
		int textCount;

		File actual_texts_dir=new File(path);
		File[] fvs=actual_texts_dir.listFiles();
		ArrayList<String> actual=new ArrayList<>();
		for(File curr:fvs){
			actual.add(curr.getName());
		}
		for(Integer key:clusters.keySet()){
			clusterTexts=clusters.get(key);

			for(String text:clusterTexts){
				if(actual.contains(text)){
					textFile=Serialization.toByteArray(new File(texts_path+File.separator+Util.get_XmlFile_Name(text)));
					finalFV=Serialization.toByteArray(new File(path+File.separator+text));

					text=filter(text);
					all_texts.add(text);
					textCount=contain(text);
					if(textCount>1){
						text=text.substring(0,text.indexOf(".html.xml"))+"_"+textCount+text.substring(text.indexOf(".html.xml"), text.length());
					}
					dbText=new DBText(Util.get_XmlFile_Name(text), textFile, text, null);

					Request r=new Request("texts/create");
					r.addParam("text", dbText);
					r.addParam("clusterID", key);
					r.addParam("fv", finalFV);
					if((int)Application.client.sendRequest(r)>0) res=res && true;
					else res=false;
				}
			}
		}

		return res;
	}


	public boolean createGlobals(String path){
		boolean res=true;
		byte[] globalFV;
		byte[] commonFV;
		globalFV=Serialization.toByteArray(new File(path+File.separator+"global.xml"));
		commonFV=Serialization.toByteArray(new File(path+File.separator+"common.xml"));


		Request r=new Request("global/create");
		r.addParam("DBGlobalFV", globalFV);
		r.addParam("DBCommonFV", commonFV);


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



