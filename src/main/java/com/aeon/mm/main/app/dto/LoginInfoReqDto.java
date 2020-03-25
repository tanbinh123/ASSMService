package com.aeon.mm.main.app.dto;

import java.io.Serializable;

public class LoginInfoReqDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5518071611648355147L;

	private String loginID;

	private String loginPassword;

	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

}
