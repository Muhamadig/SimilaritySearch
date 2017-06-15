package DBModels;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName="texts")
public class DBText {
	
	@DatabaseField(id=true)
	private String name;
	
	@DatabaseField(dataType = DataType.SERIALIZABLE,columnDefinition="MEDIUMBLOB")
	private byte[] textFile;
	
	@DatabaseField(dataType = DataType.SERIALIZABLE,columnDefinition="MEDIUMBLOB")
	private byte[] finalFV;
	
	@DatabaseField
	private int clusterId;
	
	public DBText() {
		// TODO Auto-generated constructor stub
	}

	public DBText(String name, byte[] textFile, byte[] finalFV, Integer clusterId) {
		super();
		this.name = name;
		this.textFile = textFile;
		this.finalFV = finalFV;
		this.clusterId = clusterId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getTextFile() {
		return textFile;
	}

	public void setTextFile(byte[] textFile) {
		this.textFile = textFile;
	}

	public byte[] getFinalFV() {
		return finalFV;
	}

	public void setFinalFV(byte[] finalFV) {
		this.finalFV = finalFV;
	}

	public int getClusterId() {
		return clusterId;
	}

	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}
	
	
}
