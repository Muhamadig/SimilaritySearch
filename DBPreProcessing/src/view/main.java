package view;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import controller.ReadFile;
import controller.Util;
import model.FVHashMap;
import model.FVSortedMap;
public class main {

	public static void main(String[] args) {
		
		String en_pdf=ReadFile.ReadFile("PDFs/file1_EN.pdf", "pdf");
		String sp_pdf=ReadFile.ReadFile("PDFs/file1_SP.pdf", "pdf");
		String en_docx=ReadFile.ReadFile("DOCs/file1_EN.docx", "docx");
		
		FVHashMap f1,f2,f3;
		ArrayList<FVHashMap> fv_arr= new ArrayList<>();
		fv_arr.add(f1=new FVHashMap(en_pdf));
		fv_arr.add(f2=new FVHashMap(sp_pdf));
		fv_arr.add(f3=new FVHashMap(en_docx));
		
		
		
		FVHashMap all= new FVHashMap();
		for (FVHashMap fv:fv_arr){
			all.merge(fv);
		}
		
//		String test="hello world im muhamad  muhamadigbaria , how how how how how are you, thank how how you.";
//		FVHashMap t1=new FVHashMap(all/);
		FVSortedMap t2=new FVSortedMap(all);
		ArrayList<Entry<String, Integer>> sorted=Util.sortByValues(t2);
		System.out.println(sorted.toString());
	}
}

