import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Stam {

	public static void main(String[] args){
		DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH,mm,ss");
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
		}
	}

}
