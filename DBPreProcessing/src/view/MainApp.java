package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import com.sun.tools.xjc.gen.Array;

import controller.Proccessing;
import model.FVHashMap;
import model.FVValueSorted;
import model.LangFactory;
import model.Language;
import model.Text;

import javax.swing.JInternalFrame;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;

public class MainApp extends JFrame {
	private JTextField files_text;
	private String lastPathFiles;
	private String lastPathDir;
	private JTable FVs_table;
	private File[] files;
	private JTextField directory_txt;
	private boolean files_selected;
	private boolean dir_selected;
	private String directory;
	private String sortedDirectory;
	private JTextField files_text2;
	private File[] xml_files;
	private JTextField textField_import;
	private JTextField textField_export;
	private File[] eXml_files2;
	private boolean import_dir;
	private boolean export_dir;
	private JTextField textField_expandedFVsPath;
	private JTextField threshold_word_txt;
	private String expanded_dir;
	private String files2_dir;
	private boolean files2_selected;
	private final String default_threshold="run a risk";
	private String threshold;
	private FVValueSorted global;
	private FVValueSorted common;
	private ArrayList<String> fv_paths;
	
	public MainApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("PreProccessing");

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tabbedPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.setBackground(Color.WHITE);
		getContentPane().add(tabbedPane, BorderLayout.WEST);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(700, 500));
		//		panel.setBounds(tabWidth, 0, this.getWidth()-tabWidth, 400);
		tabbedPane.addTab("Process All Texts", null, panel,"Build Frequency Vectors for All Texts");
		panel.setLayout(null);
		lastPathFiles=".";
		lastPathDir=".";

		files_text = new JTextField();
		files_text.setEditable(false);
		files_text.setBackground(Color.WHITE);
		files_text.setBounds(10, 38, 282, 20);
		panel.add(files_text);
		files_text.setColumns(10);
		JButton browseHtmlFiles_btn1 = new JButton("Browse Files");


		browseHtmlFiles_btn1.setBounds(296, 37, 114, 23);
		panel.add(browseHtmlFiles_btn1);

		JLabel upload_info = new JLabel("");
		upload_info.setFont(new Font("Tahoma", Font.PLAIN, 14));
		upload_info.setBounds(10, 69, 282, 14);
		panel.add(upload_info);

		JButton processing1_btn1 = new JButton("Begin Texts Processing");


		processing1_btn1.setBounds(253, 142, 174, 23);
		processing1_btn1.setEnabled(false);
		panel.add(processing1_btn1);

		JScrollPane fvs_scrl = new JScrollPane();
		fvs_scrl.setBounds(10, 172, 680, 278);
		panel.add(fvs_scrl);

		String[] columnNames = {"#", "Text Name", "# of Rep. Words", "# of frequencies","# stop words" };
		Object[][] texts_tabel = {};
		FVs_table = new JTable();
		FVs_table.setModel(new MyTableModel(columnNames, texts_tabel));
		FVs_table.setFillsViewportHeight(true);
		FVs_table.setSurrendersFocusOnKeystroke(true);
		FVs_table.setShowVerticalLines(false);
		FVs_table.setRowHeight(30);
		FVs_table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		fvs_scrl.setViewportView(FVs_table);
		FVs_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		FVs_table.setBackground(Color.WHITE);

		JComboBox langbox = new JComboBox();
		langbox.setModel(new DefaultComboBoxModel(new String[] {"English"}));
		langbox.setBounds(106, 109, 70, 20);
		panel.add(langbox);

		JLabel lblTextsLanguage = new JLabel("Texts Language:");
		lblTextsLanguage.setBounds(10, 112, 100, 14);
		panel.add(lblTextsLanguage);

		JButton btnSelectDirectoryForSimpleFVs_btn1 = new JButton("Select Directory");

		btnSelectDirectoryForSimpleFVs_btn1.setBounds(539, 108, 146, 23);
		panel.add(btnSelectDirectoryForSimpleFVs_btn1);

		directory_txt = new JTextField();
		directory_txt.setBackground(Color.WHITE);
		directory_txt.setEditable(false);
		directory_txt.setText("Save XML Files Into...");
		directory_txt.setBounds(186, 109, 343, 20);
		panel.add(directory_txt);
		directory_txt.setColumns(10);
		FVs_table.getColumnModel().getColumn(0).setPreferredWidth(30);
		FVs_table.getColumnModel().getColumn(1).setPreferredWidth(380);
		FVs_table.getColumnModel().getColumn(2).setPreferredWidth(90);
		FVs_table.getColumnModel().getColumn(3).setPreferredWidth(90);
		FVs_table.getColumnModel().getColumn(4).setPreferredWidth(90);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Expand All Frequency Vectors", null, panel_1, "Expand All Frequency Vectors");
		panel_1.setLayout(null);

		files_text2 = new JTextField();
		files_text2.setBounds(10, 151, 424, 20);
		panel_1.add(files_text2);
		files_text2.setColumns(10);

		JButton selectSimpleFVs_btn2 = new JButton("Import Initial Frequency Vectors");

		selectSimpleFVs_btn2.setBounds(444, 150, 223, 23);
		panel_1.add(selectSimpleFVs_btn2);

		JButton Proccess2_btn2 = new JButton("Create Global FV");

		Proccess2_btn2.setBounds(444, 218, 223, 23);
		Proccess2_btn2.setEnabled(false);

		panel_1.add(Proccess2_btn2);

		JTextArea txtrStepCreating = new JTextArea();
		txtrStepCreating.setBackground(SystemColor.info);
		txtrStepCreating.setTabSize(2);
		txtrStepCreating.setLineWrap(true);
		txtrStepCreating.setWrapStyleWord(true);
		txtrStepCreating.setText("Step 2: Creating global Frequency Vector which have all the words and frequencies from all the texts, Then expand each text frequency vector to be all the same length then find and remove the common word from each expanded vector.\r\n\r\nInput: Read all frequency vectors from an folder (XML Files).\r\nOutput:\r\n\t\t1.global.XML File which have the global frequency vector.\r\n\t\t2.common.XML which have all the common words.\r\n\t\t3.Expanded vectors after removing common words.");
		txtrStepCreating.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtrStepCreating.setEditable(false);
		txtrStepCreating.setBounds(0, 0, 695, 140);
		panel_1.add(txtrStepCreating);

		JLabel upload_info2 = new JLabel("");
		upload_info2.setBounds(10, 171, 424, 14);
		panel_1.add(upload_info2);

		textField_expandedFVsPath = new JTextField();
		textField_expandedFVsPath.setColumns(10);
		textField_expandedFVsPath.setBounds(10, 184, 424, 20);
		panel_1.add(textField_expandedFVsPath);

		JButton selectPathToSaveExpandedVectors_btn2 = new JButton("Select Directory To Save Expanded Vectors");

		selectPathToSaveExpandedVectors_btn2.setBounds(444, 184, 223, 23);
		panel_1.add(selectPathToSaveExpandedVectors_btn2);

		JLabel upload_info2_2 = new JLabel("");
		upload_info2_2.setBounds(10, 205, 424, 14);
		panel_1.add(upload_info2_2);

		JLabel thresholdWord_lbl = new JLabel("Enter The Treshold Word");
		thresholdWord_lbl.setBounds(10, 256, 162, 14);
		panel_1.add(thresholdWord_lbl);

		threshold_word_txt = new JTextField();
		threshold_word_txt.setBounds(167, 253, 260, 20);
		panel_1.add(threshold_word_txt);
		threshold_word_txt.setColumns(10);

		JButton check_threshold_btn2 = new JButton("Process");
		
		check_threshold_btn2.setBounds(444, 252, 223, 23);
		panel_1.add(check_threshold_btn2);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Clustering Preparation", null, panel_2,"Create a sorted FVs by keys");
		panel_2.setLayout(null);

		textField_import = new JTextField();
		textField_import.setColumns(10);
		textField_import.setBounds(10, 64, 424, 20);
		panel_2.add(textField_import);

		JButton btnSelectExpanded_btn3 = new JButton("Select Expanded Vectors");

		btnSelectExpanded_btn3.setBounds(444, 63, 223, 23);
		panel_2.add(btnSelectExpanded_btn3);

		JLabel label1 = new JLabel("");
		label1.setBounds(10, 95, 424, 14);
		panel_2.add(label1);

		textField_export = new JTextField();
		textField_export.setColumns(10);
		textField_export.setBounds(10, 120, 424, 20);
		panel_2.add(textField_export);

		JButton btnSelectExportDirectory_btn3 = new JButton("Select Export Directory");

		btnSelectExportDirectory_btn3.setBounds(444, 119, 223, 23);
		panel_2.add(btnSelectExportDirectory_btn3);

		JLabel label2 = new JLabel("");
		label2.setBounds(10, 151, 424, 14);
		panel_2.add(label2);

		JButton prepareClustering_btn3 = new JButton("Prepare Clustering");

		prepareClustering_btn3.setBounds(333, 174, 186, 23);
		prepareClustering_btn3.setEnabled(false);
		panel_2.add(prepareClustering_btn3);

		JTextArea Message = new JTextArea("");
		Message.setWrapStyleWord(true);
		Message.setLineWrap(true);
		Message.setEditable(false);
		Message.setBackground(SystemColor.info);
		Message.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Message.setBounds(10, 216, 675, 42);
		Message.setVisible(false);
		panel_2.add(Message);


		setBounds(0, 0, 1000, 500);
		this.setLocationRelativeTo(null);

		import_dir=false;
		export_dir=false;

		files2_selected=false;
		selectPathToSaveExpandedVectors_btn2.setEnabled(false);
		check_threshold_btn2.setEnabled(false);

		
		threshold_word_txt.setText(default_threshold);
		
		JTextArea text_area = new JTextArea();
		text_area.setBackground(SystemColor.inactiveCaption);
		text_area.setEditable(false);
		text_area.setBounds(10, 281, 272, 169);
		panel_1.add(text_area);
		/*-----------------------------------------------------------------------------------
		 * Handlers For Tab 1
		 */
		//Browse the html - Texts Files and select thim. 
		browseHtmlFiles_btn1.addActionListener(new ActionListener() {//Ready
			public void actionPerformed(ActionEvent e) {
				processing1_btn1.setEnabled(false);
				files_selected=false;

				ArrayList<String> types=new ArrayList<>();
				types.add("doc");
				types.add("docx");
				types.add("pdf");
				types.add("html");
				files=BrowseFiles(types);

				if(files.length>0)files_text.setText(lastPathFiles=files[0].getParentFile().toString());

				int files_size=files.length;
				if(files_size==0){
					upload_info.setText("Warning :No files was uploaded");
					processing1_btn1.setEnabled(false);
					files_selected=false;

				}else if(files_size>0){
					upload_info.setText("Done :Number of uploaded files: "+files_size );
					files_selected=true;

				}

			}
		});

		//Select Directory To save simple FVs 
		btnSelectDirectoryForSimpleFVs_btn1.addActionListener(new ActionListener() {//Ready

			public void actionPerformed(ActionEvent e) {
				processing1_btn1.setEnabled(false);
				dir_selected=false;
				directory=selectDirectory();
				if(directory!= null) {
					directory_txt.setText(directory);
					dir_selected=true;
					lastPathDir=directory;

				}
				else{
					directory_txt.setText("No Directory Selected");
					dir_selected=false;
				}
				if(dir_selected && files_selected) processing1_btn1.setEnabled(true);

			}

		});

		//Create simple FVs
		processing1_btn1.addActionListener(new ActionListener() {//Ready
			public void actionPerformed(ActionEvent e) {
				Proccessing proc =new Proccessing();
				Language lang=LangFactory.getLang(String.valueOf(langbox.getSelectedItem()));
				DefaultTableModel dm = (DefaultTableModel) FVs_table.getModel();
				int counter=0;
				if(lang!=null){
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

					for(File file:files){
						counter++;
						Text text=proc.process(file, directory, lang);
						dm.addRow(new Object[] {counter,text.getName(),text.getWords_num(),text.getFrequecny_num(),text.getSW_num()});
					}
					setCursor(null);

				}
			}
		});

		/*-------------------------------------------------------------------------------------
		 * Handlers For Tab 2
		 */

		//import simple FVs
		selectSimpleFVs_btn2.addActionListener(new ActionListener() {//Ready
			public void actionPerformed(ActionEvent arg0) {
				files2_selected=false;
				selectPathToSaveExpandedVectors_btn2.setEnabled(false);
				Proccess2_btn2.setEnabled(false);

				ArrayList<String> types=new ArrayList<>();
				types.add("xml");
				xml_files=BrowseFiles(types);
				if(xml_files.length>0)files_text2.setText(lastPathFiles=xml_files[0].getParentFile().toString());

				int files_size=xml_files.length;
				if(files_size==0){
					upload_info2.setText("Warning :No files was uploaded");
					files2_selected=false;
					selectPathToSaveExpandedVectors_btn2.setEnabled(false);
					Proccess2_btn2.setEnabled(false);


				}else if(files_size>0){
					upload_info2.setText("Done :Number of uploaded files: "+files_size );
					files2_dir=xml_files[0].getParentFile().toString();
					files2_selected=true;
					selectPathToSaveExpandedVectors_btn2.setEnabled(true);


				}
			}
		});

		//Process 2
		Proccess2_btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				process2(xml_files);
				check_threshold_btn2.setEnabled(true);
			}
		});

		selectPathToSaveExpandedVectors_btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				expanded_dir=selectDirectory();
				if(expanded_dir==null){
					upload_info2_2.setText("No Directory Was Selected");
					textField_expandedFVsPath.setText("");
					Proccess2_btn2.setEnabled(false);
				}

				else if(expanded_dir.equals(files2_dir)) {

					JOptionPane.showMessageDialog(null, "Can't save the expanded vectors at the same directory of the initial frequency vectors", "Error Directory", JOptionPane.ERROR_MESSAGE);
					textField_expandedFVsPath.setText("");

					Proccess2_btn2.setEnabled(false);
				}
				else{
					textField_expandedFVsPath.setText(expanded_dir);
					Proccess2_btn2.setEnabled(true);
				}

			}
		});
		check_threshold_btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

				Proccessing proc=new Proccessing();
				
				HashMap<String, Integer> result=proc.checkThresholdWord(threshold_word_txt.getText(),expanded_dir+File.separator+"results"+File.separator+"global.xml");
				if(result==null) JOptionPane.showMessageDialog(null, "Word not exist in the global vector", "Word Not Exist", JOptionPane.ERROR_MESSAGE);
				else{
					text_area.setText("Threshold word: "+threshold_word_txt.getText()+"\nword index:"+result.get("word place")+"\nword frequency:"+result.get("word FR")+
							"\n# of common words:"+result.get("num of common words")+"\nFR of common words: "+result.get("commonFR")+
							"\n# of sig words:"+result.get("num of sig words")+"\nFr of sig words:"+result.get("sigFR"));
					
					common=proc.getCommonVector(global, result.get("word place"), expanded_dir);
					
					ArrayList<String> names=new ArrayList<>();
					for(File file:xml_files){
						names.add(file.getName());
					}
					proc.expandAll(fv_paths, global, common,expanded_dir,names);
					
				}
				setCursor(null);
			}
		});
		/*----------------------------------------------------------------------------------
		 * Handlers For Tab 3
		 */

		//Prepare
		prepareClustering_btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Message.setVisible(false);
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				Proccessing proc=new Proccessing();
				ArrayList<String> fv_paths=new ArrayList<>();
				ArrayList<String> fv_names=new ArrayList<>();

				for(File file:eXml_files2){
					if((!file.getName().equals("global.xml")) && (!file.getName().equals("common.xml"))){
						fv_paths.add(file.getAbsolutePath());
						fv_names.add(file.getName());
					}
				}

				proc.sortFV_BY_Key_Export(fv_paths,fv_names,sortedDirectory);
				setCursor(null);
				Message.setText("The texts are ready for clustering , all the frequency vectors are saved as xml files and sorted by keys ,"
						+ " you can find your xml files in: "+ sortedDirectory);
				Message.setVisible(true);

			}
		});

		//Select Directory for save the Final Results
		btnSelectExportDirectory_btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prepareClustering_btn3.setEnabled(false);
				export_dir=false;
				sortedDirectory=selectDirectory();
				if(sortedDirectory!= null) {
					textField_export.setText(sortedDirectory);
					export_dir=true;
					lastPathDir=sortedDirectory;

				}
				else{
					textField_export.setText("No Directory Selected");
					export_dir=false;
				}
				if(export_dir && import_dir) prepareClustering_btn3.setEnabled(true);
			}
		});

		//import expanded FVs
		btnSelectExpanded_btn3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				import_dir=false;
				prepareClustering_btn3.setEnabled(false);
				ArrayList<String> types=new ArrayList<>();
				types.add("xml");
				eXml_files2=BrowseFiles(types);
				if(eXml_files2.length>0)textField_import.setText(lastPathFiles=eXml_files2[0].getParentFile().toString());

				int files_size=eXml_files2.length;
				if(files_size==0){
					label1.setText("Warning :No files was uploaded");
					prepareClustering_btn3.setEnabled(false);
					import_dir=false;

				}else if(files_size>0){
					label1.setText("Done :Number of uploaded files: "+files_size );
					import_dir=true;
				}
			}
		});
	}

	protected void process2(File[] xml_files2) {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		Proccessing proc=new Proccessing();
		fv_paths=new ArrayList<>();
		for(File file:xml_files2){
			if((!file.getName().equals("global.xml")) && (!file.getName().equals("common.xml"))){
				fv_paths.add(file.getAbsolutePath());
			}
		}
		//the fvs names ready

		//find global vector
		global=proc.createGlobal(fv_paths, expanded_dir);


		
//		proc.expandAll(fv_paths,global,common);
		setCursor(null);
		
	}

	protected String selectDirectory() {
		JFileChooser chooser;
		chooser = new JFileChooser(); 
		chooser.setCurrentDirectory(new java.io.File(lastPathDir));
		chooser.setDialogTitle("Select Directory");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
			return chooser.getSelectedFile().toString();
		}
		else return null;
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

	//----------------------------------------------------------------------------------------------------------


	public static void run(){
		//Schedule a job for the event dispatch thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				new MainApp().setVisible(true);
			}
		});
	}
	public static void main(String[] args) {
		//Schedule a job for the event dispatch thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				new MainApp().setVisible(true);
			}
		});
	}
}