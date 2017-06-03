package model;

import java.util.Map.Entry;

public class MyEntry implements Entry<String, Integer> {

	private String key;
	private Integer value;
	
	
	public MyEntry(String key, Integer value) {
		super();
		this.key = key;
		this.value = value;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public Integer getValue() {

		return value;
	}

	@Override
	public Integer setValue(Integer value) {
		this.value=value;
		return value;
	}
	
	@Override
	public String toString() {
		
		return key+"="+value;
	}
	
}

