package Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Serialization {

	
	public static byte[] toByteArray(File file){
		
		byte[] res = new byte[(int) file.length()];

		FileInputStream fis=null;
		
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException in toByteArray");
			e.printStackTrace();
		}	
		try {
			fis.read(res);
			fis.close();

		} catch (IOException e) {
			System.err.println("IOException in toByteArray");
			e.printStackTrace();
		}
		return res;
	}
}
