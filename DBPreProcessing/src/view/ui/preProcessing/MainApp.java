package view.ui.preProcessing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Client.Client;
import controller.DBController;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class MainApp extends JFrame {


	private static final long serialVersionUID = 1L;
	private Client wN_Client;
	private Client client;
	protected DBController dbc;
	private static Tab0 tab0;
	private JTabbedPane tabbedPane;
	private Tab1 tab1;
	private static Tab3 tab3;
	private static Tab2 tab2;
	private static JButton prev_btn;
	private static JButton next_btn;
	public MainApp(Client wN_Client, Client client) {
		getContentPane().setBackground(Color.WHITE);
		this.wN_Client=wN_Client;
		this.client=client;
		getContentPane().setFont(new Font("Arial", Font.BOLD, 36));
		setBackground(SystemColor.inactiveCaptionBorder);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Similarity Search Texts Pre-Proccessing");

		ImageIcon icon = new ImageIcon("img/icon48.png");

		this.setIconImage(icon.getImage());
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1000, 48);
		panel.setForeground(SystemColor.controlLtHighlight);
		panel.setBackground(SystemColor.controlDkShadow);

		JLabel lblNewLabel = new JLabel("Similarity Search",icon,JLabel.RIGHT);
		lblNewLabel.setForeground(SystemColor.controlLtHighlight);
		lblNewLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 17));

		JLabel lblNewLabel_1 = new JLabel("Pre Processing");
		lblNewLabel_1.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 14));
		lblNewLabel_1.setForeground(SystemColor.controlLtHighlight);
		lblNewLabel_1.setVerticalAlignment(SwingConstants.BOTTOM);

		JLabel lblNewLabel_2 = new JLabel("Word Net Server ");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setForeground(SystemColor.controlLtHighlight);

		JLabel lblNewLabel_3 = new JLabel("Similarity Search Server");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setForeground(SystemColor.controlLtHighlight);

		JLabel wnStatus = new JLabel(this.wN_Client.isConnected()?"Online":"Offline");
		wnStatus.setForeground(SystemColor.inactiveCaptionBorder);

		JLabel serverStatus = new JLabel(this.client.isConnected()?"Online":"Offline");
		serverStatus.setForeground(SystemColor.inactiveCaptionBorder);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblNewLabel)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblNewLabel_1)
						.addGap(54)
						.addComponent(lblNewLabel_2)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(wnStatus)
						.addGap(44)
						.addComponent(lblNewLabel_3)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(serverStatus)
						.addContainerGap(334, Short.MAX_VALUE))
				);
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel)
										.addComponent(lblNewLabel_1))
								.addGroup(gl_panel.createSequentialGroup()
										.addContainerGap()
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblNewLabel_2)
												.addComponent(lblNewLabel_3)
												.addComponent(wnStatus)
												.addComponent(serverStatus))))
						.addContainerGap(14, Short.MAX_VALUE))
				);
		panel.setLayout(gl_panel);


		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		tabbedPane.setBounds(0, 46, 1000, 480);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		tabbedPane.setFont(new Font("Microsoft New Tai Lue", Font.PLAIN, 18));
		tabbedPane.setBorder(null);
		tabbedPane.setBackground(SystemColor.textInactiveText);
		tabbedPane.setForeground(SystemColor.windowBorder);

