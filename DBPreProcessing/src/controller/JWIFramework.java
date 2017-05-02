package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.IRAMDictionary;
import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;

public class JWIFramework {

	
	public JWIFramework(){
		
	}
	
	public static IDictionary loadToRam() throws IOException, InterruptedException{
		String wnhome = System.getenv("WNHOME");
		String path = File.separator + "dict";
		URL url = new URL("file", null , path );
		File dir=new File("dict");
		IRAMDictionary dict = new RAMDictionary(dir,  ILoadPolicy . NO_LOAD );
				
		dict . open ();
				
		System .out . print ("\n Loading Wordnet into memory ... ");
		long t = System . currentTimeMillis ();
		dict . load ( true );
		System.out.println("done "+(System.currentTimeMillis()-t));
		return dict;
		
	}
	
	
}
