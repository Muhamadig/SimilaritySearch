package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class DB {

	public int count;
	private HashMap<String,String> htmls;
	private ArrayList<String> links;
	private HashMap <String,String > tables;
	private String [] pages ={
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
		tables = new HashMap<String,String>();
		links = new ArrayList<String>();
		htmls = new HashMap<String,String>();
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
	
	public void init(){
		long t = System.currentTimeMillis();
		System.out.println("Initialize...");
		LoadLinks();
		
		System.out.println("Done ... "+(System.currentTimeMillis()-t));
	}
	
	public void getLinks(String url) throws IOException{
		Document document = Jsoup.connect(url).get();
		Element table = document.select("table").get(9);
		Elements links = table.getElementsByTag("a");
		int len = links.size();
		for(int i=0;i<len;i++){
			String link ="http://www.courts.ie"+ links.get(i).attr("href");
			this.links.add(link);
		}
		//System.out.println("Done getting links");
	}

	public ArrayList<String> GetHtmlandText(String URL){
		System.out.println(URL);
		ArrayList<String> results = null;
		Document document;
		try {
			document = Jsoup.connect(URL).get();
			Element table = document.body().select("table").get(8);
			String html = table.html();
			Elements trs = table.select("tr");
			Element tr = trs.get(1);
			Element td = tr.select("td").first();
			Elements childs = td.children();
			String text="";
			for(Element child : childs)
				if (!child.is("img"))
					text+=child.text()+"\n";
			text = text.replaceAll("\t", "");
			text = text.replace("Back to top of document", "");
			results = new ArrayList<String>();
			results.add(text);
			results.add(html);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
		
	}
	
	public void GenerateHTML(String title,String table) throws IOException{
		//System.out.println(title);
		File file = new File("HTMLs/"+title.replaceAll("/", "")+".html");
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
		fileWriter = new FileWriter(file);
		bufferedWriter = new BufferedWriter(fileWriter);
		String htmlPage = "<html><body style=’background-color:#ccc’><center>" + table + "</center></body></html>" ;
		bufferedWriter.write(htmlPage);
		count++;
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void loadtables(String URL) throws IOException{
		Document document = Jsoup.connect(URL).get();
		Element bigtd = document.body().getElementsByClass("viewtable").first();
		Element table = bigtd.select("table").first();
		Elements tr = table.select("tr");
		for(int i=4;i<tr.size();i++){
			Elements tds= tr.get(i).select("td");
			if(tds.size() > 2){
			Element td = tds.get(2);
			String link ="http://www.courts.ie"+ td.select("a").attr("href");
			String title = tds.get(3).text();
			tables.put(title, link);
			}
		}
	}

	public void loadhtml() throws IOException{
		for(String key: tables.keySet()){
			ArrayList <String> TextHtml = GetHtmlandText(tables.get(key));
			if(TextHtml != null){
			htmls.put(TextHtml.get(0), TextHtml.get(1));
			GenerateHTML(key,TextHtml.get(1));}
		}
	}
	
	public String [] getlinks(){ return this.pages;}
	
	public static void main(String[] args) throws IOException{
		DB temp = new DB();
		String [] pgs = temp.getlinks();
		long t = System.currentTimeMillis();
		System.out.println("Start generating HTMLs:");
		for(String link : pgs){
			temp.loadtables(link);
			temp.loadhtml();
		}
		System.out.println("Done ... " + ((System.currentTimeMillis()-t)/1000) +" secs");
		System.out.println(temp.count + " files has generated");
	   }
	}