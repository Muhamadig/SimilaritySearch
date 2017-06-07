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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.Proccessing;
import model.FVValueSorted;
import view.ui.utils.Browse;

public class Tab2 extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField files_text2;
	private JTextField textField_expandedFVsPath;
	private JTextField threshold_word_txt;
	private File[] xml_files;
	private String files2_dir;
	private String expanded_dir;
	private FVValueSorted common;
	private ArrayList<String> fv_paths;
	private FVValueSorted global;
	private JButton selectSimpleFVs_btn2;
	private JButton Proccess2_btn2;
	private JTextArea txtrStepCreating;
	private JLabel upload_info2;
	private JButton selectPathToSaveExpandedVectors_btn2;
	private JLabel upload_info2_2;
	private JLabel thresholdWord_lbl;
	private JButton check_threshold_btn2;
	private JTextArea text_area;
	private final String default_threshold="run a risk";


	public Tab2() {
		setLayout(null);
		setPreferredSize(new Dimension(700, 500));

		files_text2 = new JTextField();
		files_text2.setBounds(10, 151, 424, 20);
		add(files_text2);
		files_text2.setColumns(10);

		selectSimpleFVs_btn2 = new JButton("Import Initial Frequency Vectors");

		selectSimpleFVs_btn2.setBounds(444, 150, 223, 23);
		add(selectSimpleFVs_btn2);

		Proccess2_btn2 = new JButton("Create Global FV");

		Proccess2_btn2.setBounds(444, 218, 223, 23);
		Proccess2_btn2.setEnabled(false);

		add(Proccess2_btn2);

		txtrStepCreating = new JTextArea();
		txtrStepCreating.setBackground(SystemColor.info);
		txtrStepCreating.setTabSize(2);
		txtrStepCreating.setLineWrap(true);
		txtrStepCreating.setWrapStyleWord(true);
		txtrStepCreating.setText("Step 2: Creating global Frequency Vector which have all the words and frequencies from all the texts, Then expand each text frequency vector to be all the same length then find and remove the common word from each expanded vector.\r\n\r\nInput: Read all frequency vectors from an folder (XML Files).\r\nOutput:\r\n\t\t1.global.XML File which have the global frequency vector.\r\n\t\t2.common.XML which have all the common words.\r\n\t\t3.Expanded vectors after removing common words.");
		txtrStepCreating.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtrStepCreating.setEditable(false);
		txtrStepCreating.setBounds(0, 0, 695, 140);
		add(txtrStepCreating);

		upload_info2 = new JLabel("");
		upload_info2.setBounds(10, 171, 424, 14);
		add(upload_info2);

		textField_expandedFVsPath = new JTextField();
		textField_expandedFVsPath.setColumns(10);
		textField_expandedFVsPath.setBounds(10, 184, 424, 20);
		add(textField_expandedFVsPath);

		selectPathToSaveExpandedVectors_btn2 = new JButton("Select Directory To Save Expanded Vectors");


		selectPathToSaveExpandedVectors_btn2.setBounds(444, 184, 223, 23);
		add(selectPathToSaveExpandedVectors_btn2);

		selectPathToSaveExpandedVectors_btn2.setEnabled(false);

		upload_info2_2 = new JLabel("");
		upload_info2_2.setBounds(10, 205, 424, 14);
		add(upload_info2_2);

		thresholdWord_lbl = new JLabel("Enter The Treshold Word");
		thresholdWord_lbl.setBounds(10, 256, 162, 14);
		add(thresholdWord_lbl);

		threshold_word_txt = new JTextField();
		threshold_word_txt.setBounds(167, 253, 260, 20);
		add(threshold_word_txt);
		threshold_word_txt.setColumns(10);

		check_threshold_btn2 = new JButton("Process");

		check_threshold_btn2.setBounds(444, 252, 223, 23);
		add(check_threshold_btn2);
		check_threshold_btn2.setEnabled(false);

		text_area = new JTextArea();
		text_area.setBackground(SystemColor.inactiveCaption);
		text_area.setEditable(false);
		text_area.setBounds(10, 281, 272, 169);
		add(text_area);
		
		threshold_word_txt.setText(default_threshold);

		//import simple FVs
		selectSimpleFVs_btn2.addActionListener(new ActionListener() {//Ready
			public void actionPerformed(ActionEvent arg0) {

				import_FvsHandler();
				
			}
		});

		//Process 2
		Proccess2_btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				create_global_handler(xml_files);
			}
		});

		selectPathToSaveExpandedVectors_btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectPath_export_handler();
			}
		});
		check_threshold_btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				expand_vectors_handler();
			}
		});


	}


	protected void selectPath_export_handler() {
		expanded_dir=Browse.selectDirectory(Tab2.this);
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


	protected void expand_vectors_handler() {
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


	protected void import_FvsHandler() {
		selectPathToSaveExpandedVectors_btn2.setEnabled(false);
		Proccess2_btn2.setEnabled(false);

		ArrayList<String> types=new ArrayList<>();
		types.add("xml");
		xml_files=Browse.BrowseFiles(types);
		if(xml_files.length>0)files_text2.setText(xml_files[0].getParentFile().toString());

		int files_size=xml_files.length;
		if(files_size==0){
			upload_info2.setText("Warning :No files was uploaded");
			selectPathToSaveExpandedVectors_btn2.setEnabled(false);
			Proccess2_btn2.setEnabled(false);


		}else if(files_size>0){
			upload_info2.setText("Done :Number of uploaded files: "+files_size );
			files2_dir=xml_files[0].getParentFile().toString();
			selectPathToSaveExpandedVectors_btn2.setEnabled(true);


		}
	}


	private void create_global_handler(File[] xml_files2) {
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
		check_threshold_btn2.setEnabled(true);


	}
}
