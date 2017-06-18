package Controller.KMeans;

import java.util.ArrayList;
import java.util.Random;
 
public class Point {
 
	private ArrayList<Double> cordinates;
    private int cluster_number = 0;
    private String filename;
 
   public Point (ArrayList<Double> cord){
    
    	this.cordinates = cord;
    	
    }
   
    public String getName(){
    	return this.filename;}
    
   public void setName(String name){
	   this.filename= name;
   }
   public void setCordinates(ArrayList<Double> cords){
	   cordinates.clear();
	   cordinates.addAll(cords);
   }
   
   public void setCluster(int n) {
        this.cluster_number = n;
    }
    
   public int getCluster() {
        return this.cluster_number;
    }
    
   public ArrayList<Double> getCordinates(){
	   return this.cordinates;}
    
    //Calculates the distance between two points.
    protected  static double distance(Point p, Point centroid) {
    	ArrayList <Double> cordinate1 = p.getCordinates();
    	ArrayList <Double> cordinate2 = centroid.getCordinates();
    	int len = cordinate1.size();
    	double sum =0;
    	for(int i=0;i<len; i++)
    		if(cordinate1.get(i)!=0)
    		sum +=Math.pow((cordinate1.get(i) - cordinate2.get(i)), 2);
    	sum= Math.sqrt(sum);
      return sum;
    }
    
    //Creates random point
    protected static Point createRandomPoint(int min, int max, int n) {
    	Random r = new Random();
    	ArrayList<Double> points = new ArrayList<Double> ();
    	for(int i=0;i<n;i++){
    	double x = min + (max - min) * r.nextDouble();
    	points.add(x);
    	}
    	return new Point(points);
    }
    
    
    static double max(ArrayList <Double> vec){
    	double MaxFreq=0;
    	for(int i=0;i<vec.size();i++){
    		double freq = vec.get(i);
    		if(freq >MaxFreq)
    			MaxFreq=freq;
    	}
    	return MaxFreq;
    }
    
    static double min (ArrayList <Double> vec){
    	double MinFreq = -1;
    	for(int i=0;i<vec.size();i++){
    		double freq = vec.get(i);
    		if(MinFreq == -1 || freq<MinFreq)
    			MinFreq = freq;
    	}
    	return MinFreq;
    }
    static int MaximumCordinate(ArrayList<ArrayList<Double>> allvec){
    	double maximum =0;
    	for(int i=0;i<allvec.size();i++){
    		double temp = max(allvec.get(i));
    		if(temp > maximum)
    			maximum = temp;
    	}
    	return (int)maximum;
    }
    static int MinimumCordinate(ArrayList<ArrayList<Double>> allvec){
    	 double minimum =-1;
    	 for(int i=0;i<allvec.size();i++){
     		double temp = min(allvec.get(i));
     		if(minimum ==-1 || temp < minimum)
     			minimum = temp;
     	}
    	 return (int)minimum;
     }
     
    public String toString() {
    	return "("+cordinates.toString()+")";
    }
}
