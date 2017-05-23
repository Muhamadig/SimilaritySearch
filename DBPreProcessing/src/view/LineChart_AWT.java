package view;

import org.jfree.chart.ChartPanel;

import java.util.ArrayList;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import controller.Proccessing;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChart_AWT extends ApplicationFrame {

	public LineChart_AWT( String applicationTitle , String chartTitle ) {
		super(applicationTitle);
		JFreeChart lineChart = ChartFactory.createLineChart(
				chartTitle,
				"Words","Frquencies",
				createDataset(),
				PlotOrientation.VERTICAL,
				true,true,false);

		ChartPanel chartPanel = new ChartPanel( lineChart );
		chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
		setContentPane( chartPanel );
	}

	private DefaultCategoryDataset createDataset( ) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		Proccessing proc=new Proccessing();
		ArrayList<Map.Entry<String,Integer>> map=proc.sortGlobal();
		for(Map.Entry<String,Integer> entry:map){
			if(entry.getValue()<=1000) dataset.addValue(entry.getValue(), "Frequency", entry.getKey());
		}
		
		return dataset;
	}

	public static void main( String[ ] args ) {
		LineChart_AWT chart = new LineChart_AWT(
				"" ,
				"");

		chart.pack( );
		RefineryUtilities.centerFrameOnScreen( chart );
		chart.setVisible( true );
	}
}