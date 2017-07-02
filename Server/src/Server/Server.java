package Server;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import com.j256.ormlite.logger.LocalLog;
import Database.DbHandler;
import Utils.Logger;
import Utils.Request;
import Views.Texts;
import Views.Users;
import ocsf.server.*;
/**
 * Server class extends AbstractServer
 * The main server point
 * @author aj_pa
 *
 */
public class Server extends AbstractServer {
	private Logger logger;
	private Router router;
	/**
	 * Server constructor
	 * @param  port : the port to initialize the server on.
	 */

	public Server(int port) {
		super(port);
		this.logger = Config.getConfig().getLogger();
		this.router = new Router();
		logger.info("[Similarity Search project server]");
		logger.info("Starting local TCP server");
	}
	/**
	 * Prints the status of the server.
	 */
	protected void printStatus() {
		logger.info("-----------------------");
		logger.info("     Server status");
		logger.info("-----------------------");
		logger.info("[Server is " + (this.isListening() == true ? "online" : "offline"));
		logger.info("[Port " + this.getPort());
		logger.info("[Clients connected " + this.getNumberOfClients());
		logger.info("[Connected to database : " + Config.getConfig().getDbUrl());
	}
	/**
	 * ServerStarted handler
	 */
	protected void serverStarted() {
		printStatus();
	}
	/**
	 * clientConnected handler
	 * @param client
	 */
	protected void clientConnected(ConnectionToClient client) {
		logger.info("New client connected: " + client.getInetAddress() + ", total : " + this.getNumberOfClients());
	}
	/**
	 * Server exception hook handler
	 * @param  client
	 */
	synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
		logger.info("Client disconnected: " + client.getIp() + ", total : " + this.getNumberOfClients());
		Users.clientDisconnected(client.getIp());
	}
	/**
	 * Client Disconnected hook handler
	 * @param client
	 */
	synchronized protected void clientDisconnected(ConnectionToClient client) {
		logger.info("Client unexpectedly disconnected: " + client.getIp() + ", total : " + this.getNumberOfClients());
		Users.clientDisconnected(client.getIp());
	}
	/**
	 * Server stopped hook handler
	 */
	protected void serverStopped() {
		sendToAllClients("Server Closed");
		logger.error("SERVER STOPPED..");
		printStatus();
	}

	/**
	 * handles the request from clients. and sends them to the router
	 * @param  message : the request
	 * @param  client : the client who sent the request.
	 */
	
	protected void handleMessageFromClient(Object message, ConnectionToClient client) {
		Request request = (Request) message;
		request.addParam("ip", client.getInetAddress().getHostAddress());
		logger.info("\n[REQUEST] from " + client.getInetAddress() + " : " + request.getUrl() + " "
				+ request.getParams().toString());
		try {
			client.sendToClient(router.resolve((Request) request));
		} catch (IOException e) {
			logger.error("Response not sent");
		}
	}

	/**
	 * Main entry point of the program. it configures the server and start listening
	 * @param args
	 * @throws IOException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws IOException, SQLException, ParseException {
		Config cfg = Config.fromArgs(args);
		if (!cfg.isDebug())
			System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "INFO");
		cfg.setHandler(new DbHandler(cfg.getDbUrl(), cfg.getUser(), cfg.getDbPassword()));

		Server server = new Server(cfg.getPort());

		server.listen();
		
		(new Texts()).getFVsBYCluster(3);
	}
}
