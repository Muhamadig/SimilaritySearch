package view.ui.preProcessing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import Client.Client;
import UIUtils.Browse;
import UIUtils.MyTableModel;
import controller.Proccessing;
import model.LangFactory;
import model.Language;
import model.Text;

import java.awt.SystemColor;

public class Tab1 extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textsDir_txt1;
	private JTable FVs_table1;
	private JTextField initialFVsDir_txt1;
	private JButton tab1_proc_btn1;
	private JButton browseTextsFiles_btn1;
	private JLabel textsDir_info1;
	private JLabel lblTextsLanguage ;
	private JComboBox<String> langbox1; 
	private JButton SelectDirectory_SimpleFVs_btn1;
	private  boolean files_selected;
	private File[] files;
	private boolean dir_selected;
	private String directory;
	private Client wN_Client;


	public Tab1(Client wN_Client) {
		this.wN_Client=wN_Client;
		setBackground(Color.WHITE);

		setLayout(null);
		setPreferredSize(new Dimension(700, 500));
		textsDir_txt1 = new JTextField();
		textsDir_txt1.setEditable(false);
		textsDir_txt1.setBackground(Color.WHITE);
		textsDir_txt1.setBounds(10, 38, 282, 20);
		add(textsDir_txt1);
		textsDir_txt1.setColumns(10);

		browseTextsFiles_btn1 = new JButton("Browse Files");
		browseTextsFiles_btn1.setBounds(302, 37, 174, 23);
		add(browseTextsFiles_btn1);

		textsDir_info1 = new JLabel("");
		textsDir_info1.setBackground(Color.WHITE);
		textsDir_info1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textsDir_info1.setBounds(10, 58, 282, 14);
		add(textsDir_info1);


		lblTextsLanguage = new JLabel("Texts Language:");
		lblTextsLanguage.setBounds(10, 84, 120, 14);
		add(lblTextsLanguage);

		langbox1 = new JComboBox<String>();
		langbox1.setModel(new DefaultComboBoxModel<String>(new String[] {"English"}));
		langbox1.setBounds(140, 81, 109, 20);
		add(langbox1);

		initialFVsDir_txt1 = new JTextField();
		initialFVsDir_txt1.setBackground(Color.WHITE);
		initialFVsDir_txt1.setEditable(false);
		initialFVsDir_txt1.setText("Save XML Files Into...");
		initialFVsDir_txt1.setBounds(10, 127, 282, 20);
		add(initialFVsDir_txt1);
		initialFVsDir_txt1.setColumns(10);

		SelectDirectory_SimpleFVs_btn1 = new JButton("Select Directory");

		SelectDirectory_SimpleFVs_btn1.setBounds(302, 126, 174, 23);
		add(SelectDirectory_SimpleFVs_btn1);


		tab1_proc_btn1 = new JButton("Begin Texts Processing");
		tab1_proc_btn1.setBackground(SystemColor.inactiveCaption);


		tab1_proc_btn1.setBounds(302, 175, 174, 23);
		tab1_proc_btn1.setEnabled(false);
		add(tab1_proc_btn1);


		JScrollPane fvs_scrl1 = new JScrollPane();
		fvs_scrl1.setBounds(10, 211, 680, 278);
		add(fvs_scrl1);	

		String[] columnNames = {"#", "Text Name", "Rep. Words", "Frequencies","Stop Words" };
		Object[][] texts_tabel = {};
		FVs_table1 = new JTable();
		FVs_table1.setModel(new MyTableModel(columnNames, texts_tabel));
		FVs_table1.setFillsViewportHeight(true);
		FVs_table1.setSurrendersFocusOnKeystroke(true);
		FVs_table1.setShowVerticalLines(false);
		FVs_table1.setRowHeight(30);
		FVs_table1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		fvs_scrl1.setViewportView(FVs_table1);
		FVs_table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		FVs_table1.setBackground(Color.WHITE);
		FVs_table1.getColumnModel().getColumn(0).setPreferredWidth(30);
		FVs_table1.getColumnModel().getColumn(1).setPreferredWidth(380);
		FVs_table1.getColumnModel().getColumn(2).setPreferredWidth(90);
		FVs_table1.getColumnModel().getColumn(3).setPreferredWidth(90);
		FVs_table1.getColumnModel().getColumn(4).setPreferredWidth(90);

		browseTextsFiles_btn1.addActionListener(new ActionListener() {//Ready
			public void actionPerformed(ActionEvent e) {
				selectTextsHandler();
			}
		});

		SelectDirectory_SimpleFVs_btn1.addActionListener(new ActionListener() {//Ready
			public void actionPerformed(ActionEvent e) {
				select_FVs_Directory();
			}
		});

		tab1_proc_btn1.addActionListener(new ActionListener() {//Ready
			public void actionPerformed(ActionEvent e) {
				processTextsHandler();
			}
		});
	}

	private void processTextsHandler() {

		if(!wN_Client.isConnected()) {

			JOptionPane.showMessageDialog(null, "The WordNet Server is not Connected!", "WordNet Server Offline", ERROR);
			return;
		}
		Proccessing proc =new Proccessing();
		Language lang=LangFactory.getLang(String.valueOf(langbox1.getSelectedItem()));
		DefaultTableModel dm = (DefaultTableModel) FVs_table1.getModel();
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

	private void select_FVs_Directory() {
		tab1_proc_btn1.setEnabled(false);
		dir_selected=false;
		directory=Browse.selectDirectory(Tab1.this);
		if(directory!= null) {
			initialFVsDir_txt1.setText(directory);
			dir_selected=true;
			Browse.lastPath=directory;

		}
		else{
			initialFVsDir_txt1.setText("No Directory Selected");
			dir_selected=false;
		}
		if(dir_selected && files_selected) tab1_proc_btn1.setEnabled(true);

	}

	private void selectTextsHandler() {
		tab1_proc_btn1.setEnabled(false);
		files_selected=false;

		ArrayList<String> types=new ArrayList<>();
		types.add("doc");
		types.add("docx");
		types.add("pdf");
		types.add("html");
		files=Browse.BrowseFiles(types);

		if(files.length>0)textsDir_txt1.setText(files[0].getParentFile().toString());

		int files_size=files.length;
		if(files_size==0){
			textsDir_info1.setText("Warning :No files was uploaded");
			tab1_proc_btn1.setEnabled(false);
			files_selected=false;

		}else if(files_size>0){
			textsDir_info1.setText("Done :Number of uploaded files: "+files_size );
			files_selected=true;

		}		
	}
}
