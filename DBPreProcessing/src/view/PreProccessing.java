package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;

import controller.Proccessing;
import model.Language;
import model.Language.Langs;
import view.demo.Task;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;
import java.awt.Cursor;

public class PreProccessing extends JFrame implements ActionListener,PropertyChangeListener {
	private JTextField readed_txt;
	private JTextField words_txt;
	private JTextField fq_txt;
	private JLabel time_lbl ;
	private JButton btnProcess;
	private JTextArea textArea;
	private JProgressBar progressBar;
	private Task task;
	private Proccessing proc;

	public PreProccessing() {
		setBackground(Color.WHITE);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(0, 0, 210, 150);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblOfTexts = new JLabel("# of texts readed:");
		lblOfTexts.setBounds(10, 11, 98, 14);
		panel.add(lblOfTexts);

		readed_txt = new JTextField();
		readed_txt.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		readed_txt.setEditable(false);
		readed_txt.setBounds(118, 8, 81, 20);
		panel.add(readed_txt);
		readed_txt.setColumns(10);

		JLabel lblOfDifferent = new JLabel("# of different words:");
		lblOfDifferent.setBounds(10, 36, 111, 14);
		panel.add(lblOfDifferent);

		words_txt = new JTextField();
		words_txt.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		words_txt.setEditable(false);
		words_txt.setColumns(10);
		words_txt.setBounds(118, 33, 81, 20);
		panel.add(words_txt);

		JLabel lblOfAll = new JLabel("# of all frequencies:");
		lblOfAll.setBounds(10, 61, 98, 14);
		panel.add(lblOfAll);

		fq_txt = new JTextField();
		fq_txt.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		fq_txt.setEditable(false);
		fq_txt.setColumns(10);
		fq_txt.setBounds(118, 58, 81, 20);
		panel.add(fq_txt);

		JLabel lblTime = new JLabel("Time :");
		lblTime.setBounds(10, 86, 65, 14);
		panel.add(lblTime);

		time_lbl = new JLabel("");
		time_lbl.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		time_lbl.setBounds(118, 86, 81, 14);
		panel.add(time_lbl);

		btnProcess = new JButton("Process");
		btnProcess.setBounds(71, 111, 89, 23);
		btnProcess.addActionListener(this);

		panel.add(btnProcess);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 150, 400,200);
		getContentPane().add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Arial", Font.PLAIN, 12));
		//		getContentPane().add(textArea);
		scrollPane.setViewportView(textArea);
		textArea.setBounds(0, 150, 400,200);
		textArea.setLineWrap(true);

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setBounds(220, 23, 164, 20);
		getContentPane().add(progressBar);

		setBounds(0, 0, 400, 384);
		proc=new Proccessing();
		

	}

	public void setTextsNumber(int texts){
		readed_txt.setText(Integer.toString(texts));
		readed_txt.repaint();

	}
	public void setWordsNumber(int words){
		words_txt.setText(Integer.toString(words));
		words_txt.repaint();
	}
	public void setFQNumber(int fq){
		fq_txt.setText(Integer.toString(fq));
		fq_txt.repaint();

	}
	public void updateTime(long time){
		time_lbl.setText(Long.toString(time));
		time_lbl.repaint();
	}

	public void updateLog(String log){
		String tmp=textArea.getText();
		if(tmp==null || tmp.isEmpty()) textArea.setText("-"+log);
		else textArea.append("-\n"+log);
		textArea.repaint();


	}
	public void flushLog(String log){
		textArea.setText("");
		textArea.repaint();

	}
	public void addActionListener(ActionListener e){
		btnProcess.addActionListener(e);
	}

	class Task extends SwingWorker<Void, Void> {
		/*
		 * Main task. Executed in background thread.
		 */
		@Override
		public Void doInBackground() {
			int progress = 0;
			double prog=0;
			//Initialize progress property.
			setProgress(0);
			ArrayList<String> fileNames=proc.readFilesNames("HTMLs/");
			ArrayList<Long> size=proc.getFilesSize("HTMLs/");
			double fullSize=0;
			for(Long file:size){
				fullSize+=file;
			}
			int numberOfFiles=fileNames.size();
			int tmp;
			for(int i=0;i<numberOfFiles;i++){
				tmp=Proccessing.getCounter();
				System.out.println(Thread.activeCount());
				proc.process("HTMLs", fileNames.get(i), "html", new Language(Langs.ENGLISH));
				System.out.println(Thread.activeCount());
				prog+=(double)(size.get(i)/fullSize)*100.0;
				progress+=Math.round(prog);
				setProgress(Math.min(progress, 100));
			}
			return null;  
		}

		/*
		 * Executed in event dispatching thread
		 */
		@Override
		public void done() {
			Toolkit.getDefaultToolkit().beep();
			btnProcess.setEnabled(true);
			setCursor(null); //turn off the wait cursor
			textArea.append("Done!\n");
		}
	}
	public void actionPerformed(ActionEvent evt) {
		btnProcess.setEnabled(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		//Instances of javax.swing.SwingWorker are not reusuable, so
		//we create new instances as needed.
		task = new Task();
		task.addPropertyChangeListener(this);
		task.execute();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
			textArea.append(String.format(
					"Completed %d%% of task.\n", task.getProgress()));
		} 		
	}
}
