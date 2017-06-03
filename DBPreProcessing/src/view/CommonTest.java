package view;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.jfree.ui.RefineryUtilities;

import Client.Client;
import Client.Config;
import XML.XML;
import XML.XMLFactory;
import controller.Proccessing;
import model.FVHashMap;

public class CommonTest {
	public static Client client = null;
	private ArrayList<WeightClass> Wights;
	
	public CommonTest(){
		Wights = new ArrayList<WeightClass>();
	}
	public static void main(String[] args) {
		Config.getConfig().readTextConfig();
		connect();
		//ArrayList<FVHashMap> AllVecs = new ArrayList<FVHashMap>();
		File dir = new File("FVsSW/");
		File[] files = dir.listFiles();
		ArrayList<String> fv_paths = new ArrayList<String>();
		for(File file : files)
			if((!file.getName().equals("global.xml")) && (!file.getName().equals("common.xml")))
				fv_paths.add(file.getAbsolutePath());
		Proccessing proc = new Proccessing();
//		FVHashMap global=proc.createGlobal(fv_paths, files[0].getParent());
		ArrayList<Map.Entry<String,Integer>> SortedGlobal = proc.sortFVHashMap(global);
//		XML fvxml = XMLFactory.getXML(XMLFactory.FVSortedMap);
//		fvxml.export(SortedGlobal, "SortedGlobal.xml");
		CommonTest test = new CommonTest();
		test.CalculateClasses(SortedGlobal);
		test.CreateChart(test.getWeight());
		ArrayList<WeightClass> temp = test.getWeight();
		for(int i=0;i<temp.size();i++)
			System.out.println(temp.get(i).getWeight() + " --> " + temp.get(i).getFreq() + " --> " + temp.get(i).getWords().size());
	}
	
	public ArrayList<WeightClass> getWeight(){
		return this.Wights;
	}
	public void CalculateClasses(ArrayList<Map.Entry<String,Integer>> global){
		int weight =1;
		Integer lasFreq = global.get(0).getValue();
		WeightClass temp = new WeightClass();
		temp.add(global.get(0).getKey(), lasFreq);
		for(int i=1;i<global.size();i++){
			Integer currFreq = global.get(i).getValue();
			if(currFreq == lasFreq)
				lasFreq = currFreq;
			else{
				temp.setFreq(lasFreq);
				lasFreq = currFreq;
				temp.setWeight(weight);
				weight++;
				Wights.add(temp);
				temp = new WeightClass();
				//System.gc();
			}
			
			temp.add(global.get(i).getKey(), currFreq);	
		}
	}
	public static void connect() {
		Config cfg = Config.getConfig();
		if (client != null) {
			client.close();
			client = null;
		}
		client = new Client(cfg.getHost(), cfg.getPort());
		Config.getConfig().writeTextConfig();
		client.open();
	}
	
	public void CreateChart(ArrayList<WeightClass> data){
    	LineChart_AWT chart = new LineChart_AWT("" ,"" , data);
		chart.pack( );
		RefineryUtilities.centerFrameOnScreen( chart );
		chart.setVisible( true );
    }
}
