package Kmeans;

import java.util.ArrayList;
import java.util.List;
 
public class Cluster {
	
	public ArrayList<Point> points;
	public Point centroid; // the center point of the cluster
	public int id;
	private double aggDist;
	public ArrayList<String> CommonWords;
	public ArrayList<Cluster> subClusters;
	static ArrayList<String>DBCommonWords = new ArrayList<String>();
	//Creates a new Cluster
	public Cluster(int id) {
		this.id = id;
		this.points = new ArrayList<Point>();
		this.centroid = null;
		subClusters = new ArrayList<Cluster>();
	}
 
	
	public void setCommonWords(ArrayList<String> CW){
		this.CommonWords = CW;
	}
	
	public ArrayList<String> getCommonWords(){
		return this.CommonWords;
	}
	
	public ArrayList<Cluster> getSubClusters(){
		return this.subClusters;
	}
	
	public ArrayList<Point> getPoints() {
		return points;
	}
	
	public void addPoint(Point point) {
		points.add(point);
		
	}
 
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
 
	public Point getCentroid() {
		return centroid;
	}
 
	public void setCentroid(Point centroid) {
		this.centroid = centroid;
	}
 
	public int getId() {
		return id;
	}
	
	public void clear() {
		points.clear();
	}
	

	public void plotCluster() {
		System.out.println("[Cluster: " + id+"]");
		System.out.println("[Centroid: " + centroid + "]");
		System.out.println("[Points: \n");
		for(Point p : points) {
			System.out.println(p);
		}
		System.out.println("]");
	}
	
    private double AggregateDistance(){
    	if(aggDist != 0)
    		return aggDist;
    	int len = points.size();
    	double sum =0;
    	for(int i=0; i<len-1;i++)
    		for(int j=i+1;j<len;j++)
    			sum+=Point.distance(points.get(i), points.get(j));
    	aggDist=sum;
    	return sum;
    }
    
    public static double GetAllAggDistsAvg(List<Cluster> list){
    	double aggdists= 0;
    	int len = list.size();
    	for(int i=0;i<len;i++){
    		aggdists+=(list.get(i).AggregateDistance());
    	}
    	aggdists/=len;
    	return aggdists;
    }
    
    public void NextStepCluster(){
    	ArrayList<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
    	for(Point p : points)
    		data.add(p.getCordinates());
    	int NOClusters=KMeans.CalculateNOClusters(data);
    	if(NOClusters >1){
    	KMeans km = new KMeans(NOClusters);
    	km.init();
    	km.SetPoints(data);
    	km.calculate();
    	
    	}
    }
}