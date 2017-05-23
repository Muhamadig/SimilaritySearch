package Client;

import java.io.IOException;

import XML.XML;
import XML.XMLFactory;
import controller.Proccessing;
import controller.SuperSteps;
import model.FVHashMap;
import model.Language;
import model.Language.Langs;
import view.LineChart_AWT;
import view.MainApp;
//import view.PreProccessing;

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
	}
}
