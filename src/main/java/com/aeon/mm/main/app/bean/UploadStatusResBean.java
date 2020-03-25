package com.aeon.mm.main.app.bean;

import java.io.Serializable;

public class UploadStatusResBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
