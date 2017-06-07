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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import controller.Proccessing;
import model.LangFactory;
import model.Language;
import model.Text;
import view.ui.utils.Browse;
import view.ui.utils.MyTableModel;

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
	private String lastPathFiles;
	private boolean dir_selected;
	private String directory;
	private String lastPathDir;



	public Tab1() {

		lastPathFiles=".";
		lastPathDir=".";

		setLayout(null);
		setPreferredSize(new Dimension(700, 500));
		textsDir_txt1 = new JTextField();
		textsDir_txt1.setEditable(false);
		textsDir_txt1.setBackground(Color.WHITE);
		textsDir_txt1.setBounds(10, 38, 282, 20);
		add(textsDir_txt1);
		textsDir_txt1.setColumns(10);

		browseTextsFiles_btn1 = new JButton("Browse Files");
		browseTextsFiles_btn1.setBounds(296, 37, 114, 23);
		add(browseTextsFiles_btn1);

		textsDir_info1 = new JLabel("");
		textsDir_info1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textsDir_info1.setBounds(10, 69, 282, 14);
		add(textsDir_info1);


		lblTextsLanguage = new JLabel("Texts Language:");
		lblTextsLanguage.setBounds(10, 112, 100, 14);
		add(lblTextsLanguage);

		langbox1 = new JComboBox<String>();
		langbox1.setModel(new DefaultComboBoxModel<String>(new String[] {"English"}));
		langbox1.setBounds(106, 109, 70, 20);
		add(langbox1);

		initialFVsDir_txt1 = new JTextField();
		initialFVsDir_txt1.setBackground(Color.WHITE);
		initialFVsDir_txt1.setEditable(false);
		initialFVsDir_txt1.setText("Save XML Files Into...");
		initialFVsDir_txt1.setBounds(186, 109, 343, 20);
		add(initialFVsDir_txt1);
		initialFVsDir_txt1.setColumns(10);

		SelectDirectory_SimpleFVs_btn1 = new JButton("Select Directory");

		SelectDirectory_SimpleFVs_btn1.setBounds(539, 108, 146, 23);
		add(SelectDirectory_SimpleFVs_btn1);


		tab1_proc_btn1 = new JButton("Begin Texts Processing");


		tab1_proc_btn1.setBounds(253, 142, 174, 23);
		tab1_proc_btn1.setEnabled(false);
		add(tab1_proc_btn1);


		JScrollPane fvs_scrl1 = new JScrollPane();
		fvs_scrl1.setBounds(10, 172, 680, 278);
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
			lastPathDir=directory;

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

		if(files.length>0)textsDir_txt1.setText(lastPathFiles=files[0].getParentFile().toString());

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


	public static void main(String[] args){
		JFrame frame=new JFrame();

		frame.add(new Tab1());
		frame.setSize(new Dimension(800, 600));
		frame.setVisible(true);
	}

}
