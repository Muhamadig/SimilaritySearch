package model;

public class Text {
	private String name;
	private FVHashMap fv;
	private Integer SW_num;
	private Integer words_num;
	private Integer frequecny_num;
	private String fv_xml_file_path;
	private String type;
	private FVKeySortedMap sortedMap;
	private FVKeySortedMap sortedMapXmlPath;

	public Text(String name, Integer words_num, Integer frequecny_num, String type, FVKeySortedMap sortedMap,
			FVKeySortedMap sortedMapXmlPath) {
		super();
		this.name = name;
		this.words_num = words_num;
		this.frequecny_num = frequecny_num;
		this.type = type;
		this.setSortedMap(sortedMap);
		this.setSortedMapXmlPath(sortedMapXmlPath);
	}

	public Text(){
		
	}
	
	public Text(String name, FVHashMap fv, Integer sW_num, Integer words_num, Integer frequecny_num,
			String fv_xml_file_path,String type) {
		super();
		this.name = name;
		this.fv = fv;
		SW_num = sW_num;
		this.words_num = words_num;
		this.frequecny_num = frequecny_num;
		this.fv_xml_file_path=fv_xml_file_path;
		this.type=type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public FVHashMap getFv() {
		return fv;
	}
	public void setFv(FVHashMap fv) {
		this.fv = fv;
	}
	public Integer getSW_num() {
		return SW_num;
	}
	public void setSW_num(Integer sW_num) {
		SW_num = sW_num;
	}
	public Integer getWords_num() {
		return words_num;
	}
	public void setWords_num(Integer words_num) {
		this.words_num = words_num;
	}
	public Integer getFrequecny_num() {
		return frequecny_num;
	}
	public void setFrequecny_num(Integer frequecny_num) {
		this.frequecny_num = frequecny_num;
	}
	public String getFv_xml_file_path() {
		return fv_xml_file_path;
	}
	public void setFv_xml_file_path(String fv_xml_file_path) {
		this.fv_xml_file_path = fv_xml_file_path;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public FVKeySortedMap getSortedMap() {
		return sortedMap;
	}

	public void setSortedMap(FVKeySortedMap sortedMap) {
		this.sortedMap = sortedMap;
	}

	public FVKeySortedMap getSortedMapXmlPath() {
		return sortedMapXmlPath;
	}

	public void setSortedMapXmlPath(FVKeySortedMap sortedMapXmlPath) {
		this.sortedMapXmlPath = sortedMapXmlPath;
	}
	
	
}
