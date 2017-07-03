package Client;

import java.io.IOException;

import view.ui.preProcessing.Configure;

public class Application {

	public static Client WN_Client = null;
	public static Client client = null;

	
	public static void connect() throws IOException {
		
		WNSConfig cfg = WNSConfig.getConfig();
		if (WN_Client != null) {
			WN_Client.close();
			WN_Client = null;
		}
		WN_Client = new Client(cfg.getHost(), cfg.getPort());
		WN_Client.open();
		
		ServerConfig s_cfg = ServerConfig.getConfig();
		if (client != null) {
			client.close();
			client = null;
		}
		client = new Client(s_cfg.getHost(), s_cfg.getPort());
		client.open();
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		WNSConfig wnConfig = WNSConfig.getConfig();
		ServerConfig serverConfig = ServerConfig.getConfig();
		new Configure(wnConfig,serverConfig).setVisible(true);
	}
}
