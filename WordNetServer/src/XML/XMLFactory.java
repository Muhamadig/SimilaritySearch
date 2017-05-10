package XML;

public class XMLFactory {

	public final static int SynSetMap=0;
	public final static int WordSetMap=1;
	public final static int RepWordMap=2;

	public static XML getXML(int type){
		switch(type){
		case SynSetMap:
			return new SynSetMapXML();
		case WordSetMap: 
			return new WordSetXML();
		case RepWordMap:
			return new RepWordMapXML();
		}
		return null;
	}

}
