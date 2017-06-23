package Client;

import java.io.IOException;

import Utils.Request;
import XML.XML;
import XML.XMLFactory;
import controller.DBController;
import controller.Proccessing;
import controller.SuperSteps;
import model.FVHashMap;
import model.Language;
import model.Language.Langs;
import view.ClientApp;
import view.ui.preProcessing.MainApp;

public class Application {

	public static Client WN_Client = null;
	public static Client client = null;

	
	public static void connect() {
		WNSConfig cfg = WNSConfig.getConfig();
		if (WN_Client != null) {
			WN_Client.close();
			WN_Client = null;
		}
		WN_Client = new Client(cfg.getHost(), cfg.getPort());
//		WNSConfig.getConfig().writeTextConfig();
		WN_Client.open();
		
		ServerConfig s_cfg = ServerConfig.getConfig();
		if (client != null) {
			client.close();
			client = null;
		}
		client = new Client(s_cfg.getHost(), s_cfg.getPort());
//		ServerConfig.getConfig().writeTextConfig();
		client.open();
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException {
//		WNSConfig.getConfig().readTextConfig();
//		ServerConfig.getConfig().readTextConfig();
		connect();
//		MainApp.run(WN_Client,client);
//		DBController dbc=DBController.getInstance();
//		dbc.createClusters();
//		dbc.createTexts();
//		dbc.createGlobals("Expanded/results");
		//   <value>PD -v- The Minister for Justice and Equality &amp; Ors.html.xml</value> cluster 0
//		ClientApp.run();
		
		Request r=new Request("search/search");
		FVHashMap fv=(FVHashMap) XMLFactory.getXML(XMLFactory.FV).Import("FVs/Director of Public Prosecutions -v- Awode.html.xml");
		r.addParam("fv",fv);
		client.sendRequest(r);
	}
}
