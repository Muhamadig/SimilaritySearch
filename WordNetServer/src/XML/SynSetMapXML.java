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

public class SynSetMapXML implements XML{
	
	public SynSetMapXML(){
		
	}
	private void export(SynSetMap synset,String fileName){
		try{
			//root element
			Element synSetMap = new Element("synSetMap");
			Document doc = new Document(synSetMap);			
			Element Map;
			Element key;
			Element value;
			for(String keyname:synset.keySet()){
				//Map element
				Map = new Element("Map");

				//key element
				key = new Element("key");
				value = new Element("value");

				key.setText(keyname);
				value.setText(synset.get(keyname).toString());
				Map.addContent(key);
				Map.addContent(value);

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
	
	public SynSetMap Import(String fileName){
		
		SynSetMap res=new SynSetMap();
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
		String key,value;
		HashSet<String> set =null;
		for(Element map:mapList){
			key=map.getChild("key").getText().trim();
			value= map.getChild("value").getText();
			value=value.substring(1, value.length()-1);
			String[] tokens=value.trim().split(",");
			set=new HashSet<>();
			for(String val:tokens) {
				if(!val.equals("")) set.add(val.trim());
			}
			res.put(key, set);

		}
		
		return res;
		
	}
	@Override
	public void export(Object object, String fileName) {
		SynSetMap synset=(SynSetMap) object;
		export(synset, fileName);
	}
}
