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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Tab1 extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textsDir_txt1;


	private JTable FVs_table1;
	private JButton tab1_proc_btn1;
	private JButton browseTextsFiles_btn1;
	private JLabel textsDir_info1;
	private JLabel lblTextsLanguage ;
	private JComboBox<String> langbox1; 
	private File[] files;
	private Client wN_Client;
	private boolean done=false;

	public Tab1(Client wN_Client) {
		this.wN_Client=wN_Client;
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(700, 450));
		textsDir_txt1 = new JTextField();
		textsDir_txt1.setEditable(false);
		textsDir_txt1.setBackground(Color.WHITE);
		textsDir_txt1.setColumns(10);

		browseTextsFiles_btn1 = new JButton("Browse Files");

		textsDir_info1 = new JLabel("");
		textsDir_info1.setBackground(Color.WHITE);
		textsDir_info1.setFont(new Font("Tahoma", Font.PLAIN, 14));


		lblTextsLanguage = new JLabel("Texts Language:");

		langbox1 = new JComboBox<String>();
		langbox1.setModel(new DefaultComboBoxModel<String>(new String[] {"English"}));


		tab1_proc_btn1 = new JButton("Begin Texts Processing");
		tab1_proc_btn1.setBackground(SystemColor.inactiveCaption);
		tab1_proc_btn1.setEnabled(false);


		JScrollPane fvs_scrl1 = new JScrollPane();

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
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(10)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
												.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(lblTextsLanguage, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(langbox1, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
												.addComponent(textsDir_info1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE)
												.addComponent(textsDir_txt1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 346, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addComponent(browseTextsFiles_btn1, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(247)
										.addComponent(tab1_proc_btn1, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
								.addComponent(fvs_scrl1, GroupLayout.PREFERRED_SIZE, 680, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(20, Short.MAX_VALUE))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(38)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textsDir_txt1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(browseTextsFiles_btn1))
						.addComponent(textsDir_info1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTextsLanguage)
								.addComponent(langbox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(17)
						.addComponent(tab1_proc_btn1)
						.addGap(18)
						.addComponent(fvs_scrl1, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
						.addGap(58))
				);
		setLayout(groupLayout);
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
				Text text=proc.process(file, Tab0.getWorkSpace()+File.separator+"Frequency Vectors"+File.separator+"initial FVs", lang);
				dm.addRow(new Object[] {counter,text.getName(),text.getWords_num(),text.getFrequecny_num(),text.getSW_num()});
			}
			setCursor(null);
			done=true;
			MainApp.changeNext("tab1", "Next");
			MainApp.changePrev("tab1", "Previous");
		}
	}


	public boolean isStatus() {
		return done;
	}

	private void selectTextsHandler() {
		tab1_proc_btn1.setEnabled(false);

		ArrayList<String> types=new ArrayList<>();
		types.add("doc");
		types.add("docx");
		types.add("pdf");
		types.add("html");
		files=Browse.BrowseFiles(types);
//		String currName;
//		File currFile;
//		for(File file:files){
//			currName=filter(file.getName());
//			currFile=new File(file.getParent()+File.separator+currName);
//			file.renameTo(currFile);
//		}
		if(files.length>0)textsDir_txt1.setText(files[0].getParentFile().toString());

		int files_size=files.length;
		if(files_size==0){
			textsDir_info1.setText("Warning :No files was uploaded");
			tab1_proc_btn1.setEnabled(false);
			done=false;
		}else if(files_size>0){
			textsDir_info1.setText("Done :Number of uploaded files: "+files_size );
			tab1_proc_btn1.setEnabled(true);
		}		
	}
	

	public JTextField getTextsDir_txt1() {
		return textsDir_txt1;
	}
}
