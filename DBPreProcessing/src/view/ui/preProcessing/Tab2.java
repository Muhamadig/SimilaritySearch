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

import controller.DBController;
import controller.Proccessing;
import model.FVValueSorted;

import java.awt.Color;
import javax.swing.SwingConstants;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class Tab2 extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField threshold_word_txt;
	private File[] xml_files;
	private String expanded_dir;
	private FVValueSorted common;
	private ArrayList<String> fv_paths;
	private FVValueSorted global;
	private JButton Proccess2_btn2;
	private JTextArea txtrStepCreating;
	private JLabel thresholdWord_lbl;
	private JButton check_threshold_btn2;
	private final String default_threshold="run a risk";
	private JPanel panel;
	private JLabel lblNewLabel;
	private JLabel lblThresholdWordIndex;
	private JLabel lblThresholdWordFrequency;
	private JLabel lblNumberOfCommon;
	private JLabel lblSumFrequencyOf;
	private JLabel lblNumberOfSignificant;
	private JLabel lblSumFrequenciesOf;
	private JTextField res1;
	private JTextField res2;
	private JTextField res3;
	private JTextField res5;
	private JTextField res4;
	private JTextField res6;
	private JTextField res7;
	private boolean done=false;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;

	public boolean isDone() {
		return done;
	}


	public Tab2() {
		setBackground(Color.WHITE);
		setLayout(null);
		setPreferredSize(new Dimension(700, 480));

		Proccess2_btn2 = new JButton("Create Global FV");
		Proccess2_btn2.setBackground(SystemColor.inactiveCaption);
		
		Proccess2_btn2.setBounds(10, 149, 284, 23);
		Proccess2_btn2.setEnabled(true);

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

		thresholdWord_lbl = new JLabel("Enter The Treshold Word");
		thresholdWord_lbl.setBounds(10, 197, 162, 14);
		add(thresholdWord_lbl);

		threshold_word_txt = new JTextField();
		threshold_word_txt.setBounds(193, 194, 193, 20);
		add(threshold_word_txt);
		threshold_word_txt.setColumns(10);

		check_threshold_btn2 = new JButton("Process");
		check_threshold_btn2.setBackground(SystemColor.inactiveCaption);

		check_threshold_btn2.setBounds(411, 193, 284, 23);
		add(check_threshold_btn2);
		check_threshold_btn2.setEnabled(false);

		threshold_word_txt.setText(default_threshold);

		panel = new JPanel();
		panel.setBounds(10, 235, 477, 192);
		add(panel);

		lblNewLabel = new JLabel("Threshold Word:");

		lblThresholdWordIndex = new JLabel("Threshold Word Index:");

		lblThresholdWordFrequency = new JLabel("Threshold Word Frequency:");

		lblNumberOfCommon = new JLabel("Number of common words:");

		lblSumFrequencyOf = new JLabel("Sum Frequencies of common words:");

		lblNumberOfSignificant = new JLabel("Number of Significant words:");

		lblSumFrequenciesOf = new JLabel("Sum Frequencies of Significant words:");

		res1 = new JTextField();
		res1.setHorizontalAlignment(SwingConstants.LEFT);
		res1.setEditable(false);
		res1.setColumns(10);

		res2 = new JTextField();
		res2.setHorizontalAlignment(SwingConstants.CENTER);
		res2.setEditable(false);
		res2.setColumns(10);

		res3 = new JTextField();
		res3.setHorizontalAlignment(SwingConstants.CENTER);
		res3.setEditable(false);
		res3.setColumns(10);

		res5 = new JTextField();
		res5.setHorizontalAlignment(SwingConstants.CENTER);
		res5.setEditable(false);
		res5.setColumns(10);

		res4 = new JTextField();
		res4.setHorizontalAlignment(SwingConstants.CENTER);
		res4.setEditable(false);
		res4.setColumns(10);

		res6 = new JTextField();
		res6.setHorizontalAlignment(SwingConstants.CENTER);
		res6.setEditable(false);
		res6.setColumns(10);

		res7 = new JTextField();
		res7.setHorizontalAlignment(SwingConstants.CENTER);
		res7.setEditable(false);
		res7.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(res1, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblThresholdWordIndex, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(res2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblThresholdWordFrequency, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(res3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNumberOfCommon, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(res4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblSumFrequencyOf, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(res5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNumberOfSignificant, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
							.addGap(9)
							.addComponent(res6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblSumFrequenciesOf, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
							.addGap(9)
							.addComponent(res7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel)
						.addComponent(res1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblThresholdWordIndex)
						.addComponent(res2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblThresholdWordFrequency)
						.addComponent(res3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNumberOfCommon)
						.addComponent(res4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSumFrequencyOf)
						.addComponent(res5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNumberOfSignificant)
						.addComponent(res6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSumFrequenciesOf)
						.addComponent(res7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		panel.setLayout(gl_panel);
		
		lblNewLabel_1 = new JLabel("Estimated Time: Up to 1 minutes");
		lblNewLabel_1.setBounds(304, 153, 222, 14);
		add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Estimated Time:Up to 1 minutes");
		lblNewLabel_2.setBounds(497, 227, 198, 14);
		add(lblNewLabel_2);

		panel.setVisible(false);

		//Process 2
		Proccess2_btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				create_global_handler();
			}
		});
		check_threshold_btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				expand_vectors_handler();
			}
		});


	}


	protected void expand_vectors_handler() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		Proccessing proc=new Proccessing();

		HashMap<String, Integer> result=proc.checkThresholdWord(threshold_word_txt.getText(),
				MainApp.getWS()+File.separator+"DB FV Files"+File.separator+"global.xml");
		if(result==null) JOptionPane.showMessageDialog(null, "Word not exist in the global vector", "Word Not Exist", JOptionPane.ERROR_MESSAGE);
		else{
			res1.setText(threshold_word_txt.getText());
			res2.setText(result.get("word place").toString());
			res3.setText(result.get("word FR").toString());
			res4.setText(result.get("num of common words").toString());
			res5.setText(result.get("commonFR").toString());
			res6.setText(result.get("num of sig words").toString());
			res7.setText(result.get("sigFR").toString());
			panel.setVisible(true);
			
			common=proc.getCommonVector(global, result.get("word place"), MainApp.getWS()+File.separator+"DB FV Files");

			ArrayList<String> names=new ArrayList<>();
			for(File file:xml_files){
				names.add(file.getName());
			}
			proc.expandAll(fv_paths, global, common,MainApp.getWS()+File.separator+"Frequency Vectors"+File.separator+"expanded FVs",names);
			DBController dbc=DBController.getInstance();

			if(!dbc.createGlobals(MainApp.getWS()+File.separator+"DB FV Files")){
				try {
					throw new Exception("Error occured on writing DB globals in database");
				} catch (Exception e) {

					JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			done=true;
			MainApp.changeNext("tab2", "Next");
		}
		
		
		setCursor(null);
		
	}


	private void create_global_handler() {
		
		File initialFVs=new File(MainApp.getWS()+File.separator+"Frequency Vectors"+File.separator+"initial FVs");
		xml_files=initialFVs.listFiles();
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		Proccessing proc=new Proccessing();
		fv_paths=new ArrayList<>();
		for(File file:xml_files){
			if((!file.getName().equals("global.xml")) && (!file.getName().equals("common.xml"))){
				fv_paths.add(file.getAbsolutePath());
			}
		}
		//the fvs names ready

		//find global vector
		global=proc.createGlobal(fv_paths,MainApp.getWS()+File.separator+"DB FV Files");



		//		proc.expandAll(fv_paths,global,common);
		setCursor(null);
		check_threshold_btn2.setEnabled(true);
		done=false;
		MainApp.changeNext("tab2", "Next");
	}
	
	public String getGlobalPath(){
		return expanded_dir;
		
	}
}
