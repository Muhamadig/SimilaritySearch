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
	private JCheckBox chckbx_read;
	private JCheckBox chckbx_createGlobal;
	private JCheckBox chckbxExpand;
	private JTextField textField_import;
	private JTextField textField_export;
	private File[] eXml_files2;
	private boolean import_dir;
	private boolean export_dir;

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
		JButton browse_btn = new JButton("Browse Files");


		browse_btn.setBounds(296, 37, 114, 23);
		panel.add(browse_btn);

		JLabel upload_info = new JLabel("");
		upload_info.setFont(new Font("Tahoma", Font.PLAIN, 14));
		upload_info.setBounds(10, 69, 282, 14);
		panel.add(upload_info);

		JButton proc_btn = new JButton("Begin Texts Processing");


		proc_btn.setBounds(253, 142, 174, 23);
		proc_btn.setEnabled(false);
		panel.add(proc_btn);

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

		JButton btnSelectDirectory = new JButton("Select Directory");

		btnSelectDirectory.setBounds(539, 108, 146, 23);
		panel.add(btnSelectDirectory);

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
		files_text2.setBounds(10, 173, 424, 20);
		panel_1.add(files_text2);
		files_text2.setColumns(10);
		
		JButton browse_xmlFolder = new JButton("Select Xml Files");
		
		browse_xmlFolder.setBounds(444, 172, 223, 23);
		panel_1.add(browse_xmlFolder);
		
		JButton Proccess2_btn = new JButton("Process");
		
		Proccess2_btn.setBounds(211, 224, 223, 23);
		Proccess2_btn.setEnabled(false);

		panel_1.add(Proccess2_btn);
		
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
		
		chckbx_read = new JCheckBox("Read Files");
		chckbx_read.setForeground(SystemColor.desktop);
		chckbx_read.setBounds(10, 270, 300, 23);
		panel_1.add(chckbx_read);
		
		chckbx_createGlobal = new JCheckBox("Create Global Frequency Vector");
		chckbx_createGlobal.setForeground(SystemColor.desktop);
		chckbx_createGlobal.setBounds(10, 296, 300, 23);
		panel_1.add(chckbx_createGlobal);
		
		chckbxExpand = new JCheckBox("Expand All Vectors");
		chckbxExpand.setForeground(SystemColor.desktop);
		chckbxExpand.setBounds(10, 322, 300, 23);
		panel_1.add(chckbxExpand);
		
		JLabel upload_info2 = new JLabel("");
		upload_info2.setBounds(10, 199, 424, 14);
		panel_1.add(upload_info2);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Clustering Preparation", null, panel_2,"Create a sorted FVs by keys");
		panel_2.setLayout(null);
		
		textField_import = new JTextField();
		textField_import.setColumns(10);
		textField_import.setBounds(10, 64, 424, 20);
		panel_2.add(textField_import);
		
		JButton btnSelectExpanded = new JButton("Select Expanded Vectors");
		
		btnSelectExpanded.setBounds(444, 63, 223, 23);
		panel_2.add(btnSelectExpanded);
		
		JLabel label1 = new JLabel("");
		label1.setBounds(10, 95, 424, 14);
		panel_2.add(label1);
		
		textField_export = new JTextField();
		textField_export.setColumns(10);
		textField_export.setBounds(10, 120, 424, 20);
		panel_2.add(textField_export);
		
		JButton btnSelectExportDirectory = new JButton("Select Export Directory");
		
		btnSelectExportDirectory.setBounds(444, 119, 223, 23);
		panel_2.add(btnSelectExportDirectory);
		
		JLabel label2 = new JLabel("");
		label2.setBounds(10, 151, 424, 14);
		panel_2.add(label2);
		
		JButton btnPrepare = new JButton("Prepare Clustering");
		
		btnPrepare.setBounds(333, 174, 186, 23);
		btnPrepare.setEnabled(false);
		panel_2.add(btnPrepare);
		
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
		
		btnPrepare.addActionListener(new ActionListener() {
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
				
				proc.sortFV(fv_paths,fv_names,sortedDirectory);
				setCursor(null);
				Message.setText("The texts are ready for clustering , all the frequency vectors are saved as xml files and sorted by keys ,"
						+ " you can find your xml files in: "+ sortedDirectory);
				Message.setVisible(true);

			}
		});
		
		btnSelectExportDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPrepare.setEnabled(false);
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
				if(export_dir && import_dir) btnPrepare.setEnabled(true);
			}
		});
		browse_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proc_btn.setEnabled(false);
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
					proc_btn.setEnabled(false);
					files_selected=false;

				}else if(files_size>0){
					upload_info.setText("Done :Number of uploaded files: "+files_size );
					files_selected=true;

				}

			}
		});

		btnSelectDirectory.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				proc_btn.setEnabled(false);
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
				if(dir_selected && files_selected) proc_btn.setEnabled(true);

			}

		});
		proc_btn.addActionListener(new ActionListener() {
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
		browse_xmlFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Proccess2_btn.setEnabled(false);
				chckbx_read.setSelected(false);
				chckbx_createGlobal.setSelected(false);
				chckbxExpand.setSelected(false);

				ArrayList<String> types=new ArrayList<>();
				types.add("xml");
				xml_files=BrowseFiles(types);
				if(xml_files.length>0)files_text2.setText(lastPathFiles=xml_files[0].getParentFile().toString());

				int files_size=xml_files.length;
				if(files_size==0){
					upload_info2.setText("Warning :No files was uploaded");
					Proccess2_btn.setEnabled(false);
					files_selected=false;

				}else if(files_size>0){
					upload_info2.setText("Done :Number of uploaded files: "+files_size );
					Proccess2_btn.setEnabled(true);
					

				}
			}
		});
		
		Proccess2_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				process2(xml_files);
			}
		});
		btnSelectExpanded.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				import_dir=false;
				btnPrepare.setEnabled(false);
				ArrayList<String> types=new ArrayList<>();
				types.add("xml");
				eXml_files2=BrowseFiles(types);
				if(eXml_files2.length>0)textField_import.setText(lastPathFiles=eXml_files2[0].getParentFile().toString());

				int files_size=eXml_files2.length;
				if(files_size==0){
					label1.setText("Warning :No files was uploaded");
					btnPrepare.setEnabled(false);
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
		ArrayList<String> fv_paths=new ArrayList<>();
		for(File file:xml_files2){
			if((!file.getName().equals("global.xml")) && (!file.getName().equals("common.xml"))){
				fv_paths.add(file.getAbsolutePath());
			}
		}
		chckbx_read.setSelected(true);
		//the fvs names ready
		
		//find global vector
		FVHashMap global=proc.createGlobal(fv_paths, xml_files2[0].getParent());
		chckbx_createGlobal.setSelected(true);
		//find common words vector
		FVHashMap common=proc.getCommonVector(fv_paths, xml_files2[0].getParent(), fv_paths.size(), xml_files2[0].getParent()+File.separator+"global.xml");
		
		proc.expandAll(fv_paths,global,common);
		chckbxExpand.setSelected(true);
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