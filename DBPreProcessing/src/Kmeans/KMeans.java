package Kmeans;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class KMeans {
 
	//Number of Clusters. This metric should be related to the number of points
    private int NUM_CLUSTERS;   
    
    //Number of Points
    private int NUM_POINTS;
    //Min and Max X and Y
    private static final int MIN_COORDINATE = 1;
    private static final int MAX_COORDINATE = 2900;
    
    private List<Point> points;
    private List<Cluster> clusters;
    
    public KMeans(int ClustersNum , int PointsNum) {
    	this.NUM_CLUSTERS = ClustersNum;
    	this.NUM_POINTS = PointsNum;
    	this.points = new ArrayList<Point>();
    	this.clusters = new ArrayList<Cluster>();    	
    }
    
    
    //Initializes the process
    public void init() {
    	//Create Points
    	points = Point.createRandomPoints(MIN_COORDINATE,MAX_COORDINATE,NUM_POINTS);
    	
    	//Create Clusters
    	//Set Random Centroids
    	for (int i = 0; i < NUM_CLUSTERS; i++) {
    		Cluster cluster = new Cluster(i);
    		Point centroid = Point.createRandomPoint(MIN_COORDINATE,MAX_COORDINATE,10);
    		cluster.setCentroid(centroid);
    		clusters.add(cluster);
    	}
    	
    	//Print Initial state
    	plotClusters();
    }
    
    public List<Cluster> getclusters(){return this.clusters;}
	private void plotClusters() {
    	for (int i = 0; i < NUM_CLUSTERS; i++) {
    		Cluster c = clusters.get(i);
    		c.plotCluster();
    	}
    }
    
	//The process to calculate the K Means, with iterating method.
    public void calculate() {
        boolean finish = false;
        int iteration = 0;
        // Add in new data, one at a time, recalculating centroids with each new one. 
        while(!finish) {
        	//Clear cluster state
        	clearClusters();
        	List<Point> lastCentroids = getCentroids();
        	
        	//Assign points to the closer cluster
        	assignCluster();
            
            //Calculate new centroids.
        	calculateCentroids();
        	
        	iteration++;
        	
        	List<Point> currentCentroids = getCentroids();
        	
        	//Calculates total distance between new and old Centroids
        	double distance = 0;
        	for(int i = 0; i < lastCentroids.size(); i++) {
        		distance += Point.distance(lastCentroids.get(i),currentCentroids.get(i));
        	}
        	System.out.println("#################");
        	System.out.println("Iteration: " + iteration);
        	System.out.println("Centroid distances: " + distance);
        	plotClusters();
        	        	
        	if(distance == 0) {
        		finish = true;
        	}
        }
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
            point.setCluster(cluster);
            clusters.get(cluster).addPoint(point);
        }
    }
    
    private void calculateCentroids() {
        for(Cluster cluster : clusters) {
            List<Point> list = cluster.getPoints();
            int n_points = list.size();
            ArrayList<Double> sums = new ArrayList<Double>();
            for(int i=0;i<10;i++)
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
        }
    }

    
    public static void main(String[] args) {
    	int ClustersNumber;

    	try{
    		PrintWriter writer = new PrintWriter("clusters.txt", "UTF-8");
    	for(ClustersNumber =2 ; ClustersNumber < 51;ClustersNumber++){
    		KMeans kmeans = new KMeans(ClustersNumber,15);
        	kmeans.init();
        	kmeans.calculate();
        	writer.write("Number of clusters: " + ClustersNumber + " Aggregate Distance: " + Cluster.GetAllAggDistsAvg(kmeans.getclusters())+"\n");
        	kmeans.clearClusters();
    	}
    	writer.close();
    	}catch(Exception e){System.out.println(e.toString());
    	
    }
}
}