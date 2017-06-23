package Database;



import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import DBModels.DBCluster;
import DBModels.DBGlobal;
import DBModels.DBText;


/**
 * Database Handler, the main class that intializes, fills and controls the
 * database using DAO objects.
 * 
 * @author aj_pa
 *
 */
public class DbHandler {

	private ConnectionSource connection;
	
	public Dao<DBText,String> texts;
	
	public Dao<DBCluster,Integer> clusters;
	
	public Dao<DBGlobal, String> global;

	/**
	 * need to provide url , user ,pass to conenct to database
	 * 
	 * @param url
	 * @param username
	 * @param password
	 */
	public DbHandler(String url, String username, String password) {
		try {
			connection = new JdbcConnectionSource(url, username, password);
		//	createAllTables();
			initializeDao();
		//	fillDataBase();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * fills the database with random data.
	 * 
	 * @throws Exception
	 */
	public void fillDataBase() throws Exception {
		DataFiller df = new DataFiller(this);
		
	}

	/**
	 * initializes all the DAO for ORM usage
	 * 
	 * @throws Exception
	 */
	public void initializeDao() throws Exception {		
		texts=DaoManager.createDao(connection, DBText.class);
		clusters=DaoManager.createDao(connection, DBCluster.class);
		global=DaoManager.createDao(connection, DBGlobal.class);

	}

	
	
	/**
	 * creates all the tables using ORM, it also drops the tables first
	 * 
	 * @throws Exception
	 */
	public void createAllTables() throws Exception {
		TableUtils.dropTable(connection, DBText.class, true);
		TableUtils.dropTable(connection, DBCluster.class, true);
		TableUtils.dropTable(connection, DBGlobal.class, true);

		TableUtils.createTable(connection, DBCluster.class);
		TableUtils.createTable(connection, DBText.class);
		TableUtils.createTable(connection, DBGlobal.class);

		
	}
}
