import java.util.HashMap;

import Controller.KMeans.Cluster;

public class CWTest {

	
	public static void main(String[] args) {
		System.out.println("Start...");
		long t = System.currentTimeMillis();
		Cluster clusters = new Cluster();
		
		clusters.findC_global_fv("Clusters_Global", ".", "FinalFVs");
		HashMap<Integer,String> thresholds= new HashMap<Integer,String>();
		thresholds.put(0, "1996");
		thresholds.put(1, "16");
		thresholds.put(2, "mistreat");
		thresholds.put(3, "protective");
		thresholds.put(4, "barricade");
		thresholds.put(5, "dec");
		clusters.find_CW_Sig("Clusters_CW&SIG", ".", "Clusters_Global", thresholds);
		System.out.println("Done..." + ((System.currentTimeMillis() - t)/1000)+" Seconds");
	}

}
