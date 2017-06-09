package view;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
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
		for(int i=0;i<200;i++)
			if( i%5 ==0 )
			dataset.addValue(list.get(i).getValue(), "FREQ", i+"");
			else
				dataset.addValue(list.get(i).getValue(), "FREQ", "");
		return dataset;
	}

	
	


//	
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