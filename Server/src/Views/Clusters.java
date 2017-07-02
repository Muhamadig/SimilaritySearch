package Views;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.QueryBuilder;

import DBModels.DBCluster;
import Database.DbHandler;
import Server.Config;
import Utils.Request;

public class Clusters extends View {

	
	DbHandler db = Config.getConfig().getHandler();
	/**
	 * Add new Cluster to database or update if exist
	 * @param request 
	 * @return if success return 1 ,else 0;
	 * @author Muhamad Igbaria
	 */
	
	public Object create(Request request){
	
		DBCluster cluster=(DBCluster) request.getParam("cluster");
		QueryBuilder<DBCluster, Integer> q=db.clusters.queryBuilder();
		
		byte[] cluster_CW=(byte[]) request.getParam("c_CW");
		byte[] cluster_Global=(byte[]) request.getParam("c_global");

		File clustering=new File("Clustering Data Files");
		clustering.mkdirs();
		FileOutputStream f;
		try {
			f = new FileOutputStream("Clustering Data Files"+ File.separator+ cluster.getCommonWordsFV_name());
			f.write(cluster_CW);
			f.close();
			
			f = new FileOutputStream("Clustering Data Files"+ File.separator+ cluster.getGlobalWordsFV_name());
			f.write(cluster_Global);
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
