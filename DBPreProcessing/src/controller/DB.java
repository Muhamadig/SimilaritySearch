package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.itextpdf.text.DocumentException;

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
	public String getTitle(String URL)throws IOException{
		String title="";
		Document document = Jsoup.connect(URL).followRedirects(false).timeout(60000/*wait up to 60 sec for response*/).get();
		Element table = document.body().select("table").get(9);
		Element tr = table.select("tr").first();
		Element td = tr.select("td").get(1);
		title= td.text();
		return title;
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

	public String getTableText(String URL) throws IOException{
		System.out.println("Getting text:\n");
		Document document = Jsoup.connect(URL).followRedirects(false).timeout(60000/*wait up to 60 sec for response*/).get();
		Element table = document.body().select("table").get(8);
		Elements childs = table.children();
		String text="";
		for(Element child : childs)
			if (!child.is("img"))
				text+=child.text()+"\n";
		text = text.replaceAll("\t", "");
		text = text.replace("Back to top of document", "");
		System.out.println("Done...");
		return text;
		
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
	public static void main(String[] args) throws DocumentException {

		   PDFcreator creator = new PDFcreator();
		   /*HashMap<String , FVHashMap> vector=null;
		   try{
			   temp.init();
			   temp.collect();
			   vector = temp.getTextsVectors();
			   
		   }catch(IOException e){System.out.println(e.toString());}
		   System.out.println("Number of texts: " + temp.GetCounter());*/
		   try {
			   DB temp = new DB();
			   String url = "http://www.courts.ie/Judgments.nsf/597645521f07ac9a80256ef30048ca52/8e8929c277c2fe5e8025811a0057b95a?OpenDocument";
			   String title ="PDFs/" + temp.getTitle(url)+".pdf";
			   creator.pdfcreate(temp.getTableText(url),title);
			/*String str = temp.getTableText("http://www.courts.ie/Judgments.nsf/597645521f07ac9a80256ef30048ca52/8e8929c277c2fe5e8025811a0057b95a?OpenDocument");
			System.out.println(str);*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
}
