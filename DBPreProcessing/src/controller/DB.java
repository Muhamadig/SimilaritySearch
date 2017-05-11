package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.FVHashMap;
import model.Language;
import model.Language.Langs;


public class DB {

	private int NOtexts;
	private String url = "http://www.courts.ie/Judgments.nsf/frmJudgmentsByYearAll?OpenForm&amp;Start=1&amp;Count=30&amp;CollapseView&amp;Seq=6";
	private ArrayList<String> links;
	private ArrayList <String> texts;
	private HashMap<String , FVHashMap> freqvec; 
	public DB(){
		texts = new ArrayList<String>();
		links = new ArrayList<String>();
		freqvec = new HashMap <String,FVHashMap>();
	}
	
	
	public void pdfcreate() throws IOException{
		
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage( page );

		// Create a new font object selecting one of the PDF base fonts
		PDFont font = PDType1Font.HELVETICA;	
		// Start a new content stream which will "hold" the to be created content
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		String text = PageText("http://www.courts.ie/Judgments.nsf/597645521f07ac9a80256ef30048ca52/8e8929c277c2fe5e8025811a0057b95a?OpenDocument");
		
		// Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
		contentStream.beginText();
		contentStream.setFont( font, 12 );
		contentStream.moveTextPositionByAmount( 100, 700 );
		contentStream.drawString(text);
		contentStream.endText();

		// Make sure that the content stream is closed:
		contentStream.close();

		// Save the results and ensure that the document is properly closed:
		document.save( "PDFs/Hello World.pdf");
		document.close();
	}
	public void init() throws IOException{
		long t = System.currentTimeMillis();
		System.out.println("Getting Texts..\n");
		getLinks();
		Iterator<String> it = links.iterator();
		while(it.hasNext()){
			getText(it.next());
			NOtexts++;
		}
		System.out.println("Done ... "+(System.currentTimeMillis()-t));
	}
	public void GetContent() throws IOException{
		Document document = Jsoup.connect("http://www.courts.ie/Judgments.nsf/597645521f07ac9a80256ef30048ca52/8e8929c277c2fe5e8025811a0057b95a?OpenDocument").followRedirects(false).timeout(60000/*wait up to 60 sec for response*/).get();
		String value = document.body().getElementById("ContentsFrame").text();
		value = ReadFile.clean(value);
		texts.add(value);
	}
	public void getLinks() throws IOException{
		Document document = Jsoup.connect(this.url).followRedirects(false).timeout(60000/*wait up to 60 sec for response*/).get();
		Elements links = document.getElementsByTag("a");
		int len = links.size();
		for(int i=0;i<len;i++){
			String link ="http://www.courts.ie"+ links.get(i).attr("href");
			if(link.contains("OpenDocument")){
				this.links.add(link);
				System.out.println(link);
			}
		}
		System.out.println("Done getting links");
	}
	public String PageText(String URL) throws IOException{
		System.out.println("Getting text:\n");
		long t = System.currentTimeMillis();
		String value=new String();
		Document document = Jsoup.connect(URL).followRedirects(false).timeout(60000/*wait up to 60 sec for response*/).get();
		if(document.body().getElementById("ContentOnly") != null){
			Elements childs = document.body().getElementById("ContentOnly").children();
			int len = childs.size();
			for(int i=0;i<len;i++){
				String text= childs.get(i).text();
				if(text.equals("th")){
					int size = value.length()-1;
					value = value.substring(0,size);
					value+=text+childs.get(i+1).text() +"\n";
					i++;
				}
				else
					value +=text +"\n";
			}
			System.out.println("Done ..." + (System.currentTimeMillis()-t));
		}
		return value;	
	}
	public void getText(String URL) throws IOException{
	
		Document document = Jsoup.connect(URL).followRedirects(false).timeout(60000/*wait up to 60 sec for response*/).get();
		if(document.body().getElementById("ContentOnly") != null){
		String value = document.body().getElementById("ContentOnly").text();
		value = ReadFile.clean(value);
		texts.add(value);
		}
	}
	public void collect(){
		long t = System.currentTimeMillis();
		System.out.println("Filltering:\n");
		Iterator <String> it = texts.iterator();
		while(it.hasNext()){
			String text = it.next();
			FVHashMap vec = new FVHashMap(text);
			vec = StopWordsFiltering.RemoveSW(vec , new Language(Langs.ENGLISH));
			freqvec.put(text, vec);
		}
		System.out.println("Done ..."+ (System.currentTimeMillis() - t));
		
	}
	public int GetCounter(){return this.NOtexts;}
	public HashMap<String , FVHashMap> getTextsVectors(){return freqvec;}
	public static void main(String[] args) {

		   DB temp = new DB();
		   /*HashMap<String , FVHashMap> vector=null;
		   try{
			   temp.init();
			   temp.collect();
			   vector = temp.getTextsVectors();
			   
		   }catch(IOException e){System.out.println(e.toString());}
		   System.out.println("Number of texts: " + temp.GetCounter());*/
		   try {
			   temp.pdfcreate();
		/*	String str = temp.PageText("http://www.courts.ie/Judgments.nsf/597645521f07ac9a80256ef30048ca52/8e8929c277c2fe5e8025811a0057b95a?OpenDocument");
			System.out.println(str);*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
}
