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
	private String commonWordsFV_name;
	
	
	public DBCluster() {
		// TODO Auto-generated constructor stub
	}


	public DBCluster(int id, String commonWordsFV_name) {
		super();
		this.id = id;
		this.commonWordsFV_name = commonWordsFV_name;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCommonWordsFV_name() {
		return commonWordsFV_name;
	}


	public void setCommonWordsFV_name(String commonWordsFV_name) {
		this.commonWordsFV_name = commonWordsFV_name;
	}

}
