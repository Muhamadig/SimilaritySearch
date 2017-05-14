package Client;

import java.io.IOException;

import controller.SuperSteps;
import model.FVHashMap;
import model.Language;
import model.Language.Langs;

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
		//temporary
		FVHashMap finalfv= SuperSteps.buildFrequencyVector("PDFs/file1_en.pdf", "pdf", new Language(Langs.ENGLISH));
		System.out.println(finalfv.toString());
		System.out.println(finalfv.size() +"  "+finalfv.getSum());
	}
}