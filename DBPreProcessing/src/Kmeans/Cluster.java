package Kmeans;

import java.util.ArrayList;
import java.util.List;

import XML.XML;
import XML.XMLFactory;
import model.FVHashMap;
import model.FVValueSorted;
 
public class Cluster {
	
	public ArrayList<Point> points;
	public Point centroid; // the center point of the cluster
	public int id;
	private double aggDist;
	public FVHashMap CommonWords;
	
	public static ArrayList<String>DBCommonWords = new ArrayList<String>();
	
	//Creates a new Cluster
	public Cluster(int id) {
		this.id = id;
		this.points = new ArrayList<Point>();
		this.centroid = null;
	
	}
 
	static void SetDBCommonWords(){
		XML fvxml = XMLFactory.getXML(XMLFactory.FV_ValueSorted);
		FVValueSorted CW = (FVValueSorted) fvxml.Import("Expanded/results/common.xml");
		for(int i=0;i<CW.size();i++)
			DBCommonWords.add(CW.get(i).getKey());
	}
	
	public double CommonDistnce(FVHashMap text){
		double dist=0.0;
		for(String CW : CommonWords.keySet())
			if(text.containsKey(CW))
				dist += Math.pow((text.get(CW) - CommonWords.get(CW)), 2);
		dist= Math.sqrt(dist);
	return dist;	
	}
	public void setCommonWords(FVHashMap CW){
		this.CommonWords = CW;
	}
	
	public FVHashMap getCommonWords(){
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
    
  
}