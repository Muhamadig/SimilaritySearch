package view;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import XML.XML;
import XML.XMLFactory;
import controller.Proccessing;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import model.FVHashMap;
 
public class ShowFV extends Application {
   
    FVHashMap toShow;
    ArrayList<Map.Entry<String,Integer>> sorted;
    ArrayList<Entry> diffArr;
    ArrayList<Entry> diffArr_2;
    

    PrintWriter writer1=null;

    public ShowFV() {
		XML fvXml=XMLFactory.getXML(XMLFactory.FV);
    	FVHashMap global=(FVHashMap) fvXml.Import("FVs/global.xml");
    	toShow=(FVHashMap) global.clone();
    	
    	Proccessing proc=new Proccessing();
//    	sorted= proc.sortFVHashMap(toShow);
//		try {
//			writer1 = new PrintWriter("Diff_with_ID_2.txt" , "UTF-8");
//		} catch (FileNotFoundException | UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//    	for(Map.Entry<String,Integer> map:sorted){
//    		writer1.write(map.getKey()+ " = "+map.getValue()+"\r\n");
//    	}
//    	 writer1.close();
    	
		int diff;
		Map.Entry<String,Integer> curr;
		Map.Entry<String,Integer> next;
		Entry res;
		diffArr=new ArrayList<>();
		for(int i=0;i<sorted.size()-1;i++){
			curr=sorted.get(i);
			next=sorted.get(i+1);
			diff=(curr.getValue())-(next.getValue());
//    		writer1.write(i+". ["+curr.getKey()+","+next.getKey()+"]"+ " = "+diff+"\r\n");
    		res=new Entry(i,diff);
    		diffArr.add(res);

		}
		
		Entry curr2;
		Entry next2;
		Entry res2;
		diffArr_2=new ArrayList<>();
		for(int i=2;i<1000;i++){
			curr2=diffArr.get(i);
			next2=diffArr.get(i+1);
//    		writer1.write(i+". ["+curr2.id+","+next2.id+"]"+ " = "+(curr2.diff-next2.diff)+"\r\n");

			res2=new Entry(i,curr2.diff-next2.diff);
			diffArr_2.add(res2);
		}
		
//		writer1.close();
//		for(int i=2;i<sorted.size()-1;i++){
//			curr=sorted.get(i);
//			next=sorted.get(i+1);
//			diff=(curr.getValue())-(next.getValue());
////    		writer1.write(i+". ["+curr.getKey()+","+next.getKey()+"]"+ " = "+diff+"\r\n");
//    		res=new Entry(i,diff);
//    		diffArr.add(res);
//
//		}
	}
    
   
   
    @Override public void start(Stage stage) {
        stage.setTitle("Global Vector");
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
//        final BarChart<String, Number> bc = new BarChart<>(xAxis,yAxis);
        final ScatterChart<Number,Number> bc = new ScatterChart<>(xAxis,yAxis);
        bc.setTitle("Frequency Vector");
//        bc.autosize();
        bc.setBackground(Background.EMPTY);
        xAxis.setLabel("Word");       
        yAxis.setLabel("Frequency");
 
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("frequency");
//    	for(Map.Entry<String,Integer> map:sorted){
//            series1.getData().add(new XYChart.Data(map.getKey(), Math.log(map.getValue())));
//        } 
        for(Entry entry:diffArr_2){
        	double logdiff=Math.log10(entry.diff);
            series1.getData().add(new XYChart.Data(entry.id,entry.diff));
        } 
       
        System.out.println(sorted.size());
        Scene scene  = new Scene(bc,800,600);
        bc.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
//    	new ShowFV();
        System.out.println("DONE");

    }
}


class Entry{
	Integer id;
	Integer diff;
	public Entry(Integer id,Integer diff) {
		this.id=id;
		this.diff=diff;
	}
}