package Controller.KMeans;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import XML.XML;
import XML.XMLFactory;
import model.FVKeySortedMap;

public class KMeans {

	//Number of Clusters. This metric should be related to the number of points
	private int NUM_CLUSTERS=-1;   

	//Number of Points
	private static int NUM_CORDINATES;
	//Min and Max X and Y
	private static  int MIN_COORDINATE=1;
	private static  int MAX_COORDINATE=19343;
	private List<Point> points;
	private List<Cluster> clusters;
	private static String Final_FVS_dir;
	private String clusters_path;

	public KMeans(String finalFVs_directory, String DB_CW_path, String clusters_path){
		this.points = new ArrayList<Point>();
		this.clusters = new ArrayList<Cluster>();  
		KMeans.Final_FVS_dir = finalFVs_directory;
		this.clusters_path=clusters_path;

	}

	public KMeans(int NOClusters,String directory){
		this.NUM_CLUSTERS = NOClusters;
		this.points = new ArrayList<Point>();
		this.clusters = new ArrayList<Cluster>();  
		Final_FVS_dir = directory;
	}

	public int GetNOClusters(){
		return this.NUM_CLUSTERS;
	}

	public void SetPoints(ArrayList<ArrayList<Double>> allpoints){
		File dir = new File(KMeans.Final_FVS_dir+"/");
		File[] directoryListing = dir.listFiles();
		for(int i=0;i<allpoints.size();i++){
			Point toadd = new Point(allpoints.get(i));
			toadd.setName(directoryListing[i].getName());
			points.add(toadd);
		}
	}

	static void initCordinates(int max ,int NOCordinates){
		NUM_CORDINATES = NOCordinates;
		MAX_COORDINATE = max;
	}
	//Initializes the process
	public void init() {

		//Create Clusters
		//Set Random Centroids
		for (int i = 0; i < NUM_CLUSTERS; i++) {
			Cluster cluster = new Cluster(i);
			Point centroid = Point.createRandomPoint(MIN_COORDINATE,MAX_COORDINATE,NUM_CORDINATES);
			cluster.setCentroid(centroid);
			clusters.add(cluster);
		}

		//Print Initial state
		//plotClusters();
	}

	public List<Cluster> getclusters(){
		return this.clusters;
	}


	//The process to calculate the K Means, with iterating method.
	public void calculate() {
		//System.out.println("Calculating ...");
		//long t = System.currentTimeMillis();
		boolean finish = false;
		//   int iteration = 0;
		// Add in new data, one at a time, recalculating centroids with each new one. 
		while(!finish) {
			//Clear cluster state
			clearClusters();

			List<Point> lastCentroids = getCentroids();

			//Assign points to the closer cluster
			assignCluster();

			//Calculate new centroids.
			calculateCentroids();

			//	iteration++;

			List<Point> currentCentroids = getCentroids();

			//Calculates total distance between new and old Centroids
			double distance = 0;
			for(int i = 0; i < lastCentroids.size(); i++) {
				distance += Point.distance(lastCentroids.get(i),currentCentroids.get(i));
			}
			/*System.out.println("#################");
        	System.out.println("Iteration: " + iteration);
        	System.out.println("Centroid distances: " + distance);*/
			//plotClusters();

			if(distance == 0) {
				finish = true;
			}
		}
		//plotClusters();
		//  System.out.println("Done ... " + ((System.currentTimeMillis()-t)/1000/60) + " Minutes");
	}

	public void plotClusters(String SecPath){
		TreeMap <Integer ,ArrayList<String>> data = new TreeMap <Integer ,ArrayList<String>>();
		TreeMap <Integer , ArrayList<String>> centroid = new TreeMap<Integer,ArrayList<String>>(); 
		XML fvXml = XMLFactory.getXML(XMLFactory.HashList);
		for (int i = 0; i < NUM_CLUSTERS; i++) {
			Cluster c = clusters.get(i);
			ArrayList<String> filesname = new ArrayList<String>();
			List<Point> CPoints = c.getPoints();
			Point center = c.getCentroid();
			ArrayList<String> cordinates = new ArrayList<String>();
			for(Double cordinate : center.getCordinates())
				cordinates.add(cordinate.toString());
			centroid.put(i, cordinates);
			for(Point p : CPoints)
				filesname.add(p.getName());
			data.put(i, filesname);
		}
		fvXml.export(data, "Clusters.xml");
		fvXml.export(centroid, "Centroids.xml");
		
		fvXml.export(data, SecPath+File.separator+"Clusters.xml");
		fvXml.export(centroid,SecPath+File.separator+"Centroids.xml");
		
	}

