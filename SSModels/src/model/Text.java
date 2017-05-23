package model;

public class Text {
	private String name;
	private FVHashMap fv;
	private Integer SW_num;
	private Integer words_num;
	private Integer frequecny_num;

	
	public Text(){
		
	}
	public Text(String name, FVHashMap fv, Integer sW_num, Integer words_num, Integer frequecny_num) {
		super();
		this.name = name;
		this.fv = fv;
		SW_num = sW_num;
		this.words_num = words_num;
		this.frequecny_num = frequecny_num;
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
	
	
}
