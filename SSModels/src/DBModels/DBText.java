package DBModels;

import java.io.Serializable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName="texts")
public class DBText implements Serializable{
	
	@DatabaseField(id=true)
	private String name;
	
	@DatabaseField(dataType = DataType.SERIALIZABLE,columnDefinition="MEDIUMBLOB")
	private byte[] textFile;
	
	@DatabaseField()
	private String finalFV_name;
	
	@DatabaseField(foreign=true)
	private DBCluster clusterId;
	
	public DBText() {
		// TODO Auto-generated constructor stub
	}

	public DBText(String name, byte[] textFile, String finalFV_name, DBCluster clusterId) {
		super();
		this.name = name;
		this.textFile = textFile;
		this.finalFV_name = finalFV_name;
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

	public String getFinalFV_name() {
		return finalFV_name;
	}

	public void setFinalFV_name(String finalFV_name) {
		this.finalFV_name = finalFV_name;
	}

	public DBCluster getClusterId() {
		return clusterId;
	}

	public void setClusterId(DBCluster clusterId) {
		this.clusterId = clusterId;
	}

	
}