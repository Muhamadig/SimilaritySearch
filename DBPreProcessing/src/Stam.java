import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import XML.XML;
import XML.XMLFactory;
import model.FVValueSorted;

public class Stam {

	public static void main(String[] args){
		/*DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH,mm,ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		String path = System.getProperty("user.home") + "/Desktop/Results/"+dateFormat.format(date);
		File file = new File (path);
		file.mkdirs();
		System.out.println(file.getAbsolutePath());
		File f = new File(System.getProperty("user.home") + "/Desktop/Search for similar texts in professional databases-R1.pdf");
		try {
		
			Files.copy(f.toPath(),
				        (new File(path+"/" + f.getName())).toPath(),
				        StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		XML SortXML = XMLFactory.getXML(XMLFactory.FV_ValueSorted);
		
		 
	      try
	      {
	    	  for(int i=0;i<6;i++){
	    		  Document document = new Document();
	    		  FVValueSorted sorted = (FVValueSorted) SortXML.Import("CW/"+i+"_CW.xml");
	         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(i+"_CW.pdf"));
	         document.open();
	         for(int j=0;j<sorted.size();j++)
	        	 document.add(new Paragraph((j+1) +")          " + sorted.get(j).getKey() +"  =  " + sorted.get(j).getValue()));
	    	  
	         document.close();
	         writer.close();
	    	  }
	      } catch (DocumentException e)
	      {
	         e.printStackTrace();
	      } catch (FileNotFoundException e)
	      {
	         e.printStackTrace();
	      }
	}
	
	
	  private static void CreatePDF(FVValueSorted CW, int id){
	    	 try
		      {
		    		  Document document = new Document();
		    		 
		         PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(id+"_CW_Diff.pdf"));
		         document.open();
		         for(int j=0;j<100;j++)
		        	 document.add(new Paragraph((j+1) +")          " + CW.get(j).getKey() +"  =  " + CW.get(j).getValue()));
		    	  
		         document.close();
		         writer.close();
		      } catch (DocumentException e)
		      {
		         e.printStackTrace();
		      } catch (FileNotFoundException e)
		      {
		         e.printStackTrace();
		      }
	    }

}
