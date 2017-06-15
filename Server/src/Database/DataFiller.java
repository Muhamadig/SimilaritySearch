package Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.sql.rowset.serial.SerialBlob;

import DBModels.DBText;

public class DataFiller {

	DbHandler db;
	

	public DataFiller(DbHandler d) {
		db = d;
	}
	
	
	public void test() throws IOException {
		File textFile=new File("A.O. -v- Refugee Appeals Tribunal & ors.html");
		File fv=new File("A.O. -v- Refugee Appeals Tribunal & ors.html.xml");
		
		
		byte[] bytesArray = new byte[(int) textFile.length()];

		FileInputStream file1=null;
		try {
			file1 = new FileInputStream(textFile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			file1.read(bytesArray);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //read file into bytes[]
		try {
			file1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Blob blob1= new SerialBlob(bytesArray);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DBText text=new DBText("A.O. -v- Refugee Appeals Tribunal & ors.html", bytesArray, bytesArray, 1);
		
		try {
			db.texts.create(text);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		FileOutputStream f=new FileOutputStream("output.html");
		f.write(bytesArray);
	}

}
