package DBModels;


import java.io.Serializable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="clusters")
public class DBCluster implements Serializable {
	
	@DatabaseField(id=true)
	private int id;
	
	@DatabaseField()
	private String commonWords_name;
	
	@DatabaseField()
	private boolean commonWords_upToDate;
	
	@DatabaseField(dataType = DataType.SERIALIZABLE,columnDefinition="MEDIUMBLOB")
	private byte[] commonWords;
	
	public DBCluster() {
		// TODO Auto-generated constructor stub
	}

	public DBCluster(int id, String commonWords_name, boolean commonWords_upToDate, byte[] commonWords) {
		super();
		this.id = id;
		this.commonWords_name = commonWords_name;
		this.commonWords_upToDate = commonWords_upToDate;
		this.commonWords = commonWords;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCommonWords_name() {
		return commonWords_name;
	}

	public void setCommonWords_name(String commonWords_name) {
		this.commonWords_name = commonWords_name;
	}

	public boolean isCommonWords_upToDate() {
		return commonWords_upToDate;
	}

	public void setCommonWords_upToDate(boolean commonWords_upToDate) {
		this.commonWords_upToDate = commonWords_upToDate;
	}

	public byte[] getCommonWords() {
		return commonWords;
	}

	public void setCommonWords(byte[] commonWords) {
		this.commonWords = commonWords;
	}

	
}
