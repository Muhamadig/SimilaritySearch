package Views;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;

import DBModels.DBCluster;
import Database.DbHandler;
import Server.Config;
import Utils.Request;

public class Clusters extends View {

	
	DbHandler db = Config.getConfig().getHandler();

	/*
	 * CRUD:
	 * Create
	 * Read
	 * Update
	 * Delete
	 */

	/**
	 * Add new Cluster to database or update if exist
	 * @param request 
	 * @return if success return 1 ,else 0;
	 * @author Muhamad Igbaria
	 */
	public Object create(Request request){
	
		DBCluster cluster=(DBCluster) request.getParam("cluster");
		QueryBuilder<DBCluster, Integer> q=db.clusters.queryBuilder();
		try {
			if(q.where().idEq(cluster.getId()).countOf()>0) return db.clusters.update(cluster);
			return db.clusters.create(cluster);
		} catch (SQLException e) {
			System.err.println("SQL Exception in create new cluster");
			e.printStackTrace();
		}
		
		return 0;
		
	}


	/**
	 * get cluster by id
	 * @param request:param name=id
	 * @return: {@link DBCluster} if founded else null.
	 * @author Muhamad Igbaria
	 */
	public Object read(Request request){
		try {
			return db.clusters.queryForId((int)request.getParam("id"));
		} catch (SQLException e) {
			System.err.println("SQL Exception in read cluster by id");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * update exists cluster
	 * @param request
	 * @return :if success return 1 ,else 0;
	 * @author Muhamad Igbaria
	 */
	public Object update(Request request){

		
		DBCluster cluster=new DBCluster((int)request.getParam("id"),(String)request.getParam("commonWords_name") ,
				(boolean)request.getParam("commonWords_upToDate"),(byte[])request.getParam("commonWords") );
		
		try {
			return db.clusters.update(cluster);
		} catch (SQLException e) {
			System.err.println("SQL Exception in cluster update");
			e.printStackTrace();
		}
		return 0;
	}

	
	/**
	 * delete cluster by id
	 * @param request: param name=id
	 * @return if success return 1 ,else 0;
	 * @author Muhamad Igbaria
	 */
	public Object delete(Request request){
		
		try {
			return db.clusters.deleteById((int) request.getParam("id"));
		} catch (SQLException e) {
			System.err.println("SQL Exception in cluster delete");
			e.printStackTrace();
		}
		return 0;
	}
	
	/*
	 * Other Methods:
	 */

	
	/**
	 * get all clusters
	 * @param request
	 * @return: List<{@link DBCluster}> of all clusters Or null.
	 * @author Muhamad Igbaria
	 */
	
	public List<DBCluster> getAll(){
		
		List<DBCluster> clusters_list=null;
		try {
			clusters_list = db.clusters.queryForAll();
		} catch (SQLException e) {
			System.err.println("SQL Exception in get All clusters");
			e.printStackTrace();
		}
		
		
		return clusters_list;
	}
	
}
