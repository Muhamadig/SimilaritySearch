package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import Client.Client;
import Client.Config;
import Kmeans.Cluster;
import Kmeans.KMeans;
import Kmeans.Point;
import XML.XML;
import XML.XMLFactory;
import controller.Pareto;
import controller.Proccessing;
import model.FVHashMap;
import model.FVKeySortedMap;
import model.Language;
import model.Language.Langs;
import model.Text;

import javax.swing.JLabel;

public class ClientApp extends JFrame {
//	private JTextField files_text;
	private String lastPathFiles;
	private JLabel lblUploadYourFile;
	private JPanel panel;
	private JTextField files_text;
	private JLabel lblFilesTypeShould;
	private JLabel upload_info;
	private JButton proc_btn;
	private File[] files;
	private Text inputVect;
	public static Client client = null;
	private List<Cluster> clusters;
	private JTable ResTbl;
	
	public ClientApp(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Searching");
		setBounds(0, 0, 550, 500);
		 panel = new JPanel();
		panel.setPreferredSize(new Dimension(500, 500));
		panel.setLayout(null);
		getContentPane().add(panel);
		
		 lblUploadYourFile = new JLabel("Upload your file ");
		lblUploadYourFile.setBounds(22, 32, 109, 16);
		panel.add(lblUploadYourFile);
		
		files_text = new JTextField();
		files_text.setEditable(false);
		files_text.setBackground(Color.WHITE);
		files_text.setBounds(132, 30, 288, 20);
		panel.add(files_text);
		files_text.setColumns(10);
		JButton browse_btn = new JButton("Browse Files");

		lastPathFiles=".";
		browse_btn.setBounds(430, 30, 114, 23);
		browse_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proc_btn.setEnabled(false);
				
				
				ArrayList<String> types=new ArrayList<>();
				types.add("doc");
				types.add("docx");
				types.add("pdf");
				types.add("html");
				files=BrowseFiles(types);

				if(files.length>0)
					files_text.setText(lastPathFiles=files[0].getParentFile().toString());

				int files_size=files.length;
				if(files_size==0){
					upload_info.setText("Warning :No files was uploaded");
					proc_btn.setEnabled(false);
					//files_selected=false;

				}else if(files_size>0){
					upload_info.setText("Done :"+files_size );
					//files_selected=true;
					proc_btn.setEnabled(true);
				}

			}
		});

		panel.add(browse_btn);
		
		lblFilesTypeShould = new JLabel("Files type should be :  html , pdf, doc, docx");
		lblFilesTypeShould.setBounds(142, 59, 278, 16);
		lblFilesTypeShould.setForeground(Color.RED);
		panel.add(lblFilesTypeShould);
		
		upload_info = new JLabel("");
		upload_info.setBounds(22, 87, 282, 14);
		panel.add(upload_info);


		proc_btn = new JButton("Begin Searching Process");
		proc_btn.setBounds(160, 121, 215, 23);
		proc_btn.setEnabled(false);
		panel.add(proc_btn);
		proc_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calculate();
					setCursor(null);
			}
		});
		
		ResTbl = new JTable();
		String[] columnNames = {"#", "Text Name"};
		Object[][] texts_tabel = {};
		ResTbl.setModel(new MyTableModel(columnNames, texts_tabel));
		ResTbl.setFillsViewportHeight(true);
		ResTbl.setSurrendersFocusOnKeystroke(true);
		ResTbl.setShowVerticalLines(false);
		ResTbl.setRowHeight(30);
		ResTbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(ResTbl);
		
	}
	
	private void FillTbl(ArrayList<String> files){
		DefaultTableModel dm = (DefaultTableModel) ResTbl.getModel();
		for(int i=0;i<files.size();i++)
		dm.addRow(new Object[] {i+1, files.get(i)});
	}
	
	
	protected File[] BrowseFiles(ArrayList<String> types) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File(lastPathFiles));
		chooser.setDialogTitle("Browse the folder to process");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		chooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				String res="";
				for(String type:types){
					res+=",*."+type;
				}
				return res;
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					String filename = f.getName();
					boolean res=false;
					for(String type:types){
						res=res||filename.endsWith("."+type);
					}
					return res;
				}			
			}
		});
		chooser.setMultiSelectionEnabled(true);
		chooser.showOpenDialog(null);

		File[] files = chooser.getSelectedFiles();

		return files;
	}
	
	public Text getInputVector(){
		return this.inputVect;}
	
	public static void connect() {
		Config cfg = Config.getConfig();
		if (client != null) {
			client.close();
			client = null;
		}
		client = new Client(cfg.getHost(), cfg.getPort());
		Config.getConfig().writeTextConfig();
		client.open();
	}
	
	private void Calculate(){
		
    	ArrayList<ArrayList<Double>> freqs = KMeans.getAllFrequencies();
    	KMeans km = new KMeans();
    	km.init();
    	km.SetPoints(freqs);
    	km.Clustering();
    	FVHashMap text = inputVect.getFv();
    	
    	// Remove all the database Common Words
    	text = Remove(text,Cluster.DBCommonWords);
    	
    	this.clusters = km.getclusters();
    	int cluster = getCluster();
    	
    	//Get the specific cluster Common words
    	FVHashMap ClusterCW = clusters.get(cluster).getCommonWords();
    	
    	ArrayList<String> CW= new ArrayList<String>();
    	for(String key : ClusterCW.keySet())
    		CW.add(key);
    	
    	// Remove the Cluster Common Words
    	text = Remove(text , CW);
    	
    	FVKeySortedMap sortedText = new FVKeySortedMap(text);
    	ArrayList<FVKeySortedMap> candidates = new ArrayList<FVKeySortedMap>();
    	XML fvxml = XMLFactory.getXML(XMLFactory.FVSortedMap);
    	List<Point> Cpoints = clusters.get(cluster).getPoints();
    	HashMap<String, FVKeySortedMap> ClusterText = new HashMap<String, FVKeySortedMap>();
    	for(Point p : Cpoints){
    		FVKeySortedMap toadd = (FVKeySortedMap) fvxml.Import("SortedFVs" +System.lineSeparator()+p.getName());
    		candidates.add(toadd);
    		ClusterText.put(p.getName(), toadd);
    	}
    	
    	Pareto pat = new Pareto(candidates , sortedText);
    	ArrayList<FVKeySortedMap> results = pat.ParetoCalculate();
    	ArrayList<String> filesnames = new ArrayList<String>();
    	for(FVKeySortedMap file : results)
    		filesnames.add(getKey(ClusterText,file));
    	FillTbl(filesnames);
    	CopyFiles(filesnames);
    	
	}

	private void CopyFiles(ArrayList<String> fnames){ 
		// Creating results folder on user's Desktop
		DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH,mm,ss");
		Date date = new Date();
		String path = System.getProperty("user.home") + "/Desktop/Results/"+dateFormat.format(date);
		File file = new File (path);
		file.mkdirs();
		
		
		List<File> files = new ArrayList<File>();
		for(String name : fnames)
			files.add(new File("SortedFVs"+System.lineSeparator() + name));
		for(File f : files)
			try {
				Files.copy(f.toPath(),
					        (new File(path +"/"+ f.getName())).toPath(),
					        StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	private String getKey(HashMap<String, FVKeySortedMap> texts ,FVKeySortedMap file){
		for(String key : texts.keySet())
			if(texts.get(key).equals(file))
				return key;
		return null;
	}
	
	private int getCluster(){
		int cluster =-1;
		double min =Double.MAX_VALUE;
		double dist;
		for(Cluster c: clusters){
			dist = c.CommonDistnce(inputVect.getFv());
			if(dist<min){
				min=dist;
				cluster=c.getId();
			}
		}
		return cluster;
	}
	
	private FVHashMap Remove(FVHashMap original , ArrayList<String> DBCW){
		for(String CW : DBCW)
			original.remove(CW);
		return original;
	}
	
	public static void main(String[] args) {
		Config.getConfig().readTextConfig();
		connect();
		ClientApp temp = new ClientApp();
		temp.setVisible(true);
	}
}
