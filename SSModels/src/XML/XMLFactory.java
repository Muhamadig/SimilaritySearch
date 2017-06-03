package XML;

public class XMLFactory {

	public final static int SynSetMap=0;
	public final static int WordSetMap=1;
	public final static int RepWordMap=2;
	public final static int FV=3;
	public final static int FVSortedMap=4;
	public final static int FV_ValueSorted=5;

	public final static int HashList =6;



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
		case FVSortedMap:
			return new TreeMap();
		case FV_ValueSorted:
			return new FV_ValueSorted_XML();
		case HashList:
			return new HashList();
		}
		return null;
	}

}
