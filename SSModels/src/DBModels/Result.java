package DBModels;

import java.io.Serializable;

public class Result implements Serializable{

	private String text_name_ex;
	private byte[] text_file;
	
	
	public Result(String text_name, byte[] text_file) {
		super();
		this.text_name_ex = text_name;
		this.text_file = text_file;
	}


	public String getText_name() {
		return text_name_ex;
	}


	public void setText_name(String text_name) {
		this.text_name_ex = text_name;
	}


	public byte[] getText_file() {
		return text_file;
	}


	public void setText_file(byte[] text_file) {
		this.text_file = text_file;
	}
	
	
}
