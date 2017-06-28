package view;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import Controller.KMeans.Cluster;
import XML.XML;
import XML.XMLFactory;
import model.FVHashMap;
import model.FVKeySortedMap;

public class main {

	public static void main(String[] args) throws IOException{
	
	Cluster cluster=new Cluster(0);
	HashMap<Integer, String> thresholds=new HashMap<>();
	thresholds.put(0, "1996");
	thresholds.put(1, "16");
	thresholds.put(2, "mistreat");
	thresholds.put(3, "protective");
	thresholds.put(4, "barricade");
	thresholds.put(5, "dec");

//	cluster.findC_global_fv("clusters", ".", "FinalFVs");
//	cluster.find_CW_Sig("clusters", ".", "clusters",thresholds);
	}
	
}

