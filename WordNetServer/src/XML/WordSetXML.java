package XML;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import model.SynSetMap;

public class WordSetXML implements XML{
	
	public WordSetXML(){
		
	}
	private void export(HashSet<String> wordSet,String fileName){
		try{
			//root element
			Element wordsSet = new Element("wordsSet");
			Document doc = new Document(wordsSet);			
			Element Map;
			Element key;
			for(String keyname:wordSet){
				//Map element
				Map = new Element("Map");

				//key element
				key = new Element("key");

				keyname=keyname.trim();
				if(!keyname.equals("")) key.setText(keyname);
				Map.addContent(key);

				doc.getRootElement().addContent(Map);
			}


			XMLOutputter xmlOutput = new XMLOutputter();

			// display ml
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc,new FileWriter(fileName));
		}catch(IOException e){
			e.printStackTrace();
		}	
	}
	
	public HashSet<String> Import(String fileName){
		
		HashSet<String> res=new HashSet<String>();
		File inputFile = new File(fileName);
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = null;
		try {
			document = saxBuilder.build(inputFile);
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Element root = document.getRootElement();
		List<Element> mapList = root.getChildren();
		String key;
		for(Element map:mapList){
			key=map.getChild("key").getText().trim();
			if(!key.equals(""))res.add(key);
		}
		return res;
	}
	@Override
	public void export(Object object, String fileName) {
		HashSet<String> wordSet=(HashSet<String>) object;
		export(wordSet, fileName);		
	}
}
