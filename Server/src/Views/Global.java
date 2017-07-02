package Views;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import Utils.Request;
import XML.XML;
import XML.XMLFactory;
import model.FVValueSorted;

public class Global extends View  {
	XML valueSortedXML=XMLFactory.getXML(XMLFactory.FV_ValueSorted);

//	
//	r.addParam("DBGlobalFV", globalFV);
//	r.addParam("DBCommonFV", commonFV);
	public Object create(Request request){
		byte[] globalFV=(byte[]) request.getParam("DBGlobalFV");
		byte[] commonFV=(byte[]) request.getParam("DBCommonFV");
		
		File dbfv=new File("DB Data Files");
		dbfv.mkdirs();
		FileOutputStream f;
		try {
			f = new FileOutputStream("DB Data Files"+ File.separator+ "globalFV.xml");
			f.write(globalFV);
			f.close();
			
			f = new FileOutputStream("DB Data Files"+ File.separator+ "commonFV.xml");
			f.write(commonFV);
			f.close();
			return 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;

	}

	
	public FVValueSorted getCommonVector()   {
		

		FVValueSorted commonVector=(FVValueSorted) valueSortedXML.Import("DB Data Files"+ File.separator+ "commonFV.xml");
		return commonVector;

	}

	public FVValueSorted getGlobalVector()  {
		
		FVValueSorted globalVector=(FVValueSorted) valueSortedXML.Import("DB Data Files"+ File.separator+ "globalFV.xml");
		return globalVector;

	}

}
