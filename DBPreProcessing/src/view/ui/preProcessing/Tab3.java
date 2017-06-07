package view.ui.preProcessing;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.Proccessing;
import view.ui.utils.Browse;

public class Tab3 extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sortedDirectory;
	private JTextField textField_import;
	private JTextField textField_export;
	private File[] eXml_files2;
	private boolean import_dir;
	private boolean export_dir;
	private JButton btnSelectExpanded_btn3;
	private JLabel label1;
	private JButton btnSelectExportDirectory_btn3;
	private JLabel label2;
	private JButton prepareClustering_btn3;
	private JTextArea Message;
	
	public Tab3() {
		setLayout(null);
		setPreferredSize(new Dimension(700, 500));
		
		textField_import = new JTextField();
		textField_import.setColumns(10);
		textField_import.setBounds(10, 64, 424, 20);
		add(textField_import);

		btnSelectExpanded_btn3 = new JButton("Select Expanded Vectors");

		btnSelectExpanded_btn3.setBounds(444, 63, 223, 23);
		add(btnSelectExpanded_btn3);

		label1 = new JLabel("");
		label1.setBounds(10, 95, 424, 14);
		add(label1);
		
		
		textField_export = new JTextField();
		textField_export.setColumns(10);
		textField_export.setBounds(10, 120, 424, 20);
		add(textField_export);

		btnSelectExportDirectory_btn3 = new JButton("Select Export Directory");

		btnSelectExportDirectory_btn3.setBounds(444, 119, 223, 23);
		add(btnSelectExportDirectory_btn3);

		label2 = new JLabel("");
		label2.setBounds(10, 151, 424, 14);
		add(label2);

		prepareClustering_btn3 = new JButton("Prepare Clustering");

		prepareClustering_btn3.setBounds(333, 174, 186, 23);
		prepareClustering_btn3.setEnabled(false);
		add(prepareClustering_btn3);

		Message = new JTextArea("");
		Message.setWrapStyleWord(true);
		Message.setLineWrap(true);
		Message.setEditable(false);
		Message.setBackground(SystemColor.info);
		Message.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Message.setBounds(10, 216, 675, 42);
		Message.setVisible(false);
		add(Message);

		import_dir=false;
		export_dir=false;
	
		prepareClustering_btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				prepareClustering_handler();
				
			}
		});

		//Select Directory for save the Final Results
		btnSelectExportDirectory_btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				select_export_dir_handler();
			}
		});

		//import expanded FVs
		btnSelectExpanded_btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				select_expandedFiles_handler();
			}
		});
		
	}

	private void select_expandedFiles_handler() {
		import_dir=false;
		prepareClustering_btn3.setEnabled(false);
		ArrayList<String> types=new ArrayList<>();
		types.add("xml");
		eXml_files2=Browse.BrowseFiles(types);
		if(eXml_files2.length>0)textField_import.setText(eXml_files2[0].getParentFile().toString());

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

	private void select_export_dir_handler() {
		prepareClustering_btn3.setEnabled(false);
		export_dir=false;
		sortedDirectory=Browse.selectDirectory(Tab3.this);
		if(sortedDirectory!= null) {
			textField_export.setText(sortedDirectory);
			export_dir=true;
			Browse.lastPath=sortedDirectory;

		}
		else{
			textField_export.setText("No Directory Selected");
			export_dir=false;
		}
		if(export_dir && import_dir) prepareClustering_btn3.setEnabled(true);		
	}

	private void prepareClustering_handler() {
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
}
