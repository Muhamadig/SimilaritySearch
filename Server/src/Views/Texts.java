package Views;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;

import DBModels.DBCluster;
import DBModels.DBText;
import Database.DbHandler;
import Server.Config;
import Utils.Request;

public class Texts extends View {


	DbHandler db = Config.getConfig().getHandler();

	/*
	 * CRUD:
	 * Create
	 * Read
	 * Update
	 * Delete
	 */

	/**
	 * Add new Text to database or update it if exist
	 * @param request 	<br>key=text for the {@link DBText} object which the cluster instant sent as null. <br>
	 * 					key=clusterID which is the id if the cluster that the text belongs to.
	 * @return : if success return 1 ,else 0;
	 * @author Muhamad Igbaria
	 */
	public Object create(Request request){
		DBText text=(DBText) request.getParam("text");
		int clusterID=(int) request.getParam("clusterID");
		
		Clusters clustersView=new Clusters();
		request.addParam("id", clusterID);
		DBCluster cluster=(DBCluster) clustersView.read(request);
		
		text.setClusterId(cluster);
		
		QueryBuilder<DBText, String> q=db.texts.queryBuilder();
		
		try {
			if(q.where().idEq(text.getName()).countOf()>0) return db.texts.update(text);
			return db.texts.create(text);
		} catch (SQLException e) {
			System.err.println("SQL Exception in create new text");
			e.printStackTrace();
		}
		
		return 0;
		
	}


	/**
	 * get text by id
	 * @param request:param name=id
	 * @return: DBText if founded else null.
	 * @author Muhamad Igbaria
	 */
	public Object read(Request request){
		try {
			return db.texts.queryForId((String)request.getParam("id"));
		} catch (SQLException e) {
			System.err.println("SQL Exception in read text by id");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * update exists text
	 * @param request
	 * @return :if success return 1 ,else 0;
	 * @author Muhamad Igbaria
	 */
	public Object update(Request request){

		
		DBText text=new DBText((String)request.getParam("name"),(byte[]) request.getParam("textFile"), 
				(String)request.getParam("finalFV_name"),(boolean)request.getParam("fV_upToDate") ,
				(byte[])request.getParam("finalFV"),(DBCluster) request.getParam("clusterId"));
		
		try {
			return db.texts.update(text);
		} catch (SQLException e) {
			System.err.println("SQL Exception in text update");
			e.printStackTrace();
		}
		return 0;
	}

	
	/**
	 * delete text by id
	 * @param request: param name=id
	 * @return if success return 1 ,else 0;
	 * @author Muhamad Igbaria
	 */
	public Object delete(Request request){
		
		try {
			return db.texts.deleteById((String) request.getParam("id"));
		} catch (SQLException e) {
			System.err.println("SQL Exception in text delete");
			e.printStackTrace();
		}
		return 0;
	}
	
	
	/*
	 * Other Methods:
	 */

	
	/**
	 * get all texts
	 * @param request
	 * @return: List<DBText> of all texts Or null.
	 * @author Muhamad Igbaria
	 */
	
	public Object getAll(Request request){
		
		List<DBText> texts_list=null;
		try {
			texts_list = db.texts.queryForAll();
		} catch (SQLException e) {
			System.err.println("SQL Exception in get All texts");
			e.printStackTrace();
		}
		
		return texts_list;
	}
	
	/**
	 * get all texts belongs to cluster
	 * @param request :param name =clusterId;
	 * @return : List<DBText> of  texts Or null.
	 * @author Muhamad Igbaria
	 */
	public List<DBText> getByCluster(int clusterID){
		QueryBuilder<DBText, String> qb = db.texts.queryBuilder();
		List<DBText> res=null;
		try {
			res=qb.where().eq("clusterId_id", clusterID).query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	
}
