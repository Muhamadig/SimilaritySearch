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

import model.RepWordMap;
import model.SynSetMap;

public class RepWordMapXML implements XML {

	public RepWordMapXML(){
		
	}
	
	private void export(RepWordMap repMap,String fileName){
		try{
			//root element
			Element repWordMap = new Element("RepWordMap");
			Document doc = new Document(repWordMap);			
			Element Map;
			Element key;
			Element value;
			for(HashSet<String> keyname:repMap.keySet()){
				//Map element
				Map = new Element("Map");

				//key element
				key = new Element("key");
				value = new Element("value");

				key.setText(keyname.toString());
				value.setText(repMap.get(keyname).toString());
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
	@Override
	public Object Import(String fileName) {
		RepWordMap res=new RepWordMap();
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
		
		HashSet<String> keySet;
		for(Element map:mapList){
			key=map.getChild("key").getText();
			value= map.getChild("value").getText().trim();
			key=key.substring(1, value.length()-1);
			String[] tokens=key.trim().split(",");
			keySet=new HashSet<>();
			for(String val:tokens) {
				if(!val.equals("")) keySet.add(val.trim());
			}
			res.put(keySet, value);
		}
		return res;
	}

	
	
	@Override
	public void export(Object object, String fileName) {
		RepWordMap repWordMap= (RepWordMap) object;
		export(repWordMap, fileName);
	}

}
