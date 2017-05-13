package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
	private ArrayList<String> links;
	private ArrayList <String> texts;
	private HashMap<String , FVHashMap> freqvec; 
	private final String [] pages ={
			"http://www.courts.ie/Judgments.nsf/frmJudgmentsByYearAll?OpenForm&ExpandView&Seq=1",
			"http://www.courts.ie/Judgments.nsf/frmJudgmentsByYearAll?OpenForm&Start=1.1.28&ExpandView&Seq=2",
			"http://www.courts.ie/Judgments.nsf/frmJudgmentsByYearAll?OpenForm&Start=1.1.57&ExpandView&Seq=3", 
			"http://www.courts.ie/Judgments.nsf/frmJudgmentsByYearAll?OpenForm&Start=1.1.86&ExpandView&Seq=4",
			"http://www.courts.ie/Judgments.nsf/frmJudgmentsByYearAll?OpenForm&Start=1.1.115&ExpandView&Seq=5",
			"http://www.courts.ie/Judgments.nsf/frmJudgmentsByYearAll?OpenForm&Start=1.1.115&ExpandView&Seq=6",
			"http://www.courts.ie/Judgments.nsf/frmJudgmentsByYearAll?OpenForm&Start=1.3.1&ExpandView&Seq=7",
			"http://www.courts.ie/Judgments.nsf/frmJudgmentsByYearAll?OpenForm&Start=1.3.30&ExpandView&Seq=8",
			"http://www.courts.ie/Judgments.nsf/frmJudgmentsByYearAll?OpenForm&Start=1.3.59&ExpandView&Seq=9",
			"http://www.courts.ie/Judgments.nsf/frmJudgmentsByYearAll?OpenForm&Start=1.3.88&ExpandView&Seq=10",
			"http://www.courts.ie/Judgments.nsf/frmJudgmentsByYearAll?OpenForm&Start=1.3.117&ExpandView&Seq=11",
			"http://www.courts.ie/Judgments.nsf/frmJudgmentsByYearAll?OpenForm&Start=1.3.146&ExpandView&Seq=12",
			"http://www.courts.ie/Judgments.nsf/frmJudgmentsByYearAll?OpenForm&Start=1.3.175&ExpandView&Seq=13",
			"http://www.courts.ie/Judgments.nsf/frmJudgmentsByYearAll?OpenForm&Start=1.3.204&ExpandView&Seq=14",
			"http://www.courts.ie/Judgments.nsf/frmJudgmentsByYearAll?OpenForm&Start=1.4.9&ExpandView&Seq=15"};
	public DB(){
		texts = new ArrayList<String>();
		links = new ArrayList<String>();
		freqvec = new HashMap <String,FVHashMap>();
		
	}
	

	public void LoadLinks(){
		for(String link : pages){
			try {
				getLinks(link);
			} catch (IOException e) {
				System.out.println("Bad Link");
			}
		}
	}
	
	public void init() throws IOException{
		long t = System.currentTimeMillis();
		System.out.println("Initialize...");
		LoadLinks();
		Iterator<String> it = links.iterator();
		while(it.hasNext()){
			getText(it.next());
			NOtexts++;
		}
		System.out.println("Done ... "+(System.currentTimeMillis()-t));
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
	
	public void getLinks(String url) throws IOException{
		Document document = Jsoup.connect(url).followRedirects(false).timeout(60000/*wait up to 60 sec for response*/).get();
		Element table = document.select("tabele").get(9);
		Elements links = table.getElementsByTag("a");
		int len = links.size();
		for(int i=0;i<len;i++){
			String link ="http://www.courts.ie"+ links.get(i).attr("href");
			this.links.add(link);
		}
		//System.out.println("Done getting links");
	}

	public void GenerateHTML(String table) throws IOException{
		File file = new File("HTMLs/test.html");
			
			FileWriter fileWriter = null;
			BufferedWriter bufferedWriter = null;
			try {
			fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);
			String htmlPage = "<html><body style=’background-color:#ccc’><center>" + table + "</center></body></html>" ;
			bufferedWriter.write(htmlPage);
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
	}
	public String getTableHTML(String URL)throws IOException{
		Document document = Jsoup.connect(URL).followRedirects(false).timeout(60000/*wait up to 60 sec for response*/).get();
		Element table = document.body().select("table").get(8);
		String html = table.html();
		return html;
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
	public static void main(String[] args) throws DocumentException, IOException {

		   PDFcreator creator = new PDFcreator();
		   //	   FileInputStream file = new FileInputStream("HTMLs/test.html");
		  BufferedReader buff = new BufferedReader(new FileReader(new File("HTMLs/test.html")));
		   String html="";
		   String temp;
		   while((temp=buff.readLine())!=null)
			   html+=temp;
		  creator.pdfconvertor(html);
		  /* DB temp = new DB();
		   String text = temp.getTableHTML("http://www.courts.ie/Judgments.nsf/597645521f07ac9a80256ef30048ca52/95044a0f312a3c388025811e003a218e?OpenDocument");
		   temp.GenerateHTML(text);*/
		  /* String url = "http://www.courts.ie/Judgments.nsf/597645521f07ac9a80256ef30048ca52/8e8929c277c2fe5e8025811a0057b95a?OpenDocument";
		   String title ="PDFs/" + temp.getTitle(url)+".pdf";
		   creator.pdfcreate(temp.getTableText(url),title);*/
		/*String str = temp.getTableText("http://www.courts.ie/Judgments.nsf/597645521f07ac9a80256ef30048ca52/8e8929c277c2fe5e8025811a0057b95a?OpenDocument");
		System.out.println(str);*/
	    }
}
