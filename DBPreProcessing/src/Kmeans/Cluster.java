package Kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.jfree.ui.RefineryUtilities;

import XML.XML;
import XML.XMLFactory;
import model.FVHashMap;
import model.FVKeySortedMap;
import model.FVValueSorted;
import view.LineChart_AWT;
 
public class Cluster {
	
	public ArrayList<Point> points;
	public Point centroid; // the center point of the cluster
	public int id;
	private double aggDist;
	public FVValueSorted CommonWords;
	private FVValueSorted DiffCW=null;
	private static final int [] thresholds={88,64,69,85,119,52};
	//private static final String [] thresholds={"[frand,1996]","[sculptural relief,16]","[shower down,mistreat]","[likewise,protective]","[detention,barricade]","[amy,dec]"};
	public static ArrayList<String>DBCommonWords = new ArrayList<String>();
	private ArrayList<String> ClusterCW;
	//Creates a new Cluster

	public Cluster(int id) {
		this.id = id;
		this.points = new ArrayList<Point>();
		this.centroid = null;
		CommonWords = new FVValueSorted();
		ClusterCW = new ArrayList<String>();
	}
 
	static void SetDBCommonWords(){
		XML fvxml = XMLFactory.getXML(XMLFactory.FV_ValueSorted);
		FVValueSorted CW = (FVValueSorted) fvxml.Import("Expanded/results/common.xml");
		for(int i=0;i<CW.size();i++)
			DBCommonWords.add(CW.get(i).getKey());
	}
	
	public double CommonDistnce(FVHashMap text){
		double dist=0.0;
		for(int i=0;i<CommonWords.size();i++){
			String CW = CommonWords.get(i).getKey();
			if(text.containsKey(CW))
				dist += Math.pow((text.get(CW) - CommonWords.get(i).getValue()), 2);
			else
				dist+=Math.pow((CommonWords.get(i).getValue()), 2);
		}
		dist= Math.sqrt(dist);
		System.out.println("Cluster number: "+id + " CW Distance is: " + dist);
	return dist;	
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
    
    public FVValueSorted CalculateCW(){
    	XML fvxml = XMLFactory.getXML(XMLFactory.FVSortedMap);
    	ArrayList<FVKeySortedMap> texts = new ArrayList<FVKeySortedMap>();
    	FVHashMap freqs = new FVHashMap();
    	for(Point p : points){
    		FVKeySortedMap text = (FVKeySortedMap) fvxml.Import("final/"+p.getName());
    		  texts.add(text);
    		  for(String key : text.keySet())
    			  freqs.put(key, text.get(key));
    	}
    	XML SortedXML = XMLFactory.getXML(XMLFactory.FV_ValueSorted);
    	FVValueSorted SortedCW = new FVValueSorted(freqs);
    	CommonWords = (FVValueSorted) SortedCW.clone();
    	SortedXML.export(SortedCW,"CW/"+ id+"_CW.xml");
    	return SortedCW ;
    
    }
    
    public FVValueSorted CalculateDiffCW(){

    	FVHashMap _result = new FVHashMap();
    	for(int i=0;i<CommonWords.size()-1;i++)
    		_result.put("["+CommonWords.get(i).getKey() +"," + CommonWords.get(i+1).getKey()+"]" , CommonWords.get(i).getValue() - CommonWords.get(i+1).getValue() );
    	
    	FVValueSorted results = new FVValueSorted(_result);
    	DiffCW = results;
    	return results;
    }
    
    public ArrayList<String> GetClusterCW(){
    	return this.ClusterCW;
    	}
    
    public void GetCW(){
    	int threshold = thresholds[id];
    	if(DiffCW == null)
    		DiffCW=CalculateCW();
    	int i=1;
    	String words;
    	while(i < threshold){
    		words = DiffCW.get(i).getKey();
    		words = words.replace("]", "");
    		words = words.replace("[", "");
    		String[] Splitted = words.split(",");
    		for(int j=0;j<Splitted.length;j++)
    			ClusterCW.add(Splitted[j]);
    		i++;
    	}
    	CommonWords = DiffCW;
    }
    
    public void CreateChart(FVValueSorted data){
    	LineChart_AWT chart = new LineChart_AWT("" ,id+"" , data);
		chart.pack( );
		RefineryUtilities.centerFrameOnScreen( chart );
		chart.setVisible( true );
    }
}