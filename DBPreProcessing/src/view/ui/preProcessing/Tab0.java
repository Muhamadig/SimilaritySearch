package view.ui.preProcessing;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;

import UIUtils.Browse;
import controller.Proccessing;

import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class Tab0 extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField main_dir_text;
	private JButton main_selectDir_btn;
	protected static String workSpace;


	public static String getWorkSpace() {
		return workSpace;
	}


	public Tab0() {

		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(700, 500));
		
		JLabel lblNewLabel = new JLabel("Welcome To Similarity Search Pre-Processing System");
		lblNewLabel.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 18));
		
		JTextPane panel = new JTextPane();
		panel.setFont(new Font("Microsoft New Tai Lue", Font.PLAIN, 14));
		panel.setEditable(false);
		panel.setText("This system prepare the database texts for searching at the similarity search application.\r\n\r\nThe processing is 3 steps:\r\n1.  Reading the texts and convert them to frequency vectors after stop words ,synonyms and stemming filtering, these step uses the WordNet Server which must be online.\r\n2.  Finding the Database Common Words and then expand all the frequency vectors that created at step 1 to be at the same size.\r\n3.  Preparing the texts to clustering and then run cluster algorithm.\r\n\r\nStep 2 and 3 update the system database , the pre processing process may take several minutes so please wait.");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 675, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 676, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(122, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel_1 = new JLabel("Please select Directory of workspace to save the temporary processing files.");
		
		main_dir_text = new JTextField();
		main_dir_text.setEditable(false);
		main_dir_text.setColumns(10);
		
		main_selectDir_btn = new JButton("Select Directory");
		main_selectDir_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				selectDirectory();
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(main_dir_text, GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(main_selectDir_btn))
						.addComponent(lblNewLabel_1))
					.addContainerGap(219, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(main_dir_text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(main_selectDir_btn))
					.addContainerGap(35, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		setLayout(groupLayout);
	}
	
	Proccessing proc=new Proccessing();
	protected void selectDirectory() {
		String dir= Browse.selectDirectory(Tab0.this);
		
		if(dir==null || dir.equals("")){
			main_dir_text.setText("");
			workSpace="";
			MainApp.changeNext("tab0", "Start Pre-Processing");
		}
		
		else{
			main_dir_text.setText(dir);
			workSpace=dir;
			MainApp.changeNext("tab0", "Start Pre-Processing");
			File workspace_f=new File(workSpace+File.separator+"clustering");
			workspace_f.mkdirs();
			proc.uploadClusters(workSpace+File.separator+"clustering");
		}
	}
}

