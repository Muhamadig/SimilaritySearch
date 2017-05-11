package Server;

import dictionary.Stemming;
import model.RepWordMap;
import model.SynSetMap;

public class WordNetHandler {
	
	private static SynSetMap synonymsMap;
	private static RepWordMap repWordMap;
	private static Stemming stemming;
	
	public static SynSetMap getSynonymsMap() {
		return synonymsMap;
	}
	public static void setSynonymsMap(SynSetMap synMap) {
		synonymsMap = synMap;
	}
	public static RepWordMap getRepWordMap() {
		return repWordMap;
	}
	public static void setRepWordMap(RepWordMap repWord) {
		repWordMap = repWord;
	}
	public static Stemming getStemming() {
		return stemming;
	}
	public static void setStemming(Stemming _stemming) {
		stemming = _stemming;
	}
	
	
	
}
