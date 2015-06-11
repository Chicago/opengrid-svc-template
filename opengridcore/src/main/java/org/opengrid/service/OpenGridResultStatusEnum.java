package org.opengrid.service;

public enum OpenGridResultStatusEnum {

	//will add more to this later
	SUCCESS(1,"OK"),
	FAIL(2,"FAIL"),
	NO_DATA_RETURNED(3,"NO DATA");
	
	private int code;
	private String status;
	
	OpenGridResultStatusEnum(int code, String status) {
		this.code = code;
		this.status = status;
	}
	public int getCode() {
		return code;
	}
	public String getStatus() {
		return status;
	}
}