	private void clearClusters() {
		for(Cluster cluster : clusters) {
			cluster.clear();
		}
	}

	private List<Point> getCentroids() {
		List<Point> centroids = new ArrayList<Point>(NUM_CLUSTERS);
		for(Cluster cluster : clusters) {
			Point aux = cluster.getCentroid();
			Point point = new Point(aux.getCordinates());
			centroids.add(point);
		}
		return centroids;
	}

	private void assignCluster() {

		double max = Double.MAX_VALUE;
		double min = max; 
		int cluster = 0;                 
		double distance = 0.0; 

		for(Point point : points) {
			min = max;
			for(int i = 0; i < NUM_CLUSTERS; i++) {
				Cluster c = clusters.get(i);
				distance = Point.distance(point, c.getCentroid());
				if(distance < min){
					min = distance;
					cluster = i;
				}
			}
			//  Recorder.write(point.getName() + " is in cluster number "+ cluster+"\n");
			point.setCluster(cluster);
			clusters.get(cluster).addPoint(point);
		}
	}

	public int assignCluster(Point p){
		double max = Double.MAX_VALUE;
		double min = max; 
		int cluster = 0;                 
		double distance = 0.0; 
		for(int i = 0; i < NUM_CLUSTERS; i++) {
			Cluster c = clusters.get(i);
			Point centroid = c.getCentroid();
			distance = Point.distance(p, centroid);
			if(distance < min){
				min = distance;
				cluster = i;
			}
		}
		return cluster;
	}

	private void calculateCentroids() {
		for(Cluster cluster : clusters) {
			List<Point> list = cluster.getPoints();
			int n_points = list.size();
			ArrayList<Double> sums = new ArrayList<Double>();
			for(int i=0;i<NUM_CORDINATES;i++)
				sums.add(0.0);
			for(Point point : list) {
				ArrayList<Double> cordinates = point.getCordinates();
				for(int i=0;i<cordinates.size();i++){
					double oldval = sums.get(i);
					oldval+=cordinates.get(i);
					sums.remove(i);
					sums.add(i,oldval);
				}

			}
			Point centroid = cluster.getCentroid();
			if(n_points > 0) {
				ArrayList<Double> toadd = new ArrayList<Double>();
				for(Double element : sums)
					toadd.add(element/n_points);
				centroid.setCordinates(toadd);
			}
			// cluster.setCentroid(centroid);
		}
	}

	public static ArrayList<ArrayList<Double>> getAllFrequencies(String directory){

		ArrayList<ArrayList<Double>> allfreq = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> toadd;
		XML fvXml = XMLFactory.getXML(XMLFactory.FVSortedMap);
		File dir = new File(directory+File.separator);
		File[] directoryListing = dir.listFiles();
		if (directoryListing == null){
			System.err.println("no xml files founded");
			return null;
		}
		for(int i=0;i< directoryListing.length ;i++){
			FVKeySortedMap SortedFreqVec  =  (FVKeySortedMap) fvXml.Import(Final_FVS_dir+File.separator+directoryListing[i].getName());
			toadd = new ArrayList<Double>();
			for(String key : SortedFreqVec.keySet())
				toadd.add(SortedFreqVec.get(key)*1.0);

			allfreq.add(toadd);
		}

		return allfreq;
	}

