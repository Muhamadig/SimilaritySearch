package XML;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class HashList implements XML {

	public HashList(){
		
	}
	private void export (TreeMap<Integer, ArrayList<String>> clusters, String fileName){
		try{
			//root element
			Element fvmap = new Element("Clusters_Vector");
			Document doc = new Document(fvmap);			
			Element Map;
			Element key;
			Element value;
			for(Integer ClusterNumber:clusters.keySet()){
				//Map element
				Map = new Element("Map");

				//key element
				key = new Element("key");
				key.setText(ClusterNumber +"");
				Map.addContent(key);
				
				
				ArrayList<String> values = clusters.get(ClusterNumber);
				for(String name : values){
				//value element
				value = new Element("value");
				value.setText(name);
				Map.addContent(value);
				}
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
		TreeMap<Integer, ArrayList<String>> res = new TreeMap<Integer, ArrayList<String>>();
		File inputFile = new File(fileName);
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = null;
		try {
			document = saxBuilder.build(inputFile);
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		Element root = document.getRootElement();
		List<Element> mapList = root.getChildren();
		Integer key;
		
		for(Element map:mapList){
			ArrayList<String> value = new ArrayList<String>();
			key=Integer.parseInt(map.getChild("key").getText());
			for(Element name : map.getChildren("value"))
				value.add(name.getText());
			
			res.put(key, value);
		}
		return res;
	}

	@Override
	public void export(Object object, String fileName) {
		@SuppressWarnings("unchecked")
		TreeMap<Integer, ArrayList<String>> clusters= (TreeMap<Integer, ArrayList<String>>) object;
		export(clusters,fileName);
		
	}

}
