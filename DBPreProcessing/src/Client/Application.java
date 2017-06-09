package Client;

import java.io.IOException;

import XML.XML;
import XML.XMLFactory;
import controller.Proccessing;
import controller.SuperSteps;
import model.FVHashMap;
import model.Language;
import model.Language.Langs;
import view.ClientApp;
import view.ui.preProcessing.MainApp;

public class Application {

	public static Client client = null;
	
	public static void connect() {
		Config cfg = Config.getConfig();
		if (client != null) {
			client.close();
			client = null;
		}
		client = new Client(cfg.getHost(), cfg.getPort());
		Config.getConfig().writeTextConfig();
		client.open();
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		Config.getConfig().readTextConfig();
		connect();
		MainApp.run();
		//   <value>PD -v- The Minister for Justice and Equality &amp; Ors.html.xml</value> cluster 0
//		ClientApp.run();
	}
}
