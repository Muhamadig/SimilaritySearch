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

public class WordSetXML {
	public static void export(HashSet<String> wordSet,String fileName){
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

				key.setText(keyname);
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
	
	public static HashSet<String> Import(String fileName){
		
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
		HashSet<String> set =null;
		for(Element map:mapList){
			res.add(map.getChild("key").getText());
		}
		return res;
	}
}
