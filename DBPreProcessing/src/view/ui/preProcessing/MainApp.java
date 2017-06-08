package view.ui.preProcessing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;



public class MainApp extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	
	public MainApp() {
		getContentPane().setFont(new Font("Arial", Font.BOLD, 36));
		setBackground(SystemColor.inactiveCaptionBorder);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Similarity Search Texts Pre-Proccessing");
		getContentPane().setLayout(null);
		
		ImageIcon icon = new ImageIcon("img/settings_2-64.png");
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(0, 0, 1000, 64);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(icon);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(0, 0, 72, 64);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Similariry Search");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(SystemColor.menu);
		lblNewLabel_1.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 28));
		lblNewLabel_1.setBounds(63, 0, 248, 37);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Pre-Processing");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(SystemColor.text);
		lblNewLabel_2.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 20));
		lblNewLabel_2.setBounds(63, 31, 248, 33);
		panel.add(lblNewLabel_2);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBounds(0, 64, 1000, 500);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setFont(new Font("Microsoft New Tai Lue", Font.PLAIN, 18));
		tabbedPane.setBorder(null);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setForeground(SystemColor.activeCaption);
		getContentPane().add(tabbedPane);

		ImageIcon tab=new ImageIcon("img/tabs.png");
		/**
		 * Tab1:
		 */
		JPanel tab1=new Tab1();
		tab1.setBackground(Color.WHITE);
		tabbedPane.addTab("Process All Texts", tab, tab1,"Build Frequency Vectors for All Texts");
		
		/**
		 * Tab2
		 */
		JPanel tab2=new Tab2();
		tab2.setBackground(Color.WHITE);
		tabbedPane.addTab("Expand All Frequency Vectors",tab, tab2, "Expand All Frequency Vectors");
		//
		
		JPanel tab3 = new Tab3();
		tab3.setBackground(Color.WHITE);
		tabbedPane.addTab("Clustering Preparation",tab, tab3,"Create a sorted FVs by keys");

		setBounds(0, 0, 1006, 591);
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