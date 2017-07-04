package view.ui.preProcessing;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import Controller.KMeans.Cluster;
import Controller.KMeans.Clusters;
import Controller.KMeans.KMeans;
import Controller.KMeans.Point;
import UIUtils.MyTableModel;
import controller.DBController;
import controller.Proccessing;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;


public class Tab3 extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static boolean done=false;
	private File[] eXml_files;
	private JButton prepareClustering_btn3;
	private JPanel clustering;
	private KMeans km;
	private JTable table;
	private JButton btnClustering;
	private JLabel lblNewLabel;
	private JLabel lblEstimatedTimeupTo;
	private Proccessing proc;
	public Tab3() {
		setBackground(Color.WHITE);
		setLayout(null);
		setPreferredSize(new Dimension(700, 480));


		proc=new Proccessing();
		prepareClustering_btn3 = new JButton("Prepare Clustering");
		prepareClustering_btn3.setBackground(SystemColor.inactiveCaption);
		//prepareClustering_btn3.setBorder(null);

		prepareClustering_btn3.setBounds(240, 34, 223, 23);
		add(prepareClustering_btn3);

		clustering = new JPanel();
		clustering.setBackground(Color.WHITE);
		clustering.setBounds(10, 103, 657, 336);
		add(clustering);
		clustering.setLayout(null);


		btnClustering = new JButton("Clustering");
		btnClustering.setEnabled(false);
		btnClustering.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

				String clusters_path=MainApp.getWS()+File.separator+"clustering";

				File clusters_file=new File(clusters_path+File.separator+"Clusters.xml");
				DefaultTableModel dm = (DefaultTableModel) table.getModel();

				if(!clusters_file.exists()){
					//(exported files directory,expanded files directory)
					km = new KMeans(MainApp.getWS()+File.separator+"Frequency Vectors"+File.separator+"final FVs",
							MainApp.getWS()+File.separator+"DB FV Files",clusters_path);



					km.init();
					km.Clustering();

					List<Cluster> clusters=km.getclusters();
					int count=1;



					for(int i=0;i<clusters.size();i++){
						List<Point> cpoints = clusters.get(i).getPoints();
						for(Point p: cpoints){
							dm.addRow(new Object[]{count,clusters.get(i).getId(),p.getName().replace(".html.xml", "")});
							count++;
						}
					}
				}

				else{
					TreeMap<Integer, ArrayList<String>> clusters = proc.getClustersFile(clusters_path+File.separator+"Clusters.xml");
					int count=0;
					ArrayList<String> c_texts;
					for(Integer key:clusters.keySet()){

						c_texts=clusters.get(key);
						for(String text:c_texts){
							count++;
							dm.addRow(new Object[]{count,key,text.replace(".html.xml", "")});
						}
					}
				}
				create_Clusters_CW_global();

				DBController dbc=DBController.getInstance();
				if(!dbc.createClusters(MainApp.getWS()+File.separator+"clustering"))
					try {
						throw new Exception("Error occured on writing Clusters globals and common words in database");
					} catch (Exception ex) {

						JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				setCursor(null);

				JOptionPane.showMessageDialog(null, "Pre-Processing finish, Please Update the database by click the button bellow", "Update Database!", JOptionPane.INFORMATION_MESSAGE);
				MainApp.estimated.setVisible(true);

			}
		});
		btnClustering.setBounds(291, 11, 117, 29);
		clustering.add(btnClustering);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 47, 650, 289);
		clustering.add(scrollPane);


		String[] columnNames = {"#", "Cluster","Text Name"};
		Object[][] texts_tabel = {};
		table = new JTable();
		table.setModel(new MyTableModel(columnNames, texts_tabel));
		table.setFillsViewportHeight(true);
		table.setSurrendersFocusOnKeystroke(true);
		table.setShowVerticalLines(false);
		table.setRowHeight(30);
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));		

		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBackground(Color.WHITE);

		lblEstimatedTimeupTo = new JLabel("Estimated Time:Up to 1:30 minutes");
		lblEstimatedTimeupTo.setBounds(418, 18, 232, 14);
		clustering.add(lblEstimatedTimeupTo);

		lblNewLabel = new JLabel("Estimated Time:Up to 2 minutes");
		lblNewLabel.setBounds(240, 59, 223, 14);
		add(lblNewLabel);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(500);


		prepareClustering_btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prepareClustering_handler();
			}
		});

	}

	protected void create_Clusters_CW_global() {

		String path=MainApp.getWS()+File.separator+"clustering";
		Clusters c=new Clusters(path);
		HashMap<Integer, String> thresholds=new HashMap<>();
		thresholds.put(0, "1996");
		thresholds.put(1, "16");
		thresholds.put(2, "mistreat");
		thresholds.put(3, "protective");
		thresholds.put(4, "barricade");
		thresholds.put(5, "dec");

		c.findC_global_fv(path+File.separator+"Clusters Global FVs", MainApp.getWS()+File.separator+"Frequency Vectors"+File.separator+"final FVs");
		c.find_CW_Sig(path+File.separator+"Clusters CW_Sig FVs", path+File.separator+"Clusters Global FVs", thresholds);

		done=true;
		MainApp.changeNext("tab3", "Update DataBase");
	}

	private void prepareClustering_handler() {
		done=false;
		MainApp.changeNext("tab3", "Update DataBase");
		btnClustering.setEnabled(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		Proccessing proc=new Proccessing();
		ArrayList<String> fv_paths=new ArrayList<>();
		ArrayList<String> fv_names=new ArrayList<>();
		File expandedfvs=new File(MainApp.getWS()+File.separator+"Frequency Vectors"+File.separator+"expanded FVs");
		eXml_files=expandedfvs.listFiles();
		for(File file:eXml_files){
			if((!file.getName().equals("global.xml")) && (!file.getName().equals("common.xml"))){
				fv_paths.add(file.getAbsolutePath());
				fv_names.add(file.getName());
			}
		}

		proc.sortFV_BY_Key_Export(fv_paths,fv_names,MainApp.getWS()+File.separator+"Frequency Vectors"+File.separator+"final FVs");
		setCursor(null);
		btnClustering.setEnabled(true);
	}

	public static boolean isDone() {
		return done;
	}
}
