package Kmeans;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import UIUtils.MyTableModel;

public class ClusteringResults extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable Clusters_table;
	//private ContentPane content;
	public ClusteringResults(List<Cluster> data){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Clusters");
		setBounds(0, 0, 600, 450);
        this.setLocationRelativeTo(null);
       
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(700, 600));
		getContentPane().add(panel);
		
		JScrollPane clusters = new JScrollPane();
	    clusters.setBounds(10, 138, 680, 450);
	    panel.add(clusters);
		String[] doc_columnNames = {"#", "# of Cluster","Text name"};
		Object[][] doc_data = {};
		Clusters_table = new JTable();
		Clusters_table.setModel(new MyTableModel(doc_columnNames, doc_data));
		Clusters_table.setFillsViewportHeight(true);
		Clusters_table.setSurrendersFocusOnKeystroke(true);
		Clusters_table.setShowVerticalLines(false);
		Clusters_table.setRowHeight(30);
		Clusters_table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		clusters.setViewportView(Clusters_table);
		Clusters_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Clusters_table.setBackground(Color.WHITE);
		Clusters_table.getColumnModel().getColumn(0).setPreferredWidth(50);
		Clusters_table.getColumnModel().getColumn(1).setPreferredWidth(90);
		Clusters_table.getColumnModel().getColumn(2).setPreferredWidth(600);
		setData(data);
		
	}
	
	private void setData(List<Cluster> data){
		MyTableModel model = (MyTableModel) Clusters_table.getModel();
		int count=1;
		for(int i=0;i<data.size();i++){
			List<Point> cpoints = data.get(i).getPoints();
			for(Point p: cpoints){
				model.addRow(new Object[]{count,data.get(i).getId(),p.getName().replace(".html.xml", "")});
				count++;
			}
		}
	}
}
