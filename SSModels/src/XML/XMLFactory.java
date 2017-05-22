package XML;

public class XMLFactory {

	public final static int SynSetMap=0;
	public final static int WordSetMap=1;
	public final static int RepWordMap=2;
	public final static int FV=3;


	public static XML getXML(int type){
		switch(type){
		case SynSetMap:
			return new SynSetMapXML();
		case WordSetMap: 
			return new WordSetXML();
		case RepWordMap:
			return new RepWordMapXML();
		case FV:
			return new FVXML();
		}
		return null;
	}

}
