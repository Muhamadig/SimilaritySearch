package Views;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import com.j256.ormlite.stmt.QueryBuilder;
import DBModels.DBGlobal;
import Database.DbHandler;
import Server.Config;
import Utils.Request;
import XML.XML;
import XML.XMLFactory;
import model.FVValueSorted;

public class Global extends View  {
	DbHandler db = Config.getConfig().getHandler();
	XML valueSortedXML=XMLFactory.getXML(XMLFactory.FV_ValueSorted);

	public Object create(Request request){
		DBGlobal global=(DBGlobal) request.getParam("globalRow");

		QueryBuilder<DBGlobal, String> q=db.global.queryBuilder();

		try {
			if(q.where().idEq(global.getName()).countOf()>0) return db.global.update(global);
			return db.global.create(global);
		} catch (SQLException e) {
			System.err.println("SQL Exception in create new global Row");
			e.printStackTrace();
		}

		return 0;

	}

	public FVValueSorted getVector(String name){
		try {
			switch(name){
			case "global":
				return getGlobalVector();
			case "common": 
				return getCommonVector();
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;


	}

	private FVValueSorted getCommonVector() throws SQLException, IOException {
		QueryBuilder<DBGlobal, String> q=db.global.queryBuilder();
		DBGlobal res=db.global.queryForId("commonFV");
		if(!res.isUpToDate()){
			byte[] cw=res.getVector();
			FileOutputStream f=new FileOutputStream("common.xml");
			f.write(cw);
			f.close();
			
			res.setUpToDate(true);
			db.global.update(res);
		}

		FVValueSorted commonVector=(FVValueSorted) valueSortedXML.Import("common.xml");
		return commonVector;

	}

	private FVValueSorted getGlobalVector() throws SQLException, IOException {
		QueryBuilder<DBGlobal, String> q=db.global.queryBuilder();
		DBGlobal res=db.global.queryForId("globalFV");
		if(!res.isUpToDate()){
			byte[] globaW=res.getVector();
			FileOutputStream f=new FileOutputStream("global.xml");
			f.write(globaW);
			f.close();
			
			res.setUpToDate(true);
			db.global.update(res);		}

		FVValueSorted commonVector=(FVValueSorted) valueSortedXML.Import("global.xml");
		return commonVector;

	}

}
