package Controller.KMeans;

import java.util.ArrayList;
import java.util.List;
import XML.XML;
import XML.XMLFactory;
import model.FVHashMap;
import model.FVKeySortedMap;
import model.FVValueSorted;
 
public class Cluster {
	
	public ArrayList<Point> points;
	public Point centroid; // the center point of the cluster
	public int id;
	private double aggDist;
	public FVValueSorted CommonWords;
	private FVValueSorted DiffCW=null;
	public static final int [] thresholds={45,95,133,43,68,140,251};
	//private static final String [] thresholds={"[frand,1996]","[sculptural relief,16]","[shower down,mistreat]","[likewise,protective]","[detention,barricade]","[amy,dec]"};
	public static ArrayList<String>DBCommonWords = new ArrayList<String>();
	private ArrayList<String> ClusterCW;
	public static ArrayList<String> GlobalCW=new ArrayList<String>();
	
	//Creates a new Cluster
	public Cluster(int id) {
		this.id = id;
		this.points = new ArrayList<Point>();
		this.centroid = null;
		CommonWords = new FVValueSorted();
		ClusterCW = new ArrayList<String>();
		
	}
 
	static void SetDBCommonWords(String dir){
		XML fvxml = XMLFactory.getXML(XMLFactory.FV_ValueSorted);
		FVValueSorted CW = (FVValueSorted) fvxml.Import(dir+"/results/common.xml");
		for(int i=0;i<CW.size();i++)
			DBCommonWords.add(CW.get(i).getKey());
	}
	
//	public double CommonDistnce(FVHashMap text){
//		double dist = 0.0;
//		for(String CW : GlobalCW)
//			if(text.containsKey(CW))
//				dist += Math.pow((text.get(CW) - CommonWords.getByKey(CW)), 2);
//			else
//				dist+=Math.pow(CommonWords.getByKey(CW), 2);
//		System.out.println("Cluster number: "+id + " CW Distance is: " + dist);
//		return dist;
//	}
	
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
    		FVKeySortedMap text = (FVKeySortedMap) fvxml.Import("FinalFVs/"+p.getName());
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
    	XML SortedXML = XMLFactory.getXML(XMLFactory.FV_ValueSorted);
    	if(CommonWords.isEmpty())
    		CalculateCW();
    	FVHashMap _result = new FVHashMap();
    	for(int i=0;i<CommonWords.size()-1;i++)
    		_result.put("["+CommonWords.get(i).getKey() +"," + CommonWords.get(i+1).getKey()+"]" , CommonWords.get(i).getValue() - CommonWords.get(i+1).getValue() );
    	
    	FVValueSorted results = new FVValueSorted(_result);
    	DiffCW = (FVValueSorted) results.clone();
      	SortedXML.export(results, id+"_CW.xml");
    	return results;
    }
    
    public ArrayList<String> GetClusterCW(){
    	return this.ClusterCW;
    	}
    
    public void GetCW(){
    	int threshold = thresholds[id];
    	if(DiffCW == null)
    		DiffCW = CalculateDiffCW();
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
}