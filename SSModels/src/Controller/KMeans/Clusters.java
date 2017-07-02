package Controller.KMeans;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import XML.XML;
import XML.XMLFactory;
import model.FVHashMap;
import model.FVValueSorted;

public class Clusters {

	private XML hashList;
	private XML fvXML;
	private XML valueSortedXML;
	private TreeMap<Integer, ArrayList<String>> clusters;

	@SuppressWarnings("unchecked")
	public Clusters(String clusters_path) {
		hashList=XMLFactory.getXML(XMLFactory.HashList);
		fvXML=XMLFactory.getXML(XMLFactory.FV);
		valueSortedXML=XMLFactory.getXML(XMLFactory.FV_ValueSorted);
		clusters=(TreeMap<Integer, ArrayList<String>>) hashList.Import(clusters_path+File.separator+"Clusters.xml");

	}
	public void findC_global_fv(String export_path,String fvs_path){

		File export=new File(export_path);
		export.mkdirs();
		FVHashMap currGlobal;


		File files_dir=new File(fvs_path);
		File[] files_f=files_dir.listFiles();
		ArrayList<String> files_aL=new ArrayList<>();
		for(File file:files_f){
			files_aL.add(file.getName());
		}


		for(Integer key:clusters.keySet()){
			currGlobal=new FVHashMap();
			for(String text:clusters.get(key)){
				if(files_aL.contains(text))
					currGlobal.merge((FVHashMap) fvXML.Import(fvs_path+File.separator+text));
			}
			valueSortedXML.export(new FVValueSorted(currGlobal), export_path+File.separator+key+"_global.xml");
		}
	}

	public int get_clusters_num(){
		return clusters.size();
	}

	public void find_CW_Sig(String export_path,String globals_path,HashMap<Integer,String> thresholds){

		File export=new File(export_path);
		export.mkdirs();
		FVHashMap sigWords;
		FVHashMap commonWords;
		int clusters_num=get_clusters_num();

		File files_dir=new File(globals_path);
		File[] files_f=files_dir.listFiles();
		ArrayList<String> files_aL=new ArrayList<>();
		for(File file:files_f){
			files_aL.add(file.getName());
		}

		for(int i=0;i<clusters_num;i++){
			if(files_aL.contains(i+"_global.xml")){
				FVValueSorted currGlobal=(FVValueSorted) valueSortedXML.Import(globals_path+File.separator+i+"_global.xml");
				int th_index=currGlobal.getByKey(thresholds.get(i));
				sigWords=new FVHashMap();
				commonWords=new FVHashMap();

				for(int j=0;j<=th_index;j++){
					commonWords.put(currGlobal.get(j).getKey(), currGlobal.get(j).getValue());

				}


				for(int j=th_index+1;j<currGlobal.size();j++){
					sigWords.put(currGlobal.get(j).getKey(), currGlobal.get(j).getValue());

				}

				fvXML.export(commonWords, export_path+File.separator+i+"_common.xml");
				fvXML.export(sigWords, export_path+File.separator+i+"_sig.xml");

			}
		}
	}



}