//		tabbedPane.setEnabled(false);
		ImageIcon tab=new ImageIcon("img/tabs.png");


		/**
		 * Tab0:
		 */
		tab0=new Tab0();
		tab0.setBackground(Color.WHITE);
		tabbedPane.addTab("Welcome", null, tab0,"");
		/**
		 * Tab1:
		 */
		tab1=new Tab1(this.wN_Client);
		tab1.setBackground(Color.WHITE);
		tabbedPane.addTab("Process All Texts", tab, tab1,"Build Frequency Vectors for All Texts");

		/**
		 * Tab2
		 */
		tab2=new Tab2();
		tab2.setBackground(Color.WHITE);
		tabbedPane.addTab("Expand All Frequency Vectors",tab, tab2, "Expand All Frequency Vectors");
		//

		tab3 = new Tab3();
		tab3.setBackground(Color.WHITE);
		tabbedPane.addTab("Clustering Preparation",tab, tab3,"Create a sorted FVs by keys");

		
		getContentPane().setLayout(null);
		getContentPane().add(tabbedPane);
		getContentPane().add(panel);

		prev_btn = new JButton("New button");
		prev_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				prev();
			}
		});
		prev_btn.setBounds(694, 537, 140, 23);
		getContentPane().add(prev_btn);
		prev_btn.setVisible(false);
		next_btn = new JButton("Start Pre-Processing");
		next_btn.setBounds(844, 537, 140, 23);
		getContentPane().add(next_btn);
		next_btn.setEnabled(false);
		next_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				next();
			}
		});

		setBounds(0, 0, 1000, 615);
		this.setLocationRelativeTo(null);
		tabbedPane.setEnabledAt(1, false);
		tabbedPane.setEnabledAt(2, false);
		tabbedPane.setEnabledAt(3, false);


	}

	protected void next() {

		switch(tabbedPane.getSelectedIndex()){
		case 0:

			if(!wN_Client.isConnected() || !client.isConnected()){
				JOptionPane.showMessageDialog(null, "One or more servers is offline.\n "
						+ "Please check connection of WordNet server or application server and then start the application \n The application will be closed after press ok.", "Server offline", JOptionPane.ERROR_MESSAGE);
				this.dispose();
				return;
			}
			tabbedPane.setSelectedIndex(1);
			next_btn.setEnabled(tab1.isStatus());
			next_btn.setText("Next");

			prev_btn.setEnabled(true);
			prev_btn.setVisible(true);
			prev_btn.setText("Previous");
			
			tabbedPane.setEnabledAt(0, false);
			tabbedPane.setEnabledAt(1, true);
			tabbedPane.setEnabledAt(2, false);
			tabbedPane.setEnabledAt(3, false);
			break;
		case 1:
			tabbedPane.setSelectedIndex(2);
			next_btn.setEnabled(tab2.isDone());
			tabbedPane.setEnabledAt(0, false);
			tabbedPane.setEnabledAt(1, false);
			tabbedPane.setEnabledAt(2, true);
			tabbedPane.setEnabledAt(3, false);
			break;
		case 2:
			tabbedPane.setSelectedIndex(3);
			next_btn.setEnabled(Tab3.isDone());
			next_btn.setText("Update Database");
			tabbedPane.setEnabledAt(0, false);
			tabbedPane.setEnabledAt(1, false);
			tabbedPane.setEnabledAt(2, false);
			tabbedPane.setEnabledAt(3, true);

			break;
		case 3:
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			DBController dbc=DBController.getInstance();
			if(!dbc.createTexts(getWS()+File.separator+"Frequency Vectors"+File.separator+"final FVs",tab1.getTextsDir_txt1().getText()))
				try {
					throw new Exception("Error occured on writing texts in database");
				} catch (Exception ex) {

					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			setCursor(null);

			break;
		}


	}
	
	protected void prev() {

		switch(tabbedPane.getSelectedIndex()){
		
		case 1:
			tabbedPane.setSelectedIndex(0);
			prev_btn.setVisible(false);
			if(Tab0.getWorkSpace()!=null && !Tab0.getWorkSpace().equals("")){
				next_btn.setEnabled(true);
			}
			else{
				next_btn.setEnabled(false);
			}
			next_btn.setText("Start Pre-Processing");
			tabbedPane.setEnabledAt(0, true);
			tabbedPane.setEnabledAt(1, false);
			tabbedPane.setEnabledAt(2, false);
			tabbedPane.setEnabledAt(3, false);
			break;

		case 2:
			tabbedPane.setSelectedIndex(1);
			next_btn.setEnabled(tab1.isStatus());
			tabbedPane.setEnabledAt(0, false);
			tabbedPane.setEnabledAt(1, true);
			tabbedPane.setEnabledAt(2, false);
			tabbedPane.setEnabledAt(3, false);
			break;
		case 3:
			tabbedPane.setSelectedIndex(2);
			next_btn.setEnabled(tab2.isDone());
			next_btn.setText("Next");
			tabbedPane.setEnabledAt(0, false);
			tabbedPane.setEnabledAt(1, false);
			tabbedPane.setEnabledAt(2, true);
			tabbedPane.setEnabledAt(3, false);

			break;
		}


	}

	public Client getwN_Client() {
		return wN_Client;
	}

	public void setwN_Client(Client wN_Client) {
		this.wN_Client = wN_Client;
	}

	public static void changeNext(String tab,String caption){

		switch(tab){
		case "tab0":
			if(Tab0.getWorkSpace()==null || Tab0.getWorkSpace().equals("")){
				next_btn.setEnabled(false);
			}
			else{
				next_btn.setEnabled(true);
			}
			break;
			
		case "tab1":
			next_btn.setEnabled(true);
			break;
		case "tab2":
			next_btn.setEnabled(tab2.isDone());
			break;
		case "tab3":
			next_btn.setEnabled(Tab3.isDone());
			break;
			
		}
	}

	public static void changePrev(String tab,String caption){

		switch(tab){
		}
	}

	public static String getWS(){
		return Tab0.getWorkSpace();
	}

	public static void run(Client wN_Client, Client client){
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
				new MainApp(wN_Client,client).setVisible(true);
			}
		});
	}
}