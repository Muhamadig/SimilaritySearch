package XML;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import model.FVValueSorted;
import model.MyEntry;

public class FV_ValueSorted_XML implements XML {

	
	public FV_ValueSorted_XML() {
		// TODO Auto-generated constructor stub
	}
	
	private void export(FVValueSorted fv,String fileName){
		try{
			//root element
			Element fvmap = new Element("Frequecny_Vector");
			Document doc = new Document(fvmap);			
			Element Map;
			Element key;
			Element value;
			for(Entry<String,Integer> entry:fv){
				//Map element
				Map = new Element("Map");

				//key element
				key = new Element("key");

				//value element
				value = new Element("value");
				
				String keyWord=entry.getKey().trim();
				key.setText(keyWord);
				
				value.setText(Integer.toString(entry.getValue()));
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
		FVValueSorted res=new FVValueSorted();
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

		for(Element map:mapList){
			key=map.getChild("key").getText().trim();
			value= Integer.parseInt(map.getChild("value").getText());
			
			res.add(new MyEntry(key, value));
		}
		
		return res;
	}

	@Override
	public void export(Object object, String fileName) {
		FVValueSorted fv=(FVValueSorted) object;
		export(fv, fileName);;
	}

}
