package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.itextpdf.text.DocumentException;

import Controller.ReadFile;
import model.FVHashMap;



public class HTMLsDownload {
	private static int count=0;
	private HashMap<String,FVHashMap> texts;
	private HashMap<ArrayList<String>,String> htmls; // this hashmap contains the texts and there's html code
	private HashMap <String,String > tables; // this hashmap contains the title of the document and it's link
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
	

	public HTMLsDownload(){
		tables = new HashMap<String,String>();
		htmls = new HashMap<ArrayList<String>,String>();
		texts = new HashMap<String,FVHashMap>();
	}
	
	public void DownloadAllPages() throws IOException{
		System.out.println("Start Downloding...");
		System.out.println("Loading tables ...");
		long t = System.currentTimeMillis();
		for(String link :pages)
			loadtables(link);
		System.out.println("Done loading ... " + ((System.currentTimeMillis()-t)/1000) + " Seconds");
		System.out.println("Start Creating ...");
		t=System.currentTimeMillis();
		Set<String> keys = tables.keySet();
		for(String key : keys){
			if(!key.equals(""))createhtml(tables.get(key) , key);
		}
		System.out.println("Done Creating ... " + ((System.currentTimeMillis()-t)/1000) + " Seconds");
	}
	/*
	 * @Param URL: the url of web page (our DB) that contains text.
	 * the function returns arraylist that contains the document text and the page's HTML code
	 * */
	public HashMap<ArrayList<String>, Element> GetHtmlandText(String file) throws IOException{
		System.out.println(file);
		//ArrayList<String> HTML = new ArrayList<String>();
		HashMap<ArrayList<String>, Element> res = new HashMap<ArrayList<String>,Element>();
		ArrayList<String> text = new ArrayList<String>();
		
		Document document;
		File input = new File("AllPages/"+file);
	
		document = Jsoup.parse(input,"UTF-8");
		//System.out.println(document.body().html());
		Element table = document.body().select("table").get(8);
		//String html = table.html();
//		Elements trs = table.select("tr");
//		Element tr = trs.get(1);
//		Element td = tr.select("td").first();
		Elements childs = table.children();
//			String text="";
		for(Element child : childs)
			if (!child.is("img"))
				text.add(child.text());
	//	text.remove("\t");
		text.remove("Back to top of document");
		text.toString();
//		for(Element child : table.children()){
//			HTML.add(child.html());
//		}
		res.put(text, table);
		return res;
		
	}
	
	/*
	 * @Param title: the html file name
	 * @Param table: the html code that contains the document as a html table.
	 * 
	 * the function creats html file and put it in HTMLs folder.
	 * */
	public void GenerateHTML(String title,Element table) throws IOException{
		//System.out.println(title);
		File file = new File("HTMLs/"+title.replaceAll("/", ""));
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			//System.out.println(table.html());
		fileWriter = new FileWriter(file);
		bufferedWriter = new BufferedWriter(fileWriter);
		/*String htmlPage = "<html><body style=’background-color:#ccc’><center>";
		Iterator <String> element = table.iterator();
		while(element.hasNext()){
		htmlPage+=element.next();
		
		}
		htmlPage+="</center></body></html>";*/
		
		bufferedWriter.write("<html><body style=’background-color:#ccc’><center>" + table.html()+"</center></body></html>");
		bufferedWriter.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void load() throws IOException{
		System.out.println("Start loading ...");
		long t = System.currentTimeMillis();
		File folder = new File("AllPages");
		if(folder.isDirectory())
			if(folder.list().length > 0 ){
				String [] files = folder.list();
				for(String file : files){
					HashMap<ArrayList<String>,Element> res = GetHtmlandText(file);
					Element html = res.get(res.keySet().iterator().next());
					GenerateHTML(file,html);
				}
			}
			else{
				for(String link : pages){
					DownloadAllPages();
				}
			}
		else
			System.out.println("bad directory");
		System.out.println("Done ... " + (System.currentTimeMillis() - t));
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
			String title = tds.get(3).text().replace("/", "");
			tables.put(title, link);
			}
		}
	}

	public void createhtml(String url,String title) throws IOException{
		count++;
		System.out.println("("+count+") " +title);
		URL link = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(link.openStream()));
		String line;
		PrintWriter out = new PrintWriter("AllPages/"+title+".html");
		while((line = in.readLine())!=null){
			out.println(line);
		}
		out.close();
	}
	/*
	public void loadhtml() throws IOException{
		for(String key: tables.keySet()){
			HashMap<ArrayList <String>,ArrayList<String>> TextHtml = GetHtmlandText(tables.get(key));
			if(TextHtml != null){
			//htmls.putAll(TextHtml);
			ArrayList<String> text = TextHtml.keySet().iterator().next();
			GenerateHTML(key,TextHtml.get(text));
			}
		}
	}*/
	
	
	public static void main(String[] args) throws IOException, DocumentException{
		HTMLsDownload temp = new HTMLsDownload();
		//temp.DownloadAllPages();
		//temp.load();
		ReadFile read = new ReadFile();
		String text = read.ReadFile("Vodafone GMBH -v- IV International Leasing & Anor.html", "html");
		System.out.println(text.split(" ").length);
		
//		File folder = new File("HTMLs");
//		File input = new File("HTMLs/"+folder.list()[2]);
//		Document doc = Jsoup.parse(input,"UTF-8");
//		Element table = doc.select("table").first();
//		Element div = doc.getElementById("itabs");
//		temp.FrequencyVectors(table.text() +"\n" + div.text());
	}
}
