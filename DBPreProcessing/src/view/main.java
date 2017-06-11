package view;


import XML.XML;
import XML.XMLFactory;
import model.FVHashMap;
import model.FVKeySortedMap;

public class main {

	public static void main(String[] args){
		String initial_Path="";
		String final_path="";
		
		XML iFV=XMLFactory.getXML(XMLFactory.FV);
		XML fFV=XMLFactory.getXML(XMLFactory.FVSortedMap);
		
		FVHashMap fv=(FVHashMap) iFV.Import(initial_Path+"");
		FVKeySortedMap finalFV=(FVKeySortedMap) fFV.Import(final_path+"");
		FVHashMap test=new FVHashMap();
		for(String key:finalFV.keySet()){
			if(finalFV.get(key).compareTo(0)!=0) test.put(key, finalFV.get(key));
		}
		
		iFV.export(test, "test.xml");
		System.out.println(fv.equals(test));
	}
}

 