package DBModels;

import java.io.Serializable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "global")
public class DBGlobal implements Serializable {

	@DatabaseField(id = true)
	private String name;

	@DatabaseField(dataType = DataType.SERIALIZABLE, columnDefinition = "MEDIUMBLOB")
	private byte[] vector;

	@DatabaseField()
	private boolean upToDate;

	public DBGlobal() {
		// TODO Auto-generated constructor stub
	}

	public DBGlobal(String name, byte[] vector, boolean upToDate) {
		super();
		this.name = name;
		this.vector = vector;
		this.upToDate = upToDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getVector() {
		return vector;
	}

	public void setVector(byte[] vector) {
		this.vector = vector;
	}

	public boolean isUpToDate() {
		return upToDate;
	}

	public void setUpToDate(boolean upToDate) {
		this.upToDate = upToDate;
	}

}
