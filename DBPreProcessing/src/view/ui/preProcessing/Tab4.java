package view.ui.preProcessing;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import Client.Client;
import UIUtils.Browse;
import controller.DBController;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Tab4 extends JPanel {
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;
	private int chooser_counter=0;
	private Client client;
	private String globalFilesDir;
	private String clusterFileDir;
	private String textsFilesDir;
	private JCheckBox CheckBox1;
	private JCheckBox CheckBox2;
	private JCheckBox CheckBox3;
	DBController db=DBController.getInstance();
	public Tab4(Client client) {
		this.client=client;
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(700, 500));
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 700, GroupLayout.PREFERRED_SIZE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
		);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		scrollPane.setViewportView(panel);
		
		JPanel choice_panel = new JPanel();
		choice_panel.setBackground(SystemColor.inactiveCaption);
		choice_panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Select Your Updates", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.WHITE);
		panel1.setBorder(new TitledBorder(null, "Update Global And Common Words", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.WHITE);
		panel2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Update Clusters", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		textField2 = new JTextField();
		textField2.setColumns(10);
		
		JButton button2 = new JButton("Select File");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				selectClusterFile();
			}
		});
		
		JLabel lblSelectTheClustersxml = new JLabel("Select the clusters.xml File");
		GroupLayout gl_panel2 = new GroupLayout(panel2);
		gl_panel2.setHorizontalGroup(
			gl_panel2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 678, Short.MAX_VALUE)
				.addGroup(gl_panel2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel2.createSequentialGroup()
							.addComponent(textField2, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(button2))
						.addComponent(lblSelectTheClustersxml, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(304, Short.MAX_VALUE))
		);
		gl_panel2.setVerticalGroup(
			gl_panel2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 90, Short.MAX_VALUE)
				.addGroup(gl_panel2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSelectTheClustersxml)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel2.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button2))
					.addContainerGap(45, Short.MAX_VALUE))
		);
		panel2.setLayout(gl_panel2);
		
		JPanel panel3 = new JPanel();
		panel3.setBackground(Color.WHITE);
		panel3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Update Texts", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		textField3 = new JTextField();
		textField3.setColumns(10);
		
		JButton button3 = new JButton("Select Directory");
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				selectTextsDir();
			}
		});
		
		JLabel lblSelectTheDirectory = new JLabel("Select the directory Final texts frequency vectors (the output from clustering preparation step : *.xml)");
		GroupLayout gl_panel3 = new GroupLayout(panel3);
		gl_panel3.setHorizontalGroup(
			gl_panel3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel3.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSelectTheDirectory, GroupLayout.PREFERRED_SIZE, 574, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel3.createSequentialGroup()
							.addComponent(textField3, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(button3)))
					.addContainerGap(82, Short.MAX_VALUE))
		);
		gl_panel3.setVerticalGroup(
			gl_panel3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel3.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSelectTheDirectory)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel3.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button3))
					.addContainerGap(13, Short.MAX_VALUE))
		);
		panel3.setLayout(gl_panel3);
		
		JPanel update_panel = new JPanel();
		update_panel.setBackground(Color.WHITE);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(update_panel, GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
						.addComponent(panel1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
						.addComponent(choice_panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
						.addComponent(panel2, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 678, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel3, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 678, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(choice_panel, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel1, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel2, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel3, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(update_panel, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(15, Short.MAX_VALUE))
		);
		
		JButton update_btn = new JButton("Update Database");
		update_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateDatabase();
			}
		});
		GroupLayout gl_update_panel = new GroupLayout(update_panel);
		gl_update_panel.setHorizontalGroup(
			gl_update_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_update_panel.createSequentialGroup()
					.addGap(281)
					.addComponent(update_btn)
					.addContainerGap(280, Short.MAX_VALUE))
		);
		gl_update_panel.setVerticalGroup(
			gl_update_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_update_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(update_btn)
					.addContainerGap(25, Short.MAX_VALUE))
		);
		update_panel.setLayout(gl_update_panel);
		
		JLabel lblNewLabel_1 = new JLabel("Select the directory of global.xml and common.xml");
		
		textField1 = new JTextField();
		textField1.setColumns(10);
		
		JButton button1 = new JButton("Select Directory");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				selectGlobalDir();
			}
		});
		GroupLayout gl_panel1 = new GroupLayout(panel1);
		gl_panel1.setHorizontalGroup(
			gl_panel1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel1.createSequentialGroup()
							.addComponent(textField1, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(button1))
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(304, Short.MAX_VALUE))
		);
		gl_panel1.setVerticalGroup(
			gl_panel1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel1.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button1))
					.addContainerGap(48, Short.MAX_VALUE))
		);
		panel1.setLayout(gl_panel1);
		
		panel1.setVisible(false);
		panel2.setVisible(false);
		panel3.setVisible(false);
		update_panel.setVisible(false);

		CheckBox1 = new JCheckBox("Update Global And Common Words");
		CheckBox1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel1.setVisible(CheckBox1.isSelected());
				if(CheckBox1.isSelected()) chooser_counter++;
				else chooser_counter--;
				
				if(chooser_counter>0) update_panel.setVisible(true);
				else update_panel.setVisible(false);

			}
		});
		CheckBox1.setBackground(SystemColor.inactiveCaption);
		
		CheckBox2 = new JCheckBox("Update Clusters");
		CheckBox2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel2.setVisible(CheckBox2.isSelected());
				if(CheckBox2.isSelected()) chooser_counter++;
				else chooser_counter--;
				
				if(chooser_counter>0) update_panel.setVisible(true);
				else update_panel.setVisible(false);

			}
		});
		CheckBox2.setBackground(SystemColor.inactiveCaption);
		
		CheckBox3 = new JCheckBox("Update Texts");
		CheckBox3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel3.setVisible(CheckBox3.isSelected());
				if(CheckBox3.isSelected()) chooser_counter++;
				else chooser_counter--;
				if(chooser_counter>0) update_panel.setVisible(true);
				else update_panel.setVisible(false);

			}
		});
		CheckBox3.setBackground(SystemColor.inactiveCaption);
		GroupLayout gl_choice_panel = new GroupLayout(choice_panel);
		gl_choice_panel.setHorizontalGroup(
			gl_choice_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_choice_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_choice_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(CheckBox1)
						.addComponent(CheckBox2)
						.addComponent(CheckBox3))
					.addContainerGap(467, Short.MAX_VALUE))
		);
		gl_choice_panel.setVerticalGroup(
			gl_choice_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_choice_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(CheckBox1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(CheckBox2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(CheckBox3)
					.addContainerGap(21, Short.MAX_VALUE))
		);
		gl_choice_panel.linkSize(SwingConstants.VERTICAL, new Component[] {CheckBox1, CheckBox2, CheckBox3});
		gl_choice_panel.linkSize(SwingConstants.HORIZONTAL, new Component[] {CheckBox1, CheckBox2, CheckBox3});
		choice_panel.setLayout(gl_choice_panel);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
		
	}
	protected void updateDatabase() {

		if(CheckBox1.isSelected()){
			if(!globalFilesDir.equals("")){
				db.createGlobals(globalFilesDir);
			}
			else{
				JOptionPane.showMessageDialog(null, "global.xml and common.xml files directory not selected", "Error Selection", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		if(CheckBox2.isSelected()){
			if(clusterFileDir.endsWith("Clusters.xml")){
				db.createClusters(clusterFileDir);
			}
			else{
				JOptionPane.showMessageDialog(null, "The Clusters.xml File not selected", "Error Selection", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		if(CheckBox3.isSelected()){
			
		}
	}
	protected void selectTextsDir() {

		
		textsFilesDir=Browse.selectDirectory(Tab4.this);
		textField3.setText(textsFilesDir);
	}
	protected void selectClusterFile() {
		ArrayList<String> type=new ArrayList<>();
		type.add("xml");
		File[] clusterFile=Browse.BrowseFiles(type);
		if(clusterFile.length==1 && clusterFile[0].getName().equals("Clusters.xml"))
			clusterFileDir=clusterFile[0].getAbsolutePath();
		else {
			clusterFileDir="";
			JOptionPane.showMessageDialog(null, "Please select Clusters.xml File", "Error Selection", JOptionPane.ERROR_MESSAGE);
		}
		
		textField2.setText(clusterFileDir);
	}
	protected void selectGlobalDir() {

		globalFilesDir=Browse.selectDirectory(Tab4.this);
		textField1.setText(globalFilesDir);
	}
}
