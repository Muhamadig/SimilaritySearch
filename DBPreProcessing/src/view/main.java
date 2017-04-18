package view;

import java.io.StringReader;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.analysis.tokenattributes.TermToBytesRefAttribute;
import org.apache.lucene.util.Version;
public class main {

	public static void main(String[] args) {
		
		 String text 
	      = "Lucene is simple yet powerful java based search library.";
	   Analyzer analyzer = new StandardAnalyzer();
	   TokenStream tokenStream 
	      = analyzer.tokenStream("contents",
	        new StringReader(text));
	   
	   tokenStream.
	   TermToBytesRefAttribute term = ((Object) tokenStream).addAttribute(TermToBytesRefAttribute.class);
	   while(((Object) tokenStream).incrementToken()) {
	      System.out.print("[" + term.term() + "] ");
	   }
	}
}

