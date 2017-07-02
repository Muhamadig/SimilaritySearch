package DBModels;


import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="clusters")
public class DBCluster implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DatabaseField(id=true)
	private int id;
	
	@DatabaseField()
	private String commonWordsFV_name;
	
	@DatabaseField()
	private String globalWordsFV_name;
	
	public String getGlobalWordsFV_name() {
		return globalWordsFV_name;
	}


	public void setGlobalWordsFV_name(String globalWordsFV_name) {
		this.globalWordsFV_name = globalWordsFV_name;
	}


	public DBCluster() {
		// TODO Auto-generated constructor stub
	}


	public DBCluster(int id, String commonWordsFV_name,String globalWordsFV_name) {
		super();
		this.id = id;
		this.commonWordsFV_name = commonWordsFV_name;
		this.globalWordsFV_name=globalWordsFV_name;
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
