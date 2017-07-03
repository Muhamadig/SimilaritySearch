package UI;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import Client.Client;
import Controllers.SearchController;
import DBModels.Result;
import UIUtils.Browse;
import UIUtils.MyTableModel;
import model.Language;
import model.Language.Langs;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Search extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textName_text;
	private JTable table;
	private File text;
	private JScrollPane scrollPane;
	private JButton search_btn;
	private JButton view_btn;
	private JLabel searching;

	public Search() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Similarity Search");
		getContentPane().setBackground(Color.WHITE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);

		scrollPane = new JScrollPane();

		view_btn = new JButton("View Text");
		view_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewText();
			}
		});

		JPanel panel_1 = new JPanel();
		panel_1.setForeground(Color.WHITE);
		panel_1.setBackground(SystemColor.controlDkShadow);
		ImageIcon icon = new ImageIcon("img/icon.png");

		this.setIconImage(icon.getImage());
		JLabel label = new JLabel("Similarity Search", (Icon) icon, SwingConstants.RIGHT);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 17));

		JLabel lblSearchForSimilar = new JLabel("- Search For Similar Texts In Professional Databases");
		lblSearchForSimilar.setVerticalAlignment(SwingConstants.BOTTOM);
		lblSearchForSimilar.setForeground(Color.WHITE);
		lblSearchForSimilar.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
				gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
						.addComponent(label)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblSearchForSimilar)
						.addContainerGap(138, Short.MAX_VALUE))
				);
		gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(label)
								.addComponent(lblSearchForSimilar))
						.addContainerGap(42, Short.MAX_VALUE))
				);
		panel_1.setLayout(gl_panel_1);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(292, Short.MAX_VALUE)
					.addComponent(view_btn)
					.addGap(273))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(view_btn)
					.addGap(20))
		);

		table = new JTable();
		String[] columnNames = {"Results"};
		Object[][] texts_table={};
		table = new JTable();
		table.setModel(new MyTableModel(columnNames, texts_table));
		table.setFillsViewportHeight(true);
		table.setSurrendersFocusOnKeystroke(true);
		table.setShowVerticalLines(false);
		table.setRowHeight(30);
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBackground(Color.WHITE);
		scrollPane.setSize(new Dimension(500, 150));

		scrollPane.setViewportView(table);
		JLabel lblNewLabel = new JLabel("Select text file for search");

		textName_text = new JTextField();
		textName_text.setEditable(false);
		textName_text.setColumns(10);

		JButton browse_btn = new JButton("Browse File");
		browse_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isVisible(false);
				Browsetext();
			}
		});

		search_btn = new JButton("Search Similar Texts");
		search_btn.setEnabled(false);
		search_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				searching.setVisible(true);
				clearTable();
				search();
			}
		});
		
		searching = new JLabel("Searching, Please Wait...");
		searching.setVisible(false);
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(textName_text, GroupLayout.PREFERRED_SIZE, 397, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(browse_btn))
						.addComponent(lblNewLabel))
					.addContainerGap(112, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(254, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(searching, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
						.addComponent(search_btn))
					.addGap(217))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textName_text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(browse_btn))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(search_btn)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(searching)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
		isVisible(false);
		setBounds(0, 0, 650, 476);
		this.setLocationRelativeTo(null);
	}


	protected void Browsetext() {

		search_btn.setEnabled(false);
		ArrayList<String> types=new ArrayList<>();
		types.add("html");
		types.add("pdf");
		types.add("doc");
		types.add("docx");

		text=Browse.Browse_single_File(types);
		if(text!=null){
			textName_text.setText(text.getName());
			search_btn.setEnabled(true);
		}
		else {
			textName_text.setText("");
			search_btn.setEnabled(false);
		}
	}


	protected void viewText() {

		String text=(String) table.getValueAt(table.getSelectedRow(), 0);
		File file=new File("Results Files"+File.separator+text+".html");
		try {
			Desktop.getDesktop().browse(file.toURI());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());

		}
	}


	protected void search() {
		
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		DefaultTableModel dm = (DefaultTableModel) table.getModel();

		String type=Utils.Util.getFileExtension(text.getName());

		ArrayList<Result> results=SearchController.search(text.getAbsolutePath().toString(),type, new Language(Langs.ENGLISH));
		
		if(results!=null && results.size()>0){
			isVisible(true);
		}
		
		File dir=new File("Results Files");
		dir.mkdir();
		for(Result res:results){
			FileOutputStream f = null;
			try {
				f = new FileOutputStream(dir.getAbsolutePath()+File.separator+res.getText_name());
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			try {
				f.write(res.getText_file());
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			dm.addRow(new Object[] {(res.getText_name().substring(0, res.getText_name().indexOf(".html")))});
		}
		searching.setVisible(false);
		setCursor(null);

	}


	public static void run(Client wN_Client, Client client) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Search().setVisible(true);

	}
	public void isVisible(boolean bool){
		scrollPane.setVisible(bool);
		view_btn.setVisible(bool);
	}
	
	private void clearTable(){
		MyTableModel model = (MyTableModel) table.getModel();
		while(model.getRowCount()>0)
			model.removeRow(0);
	}
}
