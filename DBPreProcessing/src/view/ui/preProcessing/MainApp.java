package view.ui.preProcessing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;



public class MainApp extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	
	public MainApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Similarity Search Texts Pre-Proccessing");

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tabbedPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.setBackground(Color.BLACK);
		tabbedPane.setForeground(Color.WHITE);

		getContentPane().add(tabbedPane, BorderLayout.WEST);

		
		/**
		 * Tab1:
		 */
		JPanel tab1=new Tab1();
		tabbedPane.addTab("Process All Texts", null, tab1,"Build Frequency Vectors for All Texts");
		
		/**
		 * Tab2
		 */
		JPanel tab2=new Tab2();
		tabbedPane.addTab("Expand All Frequency Vectors", null, tab2, "Expand All Frequency Vectors");
		//
		
		JPanel tab3 = new Tab3();
		tabbedPane.addTab("Clustering Preparation", null, tab3,"Create a sorted FVs by keys");

		setBounds(0, 0, 920, 500);
		this.setLocationRelativeTo(null);
	
	}

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