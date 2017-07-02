package Controller.KMeans;

import java.util.ArrayList;
import java.util.List;
import XML.XML;
import XML.XMLFactory;
import model.FVValueSorted;
 
public class Cluster {
	
	public ArrayList<Point> points;
	public Point centroid; // the center point of the cluster
	public int id;
	private double aggDist;
	public FVValueSorted CommonWords;
	public static final int [] thresholds={60,127,100,60,90,90,65};
	public static ArrayList<String>DBCommonWords = new ArrayList<String>();
	private ArrayList<String> ClusterCW;
	public static ArrayList<String> GlobalCW=new ArrayList<String>();
	XML hashList=XMLFactory.getXML(XMLFactory.HashList);
	XML fvXML=XMLFactory.getXML(XMLFactory.FV);
	XML valueSortedXML=XMLFactory.getXML(XMLFactory.FV_ValueSorted);

	public Cluster(){
		
	}
	//Creates a new Cluster
	public Cluster(int id) {
		this.id = id;
		this.points = new ArrayList<Point>();
		this.centroid = null;
		CommonWords = new FVValueSorted();
		ClusterCW = new ArrayList<String>();
		
	}	
	public void setCommonWords(FVValueSorted CW){
		this.CommonWords = CW;
	}
	
	public FVValueSorted getCommonWords(){
		return this.CommonWords;
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
    
    public ArrayList<String> GetClusterCW(){
    	return this.ClusterCW;
    	}
}