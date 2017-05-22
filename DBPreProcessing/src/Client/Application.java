package Client;

import java.io.IOException;

import controller.Proccessing;
import controller.SuperSteps;
import model.FVHashMap;
import model.Language;
import model.Language.Langs;
import view.PreProccessing;

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
		//temporary///HTMLs/A.O. -v- Refugee Appeals Tribunal & ors.html
//		FVHashMap finalfv= SuperSteps.buildFrequencyVector("Vodafone GMBH -v- IV International Leasing & Anor.html", "html", new Language(Langs.ENGLISH));
//		System.out.println(finalfv.toString());
//		System.out.println(finalfv.size() +"  "+finalfv.getSum());
		
		PreProccessing view=new PreProccessing();
//		Proccessing p=new Proccessing(view);
//		view.setVisible(true);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
        		view.setVisible(true);
            }
        });
	}
}
