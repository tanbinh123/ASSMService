package com.aeon.mm.main.app.bean;

import java.io.Serializable;

public class MessagePrivacyInfoReqBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5518071611648355147L;

	
	private String loginId;

	private String loginPassword;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

}
