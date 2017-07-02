package UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class Browse {
	
	
	public static String lastPath=".";
	
	public static String selectDirectory(JComponent parent) {
		JFileChooser chooser;
		chooser = new JFileChooser(); 
		chooser.setCurrentDirectory(new java.io.File(lastPath));
		chooser.setDialogTitle("Select Directory");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) { 
			lastPath=chooser.getSelectedFile().toString();
			return lastPath;
		}
		else return null;
	}

	public static File[] BrowseFiles(ArrayList<String> types) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File(lastPath));
		chooser.setDialogTitle("Select Files");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		chooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				String res="";
				for(String type:types){
					res+=",*."+type;
				}
				return res;
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					String filename = f.getName();
					boolean res=false;
					for(String type:types){
						res=res||filename.endsWith("."+type);
					}
					return res;
				}			
			}
		});
		chooser.setMultiSelectionEnabled(true);
		chooser.showOpenDialog(null);

		File[] files = chooser.getSelectedFiles();
		if(files!=null && files.length>0) lastPath=files[0].getParentFile().toString();
		return files;
	}

	
	public static File Browse_single_File(ArrayList<String> types) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File(lastPath));
		chooser.setDialogTitle("Select File");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		chooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				String res="";
				for(String type:types){
					res+=",*."+type;
				}
				return res;
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					String filename = f.getName();
					boolean res=false;
					for(String type:types){
						res=res||filename.endsWith("."+type);
					}
					return res;
				}			
			}
		});
		chooser.setMultiSelectionEnabled(false);
		chooser.showOpenDialog(null);

		File file = chooser.getSelectedFile();
		if(file!=null) lastPath=file.getParentFile().toString();
		return file;
	}
}
