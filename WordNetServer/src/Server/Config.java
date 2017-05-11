package Server;

import Utils.Logger;
/**
 * Class Config, the configuration of the server.
 * @author aj_pa
 *
 */
public class Config {
	private boolean isDebug = true;
	
	
	public boolean isDebug() {
		return isDebug;
	}

	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}

	private int port = 8000;
	
	private Logger logger = new Logger(isDebug);

	private static Config instance = new Config();

	private Config() {
	}

	public static Config getConfig() {
		return instance;
	}

	public static Config fromArgs(String[] args) {
		Config cfg = Config.getConfig();

		if (args.length == 0)
			return cfg;

		int port = Integer.parseInt(args[0]);

		if (args.length > 0 && port != 0)
			cfg.setPort(port);


		cfg.printConfig();

		return cfg;
	}

	public void printConfig() {

		logger.debug("[CONFIGURATION]");
		logger.debug("\t|PORT : " + port);
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
}
