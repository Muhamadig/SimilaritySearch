package UI;

import javax.swing.JFrame;

import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShowTextFile extends JFrame{
	
	
	public ShowTextFile() {
		File file=new File("C:/Users/MASTER/Desktop/HTMLs/A.O. -v- Refugee Appeals Tribunal & ors.html");
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().browse(file.toURI());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(242)
					.addComponent(btnNewButton)
					.addContainerGap(452, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(119)
					.addComponent(btnNewButton)
					.addContainerGap(318, Short.MAX_VALUE))
		);
		
		getContentPane().setLayout(groupLayout);
		this.setPreferredSize(new Dimension(800, 500));
		
		
	}

	public static void main(String[] args){
		new ShowTextFile().setVisible(true);
	}
}
