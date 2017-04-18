package view;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.analysis.tokenattributes.TermToBytesRefAttribute;
import org.apache.lucene.util.MSBRadixSorter;
import org.apache.lucene.util.Version;

import controller.ReadFile;
import controller.Util;
import model.FrequencyVector;
public class main {

	public static void main(String[] args) {
		
		String en_pdf=ReadFile.ReadFile("PDFs/file1_EN.pdf", "pdf");
		String sp_pdf=ReadFile.ReadFile("PDFs/file1_SP.pdf", "pdf");
		String en_docx=ReadFile.ReadFile("DOCs/file1_EN.docx", "docx");
		
		
		ArrayList<FrequencyVector> fv_arr= new ArrayList<>();
		fv_arr.add(new FrequencyVector(en_pdf));
		fv_arr.add(new FrequencyVector(sp_pdf));
		fv_arr.add(new FrequencyVector(en_docx));
		
		FrequencyVector all= new FrequencyVector();
		for (FrequencyVector fv:fv_arr){
			all.merge(fv);
		}
		
		System.out.println(all.toString());
		
	}
}

