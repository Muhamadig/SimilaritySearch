package Views;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import models.Dispatcher;
//import models.Doctor;
//import models.Labratorian;
//import models.Manager;
//import models.Secretary;
//import models.User;
import Database.DbHandler;
import Server.Config;
import Utils.Request;

public class Users extends View {
	/**
	 * dictionary of the connected users
	 */
	private static HashMap<String, String> connectedUsers = new HashMap<String, String>();

	public static void clientDisconnected(String ip) {
		for (String key : connectedUsers.keySet()) {
			if (connectedUsers.get(key).equalsIgnoreCase(ip)) {
				Config.getConfig().getLogger().debug("Disconnected online user : " + key + " IP:" + ip);
				connectedUsers.remove(key);
			}
		}
	}
}
