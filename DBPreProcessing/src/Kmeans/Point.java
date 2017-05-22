package Kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
 
public class Point {
 
	private ArrayList<Double> cordinates;
    private int cluster_number = 0;
 
    public Point (ArrayList<Double> cord){
    	cordinates = new ArrayList<Double>();
    	cordinates.addAll(cord);
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
    
    public ArrayList<Double> getCordinates(){return this.cordinates;}
    
    //Calculates the distance between two points.
    protected  static double distance(Point p, Point centroid) {
    	ArrayList <Double> cordinate1 = p.getCordinates();
    	ArrayList <Double> cordinate2 = centroid.getCordinates();
    	int len = cordinate1.size();
    	double sum =0;
    	for(int i=0;i<len; i++)
    		sum +=Math.sqrt(Math.pow((cordinate1.get(i) - cordinate2.get(i)), 2));
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
    
    protected static List<Point> createRandomPoints(int min, int max, int number) {
    	List<Point> points = new ArrayList<Point>(number);
    	for(int i = 0; i < number; i++) {
    		points.add(createRandomPoint(min,max,10));
    	}
    	return points;
    }
    
    public String toString() {
    	return "("+cordinates.toString()+")";
    }
}
