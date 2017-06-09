package view.ui.preProcessing;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;



public class MainApp extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	
	public MainApp() {
		getContentPane().setFont(new Font("Arial", Font.BOLD, 36));
		setBackground(SystemColor.inactiveCaptionBorder);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Similarity Search Texts Pre-Proccessing");
		getContentPane().setLayout(null);
		
		ImageIcon icon = new ImageIcon("img/icon48.png");

		this.setIconImage(icon.getImage());
		JPanel panel = new JPanel();
		panel.setForeground(SystemColor.controlLtHighlight);
		panel.setBackground(SystemColor.controlDkShadow);
		panel.setBounds(0, 0, 1000, 48);
		getContentPane().add(panel);
		
		JLabel lblNewLabel = new JLabel("Similarity Search",icon,JLabel.RIGHT);
		lblNewLabel.setForeground(SystemColor.controlLtHighlight);
		lblNewLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 17));
		
		JLabel lblNewLabel_1 = new JLabel("Pre Processing");
		lblNewLabel_1.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
		lblNewLabel_1.setForeground(SystemColor.controlLtHighlight);
		lblNewLabel_1.setVerticalAlignment(SwingConstants.BOTTOM);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(52)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1)
					.addContainerGap(730, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_1))
					.addContainerGap(25, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBounds(0, 46, 1000, 500);
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
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				new MainApp().setVisible(true);
			}
		});
	}

}