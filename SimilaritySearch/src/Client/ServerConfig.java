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
