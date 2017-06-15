package Client;

import java.util.ArrayList;

import Utils.FileManager;
import Utils.Logger;

public class ServerConfig {
	public boolean isDebug = true;
	private String host = "localhost";
	private int port = 10000;
	private FileManager fileManager = new FileManager("");
	private Logger logger = new Logger(isDebug);

	private static ServerConfig instance = new ServerConfig();

	private ServerConfig() {
	}

	public static ServerConfig getConfig() {
		return instance;
	}

//	public void readTextConfig() {
//		try {
//			ArrayList<String> lines = fileManager.readFile("config.txt");
//			String[] ip = lines.get(0).split(":");
//			host = ip[0];
//			port = Integer.parseInt(ip[1]);
//		} catch (Exception e) {
//			host = "localhost";
//			port = 10000;
//			ServerConfig.getConfig().getLogger().exception(e);
//		}
//
//	}

//	public void writeTextConfig() {
//		try {
//			String ip = host + ":" + port;
//			fileManager.writeFile("config.txt", ip);
//		} catch (Exception e) {
//			ServerConfig.getConfig().getLogger().exception(e);
//		}
//	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public FileManager getFileManager() {
		return fileManager;
	}

	public void setFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}

}
