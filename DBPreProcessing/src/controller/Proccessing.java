package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import model.FVHashMap;
import model.Language;
import model.Language.Langs;
import view.PreProccessing;

public class Proccessing {
	private PreProccessing view;
	private int numOfFiles;
	private int numOfWords;
	private int numOfFQ;
	private long time;
	private static int counter=0;


	public Proccessing(){
		resetParams();
	}

	public ArrayList<String> readFilesNames(String directory){
		ArrayList<String> names=new ArrayList<>();
		File dir = new File(directory);
		File[] directoryListing = dir.listFiles();
		if (directoryListing == null) return null;
		for (File file : directoryListing) {
			names.add(file.getName());
		}

		return names; 
	}
	public ArrayList<Long> getFilesSize(String directory){
		ArrayList<Long> size=new ArrayList<>();
		File dir = new File(directory);
		File[] directoryListing = dir.listFiles();
		if (directoryListing == null) return null;
		for (File file : directoryListing) {
			size.add(file.length());
		}

		return size; 
	}

	public void resetParams(){
		numOfFiles=0;
		numOfWords=0;
		numOfFQ=0;
		time=0;
	}

	public void process(String directory,String fileNname,String type,Language lang){
		long t=System.currentTimeMillis();
		FVHashMap fv=SuperSteps.buildFrequencyVector(directory+"/"+fileNname, type, lang);
		time+=(long)((System.currentTimeMillis()-t));
		numOfFiles++;
		numOfWords+=fv.size();
		numOfFQ+=fv.getSum();
		counter++;
	}
	public static int getCounter(){
		return counter;
	}
	/*public Proccessing(PreProccessing view){
		this.view=view;
		this.view.addActionListener(new ProccessingListener());
	}

	class ProccessingListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			File dir = new File("HTMLs/");
			File[] directoryListing = dir.listFiles();
			String file_name;
			FVHashMap fv;
			long t;
			numOfFiles=0;
			numOfWords=0;
			numOfFQ=0;
			time=0;

			if (directoryListing != null) {
				for (File file : directoryListing) {
					file_name=file.getName();
					t=System.currentTimeMillis();
					fv=SuperSteps.buildFrequencyVector("HTMLs/"+file_name, "html", new Language(Langs.ENGLISH));

					time+=(long)((System.currentTimeMillis()-t));
					numOfFiles++;
					numOfWords+=fv.size();
					numOfFQ+=fv.getSum();
					view.updateTime(time);
					view.setTextsNumber(numOfFiles);
					view.setWordsNumber(numOfWords);
					view.setFQNumber(numOfFQ);

					view.updateLog("-[File "+numOfFiles+" Done: "+file_name+"]\n# Of Words: "+fv.size()+" # Of Frequencies: "+ fv.getSum()+"\n----------");
					try {
						Thread.sleep(200);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else view.updateLog("[error]:No Files founded in directory HTMLs/");

			view.updateLog("\n\nProccessing DONE...");
		}

	}
	 */

}
