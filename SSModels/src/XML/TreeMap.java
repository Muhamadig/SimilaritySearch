package XML;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import model.FVKeySortedMap;

public class TreeMap implements XML {

	
	public TreeMap() {
		// TODO Auto-generated constructor stub
	}
	
	private void export(FVKeySortedMap fv,String fileName){
		try{
			//root element
			Element fvmap = new Element("Frequecny_Vector");
			Document doc = new Document(fvmap);			
			Element Map;
			Element key;
			Element value;
			for(String keyWord:fv.keySet()){
				//Map element
				Map = new Element("Map");

				//key element
				key = new Element("key");

				//value element
				value = new Element("value");
				
				keyWord=keyWord.trim();
				key.setText(keyWord);
				
				value.setText(Integer.toString(fv.get(keyWord)));
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
		FVKeySortedMap res=new FVKeySortedMap();
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
		Integer value;
//		HashSet<String> set =null;
		for(Element map:mapList){
			key=map.getChild("key").getText().trim();
			value= Integer.parseInt(map.getChild("value").getText());
			
			res.put(key, value);
		}
		
		return res;
	}

	@Override
	public void export(Object object, String fileName) {
		FVKeySortedMap fv=(FVKeySortedMap) object;
		export(fv, fileName);;
	}

}
