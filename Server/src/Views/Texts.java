package Views;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
	
	/*
	 * r.addParam("text", dbText);
				r.addParam("clusterID", key);
				r.addParam("fv", finalFV);
	 */
	public Object create(Request request){
		DBText text=(DBText) request.getParam("text");
		int clusterID=(int) request.getParam("clusterID");
		byte[] finalFV=(byte[]) request.getParam("fv");
		
		Clusters clustersView=new Clusters();
		request.addParam("id", clusterID);
		DBCluster cluster=(DBCluster) clustersView.read(request);
		
		text.setClusterId(cluster);
		
		QueryBuilder<DBText, String> q=db.texts.queryBuilder();
		
		FileOutputStream f;
		try {
			f = new FileOutputStream("TextFV"+ File.separator+ text.getFinalFV_name());
			f.write(finalFV);
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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


	public long numOfTexts(int i) throws SQLException {
		QueryBuilder<DBText, String> qb = db.texts.queryBuilder();
		
		return qb.where().eq("clusterId_id", i).countOf();
	}
	
	
}