	public static int CalculateNOClusters(ArrayList<ArrayList<Double>> FreqPoints){
		int ClustersNumber; 
		KMeans kmeans =null;
		double dist = 0;
		try{
			ArrayList<Double> data = new ArrayList<Double>();

			int len = FreqPoints.get(0).size();
			int max = Point.MaximumCordinate(FreqPoints);

			for(ClustersNumber =1 ; ClustersNumber < 15;ClustersNumber++){
				kmeans= new KMeans(ClustersNumber,Final_FVS_dir);
				KMeans.initCordinates(max, len);
				kmeans.init();
				kmeans.SetPoints(FreqPoints);
				kmeans.calculate();
				dist =Cluster.GetAllAggDistsAvg(kmeans.getclusters())/10000;
				kmeans.clearClusters();
				data.add(dist);
				System.out.println("Number of Clusters: " + ClustersNumber + " , Distance: "+ dist);
			}
			
			//    	kmeans.CreateChart(data);
			for(int i=1;i<data.size();i++){
				double dist1 = data.get(i-1);
				double dist2 = data.get(i);
				if(dist1/dist2 < 1.15){
					System.out.println("Optimal Clusters NUmber is: " +i);
					return i;
				}
			}
		}catch(Exception e){e.printStackTrace();}
		return 1;

	}

	private KMeans CalculateClusters(){
		KMeans km;
		long t = System.currentTimeMillis(); 

		ArrayList<ArrayList<Double>> frequencies = KMeans.getAllFrequencies(KMeans.Final_FVS_dir);

		if(NUM_CLUSTERS ==-1){

			int NOClusters = KMeans.CalculateNOClusters(frequencies);

			km = new KMeans(NOClusters,KMeans.Final_FVS_dir);
		}
		else
			km = new KMeans(NUM_CLUSTERS , Final_FVS_dir);
		
		km.init();
		km.SetPoints(frequencies);
		km.calculate();
		km.plotClusters(clusters_path);
		System.out.println("Done ... " + ((System.currentTimeMillis() - t)/1000/60) + " Minitues");
		return km;

	}

	private void getClustersFromFile(TreeMap<Integer, ArrayList<String>> res){
		System.out.println("Clustering from file ...");
		long t = System.currentTimeMillis();
		XML fvxml = XMLFactory.getXML(XMLFactory.HashList);

		ArrayList<Double> toadd;

		clearClusters();
		@SuppressWarnings("unchecked")
		TreeMap <Integer,ArrayList<String>> Centerpoint = 
		(TreeMap<Integer, ArrayList<String>>) fvxml.Import(clusters_path+File.separator+"Centroids.xml");

		File files_dir=new File(Final_FVS_dir);
		File[] files_f=files_dir.listFiles();
		ArrayList<String> files_aL=new ArrayList<>();
		for(File file:files_f){
			files_aL.add(file.getName());
		}
		for(Integer key : res.keySet()){
			Cluster c = new Cluster(key);
			ArrayList<String> cent = Centerpoint.get(key);
			ArrayList<Double> cordinates = new ArrayList<Double>();
			for(String cordinate  :cent)
				cordinates.add(Double.parseDouble(cordinate)); 
			Point centroid = new Point(cordinates);
			c.setCentroid(centroid);
			ArrayList<String> files = res.get(key);
			fvxml = XMLFactory.getXML(XMLFactory.FVSortedMap);

			

			for(String filename: files){
				if(files_aL.contains(filename)){
					FVKeySortedMap SortedFreqVec  =  (FVKeySortedMap) fvxml.Import(Final_FVS_dir+File.separator+filename);
					toadd = new ArrayList<Double>();
					for(String freq : SortedFreqVec.keySet())
						toadd.add(SortedFreqVec.get(freq)*1.0);
					Point p = new Point(toadd);
					p.setName(filename);
					c.addPoint(p);
					points.add(p);
				}
			}

			clusters.add(c);
		}
	}

	public KMeans Clustering(){
		XML fvxml = XMLFactory.getXML(XMLFactory.HashList);
		@SuppressWarnings("unchecked")
		TreeMap<Integer, ArrayList<String>> res = (TreeMap<Integer, ArrayList<String>>) fvxml.Import(clusters_path+File.separator+"Clusters.xml");
		if(res ==null)
			this.clusters = CalculateClusters().getclusters();
		else
			getClustersFromFile(res);
		return this;
	}
}