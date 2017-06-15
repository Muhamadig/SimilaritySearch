package view;

import java.awt.Color;
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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import Client.Client;
import Client.WNSConfig;
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
import view.ui.utils.MyTableModel;
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
	private FVHashMap inputVect;
	public static Client client = null;
	private List<Cluster> clusters;
	private JTable ResTbl;
	private JLabel Resultslbl;
	public ClientApp(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Searching");
		setBounds(0, 0, 550, 420);
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
				
				Proccessing proc =new Proccessing();
				Text input = proc.process(files[0], files[0].getParentFile().toString(), new Language(Langs.ENGLISH));
				inputVect = input.getFv();
				XML fvxml = XMLFactory.getXML(XMLFactory.FV);
				FVHashMap global = (FVHashMap) fvxml.Import("Expanded/results/global.xml");
				inputVect = Expand(inputVect , global);
				fvxml.export(inputVect, "after.xml");
				Calculate();
				setCursor(null);
			}
		});
		
		ResTbl = new JTable();
		ResTbl.setVisible(false);
		String[] columnNames = {"#", "Text Name"};
		Object[][] texts_tabel = {};
		ResTbl.setBounds(43, 181, 448, 170);
		ResTbl.setModel(new MyTableModel(columnNames, texts_tabel));
		ResTbl.setFillsViewportHeight(true);
		ResTbl.setSurrendersFocusOnKeystroke(true);
		ResTbl.setShowVerticalLines(false);
		ResTbl.setRowHeight(30);
		ResTbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ResTbl.getColumnModel().getColumn(0).setPreferredWidth(30);
		ResTbl.getColumnModel().getColumn(1).setPreferredWidth(400);
		panel.add(ResTbl);
		
		 Resultslbl = new JLabel("The result's files are in Results folder on your Desktop");
		Resultslbl.setBounds(41, 363, 450, 16);
		Resultslbl.setVisible(false);
		Resultslbl.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		panel.add(Resultslbl);
	}
	
	private void FillTbl(ArrayList<String> files){
		DefaultTableModel dm = (DefaultTableModel) ResTbl.getModel();
		for(int i=0;i<files.size();i++)
			dm.addRow(new Object[] {i+1, files.get(i).replace(".html.xml", "")});
		Resultslbl.setVisible(true);
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
	
	public FVHashMap getInputVector(){
		return this.inputVect;}
	
	public static void connect() {
		WNSConfig cfg = WNSConfig.getConfig();
		if (client != null) {
			client.close();
			client = null;
		}
		client = new Client(cfg.getHost(), cfg.getPort());
//		WNSConfig.getConfig().writeTextConfig();
		client.open();
	}
	
	private void Calculate(){
		System.out.println("Start Searching ...");
		long t = System.currentTimeMillis();
    	ArrayList<ArrayList<Double>> freqs = KMeans.getAllFrequencies();
    	KMeans km = new KMeans();
    	km.init();
    	km.SetPoints(freqs);
    	km.Clustering();
    	//FVHashMap text = inputVect.getFv();
    	FVHashMap copyinput = (FVHashMap) inputVect.clone();
    	// Remove all the database Common Words
    	copyinput = Remove(inputVect,Cluster.DBCommonWords);
    	
    	this.clusters = km.getclusters();
    	int cluster = getCluster();
    	System.out.println("Cluster number is: "+cluster);
    	//Get the specific cluster Common words
    	
    	ArrayList<String> CW= clusters.get(cluster).GetClusterCW();
    	
    	// Remove the Cluster Common Words
    	copyinput = Remove(copyinput , CW);
    	
    	FVKeySortedMap sortedText = new FVKeySortedMap(copyinput);
    	ArrayList<FVKeySortedMap> candidates = new ArrayList<FVKeySortedMap>();
    	XML fvxml = XMLFactory.getXML(XMLFactory.FVSortedMap);
    	List<Point> Cpoints = clusters.get(cluster).getPoints();
    	HashMap<String, FVKeySortedMap> ClusterText = new HashMap<String, FVKeySortedMap>();
    	for(Point p : Cpoints){
    		FVKeySortedMap toadd = (FVKeySortedMap) fvxml.Import("final/"+p.getName());
    		candidates.add(toadd);
    		ClusterText.put(p.getName(), toadd);
    	}
    	Pareto pat = new Pareto(candidates , sortedText);
    	ArrayList<FVKeySortedMap> results = pat.ParetoCalculate();
    	ArrayList<String> filesnames = new ArrayList<String>();
    	for(FVKeySortedMap file : results)
    		filesnames.add(getKey(ClusterText,file));
    	ResTbl.setVisible(true);
    	FillTbl(filesnames);
    	CopyFiles(filesnames);
    	System.out.println("Done ... " +((System.currentTimeMillis() - t)/1000) + " Seconds");
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
			files.add(new File("HTMLs/"+ name.replace(".xml", "")));
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
			if(texts.get(key).equals(file)){
				System.out.println(key);
				return key;
			}
				
		return null;
	}
	
	private int getCluster(){
		int cluster =-1;
		double min =Double.MAX_VALUE;
		double dist;
		for(Cluster c: clusters){
			dist = c.CommonDistnce(inputVect);
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
	
	private FVHashMap Expand(FVHashMap vec , FVHashMap global){
		for(String key : global.keySet())
			if(!vec.containsKey(key))
				vec.put(key, 0);
		return vec;
	}
	
	public static void run(){
		//Schedule a job for the event dispatch thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				new ClientApp().setVisible(true);
			}
		});
	}
}
