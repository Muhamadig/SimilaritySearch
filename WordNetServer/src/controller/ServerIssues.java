/*package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.ISenseEntry;
import edu.mit.jwi.item.IWord;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class ServerIssues {
	
	/*
	 * to check:
	 * IDictionary.getIndexWordIterator(POS pos) 
          Returns an iterator that will iterate over all index words of the specified part of speech.
	 */
/*
	private IDictionary dictionary;
	private TreeMap<String, HashSet<String>> synonyms;
	private HashMap<HashSet<String>,HashSet<String>> similars;
	public ServerIssues(IDictionary dic){
		this.dictionary = dic;
		synonyms = new TreeMap<String,HashSet<String>>();
		similars = new HashMap<HashSet<String>,HashSet<String>>();
		}
	
	public void AllWordsSynonyms(){
		int count= 0,StemsCount=0;
		System.out.print ("\n Synonyms & Stemming ... \n");
		long t = System . currentTimeMillis ();
		Iterator <ISenseEntry> it = dictionary.getSenseEntryIterator();
		try{
			PrintWriter writer = new PrintWriter("EnglishWordsList/results1.txt", "UTF-8");
		while(it.hasNext()){
			count++;
			IWord Word = dictionary.getWord(it.next().getSenseKey());
			String Stem = Word.getLemma();
			Stemming stm = new Stemming(dictionary);
			Stem = stm.stem(Stem);
			if(!synonyms.containsKey(Stem)){
				StemsCount++;
				synonyms.put(Stem, stm.getAllSynonyms(dictionary, Stem));
			writer.println(Stem +": "+ synonyms.get(Stem));
			}
		}
		System.out.println("done ..."+(System.currentTimeMillis()-t));
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
		System.out.println("\nThere is "+count+" word\n");
		System.out.println("\nThere is "+StemsCount+" different stems\n");
	}
	
	public void MakeSymatric() throws FileNotFoundException, UnsupportedEncodingException{
		try{
		BufferedReader br = new BufferedReader(new FileReader("EnglishWordsList/results2.txt"));     
		if (br.readLine() == null) 
		    Symatric();
		else
		{
			BufferedReader br1 = new BufferedReader(new FileReader("EnglishWordsList/results2.txt")); 
			getData(br);
			br1.close();
		}
		br.close();
		}catch(IOException e){
			Symatric();
		}
	}
	public void getData(BufferedReader file ) throws IOException{
		String line;
		HashSet<String> syns=new HashSet<String>();
		while((line=file.readLine()) != null){
			String [] splited = line.split(":");
			splited[1]=splited[1].substring(1, splited[1].length()-1);
			String [] syn = splited[1].split(",");
			for(String word : syn)
				syns.add(word.trim());
			synonyms.put(splited[0], syns);
		}
	}
	public void Symatric() throws FileNotFoundException, UnsupportedEncodingException{
		System.out.print ("\n Symatric ... \n");
		long t = System . currentTimeMillis ();
		PrintWriter writer = new PrintWriter("EnglishWordsList/results2.txt","UTF-8");
		for(String word : synonyms.keySet()){
			HashSet<String> syns = synonyms.get(word);
			if(syns.isEmpty())
				syns.add(word);
			else{
				Iterator<String> it = syns.iterator();
				while(it.hasNext()){
					String syn = it.next();
					if(synonyms.containsKey(syn))
						if(!synonyms.get(syn).contains(word))
							synonyms.get(syn).add(word);
				}
			}
			
			writer.println(word +": " + synonyms.get(word));
		}
		System.out.println("done ..."+(System.currentTimeMillis()-t));
	    writer.close();
	}
	
	public void getSimilar() throws FileNotFoundException, UnsupportedEncodingException {
		System.out.print ("\n Similars ... \n");
		long t = System . currentTimeMillis ();
		try{
		BufferedReader br = new BufferedReader(new FileReader("EnglishWordsList/results3.txt")); 
		if(br.readLine() == null)
			CollectSimilars();
		else{
			BufferedReader br1 = new BufferedReader(new FileReader("EnglishWordsList/results3.txt"));
			ReadSimilars(br1);
			br1.close();
		}
		br.close();
		}catch(IOException e){
			CollectSimilars();
		}
		System.out.println("done ..."+(System.currentTimeMillis()-t));
	}
	public void CollectSimilars() throws FileNotFoundException, UnsupportedEncodingException{

		TreeMap<String, HashSet<String>> copy = (TreeMap<String, HashSet<String>>) synonyms.clone();
		HashMap<String,Boolean> checkList = new HashMap<String,Boolean>();
		for(String key:copy.keySet()){
			checkList.put(key, false);
		}
		HashSet<String> values;
		HashSet<String> keys = new HashSet<String>();
		PrintWriter writer = new PrintWriter("EnglishWordsList/results3.txt","UTF-8");
		Set<String> k = copy.keySet();
		for(String key: k){
			values = copy.get(key);
			for(String key1 : k){
				if(!checkList.get(key1) && values.equals(copy.get(key1))){
					checkList.replace(key1, false, true);
					keys.add(key1);
				}
			}
			similars.put(keys, values);
			writer.println(keys +": " + values);
			keys.clear();
		}
		writer.close();
	}
	public void ReadSimilars(BufferedReader file) throws IOException{
		String line;
		ArrayList<HashSet<String>> rows = new ArrayList<HashSet<String>>();
		HashSet<String> temp = new HashSet<String>();
		while((line=file.readLine()) != null){
			String [] splited = line.split(":");
			for(int i =0;i<splited.length;i++){
				splited[i] = splited[i].substring(1, splited[i].length()-1);
				String [] sim = splited[i].split(",");
				for(String word : sim){
					String toadd = word.trim();
					temp.add(toadd);
				}
				rows.add(temp);
				temp.clear();
			}
			
			similars.put(rows.get(0), rows.get(1));
		}
	}
}
*/