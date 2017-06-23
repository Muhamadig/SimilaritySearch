

import org.jfree.chart.ChartPanel;

import java.util.ArrayList;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import controller.Proccessing;
import model.FVValueSorted;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChart_AWT extends ApplicationFrame {

	public LineChart_AWT(String applicationTitle , String chartTitle , FVValueSorted vec){
		super(applicationTitle);
		JFreeChart lineChart = ChartFactory.createLineChart(
				chartTitle,
				"Words","Frequency",
				createDataset(vec),
				PlotOrientation.VERTICAL,
				true,true,false);

		ChartPanel chartPanel = new ChartPanel( lineChart );
		chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
		setContentPane( chartPanel );
	}
	

	
	public DefaultCategoryDataset createDataset( FVValueSorted list) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i=0;i<list.size();i++)
			dataset.addValue(list.get(i).getValue(), "FREQ", list.get(i).getKey());
		
		return dataset;
	}

	
	

//	public DefaultCategoryDataset createDataset( ArrayList<WeightClass> data) {
//		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//		for(int i=0;i<data.size();i++){
//			Integer freq = data.get(i).getFreq();
//			Integer wight = data.get(i).getWeight();
//			//String word = 
//			dataset.addValue(freq, "Aggregate Distance", wight+"");
//		}
//		return dataset;
//	}
	
//	public static void main( String[ ] args ) {
//		LineChart_AWT chart = new LineChart_AWT(
//				"" ,
//				"");
//
//		chart.pack( );
//		RefineryUtilities.centerFrameOnScreen( chart );
//		chart.setVisible( true );
//	}
}