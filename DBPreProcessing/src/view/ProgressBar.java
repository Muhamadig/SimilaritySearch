package view;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

public class ProgressBar extends JFrame{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private Border border ;
	
	   public ProgressBar() {
		   getContentPane().setLayout(null);
		   
		   JProgressBar progressBar = new JProgressBar();
		   progressBar.setIndeterminate(true);
		   progressBar.setBounds(26, 17, 253, 42);
		    border = BorderFactory.createTitledBorder("Loading...");
		    progressBar.setBorder(border);
		    getContentPane().add(progressBar);
		    setSize(300, 100);
		    setVisible(true);
	   }
	   
	   public void done(){
		   dispose();
	   }
	 }